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

  // tests from programmingbitcoin
  test("wifs") {

    val list = List(
      (
        BigInt(
          "115792089237316194620101962879192770082288938495059262778356087116516711989248"
        ), // ffffffffffffff80000000000000000000000000000000000000000000000000
        "L5oLkpV3aqBJ4BgssVAsax1iRa77G5CVYnv9adQ6Z87te7TyUdSC",
        (true, false)
      ),
      (
        BigInt(
          "115792089237316192209694896490707356769345799983315358995051596442327459037184"
        ), // fffffffffffffe00000000000000000000000000000000000000000000000000",
        "93XfLeifX7Jx7n7ELGMAf1SUR6f9kgQs8Xke8WStMwUtrDucMzn",
        (false, true)
      ),
      (
        BigInt(
          "0dba685b4511dbd3d368e5c4358a1277de9486447af7b3604a69b8d9d8b7889d",
          16
        ),
        "5HvLFPDVgFZRK9cd4C5jcWki5Skz6fmKqi1GQJf5ZoMofid2Dty",
        (false, false)
      ),
      (
        BigInt(
          "1cca23de92fd1862fb5b76e5f4f50eb082165e5191e116c18ed1a6b24be6a53f",
          16
        ),
        "cNYfWuhDpbNM1JWc3c6JTrtrFVxU4AGhUKgw5f93NP2QaBqmxKkg",
        (true, true)
      )
    )

    for ((num, expected, (compressed, testnet)) <- list) {

      val pk = ByteVector.fromValidHex(num.toString(16))
      assert(PrivateKeyToWif.wif(pk, compressed, testnet) == expected)

    }
  }
}
