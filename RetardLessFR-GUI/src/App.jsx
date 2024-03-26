import "./App.css";
import { BrowserRouter } from "react-router-dom";
import Homepage from "../pages/Homepage";
function App() {
  return (
    <BrowserRouter>
      <Homepage />;
    </BrowserRouter>
  );
}

export default App;
