package atelier.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseTest {

	@Autowired
	AtelierConfiguration config;

	@Autowired
	private FileDatabase database;

	@BeforeEach
	void setup() {
		config.setDatabasePath("./test");
	}

	@Test
	void testDatabase() throws IOException {
		database.load();
		assertEquals("{}", database.get(29));
	}

}
