/**
 * @author Namita Singla
 *
 * Othello Strategy.
 */
public class OthelloStrategy
    implements Strategy {

  /**
   * Determine the best move possible for the player
     return the location in player as (ROW, COL)
   */
  public void bestMove(Player player, Game game) {

    int currentMax = -1;
    int s;
    int rows = -1, cols = -1;
    Board bd = game.getBoard();

    // Select the move that gives highest score
    for (int r = 0; r < bd.getRow(); r++) {
      for (int c = 0; c < bd.getColumn(); c++) {
        player.setRow(r);
        player.setCol(c);
        if (game.validMove(player, bd)) {
          s = game.score(player, bd);
          if (currentMax < s) {
            currentMax = s;
            rows = r;
            cols = c;
          }
        }
      }
      ;
    }
    player.selectCell(rows, cols);
  }
}
