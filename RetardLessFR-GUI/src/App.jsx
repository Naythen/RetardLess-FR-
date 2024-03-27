import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Homepage from "../pages/Homepage";
import PageNotFound from "../pages/PageNotFound";
import Who from "../pages/Who";
import Login from "../pages/Login";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Homepage />} />

        <Route path="login" element={<Login />} />
        <Route path="who" element={<Who />} />
        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
