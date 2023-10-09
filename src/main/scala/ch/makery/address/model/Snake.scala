package ch.makery.address.model

import scalafx.beans.property.IntegerProperty

class Snake(val head: Cell, var body: List[SnakeBody]) {
  val cellShape: Shape = new Square("square") // store the shape of the block

  // grow the snake body
  def grow(): Snake = {
    body = body :+ new SnakeBody(head.x, head.y)
    this
  }
}
