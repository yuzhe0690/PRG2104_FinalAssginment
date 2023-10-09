package ch.makery.address.model

import scalafx.scene.paint.Color

import scala.util.Random

class Food(x: Double, y: Double) extends Cell(x, y){
  color = Color.Red // set the color of the food block
  val foodShape: Shape = new Circle("circle") // set the shape of the food block

  // get random coordinates for the new food
  def randomFood(): Food = {
    // 600 / 25 = 24, canvas is 24x24 blocks
    val randX = Random.nextInt(24) * 25 // get random block between 1 - 24 and multiply by 25 to get actual coordinate
    val randY = Random.nextInt(24) * 25
    val temp =  new Food(randX.toDouble, randY.toDouble)
    temp
  }
}
