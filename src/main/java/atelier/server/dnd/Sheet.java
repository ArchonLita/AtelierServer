package atelier.server.dnd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Sheet {
    @Getter
    private int[] baseAbilityScores = new int[6];
}
