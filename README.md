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
resolver += "kmizu-repo"  at "http://kmizu.github.com/maven/"
    
libraryDependencies += "org.onion_lang" %% "jsonda"  % "0.1"
```

# For Developer

* By running sbt gen-idea, project definition files for IntelliJ IDEA is generated.
* By running sbt eclipse, project definition files for Scala IDE for Eclipse is generated.

# Syntax

Jsonda is very simple DSL for creating JSON objects.  Notations are followings:

* object: 
    %{ $key1 :- $value1; $key2 :- $value2; ... }
* array:
    $($value1, $value2, ...)
* primitive: 
  * number(integer): e.g. 100
  * number(double): e.g. 10.5
  * string (from BigInt): e.g. 100000000000000000000
  * string (from BigDecimal): e.g. 1.123456789
  * string: e.g. "Hello, World!"
  * boolean: e.g. true 
  * null: null

# Quick Start

Here are the way to create JSON using Jsonda:

```scala
import org.onion_lang.jsonda.Implicits._
    
val person = %{
  'name :- "Kota Mizushima"
  'age :- 28
}
```
    
The type of person is net.liftweb.json.JsonAST.JValue.  If you are familiar with lift-json, you can easily manipulate JSON objects.

# Scaladoc

Scaladoc is available via the following links:

* [Scaladoc(0.0.2)](http://kmizu.github.com/jsonda/api/0.0.2/)
* [Scaladoc(0.1)](http://kmizu.github.com/jsonda/api/0.1)

# License

This software is distributed under modified BSD License. See:
LICENSE.txt
