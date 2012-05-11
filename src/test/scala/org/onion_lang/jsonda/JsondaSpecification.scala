/**
 *
 */
package org.onion_lang.jsonda
import org.onion_lang.jsonda.Implicits._
import org.specs2.mutable.Specification
import net.liftweb.json.JsonAST

/**
 * @author Mizushima
 *
 */
class JsondaSpecification extends Specification {
  """a Implicits %{'name :- "Kota Mizushima", 'age :- 18}}}""" should {
      val person = %{'name :- "Kota Mizushima"; 'age :- 28} 
    """have name "Kota Mizushima""" in {
      (person \\ "name").asInstanceOf[JsonAST.JString].values must ===("Kota Mizushima")
    }
    """have age 28""" in {
      (person \\ "age").asInstanceOf[JsonAST.JInt].values.toInt must ===(28)
    }
  }
}