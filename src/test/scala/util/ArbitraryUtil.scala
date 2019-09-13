package util

import app.model.Employee
import cats.syntax.functor._
import cats.{Monad, StackSafeMonad}
import org.scalacheck.Arbitrary.arbUuid
import org.scalacheck.{Arbitrary, Gen}

object ArbitraryUtil {
  implicit val arbMonad: Monad[Arbitrary] = new Monad[Arbitrary] with StackSafeMonad[Arbitrary] {
    override def pure[A](a: A): Arbitrary[A] = Arbitrary(Gen.const(a))
    override def flatMap[A, B](arb: Arbitrary[A])(f: A => Arbitrary[B]): Arbitrary[B] =
      Arbitrary(arb.arbitrary.flatMap(a => f(a).arbitrary))
  }
  implicit val arbString: Arbitrary[String] = Arbitrary(Gen.alphaStr)
  implicit val arbEmployeeId: Arbitrary[Employee.Id] = arbUuid.map(Employee.Id.apply)
}
