package com.github.kmizu.jsonda

import com.github.kmizu.jsonda.dsl.Json4sDSL._
import org.specs2.mutable.Specification
import org.json4s._
import org.junit.runner._
import org.specs2.runner._

/**
 * @author Mizushima
 *
 */
class JsondaJson4sDSLSpecification extends Specification {
  """%{ 'some_key :- Option(100); 'none_key :- None }""" should {
    val data = %{
      'some_key :- Option(100)
      'none_key :- None
    }

    """have 100 for 'some_key""" in {
      (data \ "some_key").values must ===(100)
    }

    """have null for 'none_key""" in {
      (data \ "none_key").values must ===(null)
    }
  }

  """%{'int_key :- 100}""" should {
    val data = %{
      'long_key :- 100
    }
    """have 100 for 'long_key""" in {
      (data \ "long_key").values must ===(100)
    }
  }

  """%{'long_key :- 100L}""" should {
    val data = %{
      'long_key :- 100L
    }
    """have 100 for 'int_key""" in {
      (data \ "long_key").values must ===(100)
    }
  }

  """%{'float_key :- 1.5F}""" should {
    val data = %{
      'float_key :- 1.5f
    }
    """have 1.5 for 'float_key""" in {
      (data \ "float_key").values must ===(1.5F)
    }
  }

  """%{'double_key :- 1.5}""" should {
    val data = %{
      'double_key :- 1.5
    }
    """have 1.5 for 'double_key""" in {
      (data \ "double_key").values must ===(1.5)
    }
  }

  """%{'boolean_true_key :- true; boolean_false_key :- false }""" should {
    val data = %{
      'boolean_true_key :- true
      'boolean_false_key :- false
    }
    """have true for 'boolean_true_key""" in {
      (data \ "boolean_true_key").values must ===(true)
    }
    """have false for 'boolean_false_key""" in {
      (data \ "boolean_false_key").values must ===(false)
    }
  }

  """%{'string_key :- "Hello"}""" should {
    val data = %{
      'string_key :- "Hello"
    }
    """have "Hello" for 'string_key""" in {
      (data \ "string_key").values must ===("Hello")
    }
  }

  """%{'null_key :- JsonNull }""" should {
    val data = %{
      'null_key :- JsonNull
    }
    """have null for 'null_key""" in {
      (data \ "null_key").values must ===(null)
    }
  }

  """%{'array_key :- $(1, 2, 3) }""" should {
    val data = %{
      'array_key :- $(1, 2, 3)
    }
    """have List(1, 2, 3) for 'array_key""" in {
      (data \ "array_key").values must ===(List(1, 2, 3))
    }
  }

  """%{'name :- "Kota Mizushima", 'age :- 29}}}""" should {
    val person = % {
      'name :- "Kota Mizushima"; 'age :- 29
    }
    """have "Kota Mizushima for 'name""" in {
      (person \ "name").values must ===("Kota Mizushima")
    }
    """have 29 for 'age""" in {
      (person \ "age").values must ===(29)
    }
  }

  """%{'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- %{ 'x :- 1; 'y :- 2}}""" should {
    val data = % {
      'str :- "a String"; 'arr :- $(1, 2, 3, 4, 5); 'obj :- % {
        'x :- 1; 'y :- 2
      }
    }
    """have "a String" for 'str""" in {
      (data \ "str").values must ===("a String")
    }
    """have [1, 2, 3, 4, 5] for 'arr""" in {
      (data \ "arr").values must ===(List(1, 2, 3, 4, 5))
    }
    """have obj which x == 1 and y == 2""" in {
      val obj = (data \ "obj").asInstanceOf[JsonAST.JObject]
      (obj \ "x").values must ===(1)
      (obj \ "y").values must ===(2)

    }
  }

  """%{'foo :- Seq(1, 2, 3); 'bar :- "Seq("a", "b") }""" should {
    val data = % {
      'foo :- Seq(1, 2, 3)
      'bar :- Seq("a", "b")
    }
    """have [1, 2, 3] for 'foo""" in {
      (data \ "foo").values must ===(List(1, 2, 3))
    }
    """have ["a", "b"] for 'bar""" in {
      (data \ "bar").values must ===(List("a", "b"))
    }
  }

}

