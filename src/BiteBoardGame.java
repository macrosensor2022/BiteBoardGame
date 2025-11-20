import javax.swing.SwingUtilities;
public class BiteBoardGame{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->{
            BiteBoardGameModel model = new BiteBoardGameModel(6,8);
            new BiteBoardGUI(model).setVisible(true);

        });
    }
}