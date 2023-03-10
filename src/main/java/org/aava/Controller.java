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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    /**
     * Reference to the model(may not be needed as this game is quite simple)
     */
    private Model theModel;

    /**
     * AnimationTimer for the skiing game
     */
    AnimationTimer gameLoop;

    /**
     * Keeps track of the time the game has been running
     */
    private int time = 0;

    private Object prev;
    /**
     * Boolean property determining if the player has lost yet
     */
    private boolean lost = false;

    /**
     * The label in the GUI that displys the score
     */
    @FXML
    private Label score;

    /**
     * The skier icon that the player controls
     */
    @FXML
    private ImageView skier;

    /**
     * The Pane container that holds the skier and the gates
     */
    @FXML
    private Pane pane;

    /**
     * This is the red gate on the left side of the screen that the skier must ski around
     */
    @FXML
    private Circle l1;

    /**
     * This is the blue gate on the right side of the screen that the skier must ski around
     */
    @FXML
    private Circle r1;

    @FXML
    private Label loseLabel;

    @FXML
    private Button home;

    @FXML
    private Button play;

    @FXML
    private Button back;

    @FXML
    private Button instructions;


    /**
     * Attaches the model to our controller(agian may not be necessary)
     * @param model
     */
    public void setModel(Model model){
        this.theModel = model;
    }

    /**
     * Initializes game, also sets the initial Y location of the gates(l1 and r1)
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if (this.pane != null) {
            startGame();
            gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }
            };

            gameLoop.start();
        }
    }

    private void startGame() {
        pane.getChildren().get(1).setLayoutY(500);
        pane.getChildren().get(0).setLayoutY(250);
        skier.setX(0);
        skier.setImage(new Image("leftSkier.png"));
        time = 0;
        lost = false;
        prev = r1;
        loseLabel.setText("");
    }

    /**
     * Event handler for when the player tries to control the skier with the arrow keys
     * @param event
     */
    @FXML
    public void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.S)
            left();
        if (event.getCode() == KeyCode.F)
            right();
    }

    @FXML
    public void restart(MouseEvent mouseEvent) {
        startGame();
        score.setText("0");
    }





    /**
     * Holds the logic for if the player hits the left arrow
     */
    public void left() {
        if (lost)
            return;
        if(skier.getX() > -284)
            skier.setX(skier.getX()-5.5*(time/4500.0+1));
        Image skiMan = new Image("leftSkier.png");
        skier.setImage(skiMan);
    }

    /**
     * Holds the logic for if the skier clicks the right arrow
     */
    public void right(){
        if (lost)
            return;
        if (skier.getX() < 284)
            skier.setX(skier.getX()+5.5*(time/4500.0+1));
        Image skiMan = new Image("rightSkier.png");
        skier.setImage(skiMan);
    }

    /**
     * This method is called in the handle method every frame and updates the view with the
     * appropriate changes
     */
    private void update(){
        time++;
        List<Node> temp = pane.getChildren();

        if (lost){
            return;
        }
        for(Node node: temp){
            if(node == skier || node == loseLabel)
                continue;
            if(node.getLayoutY() >0){
                node.setLayoutY(node.getLayoutY()-2*(time/5000.0)-1);
            }
            else{
                node.setLayoutY(500);
                if(node == l1)
                    node.setLayoutX(120*Math.random()+130);
                else
                    node.setLayoutX(120*Math.random()+300);
            }
            double skiY = pane.getChildren().get(
            pane.getChildren().indexOf(skier)).getLayoutY();

            double skiX = pane.getChildren().get(
                    pane.getChildren().indexOf(skier)).getLayoutX()+skier.getX();
            if(node.getLayoutY() - skiY <  50 && node.getLayoutY() - skiY > -10) {
                if ((node == l1 && skiX+60 > node.getLayoutX()) || (node == r1 && skiX-10 < node.getLayoutX())){
                    lost = true;
                    loseLabel.setText("YOU LOSE :(");
                }
                else if (prev != node){
                    int currScore = Integer.parseInt(score.getText());
                    score.setText(String.valueOf(currScore + 1));
                    prev = node;
                }
            }
        }

    }

    @FXML
    public void goHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/homePage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) home.getScene().getWindow();

        stage.setScene(new Scene(root));
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/homePage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();

        stage.setScene(new Scene(root));
    }

    @FXML
    public void instructions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Instructions.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) instructions.getScene().getWindow();

        stage.setScene(new Scene(root));
    }

    @FXML
    public void play(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/skiing.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) play.getScene().getWindow();

        stage.setScene(new Scene(root));
    }
}