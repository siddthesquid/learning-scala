name := "advanced-scala"

version := "0.1"

scalaVersion := "2.13.6"

val catsVersion = "2.1.1"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.typelevel" %% "cats-core" % catsVersion
  )

scalacOptions ++= Seq(
  "-language:higherKinds"
)