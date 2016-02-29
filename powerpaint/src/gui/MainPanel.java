package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import tool.Ellipse;
import tool.Line;
import tool.Pencil;
import tool.Rectangle;
import tool.ToolInterface;


/**
 * A JPanel that holds all the component.
 * @author fangchen
 * @version 1.0
 */
public class MainPanel extends JPanel {

    /**
     * A generated serial version UID for object Serialization.
     */
    public static final long serialVersionUID = -8438576029794021570L;

    /**
     * slider param.
     */
    public static final int MAX_SLIDER_WIDTH = 20;

    /**
     * slider param.
     */
    public static final int INITIAL_SLIDER_WIDTH = 5;
    
    /**
     * slider param.
     */
    public static final int MAJOR_TICK_SPACING = 5;
    
    /**
     * slider param.
     */
    public static final int MINOR_TICK_SPACING = 1;
    
    /**
     * default tool.
     */
    public static final String DEFALT_TOOL = "Pencil";

    /**
     * uw icon.
     */
    protected static final String UW_ICON = "./images/uw_icon.jpg";
    
    /**
     * the button to undo all changes.
     */
    private static JMenuItem myUndoButton;
    
    /**
     * the button to save.
     */
    private static JMenuItem mySaveButton;
    
    /**
     * The menu bar.
     */
    //private JMenuBar myMenuBar;
    
    /**
     * the drawing area.
     */
    private DrawPanel myDrawPanel;

    /**
     * the tool bar.
     */
    private JToolBar myToolBar;
    
    /**
     * The action list of different tool.
     */
    private List<ToolAction> myToolActions;
    
    /**
     * The color chooser action.
     */
    private ColorChooserAction myColorAction;

    /**
     * The toggle button group.
     */
    private ButtonGroup myToggleButtons;

    /**
     * The radio button group.
     */
    private ButtonGroup myRadioButtons;

    
    /**
     * Default constructor.
     */
    public MainPanel() {
        super();
        this.setLayout(new BorderLayout());
        
        this.setComponent();
        
    }
    
    /**
     * Initialize the components.
     */
    private void setComponent() {
        myDrawPanel = null;
        myToolBar = null;
        myToolActions = new ArrayList<>(); 
        myToolActions.add(new ToolAction(new Pencil()));  
        myToolActions.add(new ToolAction(new Line()));  
        myToolActions.add(new ToolAction(new Rectangle()));  
        myToolActions.add(new ToolAction(new Ellipse()));  
        myColorAction = new ColorChooserAction("Color Chooser");
    }
    

    /**
     * Create a menu bar.
     * @param theFrame the main frame
     * @return the JMenuBar at the north
     */
    private JMenuBar createMenuBar(final JFrame theFrame) {
        //create a JMenuBar and will return it
        final JMenuBar menuBar = new JMenuBar(); 
        
        //create file menu
        final JMenu fileMenu = createFileMenu(theFrame);
        menuBar.add(fileMenu);   //file menu adding is done
        
        //create Options menu
        final JMenu optionMenu = new JMenu("Options");
        optionMenu.setMnemonic(KeyEvent.VK_O);
        //add Square/Circle only check box to option menu
        final JCheckBoxMenuItem check = new JCheckBoxMenuItem("Square/Circle only");
        check.setMnemonic(KeyEvent.VK_S);
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawPanel.setSquareCircleOnly(check.isSelected());
            }   
        });
        optionMenu.add(check);
        optionMenu.addSeparator();
        //add Thickness sub menu to option menu
        final JMenu thicknessSubMenu = new JMenu("Thickness");
        thicknessSubMenu.setMnemonic(KeyEvent.VK_T);
        final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, MAX_SLIDER_WIDTH,
                               INITIAL_SLIDER_WIDTH);
        slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        slider.setMinorTickSpacing(MINOR_TICK_SPACING);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                final int value = slider.getValue();
                if (value > 0) {
                    myDrawPanel.setWidth(value);
                }
            }
        });
        
        thicknessSubMenu.add(slider);
        optionMenu.add(thicknessSubMenu);
        optionMenu.addSeparator();
        //add color menu to option menu
        final JMenuItem colorItem = new JMenuItem("Color...");
        colorItem.setMnemonic(KeyEvent.VK_C);
        colorItem.addActionListener(myColorAction);
        optionMenu.add(colorItem);
        menuBar.add(optionMenu); //Option menu adding is done
        
        //create Tools menu
        final JMenu tools = new JMenu("Tools");
        tools.setMnemonic(KeyEvent.VK_T);
        myRadioButtons = new ButtonGroup();
        for (ToolAction ta : myToolActions) {
            final JRadioButton tb = new JRadioButton(ta.getTool());
            tb.addActionListener(ta);
            if (ta.myTool.equals(DEFALT_TOOL)) {
                tb.setSelected(true);
            }
            myRadioButtons.add(tb);
            tools.add(tb);
        }
        menuBar.add(tools); //tools menu adding is done
        
        //create Help menu
        final JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        final String aboutTitle = "About";
        final JMenuItem about = new JMenuItem(aboutTitle);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final ImageIcon icon = new ImageIcon(UW_ICON);
                final Image smallImage = 
                                icon.getImage().getScaledInstance(50, 
                                                                  50,  
                                                                  java.awt.Image.SCALE_SMOOTH);
                final ImageIcon smallIcon = new ImageIcon(smallImage);
                JOptionPane.showMessageDialog(null, "TCSS 305 PowerPaint \n"
                                + "Winter 2016 \n" 
                                + "fangchen",
                                aboutTitle, 
                                JOptionPane.OK_OPTION, 
                                smallIcon);
                
            }
            
        });
        about.setMnemonic(KeyEvent.VK_A);
        help.add(about);
        
        menuBar.add(help); //help menu adding is done
        
        return menuBar;
    }
    
    /**
     * create file menu.
     * @param theFrame the main frame
     * @return the file menu
     */
    private JMenu createFileMenu(final JFrame theFrame) {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        //add save item
        final JMenuItem save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_V);
        save.addActionListener(new SaveListener());
        
        mySaveButton = save;
        mySaveButton.setEnabled(false);
        fileMenu.add(save);
        fileMenu.addSeparator();
        
        
        
        //add JMenu item - Undo to file menu
        final JMenuItem undo = new JMenuItem("Undo all changes");
        myUndoButton = undo;
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawPanel.clearPanel();
                myDrawPanel.repaint();
                setUndoButton(false);
            }
            
        });
        undo.setEnabled(false);
        undo.setMnemonic(KeyEvent.VK_U);
        fileMenu.add(undo);
        fileMenu.addSeparator();
        //add JMenu item - Exit to file menu
        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theFrame.dispatchEvent(new WindowEvent(theFrame, WindowEvent.WINDOW_CLOSING)); 
            }
        });
        exit.setMnemonic(KeyEvent.VK_X);
        fileMenu.add(exit);
        return fileMenu;
    }
    
    /**
     * 
     * @return A new JPanel for drawing
     */
    private DrawPanel createDrawPanel() {
        final DrawPanel drawPanel = new DrawPanel();
        drawPanel.setVisible(true);
        
        return drawPanel;
    }
    
    /**
     * create a tool bar.
     * @return the created tool bar
     */
    private JToolBar createToolBar() {
        
        
        
        final JToolBar toolbar = new JToolBar();
        myToggleButtons = new ButtonGroup();
        for (ToolAction ta : myToolActions) {
            final JToggleButton tb = new JToggleButton(ta);
            if (ta.myTool.equals(DEFALT_TOOL)) {
                tb.setSelected(true);
                myDrawPanel.setCurrentTool(ta.myTool);
                
            }
            myToggleButtons.add(tb);
            //btngrp.add(tb);
            toolbar.add(tb);
        }
        
        //btngrp.clearSelection();
        
        return toolbar;
    }
    
    /**
     * Add components and show the frame.
     */
    public void start() {
        //Create and set up the window.
        final JFrame frame = new JFrame("PowerPaint");
        
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //create main panel
        final MainPanel mainPanel = new MainPanel();
        mainPanel.setOpaque(true);
 
        //add draw panel to main panel
        mainPanel.myDrawPanel = mainPanel.createDrawPanel();
        mainPanel.add(mainPanel.myDrawPanel, BorderLayout.CENTER);
        
        //create tool bar and add it to main panel
        mainPanel.myToolBar = mainPanel.createToolBar();
        mainPanel.add(mainPanel.myToolBar, BorderLayout.PAGE_END);
        
        //put main panel to frame
        frame.setContentPane(mainPanel);
        
        //create menu bar and set it to frame
        frame.setJMenuBar(mainPanel.createMenuBar(frame));
        
        //Display the window
        frame.pack();
        
        //set frame icon
        final ImageIcon img = new ImageIcon(UW_ICON);
        frame.setIconImage(img.getImage());
        
        frame.setVisible(true);
        
    }
    
    /**
     * set the undo button.
     * @param theVisible if the button is enabled.
     */
    public static void setUndoButton(final boolean theVisible) {
        myUndoButton.setEnabled(theVisible);
        mySaveButton.setEnabled(theVisible);
    }
    
    /**
     * the inner class for actions of menu items.
     * @author fangchen
     * @version 1.0
     *
     */
    private class ToolAction extends AbstractAction {
        /**
         * A generated serialization ID.
         */
        private static final long serialVersionUID = 1136558691940875710L;
        
        /**
         * the tool name.
         */
        private String myTool;
        
        /**
         * the tool icon.
         */
        private Icon myIcon;
        
//        /**
//         * the large icon.
//         */
//        private Icon myLargeIcon;
        
        /**
         * the default constructor.
         * @param theTool a tool interface
         */
        ToolAction(final ToolInterface theTool) {
            super(theTool.getClass().getSimpleName());
           
            myTool = theTool.getTool();
            myIcon = theTool.getIcon();
            //myLargeIcon = theTool.getLargeIcon();
            
            //put icon
            putValue(Action.SMALL_ICON, myIcon);
            putValue(Action.LARGE_ICON_KEY, myIcon);
            // tool tips
            putValue(Action.SHORT_DESCRIPTION, myTool.toString());
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            //System.out.println("set shape to:" + myTool);
            myDrawPanel.setCurrentTool(myTool);
            final Enumeration<AbstractButton> elements = myRadioButtons.getElements();
            while (elements.hasMoreElements()) {
                final JRadioButton rb = (JRadioButton) elements.nextElement();
                if (rb.getText().equals(myTool)) {
                    rb.setSelected(true);
                    break;
                }
            }
            
            final Enumeration<AbstractButton> toggleButtonElements = 
                            myToggleButtons.getElements();
            while (toggleButtonElements.hasMoreElements()) {
                final JToggleButton tb = (JToggleButton) toggleButtonElements.nextElement();
                if (tb.getText().equals(myTool)) {
                    tb.setSelected(true);
                    break;
                }
            }
            
        }
   
        /**
         * Get the tool.
         * @return the tool to be used to draw.
         */
        public String getTool() {
            return myTool;
        }
    } //end of inner class ToolAction

    /**
     * the inner class for actions of color chooser.
     * @author fangchen
     * @version 1.0
     *
     */
    private class ColorChooserAction extends AbstractAction {

        /**
         * generated serial number.
         */
        private static final long serialVersionUID = -5291375218192020972L;
        
        /**
         * default constructor.
         * @param theText is the title
         */
        ColorChooserAction(final String theText) {
            super(theText);

            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            putValue(ACCELERATOR_KEY, 
                     KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Color result = JColorChooser.showDialog(null, "A Color Chooser", null);
            if (result != null) {
                myDrawPanel.setColor(result);
            }
            
        }
        
    } //end of inner class Color chooser
    
    /**
     * save listener.
     * @author fangchen
     * @version 1.0
     */
    private class SaveListener extends AbstractAction {

		/**
		 *	generated serial.
		 */
		private static final long serialVersionUID = -3709547693826891802L;

		@Override
		public void actionPerformed(ActionEvent theEvent) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
			fileChooser.setFileFilter(filter);
			final int returnVal = fileChooser.showSaveDialog(MainPanel.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				final File file = fileChooser.getSelectedFile();
				
				if (file != null) {
                    int n = 1; //indicator of saving file or not, 1 means not
                     /*
                      * user provide a file name and wants to save.
                      * This file name could be a new file name or
                      * an existing file name in this directory.
                      */
                    if (file.exists()) {         //try to overwrite
                         
                         /*open a new dialog, saying: [<filename> already exists. Do you 
                          * want to replace it?] There should be two buttons, one Cancel
                          * and the other Replace.
                         */
                        final Object[] options = {"Replace", "Cancel"};
                        final String message = "\"" + file.getName() 
                                         + "\" already exists. Do you want to replace it?";
                        n = JOptionPane.showOptionDialog(null,
                                                          message,
                                                          "Warning",
                                                          JOptionPane.OK_CANCEL_OPTION,
                                                          JOptionPane.WARNING_MESSAGE,
                                                          null,
                                                          options,
                                                          options[0]);
                    } else {           //file does not exist, just save it.
                         n = 0;      
                    }
                    if (n == 0) {
                        //save image
                    	Dimension size = myDrawPanel.getSize();
                    	BufferedImage image = new BufferedImage(
                                size.width, size.height 
                                          , BufferedImage.TYPE_INT_RGB);
                    	
                    	Graphics2D g2 = image.createGraphics();
                    	myDrawPanel.paint(g2);
                    	
                    	try {
                    		//final String ext = "";
                    		int i = file.getName().lastIndexOf('.');
                    		if ( i < 0 ) {
                    			String fname = file.getAbsolutePath();
                    			File newFile = new File(fname + ".jpg");
                    			ImageIO.write(image, "jpg", newFile);
                    		} else {
                    			ImageIO.write(image, "jpg", file);
                    		}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
                        
                    }
                 } //file is not null
			}
		}
    	
    }
}
