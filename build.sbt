import sbt._
import Keys._

val scaladocBranch = TaskKey[String]("scaladoc-branch")

def Scala211 = "2.11.11"
def Scala212 = "2.12.16"
def Scala213 = "2.13.0"

scalaVersion := Scala213

crossScalaVersions := Seq(Scala211, Scala212, Scala213)


val baseSettings = Seq(
  organization := "com.github.kmizu",
  scalaVersion := Scala213,
  autoAPIMappings := true,
  libraryDependencies ++= Seq(
    "junit" % "junit" % "4.12" % "test"
  ),
  libraryDependencies ++= Seq(
    "org.specs2" %% "specs2-core" % "4.8.2" % "test",
    "junit" % "junit" % "4.11" % "test"
  ),
  scalacOptions ++= Seq("-deprecation", "-unchecked"),
  scalacOptions ++= {
    Seq("-language:implicitConversions")
  },
  scaladocBranch := "master",
  scalacOptions in (Compile, doc) ++= {
    val bd = baseDirectory.value
    val sb = scaladocBranch.value
    Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/kmizu/jsonda/tree/" + sb + "€{FILE_PATH}.scala")
  },
  testOptions += Tests.Argument(TestFrameworks.Specs2, "console"),
  publishMavenStyle := true,
  publishTo := {
    val v = version.value
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := (
    <url>https://github.com/kmizu/jsonda</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:kmizu/jsonda.git</url>
      <connection>scm:git:git@github.com:kmizu/jsonda.git</connection>
    </scm>
    <developers>
      <developer>
        <id>kmizu</id>
        <name>Kota Mizushima</name>
        <url>https://github.com/kmizu</url>
      </developer>
    </developers>
  )
)

lazy val root = Project(
  id = "jsonda",
  base = file(".")
).settings(
  baseSettings ++ Seq(
    publishArtifact := false, publish := {}, publishLocal := {}
  ): _*
).enablePlugins(
  ScalaUnidocPlugin
).aggregate(
  core, json4s, play_json
)

lazy val core = Project(
  id = "jsonda-core",
  base = file("core")
).settings(
  baseSettings: _*
)

lazy val play_json = Project(
  id = "jsonda-play_json",
  base = file("play_json")
).settings(
  baseSettings ++ Seq(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.8.1"
    )
  ): _*
).dependsOn(core)

lazy val json4s = Project(
  id = "jsonda-json4s",
  base = file("json4s")
).settings(
  baseSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "3.6.7"
    )
  ): _*
).dependsOn(core)
