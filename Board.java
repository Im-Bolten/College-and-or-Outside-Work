/**
 * @author Namita Singla
 *
 * Class representing Othello Board
 */
import java.awt.*;
import java.awt.event.*;

public class Board
    extends Canvas {

  /**
   * Constructor
   * Create the board
   * @param game
   * @param row number of rows
   * @param col number of columns
   */
  public Board(Game game, int row, int column) {
    this.game = game;
    this.row = row;
    this.column = column;
    board = new int[row][column];
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        Point p = event.getPoint();
        Board.this.game.getCurrentPlayer().selectCell(
            p.x / columnWidth,
            p.y / rowHeight);
      }
    });
  }

  /**
   * Reset board
   *
   */

  public void reset() {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        board[i][j] = 0;
      }
    }
    set(3, 3, 1);
    set(3, 4, 2);
    set(4, 3, 2);
    set(4, 4, 1);
    repaint();
  }

  /**
   * Set the piece in board
   *
   * @param row
   *            of the piece
   * @param col
   *            of the piece
   * @param playerId
   */
  public void set(int row, int col, int playerId) {
    if (inRange(row, col)) {
      board[row][col] = playerId;
      repaint();
    }
    else {
      throw new OutOfBoardException("Accessing out of Board");
    }
  }

  public void paint(Graphics g) {
    Dimension d = getSize();
    columnWidth = d.width / column;
    rowHeight = d.height / row;

    int fontSize = Math.min(rowHeight, columnWidth) - 8;
    Font font = new Font("Sans serif", Font.BOLD, fontSize);

    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, d.width, d.height);
    g.setColor(Color.black);
    int i, j;
    for (i = 0; i <= row; i++) {
      g.drawLine(0, i * rowHeight, d.width, i * rowHeight);
    }
    for (j = 0; j <= column; j++) {
      g.drawLine(j * columnWidth, 0, j * columnWidth, d.height);
    }

    g.setFont(font);
    FontMetrics fm = g.getFontMetrics();
    int adjX = (columnWidth - fm.stringWidth("H")) / 2;
    int adjO = (columnWidth - fm.stringWidth("M")) / 2;
    int adjy = (rowHeight - fm.getHeight()) / 2 + fm.getAscent();

    for (i = 0; i < row; i++) {
      for (j = 0; j < column; j++) {
        if (board[i][j] != 0) {
          switch (board[i][j]) {
            case 1:
              g.setColor(Color.white);
              g.drawString(
                  "H",
                  i * columnWidth + adjX,
                  j * rowHeight + adjy);
              break;
            case 2:
              g.setColor(Color.black);
              g.drawString(
                  "M",
                  i * columnWidth + adjO,
                  j * rowHeight + adjy);
              break;
          }
        }
      }
    }
  }

  /**
   * Return no. of rows in board
   * @return no. of rows
   */
  public int getRow() {
    return row;
  }

  /**
   * Return no. of columns in board
   * @return no. of columns
   */
  public int getColumn() {
    return column;
  }

  /**
   * Is board full?
   * @return true if board is full
   */
  public boolean isFull() {
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < column; c++) {
        if (get(r, c) == 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Is (row,col) in range of the board?
   * @param row Row
   * @param col Column
   * @return true if (row,col) in range
   */
  public boolean inRange(int row, int col) {
    return ( (0 <= row) && (row < this.row) && (0 <= col) && (col < this.column));
  }

  /**
   * Get piece in board.
   * @param row Row
   * @param col Column
   * @return Player Id
   */
  public int get(int row, int col) {
    if (inRange(row, col)) {
      return (board[row][col]);
    }
    else {
      throw new OutOfBoardException("Accessing out of Board");
    }
  }

  private Game game;
  private int row, column;
  private int board[][];
  private int columnWidth, rowHeight;
}
