package wif

import scodec.bits._

import scala.language.implicitConversions

/**
  * see https://en.bitcoin.it/wiki/Protocol_specification
  */
case class ByteVector32(bytes: ByteVector) {
  require(bytes.size == 32, s"size must be 32 bytes, is ${bytes.size} bytes")

  def reverse: ByteVector32 = ByteVector32(bytes.reverse)

  override def toString: String = bytes.toHex
}

object ByteVector32 {
  val Zeroes = ByteVector32(
    hex"0000000000000000000000000000000000000000000000000000000000000000"
  )
  val One = ByteVector32(
    hex"0100000000000000000000000000000000000000000000000000000000000000"
  )

  def fromValidHex(str: String) = ByteVector32(ByteVector.fromValidHex(str))

  implicit def byteVector32toByteVector(h: ByteVector32): ByteVector = h.bytes
}
