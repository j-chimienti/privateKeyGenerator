import com.typesafe.sbt.SbtNativePackager.autoImport.maintainer

lazy val root = project.in(file("."))
    .aggregate(core, cli, gui)

lazy val core = project
  .settings(libraryDependencies ++= commonDependencies)

lazy val cli = project
  .settings(libraryDependencies ++= commonDependencies ++ Seq("com.github.scopt" %% "scopt" % "4.0.0-RC2"))
  .aggregate(core)
  .dependsOn(core)

lazy val gui = project
  .settings(
    fork := true,
    libraryDependencies ++= commonDependencies ++ Seq("org.scalafx" %% "scalafx" % "8.0.192-R14"
  ))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JDKPackagerPlugin)
  .enablePlugins(UniversalPlugin)
  .aggregate(core)
  .dependsOn(core)

lazy val commonDependencies = Seq(
  "org.scodec" % "scodec-bits_2.12" % "1.1.12",
  "com.madgag.spongycastle" % "core" % "1.58.0.0",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

lazy val commonSettings = Seq(
  name := "wif",
  version := "0.1.1", 
  maintainer := "the dude",
  scalaVersion := "2.12.8"
)


scalacOptions ++= Seq(
  "-deprecation",     //emit warning and location for usages of deprecated APIs
  "-unchecked",       //enable additional warnings where generated code depends on assumptions
  "-explaintypes",    //explain type errors in more detail
  "-Ywarn-dead-code", //warn when dead code is identified
  "-Xfatal-warnings"  //fail the compilation if there are any warnings
)


packageSummary := "Generate a WIF from 256 binary number (256 coin flips)"

packageDescription := ""

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"




// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems


// https://www.scala-sbt.org/sbt-native-packager/formats/jdkpackager.html#example
// jdkPackagerType:= "all"
// jdkPackagerType := "deb"



// https://sbt-native-packager.readthedocs.io/en/stable/formats/jdkpackager.html#example
//lazy val iconGlob = sys.props("os.name").toLowerCase match {
//  case os if os.contains("mac") ⇒ "*.icns"
//  case os if os.contains("win") ⇒ "*.ico"
//  case _ ⇒ "*.png"
//}


// jdkAppIcon :=  (sourceDirectory.value ** iconGlob).getPaths.headOption.map(file)
