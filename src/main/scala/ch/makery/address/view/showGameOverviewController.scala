package ch.makery.address.view

import ch.makery.address.MainApp
import ch.makery.address.model.{Board, Circle, Food, Player, Shape, Snake, Square}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.beans.property.IntegerProperty
import scalafx.event.ActionEvent
import scalafx.scene.{Parent, Scene}
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label}
import scalafx.scene.input.KeyEvent
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Blue, LightGoldrenrodYellow, LightGreen}
import scalafx.stage.Stage

@sfxml
class showGameOverviewController(
  private val buttonVBox: VBox,
  private val startGame: Button,
  private val gameCanvas: Canvas,
  private val gameVBox: VBox,
  private var nameLabel: Label,
  private var scoreLabel: Label,
  private var highscoreLabel: Label,
  private val exitToMenu: Button) {

  var dialogStage: Stage = null
  var playerName: String = ""

  val gc = gameCanvas.graphicsContext2D
  private var board = new Board
  private val player = new Player(playerName)
  private var gameLoop: AnimationTimer = null
  var direction = IntegerProperty(4)

  // when click start game button
  def handleShowGameOverview(event: ActionEvent): Unit = {
    buttonVBox.setVisible(false)
    gameCanvas.setVisible(true)
    gameVBox.setVisible(true)
    updatePlayerNameLabel()
    updatePlayerScoreLabels()
    startGameLoop()
  }

  // when click return to menu button
  def handleReturnToMenuOverview(event: ActionEvent): Unit = {
    stopGameLoop()
    dialogStage.close()
  }

  // start the game loop
  private def startGameLoop(): Unit = {
    var lastUpdateTime: Long = 0
    gameLoop = AnimationTimer(t => {
      val elapsedTime = t - lastUpdateTime
      if (elapsedTime >= 50000000) {
        board.snake_=(board.moveSnake(direction.value))
        updatePlayerScores()
        updatePlayerScoreLabels()
        println(s"s: ${player.currentScore}")
        println(s"hs: ${player.highestScore}")
        checkGameOver()
        drawGame(board)
        lastUpdateTime = t
      }
    })
    gameLoop.start()
  }

  // stop the game loop
  private def stopGameLoop(): Unit = {
    gameLoop.stop()
  }

  // check if the game is over (snake is dead or not), if game over stop the game loop
  private def checkGameOver(): Unit = {
    if (board.isSnakeDead)
      gameLoop.stop()
  }

  // update the player's scores
  private def updatePlayerScores(): Unit = {
    player.currentScore = board.score
    player.highestScore = board.highScore
  }

  // draw the game
  private def drawGame(board: Board): Unit = {
    gc.clearRect(0, 0, Board.canvasWidth, Board.canvasHeight)
    drawBackground()
    drawFood(board.food)
    drawSnake(board.snake)
  }

  // draw snake
  private def drawSnake(snake: Snake): Unit = {
    drawBlock(snake.head.x, snake.head.y, Blue, snake.cellShape)
    for (body <- snake.body) {
      drawBlock(body.x, body.y, body.color, body.cellShape)
    }
  }

  // draw food
  private def drawFood(food: Food): Unit = {
    drawBlock(food.x, food.y, food.color, food.foodShape)
  }

  // draw background grid
  private def drawBackground(): Unit = {
    for (x <- 0 until Board.canvasWidth.toInt by Board.blockSize) {
      for (y <- 0 until Board.canvasHeight.toInt by Board.blockSize) {
        val color = if ((x / Board.blockSize + y / Board.blockSize) % 2 == 0) LightGreen else LightGoldrenrodYellow
        drawBlock(x, y, color, shape = new Square("square"))
      }
    }
  }

  // draw a block based on the shape of the object
  private def drawBlock(x: Double, y: Double, color: Color, shape: Shape): Unit = shape match {
    // if square shape, draw squares
    case square: Square =>
      gc.setFill(color)
      gc.fillRect(x, y, Board.blockSize, Board.blockSize)
    // if circle shape, draw circles
    case circle: Circle =>
      gc.setFill(color)
      gc.fillOval(x, y, Board.blockSize, Board.blockSize)
    case _ =>
      println("invalid shape")
  }

  // update the player name label
  private def updatePlayerNameLabel(): Unit = {
    nameLabel.text = s"$playerName"
  }

  // update the player score label
  private def updatePlayerScoreLabels(): Unit = {
    scoreLabel.text = s"Score: ${player.currentScore}"
    highscoreLabel.text = s"High Score: ${player.highestScore}"
  }
}