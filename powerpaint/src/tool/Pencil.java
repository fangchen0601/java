package tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

/**
 * A pencil tool.
 * @author fangchen
 * @version 1.0
 *
 */
public class Pencil extends AbstractTool implements ToolInterface {

    /**
     * The path for drawing.
     */
    private Path2D myPencilPath;
    
    /**
     * default constructor.
     */
    public Pencil() {
        super();
    }
    
    /**
     * Constructor.
     * @param thePencilPath the path
     * @param theColor the color
     * @param theWidth the width
     */
    public Pencil(final Path2D thePencilPath, final Color theColor, final int theWidth) {
        super(theColor, theWidth);
        myPencilPath = new Path2D.Double(thePencilPath);  //avoid encapsulation violation
    }

    @Override
    public void draw(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(myColor);
        g2d.setStroke(new BasicStroke(myWidth));
        
        g2d.draw(myPencilPath);  
    }



}
