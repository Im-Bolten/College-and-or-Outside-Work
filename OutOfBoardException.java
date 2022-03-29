/**
 * @author Namita Singla
 *
 * Out of Board Exception
 */
public class OutOfBoardException
    extends RuntimeException {

  /**
   * Constructs a OutOfBoardtException with no detail message.
   */
  public OutOfBoardException() {
  }

  /**
   * Constructs a OutOfBoardException with a detail message.
   * @param msg the detail mesage pertaining to this exception.
   */
  public OutOfBoardException(String msg) {
    super(msg);
  }
}
