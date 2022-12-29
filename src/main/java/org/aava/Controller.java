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

import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class Controller {
    private Model theModel;

    @FXML
    private Rectangle skier;

    @FXML
    private AnchorPane pane;

    public void setModel(Model model){
        this.theModel = model;
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
            skier.setX(skier.getX()-4);
    }
    public void right(){
        if (skier.getX() < 284)
            skier.setX(skier.getX()+4);
    }
}