package wif

import scala.util.matching.Regex

object RegexHelper {

  val bin256Pattern: String = """^[01]{256}$"""
  val bin256Regex: Regex = bin256Pattern.r
  val nonDigitsRegex: Regex = """\D""".r

  def validateBinaryOpt(bin: String): Option[String] =
    nonDigitsRegex.replaceAllIn(bin, "") match {
      case binary if binary.matches(bin256Pattern) =>
        Some(binary)
      case _ => None
    }

}
