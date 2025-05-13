/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actionFactory;

import actions.*;

/**
 * Конретная фабрика для создания действия {@link Debuff}
 * @author kozhe
 */
public class DebuffFactory extends ActionFactory{

    /**
     *
     * @return
     */
    @Override
    public Action createAction() {
        return new Debuff();
    }
}