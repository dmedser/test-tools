import sbt._

object CompilerPlugins {

  object Versions {
    val macroParadise = "2.1.1"
    val betterMonadicFor = "0.3.0-M4"
    val deriving = "1.0.0"
  }

  val macroParadise = compilerPlugin("org.scalamacros" % "paradise"            % Versions.macroParadise cross CrossVersion.full)
  val betterMonadicFor = compilerPlugin("com.olegpy"   %% "better-monadic-for" % Versions.betterMonadicFor)
  val deriving = compilerPlugin("org.scalaz"           %% "deriving-plugin"    % Versions.deriving)
}
