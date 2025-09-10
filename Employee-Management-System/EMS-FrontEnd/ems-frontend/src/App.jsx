import { Employees } from "./components/Employees";
import { FooterComponent } from "./components/FooterComponent";
import { HeaderComponent } from "./components/HeaderComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { EmployeeComponent } from "./components/EmployeeComponent";

function App() {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          <Route path="/" element={<Employees />} />
          <Route path="/employees" element={<Employees />} />
          <Route path="/add-employee" element={<EmployeeComponent />}></Route>
          <Route
            path="/update-employee/:id"
            element={<EmployeeComponent />}
          ></Route>
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
}

export default App;
