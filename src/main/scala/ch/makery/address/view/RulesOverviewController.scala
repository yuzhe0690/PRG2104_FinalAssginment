package ch.makery.address.view

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class RulesOverviewController(private val backToMenu: Button) {
  var dialogStage:Stage = null

  def handleReturnMenuOverview(event: ActionEvent): Unit = {
    dialogStage.close() // return to menu
  }
}
