import sbt._

object Dependencies {
  object Versions {
    val cats = "1.6.1"
    val catsEffect = "1.3.1"
    val pureConfig = "0.11.1"
    val doobie = "0.7.0"
    val logback = "1.2.3"
    val log4Cats = "0.3.0"
    val flyway = "6.0.1"
    val newType = "0.4.3"
    val meowMTL = "0.2.0"
    val scalaTest = "3.0.7"
    val scalaCheck = "1.14.0"
    val deriving = "1.0.0"
    val scalaCheckMagnolia = "0.2.0"
    val testContainers = "0.31.0"
    val testContainersPostgreSql = "1.12.0"
  }
  val catsCore = "org.typelevel"                      %% "cats-core"              % Versions.cats
  val catsEffect = "org.typelevel"                    %% "cats-effect"            % Versions.catsEffect
  val pureConfig = "com.github.pureconfig"            %% "pureconfig"             % Versions.pureConfig
  val pureConfigCats = "com.github.pureconfig"        %% "pureconfig-cats"        % Versions.pureConfig
  val pureConfigCatsEffect = "com.github.pureconfig"  %% "pureconfig-cats-effect" % Versions.pureConfig
  val doobieCore = "org.tpolecat"                     %% "doobie-core"            % Versions.doobie
  val doobiePostgres = "org.tpolecat"                 %% "doobie-postgres"        % Versions.doobie
  val logback = "ch.qos.logback"                      % "logback-classic"         % Versions.logback
  val log4CatsSlf4j = "io.chrisdavenport"             %% "log4cats-slf4j"         % Versions.log4Cats
  val flyway = "org.flywaydb"                         % "flyway-core"             % Versions.flyway
  val newType = "io.estatico"                         %% "newtype"                % Versions.newType
  val derivingMacro = "org.scalaz"                    %% "deriving-macro"         % Versions.deriving
  val meowMTL = "com.olegpy"                          %% "meow-mtl"               % Versions.meowMTL
  val scalaTest = "org.scalatest"                     %% "scalatest"              % Versions.scalaTest
  val scalaCheck = "org.scalacheck"                   %% "scalacheck"             % Versions.scalaCheck
  val scalaCheckMagnolia = "im.plmnt"                 %% "scalacheck-magnolia"    % Versions.scalaCheckMagnolia
  val testContainers = "com.dimafeng"                 %% "testcontainers-scala"   % Versions.testContainers
  val testContainersPostgreSql = "org.testcontainers" % "postgresql"              % Versions.testContainersPostgreSql
}
