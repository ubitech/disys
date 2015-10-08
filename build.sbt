name := """DISYS"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

resourceDirectory in Compile := baseDirectory.value / "resources"

unmanagedClasspath in Runtime += baseDirectory.value / "resources"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.apache.commons" % "commons-lang3" % "3.3.2"
)

libraryDependencies ++= Seq(
    "drools-compiler",
    "drools-core",
    "drools-decisiontables",
    "knowledge-api"
).map("org.drools" % _ % "6.1.0.Final")

libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.1.2",
  "com.sun.xml.bind"         % "jaxb-xjc"          % "2.2.4-1", // For drools
  "com.thoughtworks.xstream" % "xstream"           % "1.4.2",   // For drools
  "org.codehaus.janino"      % "janino"            % "2.5.16"   // For drools
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.+" % "test",
  "junit"          % "junit"     % "4.+"   % "test"
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2")
    case _ =>
      libraryDependencies.value
  }
}


initialCommands in console := """import dummy._"""

resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"