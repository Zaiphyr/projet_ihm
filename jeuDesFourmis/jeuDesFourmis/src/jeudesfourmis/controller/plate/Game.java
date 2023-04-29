/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesfourmis.controller.plate;

import java.util.Random;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import jeudesfourmis.model.Fourmiliere;

/**
 *
 * @author julie
 */
// doit initialiser la partie et la fenêtre
public class Game extends GridPane{

    
    private Fourmiliere f ;
    private Label[][] cellules = new Label[0][0];
    private double vitesse;
    private int nbIteration = 0;
    
    public int random(int min, int max){
        Random rand = new Random(0);
        int a;
        a = min+rand.nextInt(max-min);
        return a;
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
    
    private void init(){
        for (int i=0;i<cellules.length;i++){
            for (int j=0;j<cellules.length;j++){
                this.getChildren().remove(cellules[i][j]);
            }
        }
        
            
        
        cellules = new Label[f.getLargeur()][f.getHauteur()];
        
        for(int i = 0; i <= f.getLargeur(); i++){
            for(int j = 0; j <= f.getHauteur(); j++){
                if(i == 0 || i == f.getLargeur() || j == 0 || j == f.getHauteur()){
                    f.setMur(i, j, true);
                }
                //set fourmi & set graine
                int randFourmi = random(1,3);
                if(randFourmi == 1){
                    f.ajouteFourmi(i, j);
                }
                f.setQteGraines(i, j, random(0,5));
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
                this.add(cell, i, j);
                cellules[i][j] = cell;
            }
        }
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
                }
                return null;
            }
            };
             return playTask;
        };
     };
public void start(){
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
    
    
    
}
