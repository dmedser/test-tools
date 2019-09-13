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
      for {
        flywayConfig <- FlywayConfig.load[F]
        migrateDb <- MigrateDb
          .create[F](container.jdbcUrl, container.username, container.password, flywayConfig.locations)
        _ <- migrateDb.migrate
        transact <- FromDriverManagerTransactor.create[F](
          container.driverClassName,
          container.jdbcUrl,
          container.username,
          container.password
        ).map(_.trans)
        employeeRepository <- EmployeeRepository.create[F]
        result             <- transact(employeeRepository.checkIfEmployeeExists(Employee.Id(UUID.randomUUID())))
      } yield result mustBe false
    }
  }
}
