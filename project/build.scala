import sbt._,Keys._

object build extends Build{

  val baseSettings = ScctPlugin.instrumentSettings ++ Seq(
    organization := "com.github.kmizu",
    version := "0.5.0-SNAPSHOT",
    scalaVersion := "2.9.2",
    crossScalaVersions := Seq("2.9.1", "2.9.2"),
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2" % "1.12.3" % "test",
      "junit" % "junit" % "4.11" % "test"
    ),
    resolvers ++= Seq(
      Opts.resolver.sonatypeReleases
    ),
    scalacOptions ++= Seq("-deprecation","-unchecked"),
    testOptions += Tests.Argument(TestFrameworks.Specs2, "console", "junitxml"),
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
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
    "jsonda",
    file(".")
  ).settings(
    baseSettings ++ Seq(
      publishArtifact := false, publish := {}, publishLocal := {}
    ) : _*
  ).aggregate(
    core,json4s,lift,std
  )

  lazy val core = Project(
    "jsonda-core",
    file("core")
  ).settings(
    baseSettings: _*
  )

  lazy val json4s = Project(
    "jsonda-json4s",
    file("json4s")
  ).settings(
    baseSettings ++ Seq(
      libraryDependencies ++= Seq(
        "org.json4s" %% "json4s-native" % "3.1.0"
      )
    ) : _*
  ).dependsOn(core)

  lazy val lift = Project(
    "jsonda-lift",
    file("lift")
  ).settings(
    baseSettings ++ Seq(
      libraryDependencies ++= Seq(
        "net.liftweb" % "lift-json_2.9.1" % "2.4"
      ),
      initialCommands in console += {
        Iterator("net.liftweb.json._", "com.github.kmizu.jsonda.Implicits._").map("import "+).mkString("\n")
      }
    ) : _*
  ).dependsOn(core)

  lazy val std = Project(
    "jsonda-std",
    file("std")
  ).settings(
    baseSettings: _*
  ).dependsOn(core)

}
