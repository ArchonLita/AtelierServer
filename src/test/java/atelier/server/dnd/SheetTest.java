package atelier.server.dnd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class SheetTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generateScoresAndModifiers() throws JsonProcessingException {
        Sheet sheet = new Sheet();
        sheet.setBaseAbilityScores(Ability.scores(15, 14, 13, 12, 10, 9));
        sheet.load();

        assertEquals(Ability.scores(15, 14, 13, 12, 10, 9), sheet.getAbilityScores());
        assertEquals(Ability.scores(2, 2, 1, 1, 0, -1), sheet.getAbilityModifiers());
        assertEquals(Ability.scores(2, 2, 1, 1, 0, -1), sheet.getSavingModifiers());

        var data = objectMapper.readTree(objectMapper.writeValueAsString(sheet));
        var keys = IteratorUtils.toList(data.fieldNames());
        assertEquals(List.of("baseAbilityScores"), keys);
    }

}
