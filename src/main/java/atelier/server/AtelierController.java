package atelier.server;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import atelier.server.dnd.Ability;
import atelier.server.dnd.Sheet;
import jakarta.annotation.PostConstruct;

@RestController
class AtelierController {

    @Autowired
    private FileDatabase db;

    @PostConstruct
    public void initialize() {
        db.load();
    }

    @GetMapping("/sheets")
    public Set<Long> listSheetsIds() {
        return db.keys();
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
    @ResponseBody
    public Sheet getSheet(@PathVariable Long id) {
        return db.get(id);
    }

}
