package com.github.kmizu.jsonda

/**Represents marker trait that types extending [[com.github.kmizu.jsonda.PimpedType[ T]] are just for "extension method" pattern.
 */
trait PimpedType[T] {
  /**The instance that extended by PimpedType[T]
   */
  val underlying: T
}