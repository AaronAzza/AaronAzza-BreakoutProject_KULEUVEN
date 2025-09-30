package breakout.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import breakout.BreakoutState;
import breakout.GameMapParser;

/**
 * LEGIT
 */
public class BreakoutApplication
{
    private static final String MAP_ENVIRONMENT_VARIABLE = "BREAKOUT_MAP";
    
    private static final int FACTOR = 100;
    
    /**
     * Constructor made private, indicating this class is not meant to be instantiated.
     */
    private BreakoutApplication()
    {
        // NOP
    }

    public static void main(String[] args)
    {
        var state = createState();
        
        EventQueue.invokeLater(() -> {
            GameView gameView = new GameView(state, FACTOR); 
            JFrame frame = new JFrame("Breakout");
            frame.getContentPane().add(gameView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
    private static BreakoutState createState()
    {
        return GameMapParser.parse(getMap(), 100 * FACTOR, 30 * FACTOR);
    }
    
    private static String[] getMap()
    {
        var environmentVariables = System.getenv();

        if ( environmentVariables.containsKey(BreakoutApplication.MAP_ENVIRONMENT_VARIABLE) )
        {
            var value = environmentVariables.get(BreakoutApplication.MAP_ENVIRONMENT_VARIABLE);

            switch ( value )
            {
            case "OGP":
                return GameMapParser.OGP_MAP;
                
            case "OOP":
                return GameMapParser.OOP_MAP;
                
            default:
                System.out.println("Invalid map selected; must be either OGP or OOP");
                System.exit(-1);   
                return null; // compiler does not seem to realize System.exit does not return
            }
        }
        else
        {
            // Change the map to be used here
            return GameMapParser.descr2;
        }
    }
}
