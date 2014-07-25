name := "chocolate-core"

organization := "org.uqbar"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

releaseSettings

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

/////////////////////////////////////////////////////////////////////////////////////////////
// ALTERNATIVA BINTRAY

//bintrayPublishSettings

//licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

//bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("uqbar")

//credentials +=  Credentials("Bintray API Realm", "api.bintray.com", "nscarcella", "45c713816c7a1a4de33bf8e0f61cc92148b1d490")

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true