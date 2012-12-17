package com.github.kmizu.jsonda

import dsl.LiftJsonDSL

/**== Overview ==
 * Provides a DSL for constructing JSON object(based on [[net.liftweb.json.JsonAST.JValue]]).
 * To use the DSL, the following code is needed for preparation:
 *
 * {{{
 * val builder = new com.github.kmizu.jsonda.Implicits
 * import builder._
 * }}}
 *
 * After the above code, users construct JSON object as the followings:
 * {{{
 * val jsonObject = %{
 *   'name :- "A Person"
 *   'age :- 28
 *   'nick_names :- $("foo", "hoge", "bar")
 * }
 * }}}
 *
 * To prevent pollution of namespace, it is recommended that this DSL is used
 * inside blocks:
 * {{{
 * {
 * val builder = new com.github.kmizu.jsonda.Implicits
 *
 * import builder._
 * val person = %{'name :- "A Person"; 'age :- 28}
 * }
 * }}}
 */
class Implicits extends LiftJsonDSL

/**
 * It is shorthand of (new Implicits).
 * You can use Implicits object instead of (new Implicits) as the followings:
 * {{{
 * import com.github.kmizu.jsonda.Implicits._
 * val jsonObject = %{
 *   'name :- "A Person"
 *   'age :- 28
 * }
 * }}}
 */
object Implicits extends Implicits
