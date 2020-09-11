lazy val scala212 = "2.12.12"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala212, scala211)

ThisBuild / organization := "be.icteam"
ThisBuild / name := "frameless-ext"

ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

ThisBuild / scalaVersion := scala211

ThisBuild / crossScalaVersions := supportedScalaVersions

ThisBuild / libraryDependencies ++= List(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

val sparkVersion = "2.4.6"
ThisBuild / libraryDependencies ++= List(
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-hive" % sparkVersion,
    "org.apache.spark" %% "spark-yarn" % sparkVersion
)

val framelessVersion = "0.8.0"
ThisBuild / libraryDependencies ++= List(
  "org.typelevel" %% "frameless-dataset" % framelessVersion,
  "org.typelevel" %% "frameless-ml"      % framelessVersion,
  "org.typelevel" %% "frameless-cats"    % framelessVersion  
)

ThisBuild / scalacOptions ++= Seq(
  "-language:experimental.macros"
)

ThisBuild / publishTo := sonatypePublishToBundle.value