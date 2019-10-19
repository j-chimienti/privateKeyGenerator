package wif

import fr.acinq.bitcoin.Base58.Prefix
import fr.acinq.bitcoin.{Base58Check, Crypto}
import fr.acinq.bitcoin.Crypto.PrivateKey

object WifToAddress {

  def wif2Address(wif: String, testnet: Boolean = false): String = {

    val (version, data) = Base58Check.decode(wif)
    val priv = PrivateKey(data)
    val publicKey = priv.publicKey
    val computedAddress = Base58Check.encode(
      if (testnet) Prefix.PubkeyAddressTestnet else Prefix.PubkeyAddress,
      Crypto.hash160(publicKey.toBin))

    computedAddress
  }
}
