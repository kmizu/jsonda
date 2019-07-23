package com.github.kmizu.jsonda.dsl

import org.specs2.mutable.Specification
import com.github.kmizu.jsonda.dsl.PlayJsonDSL._
import play.api.libs.json._

/**
 * @author Mizushima
 *
 */
class JsondaPlayJsonDSLSpecification extends Specification {
  """%{ "some_key" :- Option(100); "none_key" :- None }""" should {
    val data = %{
      "some_key" :- Option(100)
      "none_key" :- (None:Option[Int])
    }

    """have 100 for "some_key"""" in {
      (data \ "some_key").get must ===(JsNumber(100))
    }

    """have null for "none_key"""" in {
      (data \ "none_key").get must ===(JsNull)
    }
  }

  """%{"int_key" :- 100}""" should {
    val data = %{
      "int_key" :- 100
    }
    """have 100 for "int_key"""" in {
      (data \ "int_key").get must ===(JsNumber(100))
    }
  }

  """%{"long_key" :- 100L}""" should {
    val data = %{
      "long_key" :- 100L
    }
    """have 100 for "long_key"""" in {
      (data \ "long_key").get must ===(JsNumber(100))
    }
  }

  """%{"float_key" :- 1.5F}""" should {
    val data = %{
      "float_key" :- 1.5f
    }
    """have 1.5 for "float_key"""" in {
      (data \ "float_key").get must ===(JsNumber(1.5d))
    }
  }

  """%{"double_key" :- 1.5}""" should {
    val data = %{
      "double_key" :- 1.5
    }
    """have 1.5 for "double_key"""" in {
      (data \ "double_key").get must ===(JsNumber(1.5))
    }
  }

  """%{"boolean_true_key" :- true; "boolean_false_key" :- false }""" should {
    val data = %{
      "boolean_true_key" :- true
      "boolean_false_key" :- false
    }
    """have true for "boolean_true_key"""" in {
      (data \ "boolean_true_key").get must ===(JsBoolean(true))
    }
    """have false for "boolean_false_key"""" in {
      (data \ "boolean_false_key").get must ===(JsBoolean(false))
    }
  }

  """%{"string_key" :- "Hello"}""" should {
    val data = %{
      "string_key" :- "Hello"
    }
    """have "Hello" for "string_key"""" in {
      (data \ "string_key").get must ===(JsString("Hello"))
    }
  }

  """%{"null_key" :- JsonNull }""" should {
    val data = %{
      "null_key" :- JsonNull
    }
    """have null for "null_key"""" in {
      (data \ "null_key").get must ===(JsNull)
    }
  }

  """%{"array_key" :- $(1, 2, 3) }""" should {
    val data = %{
      "array_key" :- $(1, 2, 3)
    }
    """have List(1, 2, 3) for "array_key"""" in {
      (data \ "array_key").get must ===(JsArray(Seq(JsNumber(1), JsNumber(2), JsNumber(3))))
    }
  }

  """%{ "name" :- "Kota Mizushima", "age" :- 33}}}""" should {
    val person = % {
      "name" :- "Kota Mizushima"; "age" :- 33
    }
    """have "Kota Mizushima for "name"""" in {
      (person \ "name").get must ===(JsString("Kota Mizushima"))
    }
    """have 33 for "age"""" in {
      (person \ "age").get must ===(JsNumber(33))
    }
  }

  """%{ "str" :- "a String"; "arr" :- $(1, 2, 3, 4, 5); "obj" :- %{ "x" :- 1; "y" :- 2}}""" should {
    val data = % {
      "str" :- "a String"; "arr" :- $(1, 2, 3, 4, 5); "obj" :- % {
        "x" :- 1; "y" :- 2
      }
    }
    """have "a String" for "str"""" in {
      (data \ "str").get must ===(JsString("a String"))
    }
    """have [1, 2, 3, 4, 5] for "arr"""" in {
      (data \ "arr").get must ===(JsArray(Seq(JsNumber(1), JsNumber(2), JsNumber(3), JsNumber(4), JsNumber(5))))
    }
    """have obj which x == 1 and y == 2""" in {
      val obj = (data \ "obj").get.asInstanceOf[JsonObject]
      (obj \ "x").get must ===(JsNumber(1))
      (obj \ "y").get must ===(JsNumber(2))

    }
  }

  """%{"foo" :- Seq(1, 2, 3); "bar" :- "Seq("a", "b") }""" should {
    val data = % {
      "foo" :- Seq(1, 2, 3)
      "bar" :- Seq("a", "b")
    }
    """have [1, 2, 3] for "foo"""" in {
      (data \ "foo").get must ===(JsArray(Seq(JsNumber(1), JsNumber(2), JsNumber(3))))
    }
    """have ["a", "b"] for "bar"""" in {
      (data \ "bar").get must ===(JsArray(Seq(JsString("a"), JsString("b"))))
    }
  }

}

