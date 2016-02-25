package tool;

import java.awt.Graphics;

import javax.swing.Icon;


/**
 * An interface for different draw actions.
 * @author fangchen
 * @version 1.0
 *
 */
public interface ToolInterface {
    
    /**
     * get the tool used to draw.
     * @return the tool to used draw
     */
    String getTool();
    
    /**
     * Return the icon for that action.
     * @return the icon of that action tool
     */
    Icon getIcon();
    
    /**
     * return the large icon.
     * @return the large icon.
     */
    Icon getLargeIcon();
    
    /**
     * the the specific shape.
     * @param theGraphics the graphics
     */
    void draw(final Graphics theGraphics);
}
