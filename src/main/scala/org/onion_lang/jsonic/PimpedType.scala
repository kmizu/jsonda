package org.onion_lang.jsonic

/** Represents marker trait that types extending [[org.onion_lang.jsonic.PimpedType[T]] are just for "extension method" pattern.
 */
trait PimpedType[T] {
  /** The instance that extended by PimpedType[T]
   */
  val self: T
}