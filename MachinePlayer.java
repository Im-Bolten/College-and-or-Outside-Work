/**
 * @author Namita Singla
 *
 * Class representing Machine Player
 */

public class MachinePlayer
    extends Player {

  /**
   * Constructor
   * @param id Player id
   * @param game Othello game
   */
  public MachinePlayer(int id, Game game) {
    super(id, game);
  }

  /**
   * Make best possible move
   */
  public synchronized void makeMove() {
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
    }
    Board board = game.getBoard();
    Strategy strategy = new OthelloStrategy();
    strategy.bestMove(this, game);
  }

  /**
   * Select cell
   */
  public void selectCell(int rows, int cols) {
    setRow(rows);
    setCol(cols);
  }
}
