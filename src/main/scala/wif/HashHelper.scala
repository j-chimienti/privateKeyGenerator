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

  def sha256(x: ByteVector): ByteVector32 =
    ByteVector32(hash(new SHA256Digest)(x))

  def hash256(input: ByteVector): ByteVector32 =
    sha256(sha256(input))

}
