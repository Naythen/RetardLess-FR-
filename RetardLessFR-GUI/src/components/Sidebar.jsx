import { Outlet, useNavigate } from "react-router-dom";

import AppNav from "./AppNav";
import Logo from "./Logo";
import styles from "./Sidebar.module.css";
import Button from "./Button";

function Sidebar() {
  const navigate = useNavigate();

  return (
    <div className={styles.sidebar}>
      <Logo />
      <AppNav />

      <Outlet />

      <Button
        type="back"
        onClick={(e) => {
          e.preventDefault();
          navigate("/");
        }}
      >
        Exit App
      </Button>
      {/* <Button type="back">Save</Button> */}

      <footer className={styles.footer}>
        <p className={styles.copyright}>
          &copy; Copyright {new Date().getFullYear()} by RetardLess Inc.
        </p>
      </footer>
    </div>
  );
}

export default Sidebar;
