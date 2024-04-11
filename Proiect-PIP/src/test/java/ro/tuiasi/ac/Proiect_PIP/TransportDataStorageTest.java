package ro.tuiasi.ac.Proiect_PIP;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransportDataStorageTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCheckFileExists() throws Exception {
        // Creați un fișier temporar
        File tempFile = tempFolder.newFile("test.txt");
        String filePath = tempFile.getAbsolutePath();

        // Verificați dacă metoda checkFileExists returnează true pentru fișierul temporar
        assertTrue(TransportDataStorage.checkFileExists(filePath));

        // Verificați dacă metoda checkFileExists returnează false pentru un fișier inexistent
        assertFalse(TransportDataStorage.checkFileExists("path/to/nonexistent/file.txt"));
    }
}
