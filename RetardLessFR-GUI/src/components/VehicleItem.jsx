//import { useCities } from "../contexts/CitiesContext";
import styles from "./VehicleItem.module.css";
import { Link } from "react-router-dom";
import { useVehicles } from "../../contexts/VehiclesContext";

function VehicleiItem({ vehicle }) {
  const { routeName, vehicleType, idVehicle, positions } = vehicle;
  const { currentVehicle } = useVehicles();

  const isBus = vehicleType === "Bus" ? true : false;
  console.log(isBus);

  return (
    <li>
      <Link
        className={`${styles.vehicleItem} ${
          idVehicle === currentVehicle.idVehicle
            ? styles["vehicleItem--active"]
            : ""
        }`}
        to={`${idVehicle}?lat=${positions.lat}&lng=${positions.lng}`}
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
