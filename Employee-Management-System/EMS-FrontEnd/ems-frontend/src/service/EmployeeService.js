import axios from "axios";

const Rest_Api_Base = "http://localhost:8080/api/employees";

export const listEmployees = () => axios.get(Rest_Api_Base);

export const createEmployee = (employee) => axios.post(Rest_Api_Base, employee);

export const getEmployee = (employeeId) =>
  axios.get(Rest_Api_Base + "/" + employeeId);

export const updateEmployee = (employeeId, employee) =>
  axios.put(Rest_Api_Base + "/" + employeeId, employee);

export const deleteEmployeeService = (employeeId) =>
  axios.delete(Rest_Api_Base + "/" + employeeId);
