package ch.makery.address.model

import ch.makery.address.model.Board.{canvasHeight, canvasWidth}
import scalafx.beans.property.IntegerProperty
import scalafx.scene.paint.Color

import scala.util.Random

class Board {
  // default states of the snake
  private val initialSnakeHead = new Cell(250.0, 200.0)
  private val initialSnakeBody: List[SnakeBody] = List(
    new SnakeBody(225.0, 200.0),
    new SnakeBody(200.0, 200.0)
  )
  // entities
  private var _snake: Snake = new Snake(initialSnakeHead, initialSnakeBody)
  _snake.head.color = Color.Blue
  private var _food: Food = new Food(Random.nextInt(24)*25, Random.nextInt(24)*25)
  private var _isSnakeDead = false

  // scores
  private var _score = 0
  private var _highScore = 0

  def snake = _snake
  def snake_=(newSnake: Snake) = _snake = newSnake
  def isSnakeDead = _isSnakeDead
  def food = _food

  def score = _score
  def score_=(n: Int) = _score = n

  def highScore = _highScore
  def highScore_=(score: Int): Unit = {
    if (score > highScore)
      _highScore = score // only set highscore if socre is greater than highscore
    else
      _highScore = highScore
  }

  // update the scores
  def updateScores(): Unit = {
    score_=(score + 1)
    highScore_=(score)
  }

  // assign a new random food to the food var
  def newFood(): Unit = {
    _food = _food.randomFood()
  }

  // collision detection algorithm
  def isCollided(newHead: Cell): Boolean = {
    // if snake head is out of bounds
    if (newHead.x < 0 || newHead.x > canvasWidth || newHead.y < 0 || newHead.y > canvasHeight) {
      _isSnakeDead = true // mark snake dead
      return true
    // if snake bites its body
    } else if (snake.body.exists(bodyPart => bodyPart.getPos() == newHead.getPos())) {
      _isSnakeDead = true
      return true
    // if snake eats a food
    } else if (newHead.getPos() == food.getPos()) {
      _isSnakeDead = false
      return true
    }
    // if no collision
    _isSnakeDead = false
    return false
  }

  // handle snake's movement
  def moveSnake(direction: Int): Snake = {
    // update the new head's position based on the direction
    val newHead = direction match {
      case 1 => new Cell(_snake.head.x, _snake.head.y - 25) // up
      case 2 => new Cell(_snake.head.x, _snake.head.y + 25) // down
      case 3 => new Cell(_snake.head.x - 25, _snake.head.y) // left
      case 4 => new Cell(_snake.head.x + 25, _snake.head.y) // right
      case _ => _snake.head // invalid input, do nothing
    }

    // check if the new snake head got collision or not
    // if new snake head is out of bounds or snake bites itself
    if (isCollided(newHead) && newHead.getPos() != food.getPos()) {
      score_=(0) //reset the current socre, keep the highest score
      return  new Snake(initialSnakeHead, initialSnakeBody) // return to initial state
    } else if (isCollided(newHead) && newHead.getPos() == food.getPos()) { // if snake eat food
      updateScores() // update score
      newFood() // create and assign new food
      val newSnake = new Snake(newHead, _snake.body :+ new SnakeBody(_snake.head.x, _snake.head.y)) // update snake
      val temp = newSnake.grow() // grow the snake
      return temp
    } else { // if nothing happends
      val newBody = (new SnakeBody(snake.head.x, snake.head.y)) +: snake.body.dropRight(1) // update the snake body
      val temp = new Snake(newHead, newBody)
      return temp
    }
  }
}
// companion objects to store global constants
object Board {
  val canvasWidth = 600.0
  val canvasHeight = 600.0
  val blockSize = 25
}
