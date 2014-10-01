name := "chocolate-core"

description := "A multi-purpose, scala-based game engine"

scalaVersion := "2.11.1"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val chocolateCore = FDProject(
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test",
    "org.scala-lang" % "scala-reflect" % "2.11.1",
    "org.uqbar" %% "uqbar-math" % "1.1.0-SNAPSHOT",
    "org.uqbar" %% "cacao" % "0.0.1-SNAPSHOT"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)

scalacOptions += "-feature"