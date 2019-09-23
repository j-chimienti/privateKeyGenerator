package wif

import scodec.bits.ByteVector
import wif.HashHelper._

object PrivateKeyToWif {

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

}
