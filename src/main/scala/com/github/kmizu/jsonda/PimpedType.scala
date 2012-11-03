package com.github.kmizu.jsonda

/**Represents marker trait that types extending [[com.github.kmizu.jsonda.PimpedType]] are just for "Pimp my library" pattern.
 */
trait PimpedType[T] {
  /**The instance that extended by [[com.github.kmizu.jsonda.PimpedType]]
   */
  val underlying: T
}
