package app.model

import java.util.UUID

import doobie.util.Meta
import io.estatico.newtype.macros.newtype
import scalaz.deriving
import doobie.postgres.implicits._

final case class Employee(id: Employee.Id, name: String, age: Int)

object Employee {
  @newtype
  @deriving(Meta)
  final case class Id(value: UUID)
}
