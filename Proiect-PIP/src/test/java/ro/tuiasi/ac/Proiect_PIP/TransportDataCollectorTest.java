package ro.tuiasi.ac.Proiect_PIP;

import static org.junit.Assert.assertEquals;
import org.mockito.Mockito;
import org.json.JSONObject;
import org.junit.Test;

class TransportDataCollectorTest {

    @Test
    void testCollectData() {
        // Simularea unui API extern
        TransportDataCollector collector = Mockito.mock(TransportDataCollector.class);
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("data", "sample data");

        // Configurarea comportamentului simulat
        when(collector.collectData()).thenReturn(mockResponse);

        // Apelul metodei testate
        JSONObject result = collector.collectData();

        // Verificarea rezultatului
        assertEquals(mockResponse, result);
    }
}
