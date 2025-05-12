/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actions;

/**
 * Фабрика для создания объектов действий в игре
 * Позволяет создавать конкретные реализации по типам действий
 */
public class ActionFactory {

    /**
     * Перечисление возможных типов действий 
     */
    public enum ActionType {

        /**
         * Тип: атака
         */
        HIT,

        /**
         * Тип: защита
         */
        DEFENSE,

        /**
         * Тип: ослабление противника
         */
        DEBUFF,

        /**
         * Тип: восстановление здоровья
         */
        HEAL
    }
    
    /**
     * Создает и возвращает конкретную реализацию действия по указанному типу
     * 
     * @param type тип создаваемого действия
     * @return экземпляр класса, реализующего действие
     * @throws IllegalArgumentException если передан неизвестный тип действия
     */
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
