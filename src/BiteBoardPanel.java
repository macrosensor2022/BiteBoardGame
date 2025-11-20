import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BiteBoardPanel extends JPanel implements MouseListener {

    private final BiteBoardGameModel model;
    private final BiteBoardGUI gui;
    private int squareSize = 40;
    private final int PADDING = 20;

    public BiteBoardPanel(BiteBoardGameModel model, BiteBoardGUI gui) {
        this.model = model;
        this.gui = gui;
        addMouseListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rows = model.getRows();
        int cols = model.getCols();

        squareSize = Math.min(
                (getWidth() - 2 * PADDING) / cols,
                (getHeight() - 2 * PADDING) / rows
        );

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                int drawX = PADDING + c * squareSize;
                int drawY = PADDING + (rows - 1 - r) * squareSize;

                if (model.isCellAvailable(r, c)) {
                    g.setColor(new Color(150, 100, 60));
                    g.fillRect(drawX, drawY, squareSize, squareSize);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(drawX, drawY, squareSize, squareSize);
                }

                g.setColor(Color.BLACK);
                g.drawRect(drawX, drawY, squareSize, squareSize);
            }
        }

        // Mark toxic square
        if (model.isCellAvailable(0, 0)) {
            g.setColor(Color.RED);
            g.drawString("T", PADDING + squareSize / 3, PADDING + (rows * squareSize) - squareSize / 3);
        }
    }

    private Point getBoardPosition(int x, int y) {
        int rows = model.getRows();
        int cols = model.getCols();

        int col = (x - PADDING) / squareSize;
        int rowFromTop = (y - PADDING) / squareSize;
        int row = rows - 1 - rowFromTop;

        if (col < 0 || col >= cols || row < 0 || row >= rows)
            return null;

        return new Point(row, col);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (model.isGameOver())
            return;

        Point p = getBoardPosition(e.getX(), e.getY());
        if (p == null)
            return;

        if (model.biteSquares(p.x, p.y)) {

            if (model.isGameOver()) {
                gui.updateTurnDisplay();
                gui.displayGameOver();
                repaint();
                return;
            }

            model.switchPlayer();
            gui.updateTurnDisplay();
            repaint();
        }
    }

    // Unused interface methods
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
