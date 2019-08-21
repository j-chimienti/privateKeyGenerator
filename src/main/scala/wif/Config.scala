package wif

case class Config(binaryString: String = "",
                  compressed: Boolean = false,
                  verbose: Boolean = false,
                  testnet: Boolean = false)
