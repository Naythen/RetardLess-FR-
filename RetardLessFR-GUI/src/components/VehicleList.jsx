import styles from "./VehicleList.module.css";
import Spinner from "../components/Spinner";
import VehicleItem from "../components/VehicleItem";
import Message from "../components/Message";

import { useVehicles } from "../../contexts/VehiclesContext";
function VehicleList() {
  const { vehicles, isLoading } = useVehicles();

  if (isLoading) return <Spinner></Spinner>;

  if (!vehicles.length) return <Message message="Can't fetch data" />;

  return (
    <ul className={styles.vehicleList}>
      {vehicles.map((vehicle) => (
        <VehicleItem vehicle={vehicle} key={vehicle.idVehicle} />
      ))}
    </ul>
  );
}

export default VehicleList;
