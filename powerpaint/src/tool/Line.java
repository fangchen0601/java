package tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * A Line tool.
 * @author fangchen
 * @version 1.0
 *
 */
public class Line extends AbstractTool implements ToolInterface {
    
    /**
     * the line shape.
     */
    private Line2D myLine;
    
    /**
     * default constructor.
     */
    public Line() {
        super();
    }
    
    /**
     * constructor.
     * @param thePointStart start point
     * @param thePointEnd end point
     * @param theColor color
     * @param theWidth the width
     */
    public Line(final Point thePointStart, final Point thePointEnd, 
                final Color theColor, final int theWidth) {
        super(theColor, theWidth);

        myLine = new Line2D.Double(thePointStart, thePointEnd);
    }
    


    @Override
    public void draw(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setColor(myColor);
        g2d.setStroke(new BasicStroke(myWidth));
        g2d.draw(myLine);
        
//        if (!Objects.equals(myEndPoint, null)) { 
//            g2d.drawLine(myStartPoint.x, myStartPoint.y, myEndPoint.x, myEndPoint.y);
//        }
    }

}
