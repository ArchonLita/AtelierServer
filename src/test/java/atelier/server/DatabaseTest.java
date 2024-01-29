package atelier.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import atelier.server.dnd.Sheet;

@SpringBootTest
class DatabaseTest {

	@Autowired
	AtelierConfiguration config;

	@Autowired
	private FileDatabase database;

	private String originalDatabasePath;

	@BeforeEach
	void setup() {
		originalDatabasePath = config.getDatabasePath();
		config.setDatabasePath("./test");
		database.load();
	}

	@AfterEach
	void cleanup() throws IOException {
		FileUtils.deleteDirectory(new File(config.getDatabasePath()));
		config.setDatabasePath(originalDatabasePath);
	}

	@Test
	void saveAndLoadSheet() throws IOException {
		Sheet sheet = new Sheet();

		long id = database.put(sheet);
		database.save();
		assertTrue(new File(String.format("./test/%d.json", id)).exists());

		database.load();
		Sheet loadedSheet = database.get(id);
		assertEquals(sheet, loadedSheet);

		database.remove(id);
		database.save();
	}
}
