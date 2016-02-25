package tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * An ellipse tool.
 * @author fangchen
 * @version 1.0
 *
 */
public class Ellipse extends AbstractTool implements ToolInterface {
    
    /**
     * my ellipse.
     */
    private Ellipse2D myEllipse;
    
    /**
     * If draw a circle.
     */
    private boolean myCircleOnly;
    /**
     * default constructor.
     */
    public Ellipse() {
        super();
    }
    
    /**
     * constructor.
     * @param thePointStart start point
     * @param thePointEnd end point
     * @param theColor color
     * @param theWidth the width
     * @param theCircleOnly if draw a circle
     */
    public Ellipse(final Point thePointStart, 
                   final Point thePointEnd, 
                   final Color theColor, 
                   final int theWidth,
                   final boolean theCircleOnly) {
        super(theColor, theWidth);
        myCircleOnly = theCircleOnly;
        if (myCircleOnly) { 
            final double width = Math.abs(thePointStart.x - thePointEnd.x);
            final double height = Math.abs(thePointStart.y - thePointEnd.y);
            double edge;
            if (width < height) {
                edge = width;
            } else {
                edge = height;
            }
            createMyCircle(thePointStart, thePointEnd, edge);
        } else {
            createMyEllipse(thePointStart, thePointEnd);
        }
    }
    
    /**
     * create a ellipse base on two points.
     * @param thePointStart the start point
     * @param thePointEnd the point end
     */
    private void createMyEllipse(final Point thePointStart, 
                                 final Point thePointEnd) {
        final double width = Math.abs(thePointStart.x - thePointEnd.x);
        final double height = Math.abs(thePointStart.y - thePointEnd.y);
        
        double x = 0.0;
        double y = 0.0;
        if (thePointEnd.y > thePointStart.y 
                        && thePointEnd.x > thePointStart.x) { //down, right
            x = thePointStart.x;
            y = thePointStart.y;
            
        } else if (thePointEnd.y < thePointStart.y 
                        && thePointEnd.x > thePointStart.x) { //up, right
            x = thePointEnd.x - width;
            y = thePointEnd.y;
        } else if (thePointEnd.y > thePointStart.y 
                        && thePointEnd.x < thePointStart.x) { //down, left
            x = thePointEnd.x;
            y = thePointStart.y;
        } else if (thePointEnd.y < thePointStart.y 
                        && thePointEnd.x < thePointStart.x) { //up, left
            x = thePointEnd.x;
            y = thePointEnd.y;
        }
        myEllipse = new Ellipse2D.Double(x, y, width, height);
    }
    
    /**
     * create a circle base on two points.
     * @param thePointStart the start point
     * @param thePointEnd the point end
     * @param theEdge the length of edge
     */
    private void createMyCircle(final Point thePointStart, 
                              final Point thePointEnd,
                              final double theEdge) {
        double x = 0.0;
        double y = 0.0;
        if (thePointEnd.y > thePointStart.y 
                        && thePointEnd.x > thePointStart.x) { //down, right
            x = thePointStart.x;
            y = thePointStart.y;
            
        } else if (thePointEnd.y < thePointStart.y 
                        && thePointEnd.x > thePointStart.x) { //up, right
            x = thePointStart.x;
            y = thePointStart.y - theEdge;
        } else if (thePointEnd.y > thePointStart.y 
                        && thePointEnd.x < thePointStart.x) { //down, left
            x = thePointStart.x - theEdge;
            y = thePointStart.y;
        } else if (thePointEnd.y < thePointStart.y 
                        && thePointEnd.x < thePointStart.x) { //up, left
            x = thePointStart.x - theEdge;
            y = thePointStart.y - theEdge;
        }
            
        myEllipse = new Ellipse2D.Double(x, y, theEdge, theEdge);
        
    }
    
    @Override
    public void draw(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setColor(myColor);
        g2d.setStroke(new BasicStroke(myWidth));
        
        g2d.draw(myEllipse);
        
    }

}
