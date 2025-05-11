/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package characters;

import java.net.URL;
import javax.swing.ImageIcon;

public abstract class GameCharacter {

    public ImageIcon icon;
    
    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int bonusDamageTurns = 0;
    private final CharacterFabric name;


    public GameCharacter(int level, int health, int damage, CharacterFabric name) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
        this.name=name;
    }

    public void setBonusDamageTurns(int turns) {
        this.bonusDamageTurns = turns;
    }
    
    
    public boolean hasBonusDamage() {
        return bonusDamageTurns > 0;
    }

    public void loseBonusDamageTurn() {
        if (bonusDamageTurns > 0) {
            bonusDamageTurns--;
        }
    }


    public int getBonusDamageTurns() {
        return bonusDamageTurns;
    }

    public void setPhoto(String path) {
        icon = new ImageIcon(path);
    }
    
    public void setPhoto(URL imageUrl) {
        if (imageUrl != null) {
            this.icon = new ImageIcon(imageUrl);
        } else {
            System.err.println("Image URL is null");
        }
    }

    public ImageIcon getPhoto() {
        return icon;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel() {
        this.level++;
    }

    public void addHealth(int health) {
        this.health += health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void addMaxHealth(int maxHealth) {
        this.maxHealth += maxHealth;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public CharacterFabric getName() {
        return name;
    }

    public String getStringName(){
       return name.getString();
    }
}

