package cli

import java.io.File

import scopt.{OParser, OParserBuilder}
import wif.RegexHelper.{nonDigitsRegex, validateBinaryOpt}

case class Config(binary: String = "",
                  compress: Boolean = false,
                  testnet: Boolean = false,
                  address: Boolean = false,
                  natoFormat: Boolean = false,
                  file: File = new File("."),
                  out: File = new File("."))

object Config {

  val builder: OParserBuilder[Config] = OParser.builder[Config]
  val parser: OParser[Unit, Config] = {
    import builder._
    OParser.sequence(
      programName("wif"),
      head("wif", "0.1.2"),
      help("help"),
      version("version"),
      opt[Unit]('c', "compress")
        .action((_, c) => c.copy(compress = true))
        .text("compress WIF"),
      opt[Unit]('t', "testnet")
        .action((_, c) => c.copy(testnet = true))
        .text("testnet WIF"),
      opt[Unit]('a', "address")
        .valueName("Output address")
        .action((_, c) => c.copy(address = true)),
      opt[Unit]('n', "NATO format")
        .text("output NATO format for human readable")
        .action((_, c) => c.copy(natoFormat = true)),
      opt[File]('f', "file")
        .valueName("<file>")
        .text("read binary from file")
        .action((x, c) => c.copy(file = x)),
      opt[File]('o', "out")
        .valueName("<file>")
        .action((x, c) => c.copy(out = x))
        .text("write WIF to file"),
      arg[String]("binary")
        .optional()
        .action((x, c) => c.copy(binary = nonDigitsRegex.replaceAllIn(x, "")))
        .text("256 bit binary string 001010...")
        .validate(x => {
          if (validateBinaryOpt(x).isDefined) success
          else failure(s"Invalid binary")
        })
    )
  }

}
