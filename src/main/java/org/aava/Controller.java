/* ******************************************
 * CSCI205 -Software Engineering and Design
 * Fall2022
 * Instructor: Prof. Brian King
 *
 * Name: Alejandro Vargas Altamirano
 * Date: 12/28/2022
 * Time: 11:24 AM
 *
 * Project:MyWork
 * Package:intro
 * Class: Controller
 *
 * Description:
 *
 ********************************************
 */

package org.aava;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private Model theModel;

    AnimationTimer gameLoop;

    private int time = 0;

    private boolean lost = false;

    @FXML
    private Label score;

    @FXML
    private Rectangle skier;

    @FXML
    private Pane pane;

    @FXML
    private Circle l1;

    @FXML
    private Circle r1;


    public void setModel(Model model){
        this.theModel = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        pane.getChildren().get(1).setLayoutY(500);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        gameLoop.start();
    }

    @FXML
    public void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.LEFT)
            left();
        if (event.getCode() == KeyCode.RIGHT)
            right();
    }

    public void left(){
        if(skier.getX() > -284)
            skier.setX(skier.getX()-5*(time/4000.0+1));
    }
    public void right(){
        if (skier.getX() < 284)
            skier.setX(skier.getX()+5*(time/4000.0+1));
    }

    private void update(){
        time++;
        List<Node> temp = pane.getChildren();

        if (lost){
            return;
        }
        for(Node node: temp){
            if(node == skier)
                continue;
            if(node.getLayoutY() >0){
                node.setLayoutY(node.getLayoutY()-1*(time/5000.0)-1);
            }
            else{
                node.setLayoutY(400);
                if(node == l1)
                    node.setLayoutX(150*Math.random()+100);
                else
                    node.setLayoutX(150*Math.random()+300);
            }
            double skiY = pane.getChildren().get(
            pane.getChildren().indexOf(skier)).getLayoutY();

            double skiX = pane.getChildren().get(
                    pane.getChildren().indexOf(skier)).getLayoutX()+skier.getX();
            if(node.getLayoutY() - skiY < 1 && node.getLayoutY() - skiY > 0){
                if ((node == l1 && skiX > node.getLayoutX()) || (node == r1 && skiX < node.getLayoutX()))
                    lost = true;
                else {
                    int currScore = Integer.parseInt(score.getText());
                    score.setText(String.valueOf(currScore + 1));
                }
            }
        }

    }
}