package wif

import scodec.bits.ByteVector
import HashHelper._
import scopt.OParser

object PrivateKeyToWif extends App {

  def wif(secret: ByteVector,
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

  val builder = OParser.builder[Config]
  val parser1 = {
    import builder._
    OParser.sequence(
      programName("wif"),
      head("wif", "0.1.2"),
      opt[Unit]('t', "testnet")
        .action((_, c) => c.copy(testnet = true))
        .text("use testnet"),
      opt[Unit]('c', "compressed")
        .action((_, c) => c.copy(compressed = true))
        .text("compress WIF"),
      arg[String]("<binaryString>...")
        .unbounded()
        .action((x, c) => c.copy(binaryString = x))
        .required()
        .text("256 bit binary string 001010...")
        .validate(x => {
          val digits = """\\D+""".r.replaceAllIn(x, "")
          """^[01]{256}$""".r.findFirstMatchIn(digits) match {
            case Some(_) => success
            case None    => failure("non binary string")
          }
        })
        .text("256 bit binary string")
    )
  }

  OParser.parse(parser1, args, Config()) match {

    case None =>
    case Some(config) =>
      """^[01]{256}$""".r.findFirstMatchIn(config.binaryString) match {
        case Some(_) =>
          val flipsHex = BigInt(config.binaryString, 2).toString(16)
          val privateKey = ByteVector.fromValidHex(flipsHex)
          val WIF = wif(privateKey)
          println(WIF)
        case _ =>
      }
  }

}
