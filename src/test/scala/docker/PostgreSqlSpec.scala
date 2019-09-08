package docker

import java.util.UUID

import app.config.FlywayConfig
import app.db.MigrateDb
import app.model.Employee
import app.repository.EmployeeRepository
import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import util.{FromDriverManagerTransactor, IOSpec}

class PostgreSqlSpec extends IOSpec with ForAllTestContainer {

  val container = PostgreSQLContainer()

  "Inside PostgreSQL container" - {
    "check if employee exists by employeeId" in runtTestF {
      import container._
      for {
        flywayConfig       <- FlywayConfig.load[F]
        migrateDb          <- MigrateDb.create[F](jdbcUrl, username, password, flywayConfig.locations)
        _                  <- migrateDb.migrate
        transact           <- FromDriverManagerTransactor[F](driverClassName, jdbcUrl, username, password).map(_.trans)
        employeeRepository <- EmployeeRepository.create[F]
        result             <- transact(employeeRepository.employeeExists(Employee.Id(UUID.randomUUID())))
      } yield result mustBe false
    }
  }
}
