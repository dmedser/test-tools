package util

import cats.effect.{Async, ContextShift}
import doobie.util.transactor.Transactor.Aux
import doobie.util.transactor._

object FromDriverManagerTransactor {
  def apply[F[_]](driver: String, url: String, user: String, pass: String)(
    implicit F: Async[F],
    cs: ContextShift[F]
  ): F[Aux[F, Unit]] =
    F.delay(Transactor.fromDriverManager(driver, url, user, pass))
}
