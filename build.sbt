name := "wif"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"

// https://mvnrepository.com/artifact/org.scodec/scodec-bits
libraryDependencies ++= Seq(
  //"org.scodec" %% "scodec-bits" % "1.1.12",
  "org.scodec" % "scodec-bits_2.12" % "1.1.12",
  "com.madgag.spongycastle" % "core" % "1.58.0.0",
  "fr.acinq" % "bitcoin-lib_2.12" % "0.9.17",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test"
  //"fr.acinq" % "bitcoin-lib_2.11" % "0.11"
)


scalacOptions ++= Seq(
  "-deprecation",     //emit warning and location for usages of deprecated APIs
  "-unchecked",       //enable additional warnings where generated code depends on assumptions
  "-explaintypes",    //explain type errors in more detail
  "-Ywarn-dead-code", //warn when dead code is identified
  "-Xfatal-warnings"  //fail the compilation if there are any warnings
)
