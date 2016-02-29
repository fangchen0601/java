package tool;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * 
 * @author fangchen
 * @version 1.0
 *
 */
public abstract class AbstractTool implements ToolInterface {
    
    /**
     * the color.
     */
    protected Color myColor;
    
    /**
     * the width.
     */
    protected int myWidth;
    
    /**
     * The tool of drawing.
     */
    private String myTool;
    
    /**
     * The icon of that tool.
     */
    private Icon myIcon;
    
    /**
     * The large icon.
     */
    private Icon myLargeIcon;
    
    /**
     * default constructor.
     * @throws IOException 
     */
    protected AbstractTool() {
        String name = this.getClass().getSimpleName();
        myTool = name;
        name = name.toLowerCase();

        
        URL urlC2 = ClassLoader.getSystemResource("images/" + name + "_bw.gif");
        System.out.println("by ClassLoader.getSystemResource: "+ urlC2 );
        
        
        URL imgurl = AbstractTool.class.getResource("/" + name + "_bw.gif");
        if(imgurl == null) {
        	System.out.println("img url null");
        } else {
        	System.out.println("by class.getResource" + imgurl);
        }
        myIcon = new ImageIcon(urlC2);
        

    }
    
    /**
     * Constructor.
     * @param theColor the color
     * @param theWidth the width
     */
    protected AbstractTool(final Color theColor, final int theWidth) {
        myColor = theColor;
        myWidth = theWidth;
    }
    
    
    /**
     * get the Icon.
     * @return the tool icon
     */
    public Icon getIcon() {
        return this.myIcon;
    }
    
    /**
     * Get tool.
     * @return the tool to be used to draw
     */
    public String getTool() {
        return this.myTool;
    }
    
    /**
     * get the Icon.
     * @return the large icon
     */
    public Icon getLargeIcon() {
        return this.myLargeIcon;
    }


}
