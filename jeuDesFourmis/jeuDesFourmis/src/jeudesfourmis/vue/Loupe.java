/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.vue;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jeudesfourmis.controller.plate.Game;
import jeudesfourmis.model.Fourmi;
import jeudesfourmis.model.Fourmiliere;

/**
 *
 * @author Documents
 */
public class Loupe extends Application{
    
    private Game plate;
    Label[][] cellules = new Label[11][11];
    
    public Loupe(Game plate){
        this.plate=plate;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        
        pane.setGridLinesVisible(true);
        
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(1), new BorderWidths(1))));
        
        
        for(int i = 0; i < 11 ; i++){ //même principe que la fenêtre principal mais en plus petit
            for(int j = 0; j < 11 ; j++){
                Label cell = new Label(" ");
                cell.setMinWidth(30); // pour effectuer un effet de zoom tout les composants sont agrandit
                cell.setMinHeight(30);
                cell.setMaxWidth(30);
                cell.setMaxHeight(30);
                cell.setPrefSize(30,30);
                pane.add(cell, i, j);
                cellules[i][j] = cell;
            }
        }
        
        StackPane root = new StackPane();
        root.getChildren().add(pane);

        Scene scene = new Scene(root, 330,330);
        
        primaryStage.setOnCloseRequest(e -> stop());
        
        primaryStage.setTitle("Loupe");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    Service<Long> loopService = new Service() {
         @Override
         protected Task createTask() {
            Task<Long> loopTask = new Task() {
            @Override
            protected Long call() throws Exception {
                while(true){
                    if(isCancelled()){
                        break;
                    }
                    int x = plate.getCurrentX();
                    int y = plate.getCurrentY();
                    afficher(x,y);
                }
                return null;
            }
            };
             return loopTask;
        };
     };
    
    public void afficher(int x, int y){
        Fourmiliere f = plate.getF();
        int cptx = 0;
        for (int i = x-5; i < x+5; i++) {
            if ( !(i < 0 || i > plate.getF().getLargeur())){
                int cpty = 0;
                for (int j = x-5; j < y+5; j++) {
                    if(!(j<0|| j > plate.getF().getHauteur())){
                        if(f.getMur(i, j)){
                            cellules[cptx][cpty].setBackground(new Background( new BackgroundFill( Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        else if(f.contientFourmi(i, j)){
                            for(Fourmi fourmi : f.getLesFourmis()){
                                if(fourmi.getX()==i && fourmi.getY() == j){
                                    if(fourmi.porte()){
                                        cellules[cptx][cpty].setText("O");
                                        cellules[cptx][cpty].setStyle("-fx-text-fill: blue; -fx-font-size: 25px;");
                                    }
                                    else{
                                        cellules[cptx][cpty].setText("O");
                                        cellules[cptx][cpty].setStyle("-fx-text-fill: green; -fx-font-size: 25px;");
                                    }
                                    break;
                                }
                            }
                        }
                        else{
                            int taille = (25 * f.getQteGraines(i, j)) / f.getQMax();
                            cellules[cptx][cpty].setText("O");
                            cellules[cptx][cpty].setStyle("-fx-text-fill: red; -fx-font-size: "+ taille +"px;");
                        }
                    }
                    cpty++;
                }
                cptx++;
            }
            
        }
    }
    
    public void start(){
        this.loopService.reset();
        this.loopService.start();
    }        

    public void stop(){
        this.loopService.cancel();
    }
}
