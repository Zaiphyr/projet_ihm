/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.controller.parameters;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author julie
 */
public class Etiquette extends HBox {
    private Label l = new Label();
    private Label nombre = new Label();
    
    public Etiquette(String lab){
        super();
        l.setText(lab);
        
    }

    /**
     * @return the l
     */
    public Label getL() {
        return l;
    }

    /**
     * @param l the l to set
     */
    public void setL(Label l) {
        this.l = l;
    }

    /**
     * @return the nombre
     */
    public Label getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(Label nombre) {
        this.nombre = nombre;
    }
    
    
    
}
