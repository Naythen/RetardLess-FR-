import styles from "./VehicleItem.module.css";
import { Link } from "react-router-dom";
import { useVehicles } from "../../contexts/VehiclesContext";
import BusIcon from "./BusIcon";
import TramIcon from "./TramIcon";

function VehicleiItem({ vehicle }) {
  const { routeName, vehicleType, id, positions } = vehicle;
  const { currentVehicle } = useVehicles();

  return (
    <li>
      <Link
        className={`${styles.vehicleItem} ${
          id === currentVehicle.id ? styles["vehicleItem--active"] : ""
        }`}
        to={`${id}?lat=${positions.lat}&lng=${positions.lng}`}
      >
        {vehicleType === "Bus" ? <BusIcon /> : <TramIcon />}

        <span className={styles.type}> {vehicleType}</span>
        <h3 className={styles.name}>{routeName}</h3>
      </Link>
    </li>
  );
}

export default VehicleiItem;
