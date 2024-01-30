package atelier.server.dnd;

import java.util.EnumMap;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Ability {
    STRENGTH,
    DEXTERITY,
    INTELLIGENCE,
    WISDOM,
    CHARISMA,
    CONSTITUTION;

    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }

    public static EnumMap<Ability, Integer> scores(int... scores) {
        var map = new EnumMap<Ability, Integer>(Ability.class);
        for (var ability : values())
            map.put(ability, scores[ability.ordinal()]);
        return map;
    }
}
