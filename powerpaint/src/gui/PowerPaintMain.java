package gui;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main class to start the program.
 * @author fangchen
 * @version 1.0
 *
 */
public final class PowerPaintMain {
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private PowerPaintMain() {
    	
        throw new IllegalStateException();
    }

    /**
     * main method.
     * @param theArgs Command line arguments 
     */
    public static void main(final String[] theArgs) {
    	
    	
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainPanel().start();
            }
        });

    }

}
