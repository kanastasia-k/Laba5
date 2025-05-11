/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characters;

public enum CharacterFabric {
    Human("Human"), 
    BARAKA("Baraka"), 
    LIU_KANG("Liu Kang"), 
    SONYA_BLADE("Sonya Blade"), 
    SUB_ZERO("Sub-Zero"), 
    SHAO_KAHN("Shao Kahn");
    
    private final String name;
    
    CharacterFabric(String name) {
        this.name = name;
    }
    
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
    public String getString() {
        return name;
    }
}
