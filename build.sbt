name := "wif"

version := "0.1.1"

scalaVersion := "2.12.8"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.scodec" % "scodec-bits_2.12" % "1.1.12",
  "com.madgag.spongycastle" % "core" % "1.58.0.0",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)


scalacOptions ++= Seq(
  "-deprecation",     //emit warning and location for usages of deprecated APIs
  "-unchecked",       //enable additional warnings where generated code depends on assumptions
  "-explaintypes",    //explain type errors in more detail
  "-Ywarn-dead-code", //warn when dead code is identified
  "-Xfatal-warnings"  //fail the compilation if there are any warnings
)
