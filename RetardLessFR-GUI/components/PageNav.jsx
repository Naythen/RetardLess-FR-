import { NavLink, Link } from "react-router-dom";
import styles from "./PageNav.module.css";
import Logo from "./Logo";

function PageNav() {
  return (
    <nav className={styles.nav}>
      <Logo />
      <ul>
        <li>
          <NavLink to="/who">Cine suntem?</NavLink>
        </li>
        <li>
          <NavLink to="/login" className={styles.ctaLink}>
            Log in
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default PageNav;
