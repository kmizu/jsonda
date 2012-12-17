package com.github.kmizu.jsonda
package dsl

import blueeyes.json._

class BlueeyesDSL extends JsondaDSL {
  type JsonValueType = JValue
  type JsonInt = JNumLong
  type JsonString = JString
  type JsonBool = JBool
  type JsonDouble = JNum
  type JsonObject = JObject
  type JsonArray = JArray

  class PJSON(override val underlying: JsonValueType) extends super[JsondaDSL].PJSON(underlying) {

    def dump(compress: Boolean = false): String = if (compress) {
      underlying.renderCompact
    } else {
      underlying.renderPretty
    }
  }

  implicit def null2JsonNull(mnull: Null): JsonValueType = JNull

  implicit def int2JInt(arg: Int): JsonInt = JNumLong(arg)

  implicit def long2JInt(arg: Long): JsonInt = JNumLong(arg)

  implicit def string2JString(arg: String): JsonString = JString(arg)

  implicit def boolean2JBool(arg: Boolean): JsonBool = JBool(arg)

  implicit def float2JDouble(arg: Float): JsonDouble = JNum(arg)

  implicit def double2JDouble(arg: Double): JsonDouble = JNum(arg)

  implicit def bigInt2JString(arg: BigInt): JsonString = JString(arg.toString())

  implicit def bigDecimal2String(arg: BigDecimal): JsonString = JString(arg.toString())

  implicit def pimpJsonAST(arg: JValue): PJSON = new PJSON(arg)

  def constructJsonObject() = JObject(values.value.map{case (k, v) => JField(k, v)})

  val JsonNull: JsonValueType = JNull

  def $(elements: JsonValueType*): JsonArray = JArray(elements.toList)
}

object BlueeyesDSL extends BlueeyesDSL
