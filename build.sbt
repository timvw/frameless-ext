lazy val scala212 = "2.12.12"

lazy val supportedScalaVersions = List(scala212)

ThisBuild / organization := "be.icteam"
ThisBuild / name := "frameless-ext"

ThisBuild / homepage := Some(url("https://github.com/timvw/frameless-ext"))
ThisBuild / licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / developers := List(Developer("timvw", "Tim Van Wassenhove", "tim@timvw.be", url("https://timvw.be")))

ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

ThisBuild / scalaVersion := scala212

ThisBuild / crossScalaVersions := supportedScalaVersions

ThisBuild / libraryDependencies ++= List(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

val sparkVersion = "3.0.0"
ThisBuild / libraryDependencies ++= List(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-yarn" % sparkVersion
)

val framelessVersion = "0.9.0"
ThisBuild / libraryDependencies ++= List(
  "org.typelevel" %% "frameless-dataset" % framelessVersion,
  "org.typelevel" %% "frameless-ml" % framelessVersion,
  "org.typelevel" %% "frameless-cats" % framelessVersion
)

ThisBuild / scalacOptions ++= Seq(
  "-language:experimental.macros"
)

val scalaTestVersion = "3.1.2"
ThisBuild / libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test
)
