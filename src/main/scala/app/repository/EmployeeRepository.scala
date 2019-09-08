package app.repository

import app.model.Employee
import cats.Applicative
import doobie.free.connection.ConnectionIO
import doobie.syntax.string._

trait EmployeeRepository[DB[_]] {
  def employeeExists(id: Employee.Id): DB[Boolean]
  def storeEmployee(employee: Employee): DB[Int]
}

object EmployeeRepository {

  def create[F[_]]()(implicit F: Applicative[F]): F[EmployeeRepository[ConnectionIO]] = F.pure(new Impl())

  private final class Impl() extends EmployeeRepository[ConnectionIO] {

    def employeeExists(id: Employee.Id): ConnectionIO[Boolean] =
      sql"SELECT EXISTS(SELECT 1 FROM employee WHERE id = $id)".query[Boolean].unique

    def storeEmployee(employee: Employee): ConnectionIO[Int] =
      sql"INSERT INTO employee (id, name, age) VALUES (${employee.id}, ${employee.name}, ${employee.age})".update.run
  }
}
