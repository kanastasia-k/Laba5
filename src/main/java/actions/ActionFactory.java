/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actions;

/**
 *
 * @author kozhe
 */
public class ActionFactory {
    public enum ActionType {
        HIT,
        DEFENSE,
        DEBUFF,
        HEAL
    }
    
    public static Action createAction(ActionType type) {
        return switch (type) {
            case HIT -> new Hit(); 
            case DEFENSE -> new Defense();
            case DEBUFF -> new Debuff();
            case HEAL -> new Heal();
            default -> throw new IllegalArgumentException("Unknown action type: " + type);
        };
    }
    
    
}
