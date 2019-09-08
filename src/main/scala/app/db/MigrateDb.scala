package app.db

import cats.effect.Sync
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.flywaydb.core.Flyway

trait MigrateDb[F[_]] {
  def clean: F[Unit]
  def migrate: F[Unit]
  def refresh: F[Unit]
}

object MigrateDb {

  def create[F[_] : Sync](url: String, user: String, password: String, locations: Vector[String]): F[MigrateDb[F]] =
    for {
      log <- Slf4jLogger.create
      flyway = Flyway
        .configure()
        .dataSource(url, user, password)
        .locations(locations: _*)
        .load()
    } yield new Impl(flyway, log)

  private final class Impl[F[_]](flyway: Flyway, log: Logger[F])(implicit F: Sync[F]) extends MigrateDb[F] {

    def clean: F[Unit] =
      for {
        _ <- log.info("Clean database: start")
        _ <- F.delay(flyway.clean())
        _ <- log.info("Clean database: done")
      } yield ()

    def migrate: F[Unit] =
      for {
        _ <- log.info("Migrate database: start")
        n <- F.delay(flyway.migrate())
        _ <- log.info(s"Migrate database: $n migration(s) applied")
      } yield ()

    def refresh: F[Unit] = clean *> migrate
  }
}
