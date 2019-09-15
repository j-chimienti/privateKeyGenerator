package wif

import java.io.{BufferedWriter, FileWriter}

import scodec.bits.ByteVector
import scopt.OParser
import wif.Config._
import wif.HashHelper._
import wif.RegexHelper._

import scala.io.Source

object PrivateKeyToWif extends App {

  def wif(secret: ByteVector32,
          compressed: Boolean = false,
          testnet: Boolean = false): String = {

    val prefix = if (testnet) 0xef.toByte else 0x80.toByte
    val suffix = if (compressed) Some(0x01.toByte) else None
    val extendedKey = secret.toArray.+:(prefix) ++ suffix
    val finalKey = extendedKey ++ hash256(ByteVector(extendedKey))
      .take(4)
      .toArray
    ByteVector(finalKey).toBase58
  }

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
          wif(binary2ByteVector32(binary), config.compress, config.testnet)
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
