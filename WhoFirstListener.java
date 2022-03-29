import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The Listener for ActionEvents on Radio Buttons
 */

public class WhoFirstListener
    implements ActionListener {
  private Game game;

  public WhoFirstListener(Game game) {
    this.game = game;
  }

  public void actionPerformed(ActionEvent e) {
      // Your code goes here!!
	  String command = e.getActionCommand();
	  if (command.equals("Human"))
		  game.setHumanFirst(true);
	  else
		  game.setHumanFirst(false);
	  game.newGame();
  }

}
