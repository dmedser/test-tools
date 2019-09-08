import CompilerPlugins._

libraryDependencies ++= Seq(macroParadise, betterMonadicFor, deriving)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-Xfatal-warnings",
  "-Ypartial-unification",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros"
)