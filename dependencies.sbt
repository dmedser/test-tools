libraryDependencies ++= {

  import Dependencies._

  val compile = Seq(
    catsCore,
    catsEffect,
    pureConfig,
    pureConfigCats,
    pureConfigCatsEffect,
    doobieCore,
    doobiePostgres,
    logback,
    log4CatsSlf4j,
    flyway,
    newType,
    derivingMacro
  )

  val test = Seq(
    meowMTL,
    scalaTest,
    scalaCheck,
    scalaCheckMagnolia,
    testContainers,
    testContainersPostgreSql
  ).map(_ % Test)

  compile ++ test
}
