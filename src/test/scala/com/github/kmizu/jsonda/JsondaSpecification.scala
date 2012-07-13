/**
 *
 */
package com.github.kmizu.jsonda

import com.github.kmizu.jsonda.Implicits._
import org.specs2.mutable.Specification
import net.liftweb.json.JsonAST

/**
 * @author Mizushima
 *
 */
class JsondaSpecification extends Specification {
  """%{'name :- "Kota Mizushima", 'age :- 18}}}""" should {
    val person = % {
      'name :- "Kota Mizushima"; 'age :- 28
    }
    """have name "Kota Mizushima""" in {
      (person \\ "name").values must ===("Kota Mizushima")
    }
    """have age 28""" in {
      (person \\ "age").values must ===(28)
    }
  }

  """%{'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- %{ 'x :- 1; 'y :- 2}}""" should {
    val data = % {
      'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- % {
        'x :- 1; 'y :- 2
      }
    }
    """have str "a String""""" in {
      (data \\ "str").values must ===("a String")
    }
    """have arr [1, 2, 3, 4, 5]""" in {
      (data \\ "arr").asInstanceOf[JsonAST.JArray] must ===(JsonAST.JArray(List(1, 2, 3, 4, 5)))
    }
    """have obj which x == 1 and y == 2""" in {
      val obj = (data \\ "obj").asInstanceOf[JsonAST.JObject]
      (obj \ "x").values must ===(1)
      (obj \ "y").values must ===(2)

    }

  }
}