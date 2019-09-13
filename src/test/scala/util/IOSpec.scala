package util

import cats.effect.{ContextShift, IO}

import scala.concurrent.ExecutionContext

trait IOSpec extends BaseSpec {
  override type F[T] = IO[T]
  val ec: ExecutionContext = ExecutionContext.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)
}
