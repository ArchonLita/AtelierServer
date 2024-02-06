package atelier.server.dnd;

import java.util.EnumMap;
import java.util.EnumSet;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Sheet {
    @JsonProperty
    private String id;

    @JsonProperty
    private EnumMap<Ability, Integer> baseAbilityScores = new EnumMap<>(Ability.class);

    private EnumMap<Ability, Integer> abilityScores = new EnumMap<>(Ability.class);
    private EnumMap<Ability, Integer> abilityModifiers = new EnumMap<>(Ability.class);
    private EnumMap<Ability, Integer> savingModifiers = new EnumMap<>(Ability.class);

    private EnumSet<Ability> savingProficiencies = EnumSet.noneOf(Ability.class);

    private int proficiencyBonus = 2; // TODO implement with level

    public void load() {
        for (var ability : Ability.values()) {
            abilityScores.put(ability, baseAbilityScores.get(ability));
            abilityModifiers.put(ability, Math.floorDiv(abilityScores.get(ability) - 10, 2));
            savingModifiers.put(ability, abilityModifiers.get(ability));

            if (savingProficiencies.contains(ability))
                savingModifiers.put(ability, savingModifiers.get(ability) + proficiencyBonus);
        }
    }
}
