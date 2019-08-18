package wif

import org.spongycastle.crypto.Digest
import org.spongycastle.crypto.digests.SHA256Digest
import scodec.bits.ByteVector

object HashHelper {


  def hash(digest: Digest)(input: ByteVector): ByteVector = {
    digest.update(input.toArray, 0, input.length.toInt)
    val out = new Array[Byte](digest.getDigestSize)
    digest.doFinal(out, 0)
    ByteVector.view(out)
  }

  def sha256: ByteVector => ByteVector =
    (x: ByteVector) => ByteVector32(hash(new SHA256Digest)(x))

  /**
    * 256 bits bitcoin hash
    * hash256(input) = SHA256(SHA256(input))
    *
    * @param input array of byte
    * @return the 256 bits BTC hash of input
    */
  def hash256(input: ByteVector): ByteVector32 =
    ByteVector32(sha256(sha256(input)))

  def fixSize(byteVector: ByteVector): ByteVector =
    byteVector.padLeft(32)
}
