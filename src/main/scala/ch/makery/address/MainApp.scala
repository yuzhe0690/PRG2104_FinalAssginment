package ch.makery.address

import ch.makery.address.view.{AskPlayerNameOverviewController, RulesOverviewController, showGameOverviewController}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp
import scalafx.beans.property.IntegerProperty
import scalafx.scene.Scene
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.stage.{Modality, Stage}

import scala.util.Random

object MainApp extends JFXApp{
  // Main Menu scene
  val rootResource = getClass.getResource("view/MainMenu.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val roots = loader.getRoot[jfxs.layout.AnchorPane]

  stage = new PrimaryStage {
    title = "Greedy Snake"
    scene = new Scene() {
      root = roots
    }
  }

  // Game rules and instrcution scene
  def showGameruleOverview(): Unit = {
    val resource = getClass.getResource("view/Rules.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots1 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[RulesOverviewController#Controller]

    val dialog = new Stage() {
      title = "How to Play"
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene() {
        root = roots1
      }
    }
    control.dialogStage = dialog
    dialog.showAndWait()
  }

  // ask for player's name scene
  def showAskPlayerNameOverview(): Unit = {
    val resource = getClass.getResource("view/AskPlayerName.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots1 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[AskPlayerNameOverviewController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene() {
        root = roots1
      }
    }
    control.dialogStage = dialog
    dialog.showAndWait()
  }

  // game scene
  def showGameOverview(name: String): Unit = {
    val resource = getClass.getResource("view/Game.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots1 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[showGameOverviewController#Controller]

    val dialog = new Stage() {
      title = "GreedySnake.exe"
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene() {
        root = roots1

        onKeyPressed = key => {
          val previousDirection = control.direction.value
          if (key.getText != previousDirection) {
            key.getText match {
              case "w" | "i" =>
                control.direction.value = 1
              case "a" | "j" =>
                control.direction.value = 3
              case "s" | "k" =>
                control.direction.value = 2
              case "d" | "l" =>
                control.direction.value = 4
              case _ => println("invalid input")
            }
          }
        }
      }
    }
    control.playerName = name
    control.dialogStage = dialog
    dialog.showAndWait()
  }
}
