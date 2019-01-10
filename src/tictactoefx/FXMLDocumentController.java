/*
* Tic Tac Toe
*
* Class: FXMLDocumentController.java
*/
package tictactoefx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thom
 * 
 * This class manages the controls of all buttons visible to Tic Tac Toe scene.
 * The tiles on the game board are buttons where the user and AI can place the
 * crosses (X) or noughts (O) unless the tiles are already filled.
 * New Game button resets the entire Tic Tac Toe game.
 */
public class FXMLDocumentController implements Initializable {

    /*
    * Initialize the FXML containers or controls
    * gameBoard is used to get the tiles
    */
    @FXML
    private GridPane gameBoard;

    private final Engine engine; //Run the Engine class
    private ObservableList<Node> tiles; //List of tiles on the game board

    public FXMLDocumentController() {
        this.engine = new Engine();
    }
    
    /**
     * Runs the Engine class and sets the text of a clicked button to a cross (X)
     * or nought (O). The human player plays as 'X' while the AI from the Engine
     * class plays as 'O'. It will also switch a turn once the button is clicked,
     * but it will not do so if a tile is already marked. The Engine will detect
     * if the 'X' or 'O' are lined up 3 in a row. If so, the game will be stopped
     * and all tiles are disabled unless if the user clicks the New Game button.
     * 
     * @param event The event is triggered when one tile is clicked
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button clickedSquare = (Button) event.getTarget();
        String btnLabel = clickedSquare.getText();

        // Will work if a tile has an empty text
        if (btnLabel.equals("")) {
            clickedSquare.setText(engine.getNoughtOrCross());
            int rowIndex = GridPane.getRowIndex(clickedSquare);
            int colIndex = GridPane.getColumnIndex(clickedSquare);
            engine.boardPos[rowIndex][colIndex] = engine.getNoughtOrCross(); //'X' or 'O' will be set to a specific board position
            engine.switchTurn(); //Switch turn after tile text is set
            
            if (engine.winDetected()) {
                engine.stopGame(gameBoard.getChildren()); //Stop game if three crosses/noughts are lined up a row
            }
            // Continue playing if crosses/nought are not lined up
            else{
                tiles = gameBoard.getChildren();
                engine.runAIPlayer(tiles); //Let the AI player choose a tile
                engine.switchTurn(); //Switch turn after tile text is set
            }

            // Game is a draw and stop the game if no more tiles can be filled
            if (engine.turnCount.get() == 9 && !engine.winDetected()) {
                engine.stopGame(gameBoard.getChildren());
            }
        }
        else {
            // Error if tile is already filled
            System.out.println("You cannot change this labeled square. Try again.");
        }
    }

    /**
     * Resets the entire Tic Tac Toe game
     * @param event The event is triggered when New Game is clicked
     */
    public void newGameButtonAction(ActionEvent event) {
        tiles = gameBoard.getChildren();
        engine.newGame(tiles);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiles = gameBoard.getChildren();
        int i = 0;
        for(int j=0;j<3;j++){
            for(int k=0;k<3;k++){
                engine.nodePos[j][k] = tiles.get(i+j+k);
            }
            i+=2;
        }
    }

}