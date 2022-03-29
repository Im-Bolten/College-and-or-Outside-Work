/**
 * @author Namita Singla
 *
 * Class representing Human Player
 */
public class HumanPlayer
    extends Player {

  /**
   * Constructor
   * @param id Player id
   * @param game Othello game
   */
  public HumanPlayer(int id, Game game) {
    super(id, game);
  }

  synchronized public void makeMove() {

    try {
      wait();
    }
    catch (InterruptedException e) {
    }
  }

  /**
   * Select cell
   */
  synchronized public void selectCell(int x, int y) {
    setRow(x);
    setCol(y);
    notifyAll();
  }
}
