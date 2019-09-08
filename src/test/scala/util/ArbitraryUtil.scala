package util

import cats.{Monad, StackSafeMonad}
import org.scalacheck.{Arbitrary, Gen}

object ArbitraryUtil {
  implicit val arbMonad: Monad[Arbitrary] = new Monad[Arbitrary] with StackSafeMonad[Arbitrary] {
    override def pure[A](a: A): Arbitrary[A] = Arbitrary(Gen.const(a))
    override def flatMap[A, B](arb: Arbitrary[A])(f: A => Arbitrary[B]): Arbitrary[B] =
      Arbitrary(arb.arbitrary.flatMap(a => f(a).arbitrary))
  }
}
