package cli

import java.io.{BufferedWriter, FileWriter}

import cli.Config._
import scopt.OParser
import wif.HashHelper.binary2ByteVector32
import wif.PrivateKeyToWif
import wif.RegexHelper.validateBinaryOpt

import scala.io.Source

object Main extends App {

  OParser.parse(parser, args, Config()) match {
    case None => // error parsing, help will display
    case Some(config) =>
      try {
        val binary: String =
          if (config.file.toString == ".")
            validateBinaryOpt(config.binary)
              .getOrElse(
                throw new RuntimeException(
                  s"Invalid binary = ${config.binary}"))
          else {
            val source = Source.fromFile(config.file)
            val binRaw = source.mkString("")
            source.close()
            validateBinaryOpt(binRaw).getOrElse(
              throw new RuntimeException(s"Invalid binary = $binRaw"))
          }
        val WIF =
          PrivateKeyToWif.wif(binary2ByteVector32(binary),
                              config.compress,
                              config.testnet)
        if (config.out.toString == ".")
          println(s"WIF = $WIF")
        else {
          val bufferedWriter = new BufferedWriter(new FileWriter(config.out))
          bufferedWriter.write(WIF)
          bufferedWriter.close()
        }
      } catch {
        case e: RuntimeException =>
          System.err.println(e.getMessage)
          System.err.println(OParser.usage(parser))
      }
  }

}
