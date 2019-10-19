package wif

import scala.collection.immutable

object WifToNATO {

  val char2Word: Map[Char, String] = Map(
    // lowercase letters
    'a' -> "alfa",
    'b' -> "bravo",
    'c' -> "charlie",
    'd' -> "delta",
    'e' -> "echo",
    'f' -> "foxtrot",
    'g' -> "golf",
    'h' -> "hotel",
    'i' -> "india",
    'j' -> "juliett",
    'k' -> "kilo",
    'l' -> "lima",
    'm' -> "mike",
    'n' -> "november",
    'o' -> "oscar",
    'p' -> "papa",
    'q' -> "quebec",
    'r' -> "romeo",
    's' -> "sierra",
    't' -> "tango",
    'u' -> "uniform",
    'v' -> "victor",
    'w' -> "whiskey",
    'x' -> "x-ray",
    'y' -> "yankee",
    'z' -> "zulu",
    // UPPERCASE letters
    'A' -> "ALFA",
    'B' -> "BRAVO",
    'C' -> "CHARLIE",
    'D' -> "DELTA",
    'E' -> "ECHO",
    'F' -> "FOXTROT",
    'G' -> "GOLF",
    'H' -> "HOTEL",
    'I' -> "INDIA",
    'J' -> "JULIETT",
    'K' -> "KILO",
    'L' -> "LIMA",
    'M' -> "MIKE",
    'N' -> "NOVEMBER",
    'O' -> "OSCAR",
    'P' -> "PAPA",
    'Q' -> "QUEBEC",
    'R' -> "ROMEO",
    'S' -> "SIERRA",
    'T' -> "TANGO",
    'U' -> "UNIFORM",
    'V' -> "VICTOR",
    'W' -> "WHISKEY",
    'X' -> "X-RAY",
    'Y' -> "YANKEE",
    'Z' -> "ZULU",
    //# Numbers
    '0' -> "ZERO",
    '1' -> "ONE",
    '2' -> "TWO",
    '3' -> "THREE",
    '4' -> "FOUR",
    '5' -> "FIVE",
    '6' -> "SIX",
    '7' -> "SEVEN",
    '8' -> "EIGHT",
    '9' -> "NINE"
  )

  val word2Char: Map[String, Char] = char2Word.map(_.swap)

  def char2Nato(char: Char): String =
    char2Word.getOrElse(
      char,
      throw new RuntimeException(s"Unable to locate char: $char"))

  def wifToNATO(wif: String): immutable.IndexedSeq[String] = {

    if (!(wif.length == 51 || wif.length == 52))
      throw new RuntimeException(s"Invalid wif length = ${wif.length}")

    wif.map(char2Nato)
  }

  def fromStdIn = {
    println("ENTER WIF:")
    val wif = scala.io.StdIn.readLine()
    println("Converting WIF TO NATO...")

    val nato = wifToNATO(wif)

    nato.zipWithIndex.foreach(n => {
      println(s"${n._1}: ${n._2}")
    })
  }

}
