package atelier.server;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import atelier.server.dnd.Ability;
import atelier.server.dnd.Sheet;
import jakarta.annotation.PostConstruct;

@CrossOrigin
@RestController
class AtelierController {

    @Autowired
    private FileDatabase db;

    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void initialize() {
        db.load();
    }

    @GetMapping("/sheets")
    public Collection<Sheet> listSheetsIds() {
        return db.sheets();
    }

    @GetMapping("/sheets/new")
    public Sheet newSheet() {
        Sheet sheet = new Sheet();

        sheet.setBaseAbilityScores(Ability.scores(15, 14, 13, 12, 10, 9));
        sheet.load();
        db.put(sheet);

        return sheet;
    }

    @GetMapping("/sheets/{id}")
    public String getSheet(@PathVariable String id) throws JsonProcessingException {
        //TODO find better solution to this
        return mapper.writeValueAsString(db.get(id));
    }

}
