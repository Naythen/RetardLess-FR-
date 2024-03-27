import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Homepage from "./pages/Homepage";
import PageNotFound from "./pages/PageNotFound";
import Who from "./pages/Who";
import Login from "./pages/Login";
import AppLayout from "./pages/AppLayout";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Homepage />} />

        <Route path="login" element={<Login />} />
        <Route path="who" element={<Who />} />
        <Route path="app" element={<AppLayout />}>
          <Route index element={<Navigate replace to="all" />} />
          <Route path="all" element={<PageNotFound />} />
          <Route path="buses" element={<PageNotFound />} />
          <Route path="trams" element={<PageNotFound />} />
        </Route>

        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
