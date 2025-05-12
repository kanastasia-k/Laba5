/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characters;

/**
 * Класс, представляющий игрового персонажа ShaoKahn 
 * Наследует базовые характеристики от класса GameCharacter и задает уникальные параметры персонажа
 * @author kozhe
 */
public class ShaoKahn extends GameCharacter {
    /**
     * Конструктор класса ShaoKahn.
     * Инициализирует персонажа со стандартными характеристиками:
     * - Уровень: 1
     * - Максимальное здоровье: 100
     * - Базовый урон: 12
     * - Тип персонажа: CharacterFabric.SHAO_KAHN
     */
    public ShaoKahn() {
        super(3, 100, 30, CharacterFabric.SHAO_KAHN);
    }
}
