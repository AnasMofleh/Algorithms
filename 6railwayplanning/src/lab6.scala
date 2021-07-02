import java.util
import scala.io.{Source, StdIn}

object lab6 {
  private val file = "C:\\Users\\DELL\\Desktop\\D2\\algodat\\EDAF05-labs-public-master\\6railwayplanning\\data\\secret\\4huge.in"
  private var GraphMatrix: Array[Array[Int]] = Array[Array[Int]]()
  private var tests: Array[Int] = Array[Int]()
  private var numberOFNodes = 0
  private var numberOfEdges = 0
  private var numberOfStudent = 0
  private var numberOfRoutes = 0
  private var Edges = Array[(Int, Int, Int)]()

  private def load(source: StdIn.type): Unit = {
    val bufferedSource = Source.fromFile(file).getLines().toArray
    val NMCP = bufferedSource(0).split(" ").map(x => x.toInt)
    numberOFNodes = NMCP(0)
    numberOfEdges = NMCP(1)
    numberOfStudent = NMCP(2)
    numberOfRoutes = NMCP(3)
    GraphMatrix = Array.fill(numberOFNodes)(Array.fill(numberOFNodes)(-1))
    tests = new Array[Int](numberOfRoutes)
    Edges = new Array[(Int, Int, Int)](numberOfEdges)

    for (i <- 1 to numberOfEdges) {
      val line = bufferedSource(i).split(" ").map(x => x.toInt)
      val start = line(0)
      val end = line(1)
      val capacity = line(2)

      Edges(i - 1) = (start,end,capacity)
      GraphMatrix(start)(end) =  capacity
      GraphMatrix(end)(start) = capacity
    }

    for (i <- 1 until numberOfRoutes){
      val indexOfEdge = bufferedSource(i + numberOfEdges).toInt
      tests(i - 1) = indexOfEdge

    }



  }


  private def bfs(graph: Array[Array[Int]], source: Int, sink: Int, parent: Array[Int]): Boolean = {
   if (source >= numberOFNodes || sink >= numberOFNodes || source == sink) return false
    val visited = Array.fill(numberOFNodes)(false)
    val que = new util.LinkedList[Int]()
    que.add(source)
    visited(source) = true
    parent(source) = -1

    while (que.size() != 0) {
      val vrtx = que.poll()
      for (dst <- 0 until numberOFNodes){
        if (!visited(dst) && graph(vrtx)(dst) > 0 ){
          que.add(dst)
          parent(dst) = vrtx
          visited(dst) = true
        }
      }
    }
    visited(sink)
  }


  private def fordFulkerson(graph: Array[Array[Int]], source: Int, sink: Int): Int = {
    val parents = new Array[Int](numberOFNodes)
    val graphCopy = Array.fill(numberOFNodes)(Array.fill(numberOFNodes)(-1))
    var maxFlow = 0

    for (i <- graph.indices){
      for (j <- graph.indices){
        graphCopy(i)(j) = graph(i)(j)
      }
    }

    while (bfs(graphCopy,source, sink, parents)){
      var pathFlow = Int.MaxValue

      var vrtx = sink
      while(vrtx != source){
        var father = parents(vrtx)
        pathFlow = math.min(pathFlow, graphCopy(father)(vrtx))
        vrtx = father
      }
      vrtx = sink

      while(vrtx != source) {
        var father = parents(vrtx)

        graphCopy(father)(vrtx) -= pathFlow
        graphCopy(vrtx)(father) += pathFlow

        vrtx = father
      }
      maxFlow += pathFlow
    }
    maxFlow
  }


  def graphCopy(length: Int): Array[Array[Int]] ={
    val graphCopy = Array.fill(numberOFNodes)(Array.fill(numberOFNodes)(-1))
    var maxFlow = 0

    for (i <- GraphMatrix.indices){
      for (j <- GraphMatrix.indices){
        graphCopy(i)(j) = GraphMatrix(i)(j)
      }
    }


    for (i <- 1 to length){
      val test = tests(i - 1)
      val edge = Edges(test)

      graphCopy(edge._1)(edge._2) = -1
      graphCopy(edge._2)(edge._1) = -1

    }
    graphCopy
  }

  private def printGraphMatrix(G: Array[Array[Int]]): Unit = {
    for (e <- G) {
      e.foreach(x => if ((0 to 9).contains(x)) print("  " + x) else print(" " + x))
      println()
    }
  }

  def binarsearch(): Unit = {
    var goal = 0
    var lo = 0
    var hi = numberOfRoutes
    while ((hi - lo) > 1){
      var mid = (hi + lo)/2
        goal = fordFulkerson(graphCopy(mid), 0, numberOFNodes - 1)
        if (goal >= numberOfStudent) {
          lo = mid
        } else hi = mid
    }
    println(lo + " " + fordFulkerson(graphCopy(lo),0,numberOFNodes - 1))
  }

  def main(args: Array[String]): Unit = {
    lab6.load(scala.io.StdIn)
    lab6.binarsearch()
    val max = lab6.fordFulkerson(graphCopy(0), 0, numberOFNodes -1)

  }

}
