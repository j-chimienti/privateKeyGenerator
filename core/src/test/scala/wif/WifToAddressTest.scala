package wif

import org.scalatest.FunSuite

class WifToAddressTest extends FunSuite {

  test("wif2Address") {

    // exported from the bitcoin client running in testnet mode
    val address = "mhW1BQDyhbTsnHEuB1n7yuj9V81TbeRfTY"
    val privateKey = "cRp4uUnreGMZN8vB7nQFX6XWMHU5Lc73HMAhmcDEwHfbgRS66Cqp"

    val computedAddress = WifToAddress.wif2Address(privateKey, testnet = true)
    assert(computedAddress === address)
  }
}
