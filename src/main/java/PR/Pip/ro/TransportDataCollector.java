package ro.tuiasi.ac.Proiect_PIP;

import org.json.JSONObject;

public class TransportDataCollector {
    // Metoda pentru a valida raspunsul API
    @SuppressWarnings("unused")
    protected boolean isValidResponse(JSONObject response) {
        // Implementare aici
        return true;
    }

    // Metoda pentru a filtra datele colectate
    @SuppressWarnings("unused")
    protected JSONObject filterData(JSONObject data) {
        // Implementare aici
        return new JSONObject();
    }

    // Metoda pentru a interoga API-ul și a colecta datele
    public JSONObject collectData() {
        // Implementare aici
        // descurante cu interogatul
        return new JSONObject();
    }
}