import { useEffect, useState } from "react";
import {
  listEmployees,
  deleteEmployeeService,
} from "../service/EmployeeService";
import { useNavigate } from "react-router-dom";

export const Employees = () => {
  const [employees, setEmployees] = useState([]);
  const navigator = useNavigate();
  useEffect(() => {
    getAllEmployees();
  }, []);

  function getAllEmployees() {
    listEmployees()
      .then((response) => {
        setEmployees(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function addEmployee() {
    navigator("/add-employee");
  }
  function updateEmployee(id) {
    navigator(`/update-employee/${id}`);
  }
  function deleteEmployee(id) {
    console.log(id);
    deleteEmployeeService(id)
      .then(() => {
        getAllEmployees();
      })
      .catch((error) => {
        console.error(error);
      });
  }
  return (
    <div className="container">
      <h2 className="mb-3">List of Employees</h2>
      <button className="btn btn-success mb-3 mx-2" onClick={addEmployee}>
        Add an Employee
      </button>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Department</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.id}</td>
              <td>{employee.firstName}</td>
              <td>{employee.lastName}</td>
              <td>{employee.email}</td>
              <td>{employee.department}</td>
              <td>
                <button
                  className="btn btn-warning"
                  style={{ marginRight: "10px" }}
                  onClick={() => updateEmployee(employee.id)}
                >
                  update
                </button>
                <button
                  className="btn btn-danger "
                  onClick={() => deleteEmployee(employee.id)}
                >
                  delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
