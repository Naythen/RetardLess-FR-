import { NavLink } from "react-router-dom";
import styles from "./AppNav.module.css";

function AppNav() {
  return (
    <nav className={styles.nav}>
      <ul>
        <li>
          <NavLink to="all">All</NavLink>
        </li>
        <li>
          <NavLink to="buses">Buses</NavLink>
        </li>
        <li>
          <NavLink to="trams">Trams</NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default AppNav;
