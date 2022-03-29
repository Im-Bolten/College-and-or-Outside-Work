/**
 * @author Namita Singla
 *
 * Class representing Othello player
 */
abstract public class Player
    extends Thread {

  /**
   * Constructor
   *
   * @param id
   *            Player id
   * @param game
   *            Othello game
   */
  public Player(int id, Game game) {
    this.id = id;
    this.game = game;
    next = null;
    turn = null;
    score = 2;
  }

  public synchronized void turn() {
    turn = this;
    game.turn = this;
    notifyAll();
  }

  /**
   * Get player id
   *
   * @return player id
   */
  public int getPlayerID() {
    return id;
  }

  /**
   * Set next player
   *
   * @param p
   *            other player
   */
  public synchronized void setNext(Player p) {
    next = p;
  }

  /**
   * Get next player
   *
   * @return next player
   */
  public synchronized Player getNext() {
    return next;
  }

  abstract public void makeMove();

  abstract public void selectCell(int row, int column);

  /**
   * Set current score
   *
   * @param new
   *            score
   */
  public synchronized void setScore(int score) {
    this.score = score;
  }

  /**
   * Set current move row
   *
   * @param row
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Get current move row
   *
   * @return current move row
   */
  public int getRow() {
    return row;
  }

  /**
   * Set current move column
   *
   * @param col
   */
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Get current move column
   *
   * @return current move column
   */
  public int getCol() {
    return col;
  }

  public synchronized void run() {
    while (!game.gameOver(turn, game.getBoard())) {
      while (turn != this) {
        try {
          // wait until it is your turn
          wait();
          // wake up from turn method
        }
        catch (InterruptedException ex) {
          return;
        }
      }
      if (game.gameOver(turn, game.getBoard())) {
        break;
      }
      String msg = "";
      if (turn.id == 1) {
        msg += "Your turn   " + "Your score:" + turn.getScore()
            + " Machine score:" + turn.next.getScore();
      }
      else {
        msg += "Machine turn   " + "Your score:" + turn.next.getScore()
            + " Machine score:" + turn.getScore();
      }
      game.displayMessage(msg);
      while (true) {
        turn.makeMove();
        if (game.validMove(turn, game.getBoard())) {
          game.successor(turn, game.getBoard());
          break;
        }
        else if (game.mustPass(turn, game.getBoard())) {
          if (turn.id == 1) {
            game.displayMessage("No legal move:You must pass");
          }
          else {
            game.displayMessage("No legal move:Machine must pass");
          }
          break;
        }
        else {
          game.displayMessage("Illegal move!");
        }
      }
      turn = null;
      next.turn();
    }
    game.displayMessage(game.getWinner());
  }

  /**
   * Get current score
   *
   * @return score
   */
  public synchronized int getScore() {
    return score;
  }

  private Player turn; // go if turn==this
  private Player next; // the other player
  protected Game game;
  private int id; //player unique id
  private int score; // player score
  private int row; // current move Row
  private int col; // current move Col
}
