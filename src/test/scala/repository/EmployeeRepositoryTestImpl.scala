package repository

import app.model.Employee
import app.repository.EmployeeRepository
import cats.Monad
import cats.mtl.MonadState
import cats.syntax.functor._
import cats.syntax.flatMap._
import fommil.MagnoliaArbitrary
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbUuid
import repository.EmployeeRepositoryTestImpl.State
import util.ArbitraryUtil._

class EmployeeRepositoryTestImpl[F[_]](implicit F: Monad[F], MS: MonadState[F, State]) extends EmployeeRepository[F] {

  def employeeExists(id: Employee.Id): F[Boolean] = MS.inspect(_.employees.exists(_.id == id))

  def storeEmployee(employee: Employee): F[Int] =
    for {
      beforeInsert <- MS.get
      _            <- MS.modify(_.copy(employees = beforeInsert.employees :+ employee))
      afterInsert  <- MS.get
    } yield afterInsert.employees.size - beforeInsert.employees.size
}

object EmployeeRepositoryTestImpl {

  def create[F[_]](implicit F: Monad[F], MS: MonadState[F, State]): F[EmployeeRepository[F]] =
    F.pure(new EmployeeRepositoryTestImpl)

  final case class State(employees: List[Employee])

  object State {
    implicit val arbEmployeeId: Arbitrary[Employee.Id] = arbUuid.map(Employee.Id.apply)
    implicit val arbEmployee: Arbitrary[Employee] = MagnoliaArbitrary.gen[Employee]
    implicit val arbState: Arbitrary[State] = MagnoliaArbitrary.gen[State]
  }
}
