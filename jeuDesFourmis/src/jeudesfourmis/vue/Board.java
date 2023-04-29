/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.vue;

import jeudesfourmis.controller.parameters.SliderBetter;
import jeudesfourmis.controller.parameters.Etiquette;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jeudesfourmis.controller.plate.Game;
import jeudesfourmis.model.Fourmiliere;

/**
 *
 * @author julie
 */
public class Board extends Application {

    private boolean play = false;
    @Override
    public void start(Stage primaryStage) {
        
        Game plate = new Game(new Fourmiliere(50, 50, 5));
        
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        Button loupe = new Button("Loupe");
        Button playPause = new Button("Play/Pause");
        playPause.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               
                if(play){
                   plate.stop();
               }else{
                    plate.start();
                }
            }
        });
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        
        Button init = new Button("Init");
       
        
        

        Etiquette nbrGraine = new Etiquette("nombre de graines");
        //nbrGraine.setNombre(plate.nbGraine());
        Etiquette nbrFourmi = new Etiquette("nombre de fourmis");
       // nbrFourmi.setNombre((plate.nbFourmis()));
        Etiquette nbIte = new Etiquette("nombre d'itération");

        Button sizePlatePlus = new Button("Plateau +1");
        Button sizePlateMoins = new Button("Plareau -1");
        Button maxGrainePlus = new Button("Max graine +1");
        Button maxGraineMoins = new Button("Max graine -1");

        SliderBetter vitesse = new SliderBetter("vitesse", 0, 100, 10);
        vitesse.getTfValue().setOnAction(e -> plate.setVitesse(vitesse.getSlide().getValue()));
        

        HBox hb_irq = new HBox();
        hb_irq.getChildren().addAll(init, reset, quit);

        VBox vb_plate = new VBox();
        vb_plate.getChildren().addAll(plate, hb_irq);

        HBox hb_sizePlate = new HBox();
        hb_sizePlate.getChildren().addAll(sizePlateMoins, sizePlatePlus);
        
        HBox hb_maxGraine = new HBox();
        hb_maxGraine.getChildren().addAll(maxGraineMoins, maxGrainePlus);
        
        VBox vb_param = new VBox();
        vb_param.getChildren().addAll(loupe, playPause, nbrGraine, nbrFourmi, nbIte, hb_sizePlate, hb_maxGraine, vitesse);

        HBox hb = new HBox();
        hb.getChildren().addAll(vb_plate, vb_param);

        StackPane root = new StackPane();
        root.getChildren().add(hb);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
