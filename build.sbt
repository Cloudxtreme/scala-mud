name := "scala-mud"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.akka" %% "akka-actor" % "2.4.3",
  "com.typesafe.play" %% "play-json" % "2.5.3",
  "com.lihaoyi" %% "fansi" % "0.1.2",
  "org.xerial" % "sqlite-jdbc" % "3.8.11.2"
)

scalacOptions ++= Seq(
  //"-feature"
)
