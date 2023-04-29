/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.controller.parameters;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author jtriboui
 */
public class SliderBetter extends HBox {
    
        private Label l = new Label();
        private TextField tfValue = new TextField();
        private Slider slide = new Slider();
        
        public SliderBetter(String sLabel, double min, double max, double value){
            super();
            l.setText(sLabel);
            slide.setMax(max);
            slide.setMin(min);
            slide.setValue(value);
            
            Bindings.bindBidirectional(tfValue.textProperty(), slide.valueProperty(), new NumberStringConverter());
            this.getChildren().addAll(l, tfValue, slide);
        } 

    /**
     * @return the l
     */
    public Label getL() {
        return l;
    }

    /**
     * @return the tfValue
     */
    public TextField getTfValue() {
        return tfValue;
    }


    /**
     * @return the slide
     */
    public Slider getSlide() {
        return slide;
    }

    public DoubleProperty valueProperty(){
        return slide.valueProperty();
    }
        
}
