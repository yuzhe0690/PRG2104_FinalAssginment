package ch.makery.address.model

class Circle(shape: String) extends Shape(shape){
  override def shapeDetail(): String = { // override method to format shape name to capitalize first alphabet
    val temp = shape.charAt(0).toUpper + shape.substring(1)
    temp
  }
}
