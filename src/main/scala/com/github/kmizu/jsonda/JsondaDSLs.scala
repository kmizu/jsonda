package com.github.kmizu.jsonda

import util.DynamicVariable

/**
 * @author Kota Mizushima
 * Date: 2012/10/31
 * Time: 0:48
 */
trait JsondaDSLs {
  type JsonValueType
  type JsonInt
  type JsonString
  type JsonBool
  type JsonDouble
  type JsonObject
  type JsonArray

  protected val values = new DynamicVariable[List[(String, JsonValueType)]](null)

  /**
   * A class for extending String methods in  *Pimp my library* pattern".
   * @param underlying String object to be extended
   */
  class PBinder(override val underlying: String) extends PimpedType[String] {
    /**
     * If this method is called inside % method call such as followings:
     * {{{
     *   % {
     *     `key` :- `value`
     *   }
     * }}}
     * then, the (`key`, `value`) pair is added as object's property.
     *
     * Note that this method throws Exceptions when it is called
     * outside of % method call.
     * @param value value corresponds key, which is actually `underlying`.
     */
    def :-(value: JsonValueType) {
      values.value = (underlying, value) :: values.value
    }
  }

  /**
   * "A class for extending JsonAST.JValue in *Pimp my lirary pattern"
   * to add methods for serialization.
   * @param underlying a value of the type to extend.
   * @since 0.0.2
   */
  abstract class PJSON(override val underlying: JsonValueType) extends PimpedType[JsonValueType] {

    /**
     * Dump JsonRootType object as JSON String
     * @param compress if true, dumped JSON is compact version.  Otherwise, the JSON is pretty printed.
     * @return
     * @since 0.0.2
     */
    def dump(compress: Boolean = false): String
  }

  implicit def makeBinderFromString(arg: String): PBinder = new PBinder(arg)

  implicit def makeBinderFromSymbol(arg: Symbol): PBinder = new PBinder(arg.name)

  implicit def int2JInt(arg: Int): JsonInt

  implicit def string2JString(arg: String): JsonString

  implicit def boolean2JBool(arg: Boolean): JsonBool

  implicit def double2JDouble(arg: Double): JsonDouble

  implicit def bigInt2JString(arg: BigInt): JsonString

  implicit def bigDecimal2String(arg: BigDecimal): JsonString

  implicit def pimpJsonAST(arg: JsonValueType): PJSON

  def constructJsonObject(): JsonObject

  /**
   * Constructs an object which type is [[com.github.kmizu.jsonda.JsondaDSLs.JsonObject]].
   * The object is determined by the result of evaluation of body.
   */
  def %(body: => Any): JsonObject = {
    values.withValue(List()) {
      body
      constructJsonObject()
    }
  }

  /**
   * Constructs an object of [[com.github.kmizu.jsonda.JsondaDSLs.JsonArray]].
   * The elements of the array are `elements`.
   * @param elements var-args of [[com.github.kmizu.jsonda.JsondaDSLs.JsonValueType]], which are elements of
   *        [[com.github.kmizu.jsonda.JsondaDSLs.JsonArray]]
   * @return [[com.github.kmizu.jsonda.JsondaDSLs.JsonArray]], which elements is `elements`.
   */
  def $(elements: JsonValueType*): JsonArray
}
