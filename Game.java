/**
 * @author Namita Singla
 * @author Robert Cohen
 *
 * Class representing othello game
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.GridLayout;
import javax.swing.*;

public class Game
    extends JFrame {

  private Board board;
  private Player player1;
  private Player player2;
  protected Player turn; // current player
  private JLabel messageBar;
  private boolean humanFirst = true;

  public Game() {
    board = new Board(this, 8, 8);
    messageBar = new JLabel("Game begin.");

    //Set properties of frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    setTitle("Othello");
    setSize(280, 300);

    //Add board and message bar to frame
    getContentPane().add(board, BorderLayout.CENTER);
    getContentPane().add(messageBar, BorderLayout.SOUTH);

    // Add a button to panel
    JPanel controlPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    getContentPane().add(controlPanel, BorderLayout.NORTH);
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
    controlPanel.add(Box.createHorizontalStrut(5));
    controlPanel.add(newGameButton);

    String humanString = "Human First";
    JRadioButton humanFirstBtn = new JRadioButton(humanString);
    humanFirstBtn.setMnemonic(KeyEvent.VK_H);
    humanFirstBtn.setSelected(true);
    // your code here, set action command
    
    humanFirstBtn.setActionCommand("Human");

    String computerString = "Computer First";
    JRadioButton computerFirstBtn = new JRadioButton(computerString);
    computerFirstBtn.setMnemonic(KeyEvent.VK_C);
    // your code here, set action command 
    
    computerFirstBtn.setActionCommand("Computer");

    //Group the radio buttons.
    ButtonGroup group = new ButtonGroup();
    group.add(humanFirstBtn);
    group.add(computerFirstBtn);

    //Put the radio buttons in a column in a panel.
    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
    radioPanel.add(humanFirstBtn);
    radioPanel.add(computerFirstBtn);
    controlPanel.add(Box.createHorizontalGlue());
    controlPanel.add(radioPanel);

    //Add action listener for new game button
    newGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        newGame();
      }
    });

    // Declare a WhoFirstListener btnListener
    // Add it for both radiobuttons
    // you code goes here
    humanFirstBtn.addActionListener(new WhoFirstListener(this));
    computerFirstBtn.addActionListener(new WhoFirstListener(this));

    // Start a new game the first time through
    newGame();
  }

  /**
   * Starts a new game
   */
  public void newGame() {

    player1 = new HumanPlayer(1, this);
    player2 = new MachinePlayer(2, this);
    board.reset();
    displayMessage("Game begin.");
    player1.start();
    player2.start();
    player1.setNext(player2);
    player2.setNext(player1);
    if (humanFirst) {
      player1.turn();
    }
    else {
      player2.turn();
    }
  }

  /**
   * Compute the score for the given player for a play at position (row, col)
   */
  public int score(Player player, Board bd) {
    int value = 0;
    int dx, dy;
    for (dx = -1; dx <= 1; dx++) {
      for (dy = -1; dy <= 1; dy++) {
        value = value + lineValue(player, bd, dx, dy);
      }
    }
    return value;
  }

  /**
   * Compute the value of a line starting at (row,col) position and in the
   * direction specified by (dx,dy).
   *
   * @param player
   *            Player
   * @param bd
   *            Board
   * @param dx
   *            X direction
   * @param dy
   *            Y direction
   * @return value of line
   */
  public int lineValue(Player player, Board bd, int dx, int dy) {
    int score = 0;
    int tempRow = player.getRow() + dx;
    int tempCol = player.getCol() + dy;
    Player opponent = player.getNext();
    if ( (dx == 0) && (dy == 0)) {
      return 0; // simple case
    }
    else {
      while (opponentPieces(bd, tempRow, tempCol, opponent)) {
        score++;
        tempRow = tempRow + dx;
        tempCol = tempCol + dy;
      }
      if (myPiece(bd, tempRow, tempCol, player)) {
        return score;
      }
      else {
        return 0;
      }
    }
  }

  /**
   * Check if piece (row,col) occupied by opponent
   *
   * @param bd
   *            Board
   * @param tempRow
   * @param tempCol
   * @param opponent
   * @return true if piece occupied by opponent
   */
  private boolean opponentPieces(Board bd, int tempRow, int tempCol,
                                 Player opponent) {
    return bd.inRange(tempRow, tempCol) && (bd.get(tempRow, tempCol) != 0)
        && (bd.get(tempRow, tempCol) == opponent.getPlayerID());
  }

  /**
   * Check if piece (row,col) occupied by me
   *
   * @param bd
   *            Board
   * @param tempRow
   * @param tempCol
   * @param me
   *            Me
   * @return true if piece occupied by me
   */
  private static boolean myPiece(Board bd, int tempRow, int tempCol,
                                 Player piece) {
    return bd.inRange(tempRow, tempCol) && (bd.get(tempRow, tempCol) != 0)
        && (bd.get(tempRow, tempCol) == piece.getPlayerID());
  }

  /**
   * Determine if the play at (row, col) is valid for the given player
   *
   * @param player
   *            Given player
   * @param bd
   *            board
   * @return true if (row, col) is a valid move
   */
  public boolean validMove(Player player, Board bd) {

    int row = player.getRow();
    int col = player.getCol();
    return (bd.inRange(row, col) && bd.get(row, col) == 0 && score(player,
        bd) > 0);
  }

  /**
   * No valid move. Player must pass.
   *
   * @param player
   * @param bd
   * @return true if player must pass
   */
  public boolean mustPass(Player player, Board bd) {
    int row, col;
    for (row = 0; row < board.getRow(); row++) {
      for (col = 0; col < board.getColumn(); col++) {
        player.setRow(row);
        player.setCol(col);
        if (bd.get(row, col) == 0) {
          if (validMove(player, bd)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Is game over?
   *
   * @param player
   *            Player trying to make move
   * @param bd
   *            Board
   * @return true if game is over
   */
  public boolean gameOver(Player player, Board bd) {
    // game has yet to start
    if ( (bd == null) || (player == null)) {
      return false;
    }
    return (bd.isFull() || (mustPass(player, bd) && mustPass(player
        .getNext(), bd)));
  }

  /**
   * Next move
   *
   * @param player
   *            Player making move
   * @param bd
   *            Board
   */
  public void successor(Player player, Board bd) {
    int howMany; // how many to flip?
    int dx, dy;
    int row = player.getRow();
    int col = player.getCol();
    Player opponent = player.getNext();
    bd.set(row, col, player.getPlayerID());
    player.setScore(player.getScore() + 1);
    for (dx = -1; dx <= 1; dx++) {
      for (dy = -1; dy <= 1; dy++) {
        howMany = lineValue(player, bd, dx, dy);
        if (howMany > 0) {
          flip(player, bd, dx, dy, howMany);
        }
        player.setScore(player.getScore() + howMany);
        opponent.setScore(opponent.getScore() - howMany);
      }
    }
  }

  /**
   * Flip the pieces (capture them) for the given player for the line starting
   * at (row,col) in (dx,dy) direction.
   *
   * @param player
   * @param bd
   * @param dx
   * @param dy
   * @param num
   */
  private void flip(Player player, Board bd, int dx, int dy, int num) {

    int tempRow = player.getRow();
    int tempCol = player.getCol();
    Player opponent = player.getNext();
    int score = 0;

    if ( (dx == 0) && (dy == 0)) {
      return;
    }
    else {
      tempRow = tempRow + dx;
      tempCol = tempCol + dy;
      while (opponentPieces(bd, tempRow, tempCol, opponent)) {
        score++;
        bd.set(tempRow, tempCol, player.getPlayerID());
        tempRow = tempRow + dx;
        tempCol = tempCol + dy;
      }
      if ( (score != num)
          || ( (bd.inRange(tempRow, tempCol))
              && (bd.get(tempRow, tempCol) != 0) && (bd.get(
          tempRow, tempCol) != player.getPlayerID()))) {
        System.out.println("Flip failed consistency check!");
      }
    }
  }

  /**
   * Get winner
   *
   * @return String
   */
  public String getWinner() {
    if (player1.getScore() > player2.getScore()) {
      return "You won";
    }
    else if (player2.getScore() > player1.getScore()) {
      return "Machine won";
    }
    else {
      return "Draw";
    }

  }

  /**
   * Return current player
   *
   * @return Current player
   */
  public Player getCurrentPlayer() {
    return turn;
  }

  /**
   * Return board for this game
   *
   * @return
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Diaplay message on board
   * @param msg Message to be displayed
   */
  public void displayMessage(String msg) {
    messageBar.setText(msg);
  }

  /**
   * Sets wether the human or the computer go first
   * @param val boolean
   */
  public void setHumanFirst(boolean val) {
    humanFirst = val;
  }

  /**
   * Main entry point
   */
  public static void main(String[] args) {
    Game frame = new Game();
    frame.setVisible(true);
  }

}
