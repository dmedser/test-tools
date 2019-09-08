package util

import cats.effect.{Async, ContextShift}
import doobie.util.transactor.Transactor._

object FromDriverManagerTransactor {
  def apply[F[_] : Async : ContextShift](driver: String, url: String, user: String, pass: String): F[Aux[F, Unit]] =
    Async[F].delay(fromDriverManager(driver, url, user, pass))
}
