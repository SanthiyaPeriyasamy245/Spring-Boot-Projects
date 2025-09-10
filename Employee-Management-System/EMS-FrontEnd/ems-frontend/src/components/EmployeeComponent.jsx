import React, { useEffect } from "react";

import { useState } from "react";
import {
  createEmployee,
  getEmployee,
  updateEmployee,
} from "../service/EmployeeService";
import { useNavigate, useParams } from "react-router-dom";

export const EmployeeComponent = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [department, setDepartment] = useState("");

  const { id } = useParams();

  useEffect(() => {
    if (id) {
      getEmployee(id)
        .then((response) => {
          setFirstName(response.data.firstName);
          setLastName(response.data.lastName);
          setEmail(response.data.email);
          setDepartment(response.data.department);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [id]);

  function pageTitle() {
    if (id) {
      return <h2 className="text-center">Update Employee</h2>;
    } else {
      return <h2 className="text-center">Add Employee</h2>;
    }
  }

  const handleFirstName = (e) => setFirstName(e.target.value);
  const handleLastName = (e) => setLastName(e.target.value);
  const handleEmail = (e) => setEmail(e.target.value);
  const handleDepartment = (e) => setDepartment(e.target.value);
  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    department: "",
  });
  function validateErrors() {
    let valid = true;
    const errorsCopy = { ...errors };
    if (firstName.trim()) {
      errorsCopy.firstName = "";
    } else {
      errorsCopy.firstName = "first Name is required";
      valid = false;
    }

    if (lastName.trim()) {
      errorsCopy.lastName = "";
    } else {
      errorsCopy.lastName = "last Name is required";
      valid = false;
    }
    if (email.trim()) {
      errorsCopy.email = "";
    } else {
      errorsCopy.email = "email is required";
      valid = false;
    }
    if (department.trim()) {
      errorsCopy.department = "";
    } else {
      errorsCopy.department = "department is required";
      valid = false;
    }
    setErrors(errorsCopy);
    return valid;
  }
  const navigator = useNavigate();
  function saveEmployee(e) {
    e.preventDefault();
    if (validateErrors()) {
      const employee = { firstName, lastName, email, department };

      if (id) {
        updateEmployee(id, employee)
          .then((response) => {
            console.log(response.data);
            navigator("/employees");
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        createEmployee(employee)
          .then((response) => {
            console.log(response.data);
            navigator("/employees");
          })
          .catch((error) => {
            console.error(error);
          });
      }
    }
  }

  return (
    <div>
      <div className="container">
        <br />
        <div className="row">
          <div className="card col-md-6 offset-md-3">
            {pageTitle()}
            <div className="card-body">
              <form action="">
                <div className="form-group mb-2">
                  <label className="form-label"></label>
                  <input
                    type="text"
                    placeholder="Enter employee First Name"
                    name="firstName"
                    value={firstName}
                    className={`form-control ${
                      errors.firstName ? "is-invalid" : ""
                    }`}
                    onChange={handleFirstName}
                  />
                  {errors.firstName && (
                    <div className="invalid-feedback">{errors.firstName}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"></label>
                  <input
                    type="text"
                    placeholder="Enter employee Last Name"
                    name="lastName"
                    value={lastName}
                    className={`form-control ${
                      errors.lastName ? "is-invalid" : ""
                    }`}
                    onChange={handleLastName}
                  />
                  {errors.firstName && (
                    <div className="invalid-feedback">{errors.lastName}</div>
                  )}
                </div>
                <div className="form-group mb-2">
                  <label className="form-label"></label>
                  <input
                    type="email"
                    placeholder="Enter employee email"
                    name="email"
                    value={email}
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    onChange={handleEmail}
                  />
                  {errors.email && (
                    <div className="invalid-feedback">{errors.email}</div>
                  )}
                </div>
                <div className="form-group mb-5">
                  <label className="form-label"></label>
                  <input
                    type="text"
                    placeholder="Enter employee department"
                    name="department"
                    value={department}
                    className={`form-control ${
                      errors.department ? "is-invalid" : ""
                    }`}
                    onChange={handleDepartment}
                  />
                  {errors.department && (
                    <div className="invalid-feedback">{errors.department}</div>
                  )}
                </div>
                <button
                  className="btn btn-info mb-2 mu-2"
                  onClick={saveEmployee}
                >
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
