/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characters;

/**
 * Класс, представляющий игрового персонажа SonyaBlade 
 * Наследует базовые характеристики от класса GameCharacter и задает уникальные параметры персонажа
 * @author kozhe
 */
public class SonyaBlade extends GameCharacter {
    /**
     * Конструктор класса SonyaBlade.
     * Инициализирует персонажа со стандартными характеристиками:
     * - Уровень: 1
     * - Максимальное здоровье: 100
     * - Базовый урон: 12
     * - Тип персонажа: CharacterFabric.SONYA_BLADE
     */
    public SonyaBlade() {
        super(1, 80, 16, CharacterFabric.SONYA_BLADE);
    }
}
