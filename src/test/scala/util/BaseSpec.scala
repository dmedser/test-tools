package util

import cats.effect.Effect
import cats.effect.concurrent.Ref
import cats.effect.syntax.effect._
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.olegpy.meow.effects._
import cats.mtl.MonadState
import org.scalatest.{FreeSpecLike, MustMatchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait BaseSpec extends FreeSpecLike with MustMatchers with ScalaCheckDrivenPropertyChecks {

  type F[_]

  protected def runtTestF[T](testBody: F[T])(implicit F: Effect[F]): T = testBody.toIO.unsafeRunSync()

  protected def withState[S, T](initialState: S)(check: => MonadState[F, S] => F[T])(implicit F: Effect[F]): T =
    runtTestF {
      for {
        ref <- Ref[F].of(initialState)
        res <- ref.runState(check)
      } yield res
    }
}
