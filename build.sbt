import com.github.retronym.SbtOneJar._

name := "met"

version := "1.0"

scalaVersion := "2.11.2"

oneJarSettings

libraryDependencies ++= Seq(
  "net.sf.opencsv" % "opencsv" % "2.3",
  "joda-time" % "joda-time" % "2.4",
  "org.joda" % "joda-convert" % "1.7",
  "com.novocode" % "junit-interface" % "0.9" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test"
)
