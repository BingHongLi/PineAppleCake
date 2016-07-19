name := "PineAppleCake"

version := "0.0.1"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
// for ScalaTest on Ply
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"


)

//  cors
//  https://www.playframework.com/documentation/2.5.x/CorsFilter
libraryDependencies += filters

