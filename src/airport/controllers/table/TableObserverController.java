/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers.table;

import airport.model.utils.Observer;

/**
 *
 * @author osman
 */
public class TableObserverController implements Observer {

    private Runnable update;

    public TableObserverController(Runnable update) {
        this.update = update;
    }

    @Override
    public void update() {
        update.run();
    }

}
