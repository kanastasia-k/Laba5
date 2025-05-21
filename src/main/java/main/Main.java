/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import view.GameView;

/**
 * Главный класс приложения, который запускает графический интерфейс игры
 * @author kozhe
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EventQueue.invokeLater(() -> {
            try {
                new GameView().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
