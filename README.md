# Jsonda - a simple DSL for JSON objects construction.

This project provides a Jsonda DSL, which can be used to construct JSON object
easily, in Scala.  The code size of Jsonda is small and it is easy to 
understand what it does.  Although Jsonda was formerly Jsonic, I was told that
there is already another Jsonic library in Java by @okapies san.  Then, I renamed
Jsonic to Jsonda.

# Using with sbt

If you woule like to use Jsonda with sbt, what you need to do is only
adding the following lines to your build.sbt.

```scala
libraryDependencies += "com.github.kmizu" %% "jsonda"  % "0.2"
```

# For Developer

* By running sbt gen-idea, project definition files for IntelliJ IDEA is generated.
* By running sbt eclipse, project definition files for Scala IDE for Eclipse is generated.

# Syntax

Jsonda is very simple DSL for creating JSON objects.  Notations are followings:

* object: 
    %{ $key1 :- $value1; $key2 :- $value2; ... }

or

    %{ $key1 :- $value1
       $key2 :- $value2 }

Both have same meanings.

* array:
    $($value1, $value2, ...)
* primitive: 
  * number(integer):
      100
      200
      300
  * number(double):
      10.5
      20.5
  * string (from Scala's BigInt):
      BigInt("100000000000000000000")
      BigInt("2000000000000000000000000")
  * string (from Scala's BigDecimal):
      BigDecimal("1.123456789"):
  * string:
      "Hello, World!"
      "Hello, Scala!"
  * boolean:
      true
      false
  * null:
      null

# Quick Start

Here are the way to create JSON using Jsonda:

```scala
import com.github.kmizu.jsonda.Implicits._

val person = %{
  'name :- "Kota Mizushima"
  'age :- 29
}
```
    
The type of person is net.liftweb.json.JsonAST.JValue.  If you are familiar with lift-json, you can easily manipulate JSON objects.

Nested JSON can be written easily as the followings:

```scala
import com.github.kmizu.jsonda.Implicits._
    
val config = % {
  'name :- "a config"
  'debug :- false
  'logging :- % {
    'level :- "warn"
    'verbose :- true
  }
  'optimization :- %{
    'agressive :- true
    'inline :- true
  }
}
```

# Scaladoc

Scaladoc is available via the following links:

* [Scaladoc(0.2)](http://kmizu.github.com/jsonda/api/0.2)
* [Scaladoc(0.1)](http://kmizu.github.com/jsonda/api/0.1)
* [Scaladoc(0.0.2)](http://kmizu.github.com/jsonda/api/0.0.2/)

# License

This software is distributed under modified BSD License. See:
LICENSE.txt
