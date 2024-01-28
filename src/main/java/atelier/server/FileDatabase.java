package atelier.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileDatabase {

    @Autowired
    private AtelierConfiguration config;

    private Map<Long, String> data = new HashMap<>();

    private static final Pattern JSON_PATTERN = Pattern.compile("(?:.+\\/)?(\\d+).json");

    private void loadFile(Path path) {
        String content = "";

        try {
            content = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var matcher = JSON_PATTERN.matcher(path.toString());
        if (!matcher.find())
            throw new RuntimeException("Invalid id format");
        
        long id = Long.parseLong(matcher.group(1));
        data.put(id, content);
    }

    public void load() throws IOException {
        data.clear();
        try (var paths = Files.walk(Paths.get(config.getDatabasePath()))) {
            paths.filter(Files::isRegularFile).forEach(this::loadFile);
        }
    }

    public String get(long id) {
        return data.get(id);
    }
}
