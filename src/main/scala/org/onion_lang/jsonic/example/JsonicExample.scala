package org.onion_lang.jsonic.example
import org.onion_lang.jsonic.Jsonic._

/**
 * An example program using JsonBuilder.
 */
object JsonicExample {
  def main(args: Array[String]): Unit = {
    val arb = %{
      "x" :- 10
      "y" :- 20
      "z" :- %{
        "a" :- $(1, 2, 3, 4, 5)
        "b" :- $(6, 7, %{ "xx" -> "yy" })
      }
    }
    println(arb)
    val person = %{ 'name :- "Kota Mizushima"; 'age -> 28 }
    println(person)
  }
}