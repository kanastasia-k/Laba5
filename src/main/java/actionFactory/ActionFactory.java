/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actionFactory;

import actions.Action;

/**
 * Абстрактная фабрика для создания объектов типа {@link Action}
 * @author kozhe
 */
public abstract class ActionFactory {

    /**
     *
     * @return
     */
    public abstract Action createAction();
}
