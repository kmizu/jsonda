package com.github.kmizu.jsonda.dsl

import com.github.kmizu.jsonda.JsondaDSL

import scala.util.parsing.json._

/**
 * @author Kota Mizushima
 * Date: 2012/11/05
 * Time: 22:26
 */
class StdJsonDSL extends JsondaDSL {
  type JsonValueType = Any
  type JsonInt = Int
  type JsonString = String
  type JsonBool = Boolean
  type JsonDouble = Double
  type JsonObject = JSONObject
  type JsonArray = JSONArray

  /**
   * "A class for extending JSONType in *Pimp my lirary pattern"
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
    def dump(compress: Boolean = false): String = {
      underlying.toString()
    }
  }

  implicit def null2JsonNull(mnull: Null): JsonValueType = null

  implicit def int2JInt(arg: Int): JsonInt = arg

  implicit def long2JInt(arg: Long): JsonInt = arg

  implicit def string2JString(arg: String): JsonString = arg

  implicit def boolean2JBool(arg: Boolean): JsonBool = arg

  implicit def float2JDouble(arg: Float): JsonDouble = arg:Double

  implicit def double2JDouble(arg: Double): JsonDouble = arg

  implicit def bigInt2JString(arg: BigInt): JsonString = arg.toString()

  implicit def bigDecimal2String(arg: BigDecimal): JsonString = arg.toString()

  implicit def pimpJsonAST(arg: JsonValueType): PJSON = new PJSON(arg)

  implicit def toJsonArray[A <% JsonValueType](arg: Traversable[A]): JsonArray = JSONArray(arg.map{e => e: JsonValueType}.toList)

  def constructJsonObject() = JSONObject(values.value.map{case (k, v) => (k, v)}.toMap)

  val JsonNull: JsonValueType = null

  def $(elements: JsonValueType*): JsonArray = JSONArray(elements.toList)
}

object StdJsonDSL extends StdJsonDSL
