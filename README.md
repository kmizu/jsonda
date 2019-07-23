# Jsonda - Simple DSL for JSON objects which is independent from specific JSON library

[![Build Status](https://travis-ci.org/kmizu/jsonda.png?branch=master)](https://travis-ci.org/kmizu/jsonda)

[![Gitter](https://badges.gitter.im/kmizu/jsonda.svg)](https://gitter.im/kmizu/jsonda?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/kmizu/jsonda.png?branch=master)](https://travis-ci.org/kmizu/jsonda)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.kmizu/jsonda-core_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.kmizu/jsonda-core_2.13)
[![Scaladoc](http://javadoc-badge.appspot.com/com.github.kmizu/jsonda-core_2.13.svg?label=scaladoc)](http://javadoc-badge.appspot.com/com.github.kmizu/jsonda-core_2.13/index.html#com.github.kmizu.jsonda.package)
[![Reference Status](https://www.versioneye.com/java/com.github.kmizu:jsonda-core_2.13/reference_badge.svg?style=flat)](https://www.versioneye.com/java/com.github.kmizu:jsonda-core_2.13/references)

This project provides a Jsonda DSL, which can be used to construct JSON object
easily, in Scala.  The code size of Jsonda is small and it is easy to 
understand what it does.  Although Jsonda was formerly Jsonic, I was told that
there is already another Jsonic library in Java by @okapies san.  Then, I renamed
Jsonic to Jsonda.

# Using with sbt

If you woule like to use Jsonda with sbt, what you need to do is only
adding the following lines to your build.sbt.  There exists jsonda 2.0.0
for Scala.2.11.X, Scala 2.12.X, and Scala 2.13.X.

For jsonda-json4s:

```scala
libraryDependencies += "com.github.kmizu" %% "jsonda-json4s" % "2.0.0"
```

For jsonda-play_json:

```scala
libraryDependencies += "com.github.kmizu" %% "jsonda-play_json" % "2.0.0"
```

## Release Note

### 2.0.0
* Drop symbol literals as keys
  - such as `{'key :- "value"}`
  - use `{"key" :- "value"}` form instead
* Support Scala 2.1.3
* Follow [Semantic Versioning](https://semver.org/spec/v2.0.0.html) from this version

### 1.6.0

* Drop spray-json support
* Add support for play-json 

### 1.4.0

* Drop Scala 2.9.X and Scala 2.10.X support
* Add support for spray-json

### 1.2.0

* Drop std-json and lif-json support.  The reason why lift-json support is dropped
is that lift-json against Scala 2.11 was not published.

# Syntax

Jsonda is very simple DSL for creating JSON objects.  Notations are followings:

* object: 

```scala
    %{ $key1 :- $value1; $key2 :- $value2; ... }
```

available from 1.0.0
```scala
    %{ $key1 := $value1; $key2 := $value2; ... }
```

or

```scala
    %{ $key1 :- $value1
       $key2 :- $value2 }
```

available from 1.0.0:

```scala
    %{ $key1 := $value1
       $key2 := $value2 }
```

Both have same meanings.

* array:

```scala
    $($value1, $value2, ...)
```

or Traversable[JsonValueType] as the followings (new in jsonda 0.8.0):

```scala
    Seq($value1, $value2, ...)
```

* primitive: 
  * number(Int):

```scala
      100
      200
      300
```

  * number (Long)

```scala
      1000000000000L
      2000000000000L
      3000000000000L
```

  * number(Float):
```scala
      10.5F
      12.0F
```

  * number(Double):

```scala
      10.5
      20.5
```

  * string (from Scala's BigInt):

```scala
      BigInt("100000000000000000000")
      BigInt("2000000000000000000000000")
```

  * string (from Scala's BigDecimal):

```scala
      BigDecimal("1.123456789")
      BigDecimal("2.234568978")
```

  * string:

```scala
      "Hello, World!"
      "Hello, Scala!"
```

  * boolean:

```scala
      true
      false
```

  * null:

```scala
      JsonNull
```

  * Option[A]:
    If value of Option[A] is Some(a), a is implicitly converted.  None is regarded as null in JSON.

# Quick Start

Here are the way to create JSON using Jsonda (with jsonda-json4s):

```scala
import com.github.kmizu.jsonda.dsl.Json4sDSL._

val person = %{
  "name" :- "Kota Mizushima"
  "age" :- 33
}
```

The type of person is `org.json4s.native.JValue`.  If you are familiar with [json4s](https://github.com/json4s/json4s), 
you can easily manipulate JSON objects.  

Or the below (with jsonda-play_json): 

```scala
import com.github.kmizu.jsonda.dsl.PlayJsonDSL._

val person = %{
  "name" :- "Kota Mizushima"
  "age" :- 33
}
```

The type of person is `play.api.libs.json.JsValue`.  If you are familiar with [play-json](https://www.playframework.com/documentation/2.7.x/ScalaJson)
you can easily manipulate JSON objects.  

Nested JSON can be written easily as the followings:

```scala
import com.github.kmizu.jsonda.dsl.Json4sDSL._
    
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

* [Scaladoc(1.6.0)](http://javadoc-badge.appspot.com/com.github.kmizu/jsonda_2.11/index.html)
* [Scaladoc(1.4.0)](http://kmizu.github.com/jsonda/api/1.4.0)
* [Scaladoc(1.2.0)](http://kmizu.github.com/jsonda/api/1.2.0)
* [Scaladoc(1.0.0)](http://kmizu.github.com/jsonda/api/1.0.0)
* [Scaladoc(0.8.0)](http://kmizu.github.com/jsonda/api/0.8.0)
* [Scaladoc(0.6.0)](http://kmizu.github.com/jsonda/api/0.6.0)
* [Scaladoc(0.4.0)](http://kmizu.github.com/jsonda/api/0.4.0)
* [Scaladoc(0.2.1)](http://kmizu.github.com/jsonda/api/0.2.1)
* [Scaladoc(0.2)](http://kmizu.github.com/jsonda/api/0.2)
* [Scaladoc(0.1)](http://kmizu.github.com/jsonda/api/0.1)
* [Scaladoc(0.0.2)](http://kmizu.github.com/jsonda/api/0.0.2/)

# License

This software is distributed under modified BSD License. See LICENSE.txt
