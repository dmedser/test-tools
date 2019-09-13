package util

import cats.effect.{Async, ContextShift}
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

object FromDriverManagerTransactor {
  def create[F[_]](driver: String, url: String, user: String, pass: String)(
    implicit F: Async[F],
    cs: ContextShift[F]
  ): F[Aux[F, Unit]] =
    F.delay(Transactor.fromDriverManager(driver, url, user, pass))
}
