import javax.swing.*;
import java.awt.*;

public class BiteBoardGUI  extends JFrame {
    public final BiteBoardGameModel model;
    public final BiteBoardPanel boardPanel;
    public final JLabel turnlabel ;

    public BiteBoardGUI(BiteBoardGameModel model){
        super("Bite Board");
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        boardPanel = new BiteBoardPanel(model , this);
        add(boardPanel,BorderLayout.CENTER);
        turnlabel = new JLabel();
        updateTurnDisplay();
        JPanel bottom = new JPanel(new  FlowLayout(FlowLayout.LEFT));
        bottom.add(turnlabel);
        add(bottom,BorderLayout.SOUTH);
        setJMenuBar(createMenuBar());

    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem reset = new JMenuItem("Reset Game");
        JMenuItem exit = new JMenuItem("Exit");

        newGame.addActionListener(e -> promptNewGame());
        reset.addActionListener(e -> resetGame());
        exit.addActionListener(e -> System.exit(0));

        game.add(newGame);
        game.add(reset);
        game.add(exit);

        JMenu help = new JMenu("Help");
        JMenuItem rules = new JMenuItem("Rules");
        JMenuItem about = new JMenuItem("About");

        rules.addActionListener(e -> showRules());
        about.addActionListener(e -> showAbout());

        help.add(rules);
        help.add(about);

        mb.add(game);
        mb.add(help);

        return mb;
    }

    public void updateTurnDisplay() {
        if (model.isGameOver()) {
            turnlabel.setText("Game Over! Winner: Player " + model.getWinner());
        } else {
            turnlabel.setText("Turn: Player " + model.getCurrentPlayer());
        }
    }

    public void displayGameOver() {
        JOptionPane.showMessageDialog(this,
                "Player " + model.getWinner() + " wins!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        promptNewGame();
    }

    public void showRules() {
        JOptionPane.showMessageDialog(this,
                "- Click to bite chocolate.\n" +
                        "- Biting removes squares above and to the right.\n" +
                        "- Bottom-left square is toxic. Avoid it!",
                "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(this,
                "BiteBoard Game\nCreated in Java Swing.",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void resetGame() {
        model.reset(model.getRows(), model.getCols());
        boardPanel.repaint();
        updateTurnDisplay();
    }

    public void promptNewGame() {
        JTextField rField = new JTextField(String.valueOf(model.getRows()));
        JTextField cField = new JTextField(String.valueOf(model.getCols()));

        JPanel p = new JPanel(new GridLayout(2, 2));
        p.add(new JLabel("Rows:"));
        p.add(rField);
        p.add(new JLabel("Cols:"));
        p.add(cField);

        int result = JOptionPane.showConfirmDialog(this, p, "New Game",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int r = Integer.parseInt(rField.getText());
                int c = Integer.parseInt(cField.getText());
                model.reset(r, c);
                boardPanel.repaint();
                updateTurnDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        }
    }
}



