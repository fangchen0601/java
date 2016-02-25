package tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 * A rectangle tool.
 * @author fangchen
 * @version 1.0
 *
 */
public class Rectangle extends AbstractTool implements ToolInterface {
    
    /**
     * The rectangle.
     */
    private Rectangle2D myRectangle;
    
    /**
     * If draw a square.
     */
    private boolean mySquareOnly;
    
    /**
     * default constructor.
     */
    public Rectangle() {
        super();
    }
    
    /**
     * constructor.
     * @param thePointStart start point
     * @param thePointEnd end point
     * @param theColor color
     * @param theWidth the width
     * @param theSquareOnly if draw square
     */
    public Rectangle(final Point thePointStart, 
                     final Point thePointEnd, 
                     final Color theColor, 
                     final int theWidth, 
                     final boolean theSquareOnly) {
        super(theColor, theWidth);
        mySquareOnly = theSquareOnly;
    
        if (mySquareOnly) { 
            final double width = Math.abs(thePointStart.x - thePointEnd.x);
            final double height = Math.abs(thePointStart.y - thePointEnd.y);
            double edge;
            if (width < height) {
                edge = width;
            } else {
                edge = height;
            }
            createSquare(thePointStart, thePointEnd, edge);
        } else { 
            createMyRectangle(thePointStart, thePointEnd);
        }
    }
    
    /**
     * create a rectangle base on two points.
     * @param thePointStart the start point
     * @param thePointEnd the point end
     */
    private void createMyRectangle(final Point thePointStart, 
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
            
        myRectangle = new Rectangle2D.Double(x, y, width, height);
    }
    
    /**
     * create a square base on two points.
     * @param thePointStart the start point
     * @param thePointEnd the point end
     * @param theEdge the length of edge
     */
    private void createSquare(final Point thePointStart, 
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
            
        myRectangle = new Rectangle2D.Double(x, y, theEdge, theEdge);
        
        
        
    }
    
    @Override
    public void draw(final Graphics theGraphics) {
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setColor(myColor);
        g2d.setStroke(new BasicStroke(myWidth));
        
        g2d.draw(myRectangle);
        
    }

}
