package repository

import app.model.Employee
import app.repository.EmployeeRepository
import cats.Monad
import cats.mtl.MonadState
import cats.syntax.flatMap._
import cats.syntax.functor._
import repository.EmployeeRepositoryTestImpl.{EmployeeSummary, State}

class EmployeeRepositoryTestImpl[F[_]](implicit F: Monad[F], MS: MonadState[F, State]) extends EmployeeRepository[F] {

  def checkIfEmployeeExists(id: Employee.Id): F[Boolean] = MS.inspect(_.employees.contains(id))

  def storeEmployee(employee: Employee): F[Int] =
    for {
      beforeInsert <- MS.get
      _ <- MS.modify(
        _.copy(employees = beforeInsert.employees + (employee.id -> EmployeeSummary(employee.name, employee.age)))
      )
      afterInsert <- MS.get
    } yield afterInsert.employees.size - beforeInsert.employees.size

  def updateEmployee(employee: Employee): F[Int] =
    F.ifM(MS.inspect(_.employees.contains(employee.id)))(
      MS.modify(
          state =>
            state.copy(
              employees = state.employees + (employee.id -> EmployeeSummary(employee.name, employee.age))
          )
        )
        .map(_ => 1),
      F.pure(0)
    )
}

object EmployeeRepositoryTestImpl {

  def create[F[_]](implicit F: Monad[F], MS: MonadState[F, State]): F[EmployeeRepository[F]] =
    F.pure(new EmployeeRepositoryTestImpl)

  final case class State(employees: Map[Employee.Id, EmployeeSummary])

  final case class EmployeeSummary(name: String, age: Int)
}
