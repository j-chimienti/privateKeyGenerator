package gui

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.HBox
import scalafx.scene.text.Text
import wif.{ByteVector32, PrivateKeyToWif}

object UI {

  def validInput(input: String): Boolean = {

    input.length == 256 && input
      .filter(i => i == '1' || i == '0')
      .length == input.length
  }

  val stage: PrimaryStage = new PrimaryStage {
    //    initStyle(StageStyle.Unified)
    title = "WIF GENERATOR"
    scene = new Scene(600, 600) {

      val testnetCheckbox: CheckBox = new CheckBox("testnet") {
        selected = false
      }
      val compressedCheckbox: CheckBox = new CheckBox("compresssed") {
        selected = false
      }
      val box: HBox = new HBox() {

        children = Seq(
          testnetCheckbox,
          compressedCheckbox
        )
      }

      val privateKeyField: TextArea = new TextArea() {
        //prefColumnCount = 100
        prefHeight = 25
        prefWidth = 300
        promptText = "Enter binary data"

      }

      val errorDisplay: Text = new Text() {
        text = ""
      }

      val wifLabel: Label = new Label("WIF") {

        visible = false
      }
      val wifDisplay: TextField = new TextField() {
        disabled
        text = ""
        visible = false
        prefColumnCount = 40
      }
      val genWifBtn: Button = new Button() {
        text = "Generate WIF"
        onAction = (e: ActionEvent) => {

          errorDisplay.text = ""
          wifDisplay.text = ""

          println(privateKeyField.getText)

          if (validInput(privateKeyField.getText)) {

            val testnet = testnetCheckbox.selected.value
            val compressed = compressedCheckbox.selected.value
            val pk =
              ByteVector32.fromValidHex(
                BigInt(privateKeyField.getText, 2).toString(16))
            val WIF = PrivateKeyToWif.wif(pk, compressed, testnet)

            wifLabel.visible = true
            wifDisplay.visible = true
            wifDisplay.text = WIF
          } else {
            println("invalid input")
            errorDisplay.visible = true
            errorDisplay.text = "Invalid input"

          }

        }
      }

      privateKeyField.text.onChange { (_, _, newValue) =>
        genWifBtn.disable = !validInput(newValue)
      }
      box.layoutX = 20
      box.layoutY = 50
      privateKeyField.layoutX = 20
      privateKeyField.layoutY = 100
      errorDisplay.layoutX = 20
      errorDisplay.layoutY = 175
      genWifBtn.layoutX = 20
      genWifBtn.layoutY = 200
      wifLabel.layoutX = 20
      wifLabel.layoutY = 250
      wifDisplay.layoutX = 20
      wifDisplay.layoutY = 275

      content = List(
        box,
        genWifBtn,
        privateKeyField,
        wifLabel,
        wifDisplay,
        errorDisplay
      )
    }
  }
}
