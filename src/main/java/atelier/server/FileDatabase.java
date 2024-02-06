package atelier.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import atelier.server.dnd.Sheet;
import jakarta.annotation.PreDestroy;

@Component
public class FileDatabase {

    @Autowired
    private AtelierConfiguration config;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Sheet> data = new HashMap<>();

    @PreDestroy
    public void destroy() {
        save();
    }

    public Sheet get(String id) {
        return data.get(id);
    }

    public Set<String> keys() {
        return data.keySet();
    }

    public Collection<Sheet> sheets() {
        return data.values();
    }

    public String put(Sheet sheet) {
        String id = Long.toString(Math.abs(ThreadLocalRandom.current().nextLong()));
        sheet.setId(id);
        data.put(id, sheet);
        return id;
    }

    public void put(String id, Sheet sheet) {
        sheet.setId(id);
        data.put(id, sheet);
    }

    public Sheet remove(String id) {
        return data.remove(id);
    }

    public void save() {
        File dir = new File(config.getDatabasePath());
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir.mkdirs();

        for (String id : data.keySet()) {
            try {
                File file = new File(String.format("%s/%s.json", config.getDatabasePath(), id));
                objectMapper.writeValue(file, data.get(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        data.clear();
        try {
            var files = Files.walk(Paths.get(config.getDatabasePath()))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            for (var file : files) {
                if(file.isDirectory()) continue;
                String id = FilenameUtils.getBaseName(file.toString());
                Sheet sheet = objectMapper.readValue(file, Sheet.class);
                sheet.load();
                data.put(id, sheet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
