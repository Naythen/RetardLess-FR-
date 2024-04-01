import { createContext, useContext, useEffect, useState } from "react";

const URL = "http://localhost:8000";

const VehiclesContext = createContext();

function VehiclesProvider({ children }) {
  const [vehicles, setVehicles] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [currentVehicle, setCurrentVehicle] = useState({});

  useEffect(function () {
    async function fetchVehicles() {
      try {
        setIsLoading(true);
        const res = await fetch(`${URL}/vehicles`);
        const data = await res.json();
        setVehicles(data);
      } catch {
        alert("There was an error loading data");
      } finally {
        setIsLoading(false);
      }
    }
    fetchVehicles();
  }, []);

  async function getVehicles(id) {
    try {
      setIsLoading(true);
      const res = await fetch(`${URL}/vehicles/${id}`);
      const data = await res.json();
      setCurrentVehicle(data);
    } catch {
      alert("There was an error loading data on get Vehicle");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <VehiclesContext.Provider
      value={{
        vehicles,
        isLoading,
        currentVehicle,
        getVehicles,
      }}
    >
      {children}
    </VehiclesContext.Provider>
  );
}

function useVehicles() {
  const context = useContext(VehiclesContext);

  if (context === undefined)
    throw new Error("Cities context was used outside of cities provider");

  return context;
}

export { VehiclesProvider, useVehicles };
