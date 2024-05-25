import { useParams, useSearchParams } from "react-router-dom";
import styles from "./Vehicle.module.css";

import { useVehicles } from "../../contexts/VehiclesContext";
import { useEffect } from "react";
import Spinner from "./Spinner";
import BackButton from "./BackButton";
import BusIcon from "./BusIcon";
import TramIcon from "./TramIcon";

function Vehicle() {
  const { id } = useParams();
  const { getVehicles, currentVehicle, isLoading } = useVehicles();
  //const [searchParams, setSearchParams] = useSearchParams();

  useEffect(
    function () {
      getVehicles(id);
    },
    [id]
  );

  const { features, routeName, vehicleType, direction } = currentVehicle;

  if (isLoading) return <Spinner />;

  return (
    <div className={styles.vehicle}>
      <div className={styles.row}>
        <h6>Route name</h6>
        <h3>
          <span>{vehicleType === "Bus" ? <BusIcon /> : <TramIcon />}</span>
          {routeName}
        </h3>
      </div>

      <div className={styles.row}>
        <h6>Heading</h6>
        <p>{direction}</p>
      </div>

      <div className={styles.row}>
        <h6>Features</h6>
        <p>{features}</p>
      </div>

      <BackButton />
    </div>
  );
}

export default Vehicle;
