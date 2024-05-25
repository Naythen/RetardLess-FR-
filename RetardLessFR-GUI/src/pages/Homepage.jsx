import styles from "./Homepage.module.css";

import { Link } from "react-router-dom";

import PageNav from "../components/PageNav";

export default function Homepage() {
  return (
    <main className={styles.homepage}>
      <PageNav />
      <section>
        <h1>
          RetardLESS
          <br />
          Informatii LIVE legate de CTP Iasi.
        </h1>
        <h2>O aplicatie facuta dupa TRANZY API.</h2>
        <Link to="/app" className="cta">
          Vezi traseele acum
        </Link>
      </section>
    </main>
  );
}
