package atelier.server.dnd;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Sheet {
    @JsonProperty
    private int[] baseAbilityScores = new int[6];

    private int[] abilityScores = new int[6];
    private int[] abilityModifiers = new int[6];
    private int[] savingModifiers = new int[6];

    public void load() {
        for (int i = 0; i < 6; i++) {
            abilityScores[i] = baseAbilityScores[i];
            abilityModifiers[i] = Math.floorDiv((abilityScores[i] - 10), 2);
            savingModifiers[i] = abilityModifiers[i];
        }
    }
}
