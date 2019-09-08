package app.config

import cats.effect.Sync
import pureconfig.generic.ProductHint
import pureconfig.module.catseffect.loadConfigF
import pureconfig.{CamelCase, ConfigFieldMapping}
import pureconfig.generic.auto._

final case class FlywayConfig(locations: Vector[String])

object FlywayConfig {
  def load[F[_] : Sync]: F[FlywayConfig] = {
    implicit def hint[A]: ProductHint[A] = ProductHint(ConfigFieldMapping(CamelCase, CamelCase))
    loadConfigF("flyway")
  }
}
