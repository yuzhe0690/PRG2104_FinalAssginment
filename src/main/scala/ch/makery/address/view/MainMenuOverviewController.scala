package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuOverviewController(private val playGame: Button, private val howToPlay: Button) {

  def handleAskPlayerNameOverview(event: ActionEvent): Unit = {
    MainApp.showAskPlayerNameOverview() // create dialog window for asking player name scene
  }

  def handleGameruleOverview(event: ActionEvent): Unit = {
    MainApp.showGameruleOverview() // create dialog window for game rules and instruction scene
  }
}
