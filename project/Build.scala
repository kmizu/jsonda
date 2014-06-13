import sbt._,Keys._

object Build extends sbt.Build{
  val scaladocBranch = TaskKey[String]("scaladoc-branch")

  val baseSettings = Seq(
    organization := "com.github.kmizu",
    version := "1.1.0-SNAPSHOT",
    scalaVersion := "2.11.1",
    crossScalaVersions := Seq("2.11.1"),
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.11" % "test"
    ),
    libraryDependencies += "org.specs2" %% "specs2" % "2.3.12" % "test",
    scalacOptions ++= Seq("-deprecation","-unchecked"),
    scalacOptions <++= scalaVersion.map{s =>
      if(s.startsWith("2.10"))
        Seq("-language:implicitConversions")
      else
        Nil
    },
    scaladocBranch := "master",
    scalacOptions in (Compile, doc) <++= (baseDirectory, scaladocBranch).map{ (bd, branch) =>
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/kmizu/jsonda/tree/" + branch + "€{FILE_PATH}.scala")
    },
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
    baseSettings ++ Unidoc.settings ++ Seq(
      publishArtifact := false, publish := {}, publishLocal := {}
    ) : _*
  ).aggregate(
    core,json4s
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
        "org.json4s" %% "json4s-native" % "3.2.9"
      )
    ) : _*
  ).dependsOn(core)
}
