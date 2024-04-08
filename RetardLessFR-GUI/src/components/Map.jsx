import { useNavigate, useSearchParams } from "react-router-dom";
import styles from "./Map.module.css";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import { useState } from "react";
import { useVehicles } from "../../contexts/VehiclesContext";
import BusIcon from "../components/BusIcon";
import TramIcon from "./TramIcon";
import "leaflet/dist/leaflet.css";
import { Icon } from "leaflet";

const markerIcon = new Icon({
  iconUrl: "../../public/MarkerIcon.png",
  iconSize: [30, 40],
});

function Map() {
  const navigate = useNavigate();
  const { vehicles, currentVehicle } = useVehicles();
  const [mapPosition, setMapPosition] = useState([47.1585, 27.6014]);

  const [searchParams, setSearchParams] = useSearchParams();

  // const lat = searchParams.get("lat");
  // const lng = searchParams.get("lng");

  return (
    <div className={styles.mapContainer}>
      <MapContainer
        center={mapPosition}
        zoom={13}
        scrollWheelZoom={true}
        className={styles.map}
      >
        {/* <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.fr/hot/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        /> */}
        <TileLayer
          attribution='&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.{ext}"
          ext="png"
        />
        {currentVehicle.id !== undefined && (
          <Marker
            icon={markerIcon}
            position={[
              currentVehicle.positions.lat,
              currentVehicle.positions.lng,
            ]}
            key={currentVehicle.id}
          >
            <Popup>
              <span>
                {currentVehicle.vehicleType === "Bus" ? (
                  <BusIcon />
                ) : (
                  <TramIcon />
                )}
              </span>
              <span>{currentVehicle.routeName}</span>
            </Popup>
          </Marker>
        )}
        {/* {vehicles.map((vehicle) => (
          <Marker
          position={[vehicle.positions.lat, vehicle.positions.lng]}
          key={vehicle.id}
          >
            <Popup>
              <span>
                {vehicle.vehicleType === "Bus" ? <BusIcon /> : <TramIcon />}
              </span>
              <span>{vehicle.routeName}</span>
            </Popup>
          </Marker>
        ))} */}
      </MapContainer>
    </div>
  );
}

export default Map;
