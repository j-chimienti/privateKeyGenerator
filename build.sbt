import com.typesafe.sbt.SbtNativePackager.autoImport.{maintainer, packageDescription}
import sbt.Keys.resolvers


// https://github.com/j-chimienti/sbt-native-packager

lazy val securePrivateKeyGenerator = project.in(file("."))
    .aggregate(core, cli, gui)

lazy val commonDependencies = Seq(
  "org.scodec" % "scodec-bits_2.12" % "1.1.12",
  "com.madgag.spongycastle" % "core" % "1.58.0.0",
  "org.scalactic" %% "scalactic" % "3.0.9",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "fr.acinq" % "bitcoin-lib_2.12" % "0.9.17"
)

lazy val commonSettings = Seq(
  name := "privateKeyGen",
  version := "0.1.1",
  maintainer := "the dude",
  scalaVersion := "2.12.8",
  packageSummary := "Generate a secure private key",
  packageDescription := "",
  scalacOptions ++= Seq(
    "-deprecation",     //emit warning and location for usages of deprecated APIs
    "-unchecked",       //enable additional warnings where generated code depends on assumptions
    "-explaintypes",    //explain type errors in more detail
    "-Ywarn-dead-code", //warn when dead code is identified
    "-Xfatal-warnings"  //fail the compilation if there are any warnings
  ),

  resolvers ++= Seq(
    "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/",
    "Artima Maven Repository" at "http://repo.artima.com/releases"
  ),
  libraryDependencies ++= commonDependencies
)




lazy val core = project
  .settings(libraryDependencies ++= commonDependencies)

lazy val cli = project
  .settings(
      commonSettings,
      graalVMNativeImageOptions ++= Seq(
        "--initialize-at-build-time=scala.Function1",
        "--allow-incomplete-classpath",
        "--no-server",
        "-H:+ReportExceptionStackTraces"
      ),
      libraryDependencies ++= Seq("com.github.scopt" %% "scopt" % "4.0.0-RC2"))
  .enablePlugins(GraalVMNativeImagePlugin)
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .aggregate(core)
  .dependsOn(core)

lazy val gui = project
  .settings(
    commonSettings,
    // Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
    fork := true,
    jdkPackagerType := "installer",
    libraryDependencies ++= Seq("org.scalafx" %% "scalafx" % "8.0.192-R14"
  ))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JDKPackagerPlugin)
  .enablePlugins(UniversalPlugin)
  .aggregate(core)
  .dependsOn(core)


// https://sbt-native-packager.readthedocs.io/en/stable/formats/jdkpackager.html#example
//lazy val iconGlob = sys.props("os.name").toLowerCase match {
//  case os if os.contains("mac") ⇒ "*.icns"
//  case os if os.contains("win") ⇒ "*.ico"
//  case _ ⇒ "*.png"
//}


// jdkAppIcon :=  (sourceDirectory.value ** iconGlob).getPaths.headOption.map(file)
