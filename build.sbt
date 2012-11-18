organization := "com.github.kmizu"

name := "jsonda"

version := "0.2"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "net.liftweb" % "lift-json_2.9.1" % "2.4"
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.11" % "test"
)

scalacOptions ++= Seq("-deprecation","-unchecked")

initialCommands in console += {
  Iterator("net.liftweb.json._").map("import "+).mkString("\n")
}

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) 
      Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

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
