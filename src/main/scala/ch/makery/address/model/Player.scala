package ch.makery.address.model

class Player(val name: String) {
  // encapsulation
  private var _currentScore = 0
  private var _highestScore = 0

  // current score getter and setter
  def currentScore = _currentScore
  def currentScore_=(newScore: Int): Unit = _currentScore = newScore

  // highest score getter and setter
  def highestScore = _highestScore
  def highestScore_=(newHighScore: Int): Unit = _highestScore = newHighScore

  // player's name getter
  def playerName: String = name
}
