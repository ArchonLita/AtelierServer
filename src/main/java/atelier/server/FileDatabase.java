package atelier.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import atelier.server.dnd.Sheet;

@Component
public class FileDatabase {

    @Autowired
    private AtelierConfiguration config;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<Long, Sheet> data = new HashMap<>();

    public Sheet get(long id) {
        return data.get(id);
    }

    public long put(Sheet sheet) {
        long id = Math.abs(ThreadLocalRandom.current().nextLong());
        data.put(id, sheet);
        return id;
    }

    public void put(long id, Sheet sheet) {
        data.put(id, sheet);
    }

    public Sheet remove(long id) {
        return data.remove(id);
    }

    public void save() {
        File dir = new File(config.getDatabasePath());
        dir.delete();
        dir.mkdirs();

        for (long id : data.keySet()) {
            try {
                mapper.writeValue(new File(String.format("%s/%d.json", config.getDatabasePath(), id)), data.get(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static long getIdFromFilename(File file) {
        var temp = file.toString().split("/");
        temp = temp[temp.length - 1].split("\\.");
        if (temp.length == 0)
            return -1;
        return Long.parseLong(temp[0]);
    }

    public void load() {
        data.clear();
        try {
            var files = Files.walk(Paths.get(config.getDatabasePath()))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            for (var file : files) {
                long id;
                try {
                    id = getIdFromFilename(file);
                } catch (NumberFormatException e) {
                    continue;
                }

                Sheet sheet = mapper.readValue(file, Sheet.class);
                data.put(id, sheet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
