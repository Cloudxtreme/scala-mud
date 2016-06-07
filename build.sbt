name := "scala-mud"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "com.typesafe.akka" %% "akka-actor" % "2.4.3",
  "com.typesafe.play" %% "play-json" % "2.5.3",
  "com.lihaoyi" %% "fansi" % "0.1.2"
)

scalacOptions ++= Seq(
  //"-feature"
)
