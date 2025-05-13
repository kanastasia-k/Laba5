/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characterFactory;

import characters.*;

/**
 * Фабрика персонажей для создания экземпляров {@link Baraka}
 * @author kozhe
 */
public class BarakaFactory extends CharacterFactory{
    /**
     *
     * @return
     */
    public GameCharacter createCharacter(){
        return new Baraka();
    }
}
