import { useParams, useSearchParams } from "react-router-dom";
import styles from "./Vehicle.module.css";

import { useVehicles } from "../../contexts/VehiclesContext";
import { useEffect } from "react";
import Spinner from "./Spinner";
import BackButton from "./BackButton";

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

  if (isLoading) return <Spinner />;

  return (
    <div className={styles.vehicle}>
      Details
      <BackButton />
    </div>
  );
}

export default Vehicle;
