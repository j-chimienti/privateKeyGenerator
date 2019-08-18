package wif

import scodec.bits.ByteVector
import HashHelper._

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

  def help = {

    val helpStr =
      """
        | Usage: wif 0010100010001111001110011011101111111101001101101101101001011010000100101101000101100010110010110001100010010111110010010000001000101000100010110011100110111011001101101101101001011010000100101101000101100010110010110001100010010111110010010000001000111101
      """.stripMargin

    println(helpStr)
  }

  // todo: handle args better. Allow flags and support compression and testnet
  args.length match {

    case 1 =>
      val binary = """\\s|\\n|,""".r.replaceAllIn(args.head, "")
      binary.length match {
        case 256 =>
          val flipsHex = BigInt(binary, 2).toString(16)
          val privateKey = ByteVector.fromValidHex(flipsHex)
          val WIF = wif(privateKey)
          println(WIF)
        case invalidLength =>
          println(s"Invalid binary key length = $invalidLength. Must be 256")
          help

      }
    case _ => help
  }

}
