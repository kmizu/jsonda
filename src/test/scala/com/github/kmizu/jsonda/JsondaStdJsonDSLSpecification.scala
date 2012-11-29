package com.github.kmizu.jsonda

import dsl.StdJsonDSL
import dsl.StdJsonDSL._
import org.specs2.mutable.Specification
import util.parsing.json.JSONObject
import util.parsing.json.JSONArray
import org.junit.runner._
import org.specs2.runner._

/**
 * @author Mizushima
 *
 */
@RunWith(classOf[JUnitRunner])
class JsondaStdJsonDSLSpecification extends Specification {

  """%{ 'some_key :- Option(100); 'none_key :- None }""" should {
    val data = %{
      'some_key :- Option(100)
      'none_key :- None
    }

    """have Option(100) for 'some_key.  Note that it is intended behaviour""" in {
      data.obj("some_key") must ===(Option(100))
    }

    """have None for 'none_key.  Note that it is intended behavior""" in {
      data.obj("none_key") must ===(None)
    }
  }

  """%{'int_key :- 100}""" should {
    val data = %{
      'long_key :- 100
    }
    """have 100 for 'long_key""" in {
      data.obj("long_key") must ===(100)
    }
  }

  """%{'long_key :- 100L}""" should {
    val data = %{
      'long_key :- 100L
    }
    """have 100 for 'int_key""" in {
      data.obj("long_key") must ===(100)
    }
  }

  """%{'float_key :- 1.5F}""" should {
    val data = %{
      'float_key :- 1.5f
    }
    """have 1.5 for 'float_key""" in {
      data.obj("float_key") must ===(1.5F)
    }
  }

  """%{'double_key :- 1.5}""" should {
    val data = %{
      'double_key :- 1.5
    }
    """have 1.5 for 'double_key""" in {
      data.obj("double_key") must ===(1.5)
    }
  }

  """%{'boolean_true_key :- true; boolean_false_key :- false }""" should {
    val data = %{
      'boolean_true_key :- true
      'boolean_false_key :- false
    }
    """have true for 'boolean_true_key""" in {
      data.obj("boolean_true_key") must ===(true)
    }
    """have false for 'boolean_false_key""" in {
      data.obj("boolean_false_key") must ===(false)
    }
  }

  """%{'string_key :- "Hello"}""" should {
    val data = %{
      'string_key :- "Hello"
    }
    """have "Hello" for 'string_key""" in {
      data.obj("string_key") must ===("Hello")
    }
  }

  """%{'array_key :- $(1, 2, 3) }""" should {
    val data = %{
      'array_key :- $(1, 2, 3)
    }
    """have JsonArray(List(1, 2, 3)) for 'array_key""" in {
      data.obj("array_key") must ===(JSONArray(List(1, 2, 3)))
    }
  }

  """%{'name :- "Kota Mizushima", 'age :- 29}}}""" should {
    val person = % {
      'name :- "Kota Mizushima"; 'age :- 29
    }
    """have "Kota Mizushima for 'name""" in {
      person.obj("name") must ===("Kota Mizushima")
    }
    """have 28 for 'age""" in {
      person.obj("age") must ===(29)
    }
  }

  """%{'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- %{ 'x :- 1; 'y :- 2}}""" should {
    val data: StdJsonDSL.JsonObject = % {
      'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- % {
        'x :- 1; 'y :- 2
      }
    }
    """have "a String" for 'str""" in {
      data.obj("str").asInstanceOf[String] must ===("a String")
    }
    """have [1, 2, 3, 4, 5] for 'arr""" in {
      data.obj("arr").asInstanceOf[JSONArray].list must_== List(1, 2, 3, 4, 5)
    }
    """have object which x == 1 and y == 2 for 'obj""" in {
      val obj = data.obj("obj").asInstanceOf[JSONObject]
      obj.obj("x") must ===(1)
      obj.obj("y") must ===(2)
    }
  }
}
