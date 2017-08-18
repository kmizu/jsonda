package com.github.kmizu.jsonda.dsl

import com.github.kmizu.jsonda.JsondaDSL

import play.api.libs.json._


/**
 * @author Kota Mizushima
 * Date: 2012/11/05
 * Time: 22:26
 */
class PlayJsonDSL extends JsondaDSL {
  type JsonValueType = JsValue
  type JsonInt = JsNumber
  type JsonString = JsString
  type JsonBool = JsBoolean
  type JsonDouble = JsNumber
  type JsonObject = JsObject
  type JsonArray = JsArray

  /**
   * "A class for extending JsonAST.JValue in *Pimp my lirary pattern"
   * to add methods for serialization.
   * @param underlying a value of the type to extend.
   * @since 0.0.2
   */
  class PJSON(override val underlying: JsonValueType) extends super[JsondaDSL].PJSON(underlying) {

    /**
     * Dump JsonRootType object as JSON String
     * @param compress if true, dumped JSON is compact version.  Otherwise, the JSON is pretty printed.
     * @return
     * @since 0.0.2
     */
    def dump(compress: Boolean = false): String = if (compress) {
      Json.stringify(underlying)
    } else {
      Json.prettyPrint(underlying)
    }
  }

  implicit def null2JsonNull(mnull: Null): JsonValueType = JsonNull

  implicit def int2JInt(arg: Int): JsonInt = JsNumber(arg)

  implicit def long2JInt(arg: Long): JsonInt = JsNumber(arg)

  implicit def string2JString(arg: String): JsonString = JsString(arg)

  implicit def boolean2JBool(arg: Boolean): JsonBool = JsBoolean(arg)

  implicit def float2JDouble(arg: Float): JsonDouble = JsNumber(BigDecimal(arg.toString))

  implicit def double2JDouble(arg: Double): JsonDouble = JsNumber(BigDecimal(arg.toString))

  implicit def bigInt2JString(arg: BigInt): JsonString = JsString(arg.toString())

  implicit def bigDecimal2String(arg: BigDecimal): JsonString = JsString(arg.toString())

  implicit def pimpJsonAST(arg: JsValue): PJSON = new PJSON(arg)

  implicit def toJsonArray[A <% JsonValueType](arg: Traversable[A]): JsonArray = JsArray(arg.map{e => e:JsonValueType}.toList)

  def constructJsonObject() = JsObject(values.value)

  val JsonNull: JsonValueType = JsNull

  def $(elements: JsonValueType*): JsonArray = JsArray(elements.toList)
}

object PlayJsonDSL extends PlayJsonDSL
