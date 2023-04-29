/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.controller.plate;

import java.util.Random;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import jeudesfourmis.model.Fourmi;
import jeudesfourmis.model.Fourmiliere;

/**
 *
 * @author julie
 */
// doit initialiser la partie et la fenêtre
public class Game extends GridPane{

    private int currentX;
    private int currentY;
    private Fourmiliere f ;
    private Label[][] cellules = new Label[0][0];
    private double vitesse;
    private int nbIteration = 0;
    
    public int random(int min, int max){
        return  (int) (Math.random() * max + min);
    }
    
    public Game(Fourmiliere f){
        
        super();
        this.f = f;
        
        
        
        init();
        
        this.setGridLinesVisible(true);
        
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(1), new BorderWidths(1))));
    }
    
    
    public int nbGraine(){
        int nbG = 0;
        for(int i = 0; i <= getF().getHauteur(); i++){
            for(int j = 0; j <= getF().getLargeur(); j++){
               nbG = nbG + getF().getQteGraines(i, j);
            }
        }
        return nbG;   
    }
    
    public int nbFourmis(){
        int nbF = 0;
        for(int i = 0; i <= getF().getHauteur(); i++){
            for(int j = 0; j <= getF().getLargeur(); j++){
                if(getF().contientFourmi(i, j) == true){
                    nbF++ ;
                }
            }
        }
        return nbF;
    }
    
    public void placeMur(){
        f.setMur(REMAINING, REMAINING, true); //TODO
    }
    
    public void placeFourmi(){
        f.ajouteFourmi(REMAINING, REMAINING);//TODO
    }

    
    
    /**
     * @return the cellules
     */
    public Label[][] getCellules() {
        return cellules;
    }

    /**
     * @param cellules the cellules to set
     */
    public void setCellules(Label[][] cellules) {
        
        this.cellules = cellules;
        
    }

    /**
     * @return the f
     */
    public Fourmiliere getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(Fourmiliere f) {
        this.f = f;
    }
    
    public void init(){
        for (int i=0;i<cellules.length;i++){
            for (int j=0;j<cellules.length;j++){
                this.getChildren().remove(cellules[i][j]);
            }
        }
        
            
        
        cellules = new Label[f.getLargeur()][f.getHauteur()];
        
        for(int i = 0; i < f.getLargeur(); i++){
            for(int j = 0; j < f.getHauteur(); j++){
                //set graine
                f.setQteGraines(i, j,random(0,f.getQMax()));
            }      
        }
        
        for (int i = 0; i < f.getLargeur(); i++) {
            for (int j = 0; j < f.getHauteur(); j++) {
                
                Label cell = new Label(" ");
                cell.setMinWidth(10);
                cell.setMinHeight(10);
                cell.setMaxWidth(10);
                cell.setMaxHeight(10);
                cell.setPrefSize(10,10);
                int x = i;
                int y = j;
                cell.setOnMouseClicked(e -> eventLabel(e,x,y));
                cell.setOnMouseEntered(e -> changeCurent(x,y));
                this.add(cell, i, j);
                cellules[i][j] = cell;
            }
        }
        afficher();
    }
    
    public void reinit(int x , int y, int nbG){
        this.f = new Fourmiliere(x,y,nbG);
        init();
    }
    
    Service<Long> playService = new Service() {
         @Override
         protected Task createTask() {
            Task<Long> playTask = new Task() {
            @Override
            protected Long call() throws Exception {
                while(true){
                Thread.sleep((long) (1000-(9*getVitesse())));
                if(isCancelled()){
                    break;
                }
                getF().evolue();
                setNbIteration(getNbIteration() + 1);
                afficher();
                }
                return null;
            }
            };
             return playTask;
        };
     };
public void start(){
      playService.reset();
      playService.start();
    }

    public void stop(){
        playService.cancel();
    }

    /**
     * @return the vitesse
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * @param vitesse the vitesse to set
     */
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * @return the nbIteration
     */
    public int getNbIteration() {
        return nbIteration;
    }

    /**
     * @param nbIteration the nbIteration to set
     */
    public void setNbIteration(int nbIteration) {
        this.nbIteration = nbIteration;
    }
    
    public void afficher(){
        for (int i = 0; i < f.getLargeur(); i++) {
            for (int j = 0; j < f.getHauteur(); j++) {
                if(f.getMur(i, j)){
                    cellules[i][j].setBackground(new Background( new BackgroundFill( Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else if(f.contientFourmi(i, j)){
                    for(Fourmi fourmi : f.getLesFourmis()){
                        if(fourmi.getX()==i && fourmi.getY() == j){
                            if(fourmi.porte()){
                                cellules[i][j].setText("O");
                                cellules[i][j].setStyle("-fx-text-fill: blue; -fx-font-size: 8px;");
                            }
                            else{
                                cellules[i][j].setText("O");
                                cellules[i][j].setStyle("-fx-text-fill: green; -fx-font-size: 8px;");
                            }
                            break;
                        }
                    }
                }
                else{
                    int taille = (8 * f.getQteGraines(i, j)) / f.getQMax();
                    cellules[i][j].setText("O");
                    cellules[i][j].setStyle("-fx-text-fill: red; -fx-font-size: "+ taille +"px;");
                }
            }
        }
    }
    
    public void eventLabel(MouseEvent e,int x, int y){
        
        if (e.isShiftDown()){
            f.ajouteFourmi(x, y);
        }else{
            f.setMur(x, y,true);
        }
        
        afficher();
    }

    /**
     * @return the cuurentX
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * @return the currentY
     */
    public int getCurrentY() {
        return currentY;
    }

    private void changeCurent(int x, int y) {
        this.currentX = x;
        this.currentY = y;
    }
    
}
