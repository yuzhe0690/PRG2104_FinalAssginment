package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Button, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class AskPlayerNameOverviewController(
  private val nameField: TextField,
  private val cancel: Button,
  private val ok: Button) {

  var dialogStage: Stage = null
  private var _playerName: String = null

  def handleOk(event: ActionEvent): Unit = {
    if(isInputValid()) { // only perform if inout field is valid
      playerName_=(nameField.text.value) // set the player's name to the input field
      MainApp.showGameOverview(playerName) // call the game scene and pass in the player's name
      dialogStage.close() // close window once game scene closes
    }
  }

  def handleCancel(event: ActionEvent): Unit = {
    dialogStage.close() // return to menu
  }

  // check null in string
  def nullCheck(x: String) = x == null || x.length == 0

  // input field error checking
  def isInputValid(): Boolean = {
    var errorMsg = ""

    if (nullCheck(nameField.text.value))
      errorMsg += "Invalid player name\n"
    if (errorMsg.length == 0)
      return true
    else {
      val alert = new Alert(Alert.AlertType.Error) {
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please enter a valid name"
        contentText = errorMsg
      }.showAndWait()
      return false
    }
  }

  // player name getter and setter
  def playerName = _playerName
  def playerName_=(name: String): Unit = {
    _playerName = name
  }
}
