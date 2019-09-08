package repository

import app.model.Employee
import repository.EmployeeRepositoryTestImpl.State
import repository.EmployeeRepositoryTestImpl.State._
import util.IOSpec

class EmployeeRepositorySpec extends IOSpec {

  "check if employee exists by employeeId" in {
    forAll { state: State =>
      whenever(state.employees.nonEmpty) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            employee = state.employees.head
            result <- employeeRepository.employeeExists(employee.id)
          } yield result mustBe true
        }
      }
    }
  }

  "check if employee exists by employeeId: not found" in {
    forAll { (state: State, nonExistentEmployeeId: Employee.Id) =>
      whenever(state.employees.nonEmpty && !state.employees.exists(_.id == nonExistentEmployeeId)) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            result             <- employeeRepository.employeeExists(nonExistentEmployeeId)
          } yield result mustBe false
        }
      }
    }
  }

  "store employee" in {
    forAll { (state: State, employee: Employee) =>
      withState(state) { implicit MS =>
        for {
          employeeRepository <- EmployeeRepositoryTestImpl.create[F]
          result             <- employeeRepository.storeEmployee(employee)
        } yield result mustBe 1
      }
    }
  }
}
