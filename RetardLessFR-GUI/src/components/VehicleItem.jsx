//import { useCities } from "../contexts/CitiesContext";
import styles from "./VehicleItem.module.css";
import { Link } from "react-router-dom";
import { useVehicles } from "../../contexts/VehiclesContext";

function VehicleiItem({ vehicle }) {
  const { routeName, vehicleType, id, positions } = vehicle;
  const { currentVehicle } = useVehicles();

  const isBus = vehicleType === "Bus" ? true : false;

  return (
    <li>
      <Link
        className={`${styles.vehicleItem} ${
          id === currentVehicle.id ? styles["vehicleItem--active"] : ""
        }`}
        to={`${id}?lat=${positions.lat}&lng=${positions.lng}`}
      >
        {isBus ? (
          <img src="/bus-icon.png" alt="bus icon" className={styles.icon} />
        ) : (
          <img src="/tram-icon.jpg" alt="bus icon" className={styles.icon} />
        )}

        <span className={styles.type}> {vehicleType}</span>
        <h3 className={styles.name}>{routeName}</h3>
      </Link>
    </li>
  );
}

export default VehicleiItem;
