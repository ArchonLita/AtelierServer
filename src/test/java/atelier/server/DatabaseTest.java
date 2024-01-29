package atelier.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

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

	@BeforeEach
	void setup() {
		database.load();
	}

	@Test
	void testDatabase() throws IOException {
		Sheet sheet = new Sheet();
		System.arraycopy(new int[] { 15, 14, 13, 12, 10, 9 }, 0, sheet.getBaseAbilityScores(), 0, 6);

		long id = database.put(sheet);
		database.save();
		assertTrue(new File(String.format("./data/%d.json", id)).exists());

		database.load();
		Sheet loadedSheet = database.get(id);
		assertEquals(sheet, loadedSheet);

		database.remove(id);
		database.save();
	}
}
