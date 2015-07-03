import sbt._, Keys._

object build extends Build {

  private def gitHash: String = scala.util.Try(
    sys.process.Process("git rev-parse HEAD").lines_!.head
  ).getOrElse("master")

  private val msgpack4zJava07Name = "msgpack4z-java07"

  val modules = msgpack4zJava07Name :: Nil

  lazy val msgpack4zJava07 = Project("msgpack4z-java07", file(".")).settings(
  ).settings(
    autoScalaLibrary := false,
    crossPaths := false,
    name := msgpack4zJava07Name,
    javacOptions in compile ++= Seq("-target", "6", "-source", "6"),
    javacOptions in (Compile, doc) ++= Seq("-locale", "en_US"),
    libraryDependencies ++= (
      ("com.github.xuwei-k" % "msgpack4z-api" % "0.1.0") ::
      Nil
    ),
    credentials ++= PartialFunction.condOpt(sys.env.get("SONATYPE_USER") -> sys.env.get("SONATYPE_PASS")){
      case (Some(user), Some(pass)) =>
        Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
    }.toList,
    organization := "com.github.xuwei-k",
    homepage := Some(url("https://github.com/msgpack4z")),
    licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
    scalacOptions ++= (
      "-deprecation" ::
      "-unchecked" ::
      "-Xlint" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      Nil
    ),
    scalacOptions in compile ++= (
      "-Ywarn-unused" ::
      "-Ywarn-unused-import" ::
      Nil
    ),
    scalaVersion := "2.11.7",
    crossScalaVersions := scalaVersion.value :: Nil,
    pomExtra :=
      <developers>
        <developer>
          <id>xuwei-k</id>
          <name>Kenji Yoshida</name>
          <url>https://github.com/xuwei-k</url>
        </developer>
      </developers>
      <scm>
        <url>git@github.com:msgpack4z/msgpack4z-java07.git</url>
        <connection>scm:git:git@github.com:msgpack4z/msgpack4z-java07.git</connection>
        <tag>{if(isSnapshot.value) gitHash else { "v" + version.value }}</tag>
      </scm>
    ,
    description := "msgpack4z"
  ).dependsOn(
    ProjectRef(uri("git://github.com/msgpack/msgpack-java.git#b80b06eca"), "msgpack-core")
  )

}
