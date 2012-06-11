package org.onion_lang.jsonda.example
import org.onion_lang.jsonda.Implicits._

/**
 * A simple Jsonda example program.
 */
object JsondaExample {
  def main(args: Array[String]): Unit = {
    val arc = %{
      "x" :- 10
      "y" :- 20
      "z" :- %{
        "a" :- $(1, 2, 3, 4, 5)
        "b" :- $(6, 7, %{ "xx" :- "yy" })
      }
    }
    println(arc.dump())
    /*
     * The above expression print the following result:
     * { "x":10, "y":20,
     *   "z":{"a":[1, 2, 3, 4, 5], "b":[6, 7, {"xx":"yy"}]}
     * }
     */

    val person = %{ 'name :- "Kota Mizushima"; 'age :- 28 }
    /*
     * The above expression print the following result:
     * { "name":"Kota Mizushima", "age":28 }
     */
    println(person.dump())
  }
}
