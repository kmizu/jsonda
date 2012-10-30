package com.github.kmizu.jsonda.parser

/**== Overview ==
 * I have the plan to yet another JSON Parser which behavior is customized
 * easily.  For example, I think that it is better to provide the mechanism
 * that users can register custom handlers.  If there exists,  library users
 * can interpret each JSON data as you like.
 * However, the implementation is not started yet.
 */
trait JsondaParser[+A, I] {
  def apply(input: I): A
}