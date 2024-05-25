import styles from "./VehicleList.module.css";
import Spinner from "../components/Spinner";
import VehicleItem from "./VehicleItem";
import Message from "./Message";

import { useVehicles } from "../../contexts/VehiclesContext";
function VehicleList() {
  const { vehicles, isLoading } = useVehicles();

  if (isLoading) return <Spinner></Spinner>;
  if (!vehicles.length) return <Message message="Can't fetch data" />;
  return (
    <ul className={styles.vehicleList}>
      {vehicles
        .filter((vehicle) => vehicle.vehicleType === "Tram")
        .map((filtered) => (
          <VehicleItem vehicle={filtered} key={filtered.idVehicle} />
        ))}
    </ul>
  );
}

export default VehicleList;
