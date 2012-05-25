organization := "org.onion_lang"

name := "jsonda"

version := "0.0.2-SNAPTHOT"

scalaVersion := "2.9.1"

seq(assemblySettings: _*)

libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-json" % "2.4"
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.8.2" % "test"
)
  
// Read here for optional dependencies: 
// http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html#Dependencies
resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots", 
  "releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

publishTo := Some(Resolver.file("Github Pages", Path.userHome / "git" / "kmizu.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern)))

publishMavenStyle := true

scalacOptions ++= Seq("-deprecation","-unchecked")

initialCommands in console += {
  Iterator(
    "net.liftweb.json._",
    "org.onion_lang.jsonda.Implicits._"
  ).map("import "+).mkString("\n")
}
