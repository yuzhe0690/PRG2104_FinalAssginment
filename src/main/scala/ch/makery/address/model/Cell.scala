package ch.makery.address.model

import scalafx.scene.paint.Color

class Cell(var x: Double, var y: Double){
  val cellShape: Shape = new Square("square") // set the shape of a cell
  val cellSize = 25 // set the cell size
  var color: Color = Color.Black // set the default color of the cell if it's not overridden

  // method to get the x and y coordinates of the block
  def getPos(): (Double, Double) = (x, y)
}
