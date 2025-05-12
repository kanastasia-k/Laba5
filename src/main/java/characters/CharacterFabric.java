/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characters;
/**
 * Фабричное перечисление для создания игровых персонажей.
 * Предоставляет доступ ко всем доступным персонажам игры и методам их создания.
 * 
 */
public enum CharacterFabric {

    /**
     *Персонаж: Вы
     */
    Human("Human"), 

    /**
     * Персонаж: Baraka
     */
    BARAKA("Baraka"), 

    /**
     * Персонаж: Liu Kang
     */
    LIU_KANG("Liu Kang"), 

    /**
     * Персонаж Sonya Blade
     */
    SONYA_BLADE("Sonya Blade"), 

    /**
     * Персонаж Sub-Zero
     */
    SUB_ZERO("Sub-Zero"), 

    /**
     * Персонаж Shao Kahn
     */
    SHAO_KAHN("Shao Kahn");
    
    private final String name;
    
    CharacterFabric(String name) {
        this.name = name;
    }
    
    /**
     * Фабричный метод для создания экземпляра персонажа
     * @return новый экземпляр соответствующего персонажа
     * @implNote Для каждого типа персонажа возвращается соответствующая реализация
     */
    public GameCharacter createCharacter() {
    return switch (this) {
        case Human -> new Human();
        case BARAKA -> new Baraka();
        case LIU_KANG -> new LiuKang();
        case SONYA_BLADE -> new SonyaBlade();
        case SUB_ZERO -> new SubZero();
        case SHAO_KAHN -> new ShaoKahn();
    };
}

    /**
     * Возвращает читаемое имя персонажа
     * @return строковое представление имени персонажа
     */
    public String getString() {
        return name;
    }
}
