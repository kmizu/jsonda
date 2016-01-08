package com.github.kmizu.jsonda.dsl

import com.github.kmizu.jsonda.JsondaDSL
import spray.json._

/**
  * Created by kota_mizushima on 2016/01/08.
  */
class SprayJsonDSL extends JsondaDSL {
  type JsonValueType = JsValue
  type JsonInt =  JsNumber
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
  class PJSON(override val underlying: JsValue) extends super[JsondaDSL].PJSON(underlying) {

    /**
      * Dump JsonRootType object as JSON String
      * @param compress if true, dumped JSON is compact version.  Otherwise, the JSON is pretty printed.
      * @return
      * @since 0.0.2
      */
    def dump(compress: Boolean = false): String = if (compress) {
      underlying.compactPrint
    } else {
      underlying.prettyPrint
    }
  }

  /**
    * Constructs an object of [[JsondaDSL.JsonArray]].
    * The elements of the array are `elements`.
    * @param elements var-args of [[com.github.kmizu.jsonda.JsondaDSL.JsonValueType]], which are elements of
    *                 [[com.github.kmizu.jsonda.JsondaDSL.JsonArray]]
    * @return [[com.github.kmizu.jsonda.JsondaDSL.JsonArray]], which elements is `elements`.
    */
  override def $(elements: JsValue*): JsArray = JsArray(elements:_*)

  override def constructJsonObject(): JsObject = JsObject(values.value.toMap)

  override implicit def bigDecimal2String(arg: BigDecimal): JsString = JsString(arg.toString)

  override implicit def null2JsonNull(mnull: Null): JsValue = JsNull

  override implicit def float2JDouble(arg: Float): JsNumber = JsNumber(arg.toDouble)

  override implicit def boolean2JBool(arg: Boolean): JsBoolean = JsBoolean(arg)

  override implicit def pimpJsonAST(arg: JsValue): PJSON = new PJSON(arg)

  override implicit def int2JInt(arg: Int): JsNumber = JsNumber(arg)

  override implicit def double2JDouble(arg: Double): JsNumber = JsNumber(arg)

  override implicit def bigInt2JString(arg: BigInt): JsString = JsString(arg.toString)

  override implicit def toJsonArray[A](arg: Traversable[A])(implicit ev: A => JsValue): JsArray = JsArray(arg.map{e => e:JsValue}.toSeq:_*)

  override implicit def string2JString(arg: String): JsString = JsString(arg)

  override implicit def long2JInt(arg: Long): JsNumber = JsNumber(arg)

  override val JsonNull: JsValue = JsNull
}

object SprayJsonDSL extends SprayJsonDSL
