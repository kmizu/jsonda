/**
 *
 */
package org.onion_lang.jsonic
import org.onion_lang.jsonic.Jsonic._
import org.specs2.mutable.Specification
import net.liftweb.json.JsonAST

/**
 * @author Mizushima
 *
 */
class JsonicSpecification extends Specification {
  """a Jsonic %{'name :- "Kota Mizushima", 'age :- 18}}}""" should {
      val person = %{'name :- "Kota Mizushima"; 'age :- 28} 
    """have name "Kota Mizushima""" in {
      (person \\ "name").asInstanceOf[JsonAST.JString].values must ===("Kota Mizushima")
    }
    """have age 28""" in {
      (person \\ "age").asInstanceOf[JsonAST.JInt].values.toInt must ===(28)
    }
  }
}