import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Homepage from "../pages/Homepage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Homepage />} />
        <Route path="app" element={<h1>da</h1>}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
