/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actions;

import characters.GameCharacter;

public class Debuff extends Action {

    @Override
    public String getType() {
        return "Debuff";
    }

    @Override
    public void realization(GameCharacter human, GameCharacter enemy, String enemyActionType) {
        switch (enemyActionType) {
            case "Debuff", "Heal" -> {
            }
            case "Defense" -> {
                if (Math.random()<0.75){
                    enemy.setBonusDamageTurns(enemy.getLevel());
                }
            }
            case "Hit" -> {
                enemy.setBonusDamageTurns(1);
            }
        }
    }
}