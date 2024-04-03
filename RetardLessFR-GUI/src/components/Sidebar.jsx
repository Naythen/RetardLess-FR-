import { Outlet, useNavigate } from "react-router-dom";

import AppNav from "./AppNav";
import Logo from "./Logo";
import styles from "./Sidebar.module.css";
import Button from "../components/Button";

function Sidebar() {
  const navigate = useNavigate();

  return (
    <div className={styles.sidebar}>
      <Logo />
      <AppNav />

      <Outlet />

      <div>
        <div>
          <Button
            type="back"
            onClick={(e) => {
              e.preventDefault();
              navigate("/");
            }}
          >
            Exit App
          </Button>

          <Button
            type="back"
            onClick={(e) => {
              e.preventDefault();
              console.log("data saved");
            }}
          >
            Save Data
          </Button>
        </div>

        <footer className={styles.footer}>
          <p className={styles.copyright}>
            &copy; Copyright {new Date().getFullYear()} by RetardLess Inc.
          </p>
        </footer>
      </div>
    </div>
  );
}

export default Sidebar;
