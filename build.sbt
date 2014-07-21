name := "chocolate-core"

organization := "org.uqbar"

version := "0.2.1"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true