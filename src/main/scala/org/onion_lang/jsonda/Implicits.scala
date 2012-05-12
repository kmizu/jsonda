package org.onion_lang.jsonda
import scala.util.DynamicVariable
import net.liftweb.json.JsonAST

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
   */
  class BinderP(override val self: String) extends PimpedType[String] {
    
    /**
     * If this method is called inside % method call such as followings:
     * {{{
     *   % {
     *     '$key :- $value
     *   }
     * }}}
     * then, the ("$key", $value) pair is added as object's property.
     * 
     * Note that thie method throws Exceptions when it is called
     * outside of % method call.
     */
    def :-(value: JsonAST.JValue) {
      values.value = JsonAST.JField(self, value) :: values.value
    }
  }
  
  implicit def makeBinderFromString(arg: String): BinderP = new BinderP(arg)
  
  implicit def makeBinderFromSymbol(arg: Symbol): BinderP = new BinderP(arg.name) 
  
  implicit def int2JInt(arg: Int): JsonAST.JInt = JsonAST.JInt(arg)
  
  implicit def string2JString(arg: String): JsonAST.JString = JsonAST.JString(arg)
  
  implicit def boolean2JBool(arg: Boolean): JsonAST.JBool = JsonAST.JBool(arg)
  
  implicit def double2JDouble(arg: Double): JsonAST.JDouble = JsonAST.JDouble(arg)
  
  /**
   * Constructs an object which type is [[net.liftweb.json.JsonAST.JObject].
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
   * The elements of the array are $elements.
   */
  def $(elements: JsonAST.JValue*): JsonAST.JArray = JsonAST.JArray(elements.toList)
}

/**
 * It is shorthand of (new Implicits).
 * You can use Implicits object instead of (new Implicits) as the followings:
 * {{{
 * import org.onion_lang.jsonda.Implicits._
 * }}}
 */
object Implicits extends Implicits