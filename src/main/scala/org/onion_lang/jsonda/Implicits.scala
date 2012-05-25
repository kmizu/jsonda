package org.onion_lang.jsonda
import scala.util.DynamicVariable
import net.liftweb.json._

/** == Overview ==
 * Provides a DSL for constructing JSON object(based on [[net.liftweb.json.JsonAST.JValue]]).
 * To use the DSL, the following code is needed for preparation:
 * 
 * {{{
 * val builder = new org.onion_lang.jsonda.Implicits
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
 *   val builder = new org.onion_lang.jsonda.Implicits
 *   import builder._
 *   val person = %{'name :- "A Person"; 'age :- 28}
 * }
 * }}}
 */
class Implicits {
  private[this] val values = new DynamicVariable[List[JsonAST.JField]](null)
  
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
    def :-(value: JsonAST.JValue) {
      values.value = JsonAST.JField(underlying, value) :: values.value
    }
  }

  /**
   * "A class for extending JsonAST.JValue in *Pimp my lirary pattern"
   * to add methods for serialization.
   * @param underlying a value of the type to extend.
   * @since 0.0.2
   */
  class PJSON(override val underlying: JsonAST.JValue) extends PimpedType[JsonAST.JValue] {

    /**
     * Dump underlying [[net.liftweb.JsonAST.JValue]] object as JSON String.
     * @param compaction if true, dumped JSON is compact version.  Otherwise, the JSON is pretty printed.
     * @return
     * @since 0.0.2
     */
    def dump(compaction: Boolean=false): String = {
      val source = render(underlying)
      if(compaction) compact(source) else pretty(source)
    }
  }

  implicit def makeBinderFromString(arg: String): PBinder = new PBinder(arg)
  
  implicit def makeBinderFromSymbol(arg: Symbol): PBinder = new PBinder(arg.name) 
  
  implicit def int2JInt(arg: Int): JsonAST.JInt = JsonAST.JInt(arg)
  
  implicit def string2JString(arg: String): JsonAST.JString = JsonAST.JString(arg)
  
  implicit def boolean2JBool(arg: Boolean): JsonAST.JBool = JsonAST.JBool(arg)
  
  implicit def double2JDouble(arg: Double): JsonAST.JDouble = JsonAST.JDouble(arg)

  implicit def pimpJsonAST(arg: JsonAST.JValue): PJSON = new PJSON(arg)
  
  /**
   * Constructs an object which type is [[net.liftweb.json.JsonAST.JObject]].
   * The object is determined by the result of evaluation of body.
   */
  def %(body: => Any): JsonAST.JObject = {
    values.withValue(List()) {
      body
      JsonAST.JObject(values.value)
    }
  }
  
  /**
   * Constructs an object of [net.liftweb.json.JsonAST.JArray].
   * The elements of the array are `elements`.
   * @param elements var-args of [[net.liftweb.json.JsonAST.JValue]], which are elements of [[net.liftweb.json.JsonAST.JArray]]
   * @return [[net.liftweb.json.JsonAST.JArray]], which elements is `elements`.
   */
   def $(elements: JsonAST.JValue*): JsonAST.JArray = JsonAST.JArray(elements.toList)
}

/**
 * It is shorthand of (new Implicits).
 * You can use Implicits object instead of (new Implicits) as the followings:
 * {{{
 * import org.onion_lang.jsonda.Implicits._
 * val jsonObject = %{
 *   'name :- "A Person"
 *   'age :- 28
 * }
 * }}}
 */
object Implicits extends Implicits