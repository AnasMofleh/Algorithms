import scala.io.{Source, StdIn}
import scala.collection.mutable.ArrayBuffer

object lab4 {

  case class Point(x: Int, y: Int)

  private var positionsX: Vector[Point] = Vector[Point]()
  val file = "C:\\Users\\DELL\\Desktop\\D2\\algodat\\EDAF05-labs-public-master\\4closestpair\\data\\secret\\4larger.in"
  var Players = 0

  def load(): Unit = {
    val posX = new ArrayBuffer[Point]()
    val posY = new ArrayBuffer[Point]()

    // val myFile = Source.fromFile(file).getLines()
     val myFile = Source.stdin.getLines()
    Players = myFile.next().toString.stripLineEnd.toInt
    for (line <- myFile) {
      val st = line.stripLineEnd.split(' ')
      val point = new Point(st(0).toInt, st(1).toInt)
      posX.append(point)
      posY.append(point)
    }
    positionsX = posX.sortWith((t1, t2) => t1.x <= t2.x).toVector
  }

  def distance(a: Point, b: Point): Double = {
    math.hypot(a.x - b.x, a.y - b.y)
  }

  def FindClosest(P: Vector[Point], numberOfRuns: Int): Double = {
    var n = 0
    if (numberOfRuns >= P.length) n = P.length
    else n = numberOfRuns
    var min = Double.MaxValue
    for (i <- 0 until n) {
      for (j <- (i + 1) until n) {
        if (distance(P(i), P(j)) < min) min = distance(P(i), P(j))
      }
    }
    min
  }


  def DivideAndConquer(): Double = {
    DivideAndConquer(positionsX, Players)
  }

  private def DivideAndConquer(VX: Vector[Point], players: Int): Double = {
    var goal = 0.0
    if (players <= 3) goal = FindClosest(VX, VX.length)
    else {
      val LeftX  = VX.take(players/2)
      val RightX = VX.drop(players/2)


      val SmallestL = DivideAndConquer(LeftX ,  LeftX.size)
      val SmallestR = DivideAndConquer(RightX,  RightX.size)
      goal = Math.min(SmallestL, SmallestR)

      val Middle = ArrayBuffer[Point]()

      for(i <- VX.indices) {
         if(math.abs(VX(i).x - RightX.head.x) < goal){
          Middle.append(VX(i))
        }
      }

      val MiddleSorted = Middle.sortWith((p1, p2) => p1.y <= p2.y).toVector
      val ShortestMiddle = FindClosest(MiddleSorted, 16)
      goal = math.min(goal, ShortestMiddle)
    }

    goal

  }


  def main(args: Array[String]): Unit = {
    val l = lab4
    val t3 = System.nanoTime()
    l.load()
    val t1 = System.nanoTime()
    printf("%6f\n", l.DivideAndConquer())
    val t2 = System.nanoTime()
   // println("loding time: " + (t1 - t3) + "ns")
   // println("\nElapsed time: " + (t2 - t1) + "ns")

  }

}