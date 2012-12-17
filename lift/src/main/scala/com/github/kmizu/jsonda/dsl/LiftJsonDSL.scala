package com.github.kmizu.jsonda.dsl

import com.github.kmizu.jsonda.JsondaDSL

import net.liftweb.json.JsonAST._
import net.liftweb.json.pretty
import net.liftweb.json.compact
import net.liftweb.json.render

/**
 * @author Kota Mizushima
 * Date: 2012/11/05
 * Time: 22:26
 */
class LiftJsonDSL extends JsondaDSL {
  type JsonValueType = JValue
  type JsonInt = JInt
  type JsonString = JString
  type JsonBool = JBool
  type JsonDouble = JDouble
  type JsonObject = JObject
  type JsonArray = JArray

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
      compact(render(underlying))
    } else {
      pretty(render(underlying))
    }
  }

  implicit def null2JsonNull(mnull: Null): JsonValueType = JNull

  implicit def int2JInt(arg: Int): JsonInt = JInt(arg)

  implicit def long2JInt(arg: Long): JsonInt = JInt(arg)

  implicit def string2JString(arg: String): JsonString = JString(arg)

  implicit def boolean2JBool(arg: Boolean): JsonBool = JBool(arg)

  implicit def float2JDouble(arg: Float): JsonDouble = JDouble(arg)

  implicit def double2JDouble(arg: Double): JsonDouble = JDouble(arg)

  implicit def bigInt2JString(arg: BigInt): JsonString = JString(arg.toString())

  implicit def bigDecimal2String(arg: BigDecimal): JsonString = JString(arg.toString())

  implicit def pimpJsonAST(arg: JValue): PJSON = new PJSON(arg)

  def constructJsonObject() = JObject(values.value.map{case (k, v) => JField(k, v)})

  val JsonNull: JsonValueType = JNull

  def $(elements: JsonValueType*): JsonArray = JArray(elements.toList)
}

object LiftJsonDSL extends LiftJsonDSL