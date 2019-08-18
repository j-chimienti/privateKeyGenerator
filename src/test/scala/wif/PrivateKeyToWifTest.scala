package wif

import org.scalatest.FunSuite
import scodec.bits.ByteVector

class PrivateKeyToWifTest extends FunSuite {
  test("testWif") {

    // https://bitcointalk.org/index.php?topic=944596.0

    val bin =
      """
        |0010 1000 1000 1111 0011 1001 1011 1011 1111 1101 0011 0110 1101 1010 0101 1010
        |0001 0010 1101 0001 0110 0010 1100 1011 0001 1000 1001 0111 1100 1001 0000 0010
        |0010 1000 1000 1011 0011 1001 1011 1011 0011 0110 1101 1010 0101 1010 0001 0010
        |1101 0001 0110 0010 1100 1011 0001 1000 1001 0111 1100 1001 0000 0010 0011 1101
      """.stripMargin
        .replaceAll("\n", "")
        .replaceAll(" ", "")

    val i = BigInt(bin, 2).toString(16)
    val hexx =
      "288F 39BB FD36 DA5A 12D1 62CB 1897 C902 288B 39BB 36DA 5A12 D162 CB18 97C9 023D"
        .replaceAll(" ", "")
        .toLowerCase()

    assert(hexx == i)
    val pk = ByteVector.fromValidHex(i)
    val WIF = PrivateKeyToWif.wif(pk, compressed = false)

    assert(WIF == "5J89cr5WGdvQWeeekN5ZGzuXVsWREbAYku6MDeUgrJTjX1ZHhCX")
  }

}
