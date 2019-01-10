/*
* Tic Tac Toe
*
* Class: Engine.java
*/
package tictactoefx;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thom
 * 
 * This is the entire Engine class, and it is responsible for setting up a new
 * game, stopping the game depending on the circumstance, running its AI player
 * and detecting if a player get a row of crosses (X) or noughts (O).
 */
public class Engine {
    final String CROSS = "X";
    final String NOUGHT = "O";
    protected AtomicInteger turnCount = new AtomicInteger();
    protected String boardPos[][] = new String[3][3]; //Board positions
    public Node nodePos[][] = new Node[3][3]; //Positions of all nodes, especially tiles
    private boolean isPlayerTurn = true; //Human player will go first
    private String playerName; //Each player will be named as X or O
    private String howPlayerWon; //Determine which row is successful
    
    /**
     * Resets the entire game. Board positions and tiles from the game board
     * will be reset.
     * 
     * @param tiles Tiles from the game board
     */
    public void newGame(ObservableList<Node> tiles){
        turnCount = new AtomicInteger();
        System.out.println("Turns are reset to " + turnCount);
        isPlayerTurn = true;
        playerName = null;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                boardPos[i][j] = null;
            }
        }
        tiles.forEach(tile -> {
            ((Button)tile).setDisable(false);
            ((Button)tile).setText("");
        });
    }
    
    /**
     * Stops the entire game if a player wins of if the game draws.
     * 
     * @param tiles Tiles from the game board
     */
    public void stopGame(ObservableList<Node> tiles){
        if(playerName != null)
            System.out.println(howPlayerWon + playerName + " wins!"); //Winner is found
        else
            System.out.println("Draw"); //Draw game
        tiles.forEach(button -> {
            ((Button)button).setDisable(true); //Disable all buttons once game is over
        });
    }
    
    /**
     * The AI player will determine which tile it wants to set. It will only
     * pay attention to the empty tiles without having to interfere with the
     * other player's filled tiles. The AI player will not choose another tile
     * if game is over.
     * 
     * @param tiles Tiles from the game board
     */
    public void runAIPlayer(ObservableList<Node> tiles){
        ObservableList<Node> emptySquares = FXCollections.observableArrayList();
        Random generateRandom = new Random();
        for(int i=0;i<tiles.size();i++){
            Button tileButtonFromNode = (Button)tiles.get(i);
            // Add empty tiles to the ObservableList
            if("".equals(tileButtonFromNode.getText())){
                emptySquares.add(tiles.get(i));
            }
        }
        // AI player will choose tiles that are available
        if(emptySquares.size() > 0){
            int randomIndex = generateRandom.nextInt(emptySquares.size());
            Node squareToBeChosen = emptySquares.get(randomIndex);
            Button futureSquare = (Button)squareToBeChosen;
            futureSquare.setText(getNoughtOrCross());
            int rowIndex = GridPane.getRowIndex(futureSquare);
            int colIndex = GridPane.getColumnIndex(futureSquare);

            boardPos[rowIndex][colIndex] = getNoughtOrCross();

            if(winDetected()){
                stopGame(tiles);
            }
        }
        // Do not continue if there are no more empty tiles
        else{
            stopGame(tiles);
        }
    }
    
    /**
     * Returns X or O
     * @return 
     */
    public String getNoughtOrCross(){
        if(isPlayerTurn)
            return CROSS;
        else
            return NOUGHT;
    }
    
    /**
     * Switch turn after setting a tile
     */
    public void switchTurn(){
        if(isPlayerTurn)
            isPlayerTurn = false;
        else
            isPlayerTurn = true;
        turnCount.getAndIncrement();
    }
    
    /**
     * Determines if the 3 tiles in a row have the same string
     * @return 
     */
    public boolean winDetected(){
        // Horizontal rows
        if(boardPos[0][0]!= null && boardPos[0][0]==boardPos[0][1] && boardPos[0][1]==boardPos[0][2]){
            playerName = boardPos[0][0];
            howPlayerWon = "Horizontal 1 to 3. ";
            return true;
        }
        else if(boardPos[1][0]!= null && boardPos[1][0]==boardPos[1][1] && boardPos[1][1]==boardPos[1][2]){
            playerName = boardPos[1][0];
            howPlayerWon = "Horizontal 4 to 6. ";
            return true;
        }
        else if(boardPos[2][0]!= null && boardPos[2][0]==boardPos[2][1] && boardPos[2][1]==boardPos[2][2]){
            playerName = boardPos[2][0];
            howPlayerWon = "Horizontal 7 to 9. ";
            return true;
        }
        // Vertical rows
        else if(boardPos[0][0]!= null && boardPos[0][0]==boardPos[1][0] && boardPos[1][0]==boardPos[2][0]){
            playerName = boardPos[0][0];
            howPlayerWon = "Vertical 1 to 7. ";
            return true;
        }
        else if(boardPos[0][1]!= null && boardPos[0][1]==boardPos[1][1] && boardPos[1][1]==boardPos[2][1]){
            playerName = boardPos[0][1];
            howPlayerWon = "Vertical 2 to 8. ";
            return true;
        }
        else if(boardPos[0][2]!= null && boardPos[0][2]==boardPos[1][2] && boardPos[1][2]==boardPos[2][2]){
            playerName = boardPos[0][2];
            howPlayerWon = "Vertical 3 to 9. ";
            return true;
        }
        // Diagonal
        else if(boardPos[0][0]!= null && boardPos[0][0]==boardPos[1][1] && boardPos[1][1]==boardPos[2][2]){
            playerName = boardPos[0][0];
            howPlayerWon = "Diagonal 1 to 9. ";
            return true;
        }
        else if(boardPos[0][2]!= null && boardPos[0][2]==boardPos[1][1] && boardPos[1][1]==boardPos[2][0]){
            playerName = boardPos[0][2];
            howPlayerWon = "Diagonal 3 to 7. ";
            return true;
        }
        else{
            playerName = null;
            return false;
        }
    }
}
