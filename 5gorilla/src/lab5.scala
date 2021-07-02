import java.io.FileReader
import java.util.Scanner

import scala.collection.mutable.ArrayBuffer


object lab5 {
  val missmatch = -4
  var nbrOfTests = 0
  var charecters = Vector[String]()
  var matrix = Array[Array[Int]]()
  var value = Map[Char, Int]()
  var tests = Array[(String, String)]()
  var backTrack = Array[Array[Int]]()


  def load(filePath: String): Unit = {
    //val sc = new Scanner(new FileReader(filePath))
    val sc = new Scanner(System.in)
    charecters = sc.nextLine().split(" ").toVector
    value = charecters.map(x => x.head).zipWithIndex.toMap
    val dim = charecters.length
    matrix = Array.ofDim(dim, dim)

    val numb = ArrayBuffer[Int]()
    var counter = 0
    var counter1 = 0
    while (sc.hasNextInt()) {
      numb.append(sc.nextInt())
      if (numb.length == charecters.length) {
        matrix(counter1) = numb.toArray
        numb.clear()
        counter1 += 1
        counter = 0
      }
      counter += 1
    }
    sc.nextLine()
    nbrOfTests = numb.toVector.head
    tests = new Array[(String, String)](nbrOfTests)
    for (i <- 0 until nbrOfTests) {
      val test = sc.nextLine().split(" ").toVector
      tests(i) = (test(0), test(1))
    }
  }

  def clear(): Unit = {
    backTrack = Array[Array[Int]]()
  }


  def findPath(s1: String, s2: String): Unit ={
    var table = Array.fill(s1.length + 1, s2.length + 1)(0)
    for (i <- 0 to s1.length){
      table(i)(0) = i * missmatch
    }
    for (i <- 0 to s2.length){
      table(0)(i) = i * missmatch
    }

    for (i <- 1 to s1.length){
      for (j <- 1 to s2.length) {

        table(i)(j) = math.max( matrix(value(s1.charAt(i-1)))(value(s2.charAt(j - 1))) + table(i - 1)(j - 1), math.max(missmatch + table(i)(j - 1) , missmatch + table(i - 1)(j)))
      }
    }


    val str1 = new StringBuilder
    val str2 = new StringBuilder

    var i = s1.length
    var j = s2.length


    while (i > 0 & j > 0) {
      val score = table(i)(j)
      val scoredigonal = table(i - 1)(j - 1)
      val scoreUp = table(i)(j - 1)
      val scoreSide = table(i - 1)(j)

      if (score == scoredigonal + matrix(value(s1.charAt(i - 1)))(value(s2.charAt(j - 1)))) {
        str1.append(s1.charAt(i - 1))
        str2.append(s2.charAt(j - 1))
        i -= 1
        j -= 1
      } else if (score == scoreUp + missmatch) {
        str1.append("*")
        str2.append(s2.charAt(j - 1))
        j -= 1
      } else if (score == scoreSide + missmatch) {
        str1.append(s1.charAt(i - 1))
        str2.append("*")
        i -= 1
      }
    }

    while(i > 0){
      str1.append(s1.charAt(i - 1))
      str2.append("*")
      i -= 1
    }
    while (j > 0){
      str1.append("*")
      str2.append(s2.charAt(j - 1))
      j -= 1
    }

    println(str1.reverse.toString() ++ " " ++ str2.reverse.toString())
  }





  def main(args: Array[String]): Unit = {
    val lab = lab5
    lab.load("C:\\Users\\DELL\\Desktop\\D2\\algodat\\" +
      "EDAF05-labs-public-master\\5gorilla\\data\\secret\\0mini.in")


    for (i <- tests.indices) {
      lab.clear()
      lab.findPath(tests(i)._1, tests(i)._2)
    }


  }
}


