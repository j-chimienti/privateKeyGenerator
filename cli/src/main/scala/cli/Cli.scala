package cli

import java.io.{BufferedWriter, FileInputStream, FileWriter}

import cli.Config._
import scopt.OParser
import wif.HashHelper.binary2ByteVector32
import wif.{PrivateKeyToWif, WifToAddress, WifToNATO}
import wif.RegexHelper.validateBinaryOpt

import scala.io.Source
import scala.util.Try

object Cli {

  def main(args: Array[String]): Unit = {

    OParser.parse(parser, args, Config()) match {
      case None => // error parsing, help will display
      case Some(config) =>
        Try {
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

          var natoFormat: Option[IndexedSeq[String]] = None
          var natoString: Option[IndexedSeq[String]] = None

          if (config.natoFormat) {

            natoFormat = Some(WifToNATO.wifToNATO(WIF))
            println("NATO FORMAT")
            natoString = Some(natoFormat.get.zipWithIndex.map(n => {
              s"${n._2}: ${n._1}"
            }))
            natoString.get.foreach(println)

          }

          if (config.out.toString != ".") {
            val output = natoString match {
              case None       => WIF
              case Some(nato) => s"$WIF\n${nato.mkString("\n")}"
            }
            val bufferedWriter = new BufferedWriter(new FileWriter(config.out))
            bufferedWriter.write(output)
            bufferedWriter.close()
          }
          if (config.address) {
            val addr = WifToAddress.wif2Address(WIF, config.testnet)
            println(s"Address = $addr")
          }
        } recover {
          case e: RuntimeException =>
            System.err.println(e.getMessage)
            System.err.println(OParser.usage(parser))
        }
    }
  }

}
