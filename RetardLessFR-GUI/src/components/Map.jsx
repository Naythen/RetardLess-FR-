import { useNavigate, useSearchParams } from "react-router-dom";
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  LayersControl,
} from "react-leaflet";
import { useState, useRef, useEffect } from "react";
import { useVehicles } from "../../contexts/VehiclesContext";
import BusIcon from "../components/BusIcon";
import TramIcon from "./TramIcon";

import styles from "./Map.module.css";

import "leaflet/dist/leaflet.css";
import { Icon } from "leaflet";

import L from "leaflet";
import "leaflet-routing-machine";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";

const markerIcon = new Icon({
  iconUrl: "../../public/MarkerIcon.png",
  iconSize: [30, 40],
});

const maps = {
  base: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
};

function Map() {
  const navigate = useNavigate();
  const { vehicles, currentVehicle } = useVehicles();
  const [mapPosition, setMapPosition] = useState([47.1585, 27.6014]);

  const [searchParams, setSearchParams] = useSearchParams();

  const mapRef = useRef();

  return (
    <div className={styles.mapContainer}>
      <MapContainer
        center={mapPosition}
        zoom={13}
        scrollWheelZoom={true}
        className={styles.map}
        whenCreated={(map) => (mapRef.current = map)}
      >
        <LayersControl position="topright">
          <LayersControl.BaseLayer checked name="Map">
            {/* <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.fr/hot/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            /> */}

            <TileLayer
              attribution='&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
              url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.{ext}"
              ext="png"
            />
          </LayersControl.BaseLayer>
        </LayersControl>

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
      </MapContainer>
    </div>
  );
}

export default Map;
