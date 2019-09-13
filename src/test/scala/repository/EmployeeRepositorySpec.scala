package repository

import app.model.Employee
import im.plmnt.scalacheck.magnolia.auto._
import repository.EmployeeRepositoryTestImpl.State
import util.ArbitraryUtil._
import util.IOSpec

class EmployeeRepositorySpec extends IOSpec {

  "check if employee exists by employeeId" in {
    forAll { state: State =>
      whenever(state.employees.nonEmpty) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            (employeeId, _)    <- MS.inspect(_.employees.head)
            employeeExists     <- employeeRepository.checkIfEmployeeExists(employeeId)
          } yield employeeExists mustBe true
        }
      }
    }
  }

  "check if employee exists by employeeId: not found" in {
    forAll { (state: State, nonExistentEmployeeId: Employee.Id) =>
      whenever(state.employees.nonEmpty && !state.employees.contains(nonExistentEmployeeId)) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            employeeExists     <- employeeRepository.checkIfEmployeeExists(nonExistentEmployeeId)
          } yield employeeExists mustBe false
        }
      }
    }
  }

  "store employee" in {
    forAll { (state: State, employee: Employee) =>
      withState(state) { implicit MS =>
        for {
          employeeRepository <- EmployeeRepositoryTestImpl.create[F]
          affectedRows       <- employeeRepository.storeEmployee(employee)
        } yield affectedRows mustBe 1
      }
    }
  }

  "update employee" in {
    forAll { (state: State, name: String, age: Int) =>
      whenever(state.employees.nonEmpty) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            (employeeId, _)    <- MS.inspect(_.employees.head)
            affectedRows       <- employeeRepository.updateEmployee(Employee(employeeId, name, age))
            updatedEmployee    <- MS.inspect(_.employees(employeeId))
          } yield {
            affectedRows mustBe 1
            updatedEmployee.name mustBe name
            updatedEmployee.age mustBe age
          }
        }
      }
    }
  }

  "update employee: not found" in {
    forAll { (state: State, employee: Employee) =>
      whenever(state.employees.nonEmpty && !state.employees.contains(employee.id)) {
        withState(state) { implicit MS =>
          for {
            employeeRepository <- EmployeeRepositoryTestImpl.create[F]
            affectedRows       <- employeeRepository.updateEmployee(employee)
          } yield affectedRows mustBe 0
        }
      }
    }
  }
}
