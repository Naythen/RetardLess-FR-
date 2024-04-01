import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Homepage from "./pages/Homepage";
import PageNotFound from "./pages/PageNotFound";
import Who from "./pages/Who";
import Login from "./pages/Login";
import AppLayout from "./pages/AppLayout";
import VehicleList from "./components/VehicleList";
import BusesList from "./components/BusesList";
import TramList from "./components/TramList";
import Vehicle from "./components/Vehicle";
import { VehiclesProvider } from "../contexts/VehiclesContext";

function App() {
  return (
    <VehiclesProvider>
      <BrowserRouter>
        <Routes>
          <Route index element={<Homepage />} />

          <Route path="login" element={<Login />} />
          <Route path="who" element={<Who />} />
          <Route path="app" element={<AppLayout />}>
            <Route index element={<Navigate replace to="all" />} />
            <Route path="all" element={<VehicleList />} />
            <Route path="all/:id" element={<Vehicle />} />
            <Route path="buses" element={<BusesList />} />
            <Route path="trams" element={<TramList />} />
          </Route>

          <Route path="*" element={<PageNotFound />} />
        </Routes>
      </BrowserRouter>
    </VehiclesProvider>
  );
}

export default App;
