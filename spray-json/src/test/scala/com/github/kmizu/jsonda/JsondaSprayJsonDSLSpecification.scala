package com.github.kmizu.jsonda

import com.github.kmizu.jsonda.dsl.SprayJsonDSL._
import org.junit.runner._
import org.specs2.mutable.Specification
import org.specs2.runner._
import spray.json._

/**
 * @author Mizushima
 *
 */
@RunWith(classOf[JUnitRunner])
class JsondaSprayJsonDSLSpecification extends Specification {
  """%{ 'some_key :- Option(100); 'none_key :- None }""" should {
    val data = %{
      'some_key :- Option(100)
      'none_key :- None
    }

    """have 100 for 'some_key""" in {
      data.fields("some_key") must ===(JsNumber(100))
    }

    """have null for 'none_key""" in {
      data.fields("none_key") must ===(JsonNull)
    }
  }

  """%{'int_key :- 100}""" should {
    val data = %{
      'long_key :- 100
    }
    """have 100 for 'long_key""" in {
      data.fields("long_key") must ===(JsNumber(100))
    }
  }

  """%{'long_key :- 100L}""" should {
    val data = %{
      'long_key :- 100L
    }
    """have 100 for 'int_key""" in {
      data.fields("long_key") must ===(JsNumber(100L))
    }
  }

  """%{'float_key :- 1.5F}""" should {
    val data = %{
      'float_key :- 1.5f
    }
    """have 1.5 for 'float_key""" in {
      data.fields("float_key") must ===(JsNumber(1.5F))
    }
  }

  """%{'double_key :- 1.5}""" should {
    val data = %{
      'double_key :- 1.5
    }
    """have 1.5 for 'double_key""" in {
      data.fields("double_key") must ===(JsNumber(1.5))
    }
  }

  """%{'boolean_true_key :- true; boolean_false_key :- false }""" should {
    val data = %{
      'boolean_true_key :- true
      'boolean_false_key :- false
    }
    """have true for 'boolean_true_key""" in {
      data.fields("boolean_true_key") must ===(JsBoolean(true))
    }
    """have false for 'boolean_false_key""" in {
      data.fields("boolean_false_key") must ===(JsBoolean(false))
    }
  }

  """%{'string_key :- "Hello"}""" should {
    val data = %{
      'string_key :- "Hello"
    }
    """have "Hello" for 'string_key""" in {
      data.fields("string_key") must ===(JsString("Hello"))
    }
  }

  """%{'null_key :- JsonNull }""" should {
    val data = %{
      'null_key :- JsonNull
    }
    """have null for 'null_key""" in {
      data.fields("null_key") must ===(JsonNull)
    }
  }

  """%{'array_key :- $(1, 2, 3) }""" should {
    val data = %{
      'array_key :- $(1, 2, 3)
    }
    """have List(1, 2, 3) for 'array_key""" in {
      data.fields("array_key") must ===(JsArray(1, 2, 3))
    }
  }

  """%{'name :- "Kota Mizushima", 'age :- 29}}}""" should {
    val person = % {
      'name :- "Kota Mizushima"; 'age :- 29
    }
    """have "Kota Mizushima for 'name""" in {
      person.fields("name") must ===(JsString("Kota Mizushima"))
    }
    """have 29 for 'age""" in {
      person.fields("age") must ===(JsNumber(29))
    }
  }

  """%{'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- %{ 'x :- 1; 'y :- 2}}""" should {
    val data = % {
      'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- % {
        'x :- 1; 'y :- 2
      }
    }
    """have "a String" for 'str""" in {
      data.fields("str") must ===(JsString("a String"))
    }
    """have [1, 2, 3, 4, 5] for 'arr""" in {
      data.fields("arr") must ===(JsArray(1, 2, 3, 4, 5))
    }
    """have obj which x == 1 and y == 2""" in {
      val obj = data.fields("obj").asInstanceOf[JsObject]
      obj.fields("x") must ===(JsNumber(1))
      obj.fields("y") must ===(JsNumber(2))

    }
  }

  """%{'foo :- Seq(1, 2, 3); 'bar :- "Seq("a", "b") }""" should {
    val data = % {
      'foo :- Seq(1, 2, 3)
      'bar :- Seq("a", "b")
    }
    """have [1, 2, 3] for 'foo""" in {
      data.fields("foo") must ===(JsArray(1, 2, 3))
    }
    """have ["a", "b"] for 'bar""" in {
      data.fields("bar") must ===(JsArray("a", "b"))
    }
  }

}

