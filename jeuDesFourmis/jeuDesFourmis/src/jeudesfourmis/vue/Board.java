/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.vue;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jeudesfourmis.controller.parameters.SliderBetter;
import jeudesfourmis.controller.parameters.Etiquette;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jeudesfourmis.controller.plate.Game;
import jeudesfourmis.model.Fourmiliere;

/**
 *
 * @author julie
 */
public class Board extends Application {
    
    private int tailleX = 50;
    private int tailleY = 50;
    private int nbGraine = 5;
    private boolean play = false;
    private Game plate;
    private Etiquette nbrGraine = new Etiquette("nombre de graines");
    private Etiquette nbrFourmi = new Etiquette("nombre de fourmis");
    private Etiquette nbIte = new Etiquette("nombre d'itération");
    //private SimpleIntegerProperty graine = new SimpleIntegerProperty(0) ,fourmi = new SimpleIntegerProperty(0) ,ite = new SimpleIntegerProperty(0);
    private ImageView playIV = new ImageView(new Image(getClass().getResourceAsStream("play.png")));
    private ImageView pauseIV = new ImageView(new Image(getClass().getResourceAsStream("pause.png")));
    
    
    /*Service<Long> etiquetteService = new Service() { essai du service pour compter le nombre de fourmis, graines et itérations
         @Override
         protected Task createTask() {
            Task<Long> etiquetteTask = new Task() {
            @Override
            protected Long call() throws Exception {
                while(true){
                    
                    graine.set(plate.nbGraine());
                    fourmi.set(plate.nbFourmis());
                    ite.set(plate.getNbIteration());
                if(isCancelled()){
                    break;
                }
                }
                return null;
            }
            };
             return etiquetteTask;
        };
     };*/

    @Override
    public void start(Stage primaryStage) {
        
        plate = new Game(new Fourmiliere(50, 50, 5)); //Plateau de jeu initialiser avec les données du sujet à par pour le nombre max de graîne qui a été choisi par nos soins
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        Button loupe = new Button("Loupe");
        loupe.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Loupe l = new Loupe(plate);
                try {
                    l.start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
                l.start(); //ouvre une nouvelle fenêtre pour zoomer
            }
        });
        
        
        Button playPause = new Button();
        playPause.setGraphic(playIV);
        playPause.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(play){ //vérification si le jeu est en pause ou non
                   plate.stop();
                   play = false;
                   playPause.setGraphic(playIV);
               }else{
                    plate.start();
                    play = true;
                    playPause.setGraphic(pauseIV);
                }
            }
        });
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //ouverture de la fenetre de confirmation 
                alert.initOwner(primaryStage); 
                alert.setTitle("Demande de confirmation"); 
                alert.setHeaderText("attention cela va réinitialiser le plateau"); 
                alert.setContentText("Souhaitez-vous réinitialiser le plateau ?"); 
                final Optional<ButtonType> result = alert.showAndWait(); 
                result.ifPresent(button -> { 
                    if (button == ButtonType.OK) { 
                        plate.init();
                    } 
                });
                
            }
        });
        
        Button init = new Button("Init");
        init.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                plate.reinit(tailleX, tailleY, nbGraine);
            }
        });

        Button sizePlatePlus = new Button("Size +");
        sizePlatePlus.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
                alert.initOwner(primaryStage); 
                alert.setTitle("Demande de confirmation"); 
                alert.setHeaderText("attention cela va augmenter la taille du plateau"); 
                alert.setContentText("Souhaitez-vous augmenter la taille le plateau ?"); 
                final Optional<ButtonType> result = alert.showAndWait(); 
                result.ifPresent(button -> { 
                    if (button == ButtonType.OK) {
                        tailleX++;
                        tailleY++;
                    } 
                });
            }
        });
        Button sizePlateMoins = new Button("Size -");
        sizePlateMoins.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                if(tailleX > 20){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
                    alert.initOwner(primaryStage); 
                    alert.setTitle("Demande de confirmation"); 
                    alert.setHeaderText("attention cela va diminuer la taille du plateau"); 
                    alert.setContentText("Souhaitez-vous diminuer la taille du plateau ?"); 
                    final Optional<ButtonType> result = alert.showAndWait(); 
                    result.ifPresent(button -> { 
                        if (button == ButtonType.OK) {
                            tailleX--;
                            tailleY--;
                        } 
                    });
                }
                
            }
        });
        Button maxGrainePlus = new Button("Nombre de graine max +");
        maxGrainePlus.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                nbGraine++;
                
            }
        });
        Button maxGraineMoins = new Button("Nombre de graine +");
        maxGraineMoins.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(nbGraine > 0){
                    nbGraine--;
                }
            }
        });

        SliderBetter vitesse = new SliderBetter("vitesse", 0, 100, 10);
        vitesse.getTfValue().textProperty().addListener(e -> plate.setVitesse(vitesse.getSlide().getValue()));
        
        //organisation de la fenêtre d'affichage
        HBox hb_irq = new HBox();
        hb_irq.getChildren().addAll(init, reset, quit);

        VBox vb_plate = new VBox();
        vb_plate.getChildren().addAll(plate, hb_irq);

        HBox hb_sizePlate = new HBox();
        hb_sizePlate.getChildren().addAll(sizePlatePlus, sizePlateMoins);
        
        HBox hb_maxGraine = new HBox();
        hb_maxGraine.getChildren().addAll(maxGrainePlus, maxGraineMoins);
        
        VBox vb_param = new VBox();
        vb_param.getChildren().addAll(loupe, playPause, nbrGraine, nbrFourmi, nbIte, hb_sizePlate, hb_maxGraine, vitesse);

        HBox hb = new HBox();
        hb.getChildren().addAll(vb_plate, vb_param);

        StackPane root = new StackPane();
        root.getChildren().add(hb);

        Scene scene = new Scene(root, 1000,800);
        
        /*this.nbrGraine.getNombre().textProperty().bind(this.graine.asString());
        this.nbrFourmi.getNombre().textProperty().bind(this.fourmi.asString());
        this.nbIte.getNombre().textProperty().bind(this.ite.asString());*/
        
        
        /*this.etiquetteService.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(graine);*/
        plate.afficher();
        primaryStage.setTitle("une fourmilière");
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
