package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import tool.AbstractTool;
import tool.Ellipse;
import tool.Line;
import tool.Pencil;
import tool.Rectangle;

/**
 * the draw panel.
 * @author fangchen
 * @version 1.0
 *
 */
public class DrawPanel extends JPanel {
    
    /**
     * Height of drawing area.
     */
    public static final int DRAW_PANEL_HEIGHT = 400;
    
    /**
     * Width of drawing area.
     */
    public static final int DRAW_PANEL_WIDTH = 500;
    
    /**
     * UW purple red value.
     */
    public static final int UWDEFAULT_R = 51;

    /**
     * UW purple green value.
     */
    public static final int UWDEFAULT_G = 0;

    /**
     * UW purple blue value.
     */
    public static final int UWDEFAULT_B = 111;
    
    /**
     * The string constant value.
     */
    public static final String PENCIL = "Pencil";
    
    /**
     * The string constant value.
     */
    public static final String LINE = "Line";
    
    /**
     * The string constant value.
     */
    public static final String RECTANGLE = "Rectangle";
    
    /**
     * The string constant value.
     */
    public static final String ELLIPSE = "Ellipse";
    
    /**
     * a generated serial number.
     */
    private static final long serialVersionUID = -950718375757439961L;

    /**
     * line width.
     */
    private static final int DEFAULT_LINE_WIDTH = 5;
    
    /**
     * The color.
     */
    private Color myColor;
    
    /**
     * The current tool to draw.
     */
    private String myCurrentShape;

    /**
     * the start point of the shape.
     */
    private Point myStartPoint; 
    
    /**
     * The end point of the shape.
     */
    private Point myEndPoint;

    /**
     * width of the line.
     */
    private int myWidth;
    
    /**
     * An object to record a current path for pencil drawing.
     */
    private Path2D myPencilPath;
    
    /**
     * if Square/CircleOnly box is checked.
     */
    private boolean mySquareCircleOnly;
    
    /** 
     * A collection of shapes drown.
     *  */
    private List<AbstractTool> myShapes;
    
    /**
     * the default constructor.
     */
    public DrawPanel() {
        super();
        setupDrawPanel();
        
    }
    
    /**
     * setup the panel.
     */
    public void setupDrawPanel() {
        myStartPoint = new Point();
        myEndPoint = new Point();
        myWidth = DEFAULT_LINE_WIDTH;
        myShapes = new ArrayList<>();
        setPreferredSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));
        setBackground(Color.WHITE);
        setColor(new Color(UWDEFAULT_R, UWDEFAULT_G, UWDEFAULT_B));  //UW_purple
        
        final MouseListener l = new MouseListener();
        addMouseListener(l);
        addMouseMotionListener(l);
    }
    
    /**
     * getter.
     * @return myColor
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * setter.
     * @param theColor return my color.
     */
    public void setColor(final Color theColor) {
        this.myColor = theColor;
    }
    
    /**
     * getter.
     * @return the tool
     */
    public String getCurrentTool() {
        return myCurrentShape;
    }

    /**
     * setter.
     * @param theCurrentTool the tool
     */
    public void setCurrentTool(final String theCurrentTool) {
        this.myCurrentShape = theCurrentTool;
    }

    /**
     * Set the square/circle only.
     * @param theValue the boolean if the box is checked.
     */
    public void setSquareCircleOnly(final boolean theValue) {
        mySquareCircleOnly = theValue;
    }
    /**
     * clear the panel.
     */
    public void clearPanel() {
        this.myShapes.clear();
        this.myStartPoint = null;
        this.myEndPoint = null;
    }
    
    /**
     * set the width.
     * @param theWidth the width
     */
    public void setWidth(final int theWidth) {
        this.myWidth = theWidth;
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        
        for (AbstractTool shape : myShapes) {
            shape.draw(theGraphics);
        }
    }

    /**
     * Add a line.
     */
    private void addLineShape() {
        if (!myEndPoint.equals(myStartPoint)) {   //a single click will not create a line
            final Line line = new Line(myStartPoint, myEndPoint, myColor, myWidth);
            myShapes.add(line);
        }
    }
    
    /**
     * add a rectangle.
     */
    private void addRectangleShape() {
        if (!myEndPoint.equals(myStartPoint)) { //a single click will not create a rectangle
            final Rectangle rectangle = new Rectangle(myStartPoint, 
                                                      myEndPoint, 
                                                      myColor, 
                                                      myWidth,
                                                      mySquareCircleOnly);
            myShapes.add(rectangle);
        }
    }
    
    /**
     * Add a pencil path.
     */
    private void addPencilShape() {
        
        final Pencil pencil = new Pencil(myPencilPath, myColor, myWidth);
        
        myShapes.add(pencil);
    }
    
    /**
     * Add an ellipse.
     */
    private void addEllipseShape() {
        if (!myEndPoint.equals(myStartPoint)) { //a single click will not create a rectangle
            final Ellipse ellipse = new Ellipse(myStartPoint,
                                                myEndPoint, 
                                                myColor, 
                                                myWidth,
                                                mySquareCircleOnly);
            myShapes.add(ellipse);
        }
    }
    
    /**
     * record the points base on different shapes when mouse pressed.
     * @param theEvent mouse event
     */
    private void recordMousePressedPoints(final MouseEvent theEvent) {
        switch (myCurrentShape) {
            case PENCIL:
                myStartPoint = (Point) theEvent.getPoint().clone();
                myPencilPath = new Path2D.Double();
                break;
            default:
                myStartPoint = (Point) theEvent.getPoint().clone();
                myEndPoint = null;
                break;
        }
        
    }
    
    /**
     * record the points base on different shapes when mouse dragged.
     * @param theEvent mouse event
     */
    private void recordMouseDraggedPoints(final MouseEvent theEvent) {
        switch (myCurrentShape) {
            case PENCIL:
                myEndPoint = (Point) theEvent.getPoint().clone();
                myPencilPath.moveTo(myStartPoint.x, myStartPoint.y);
                myPencilPath.lineTo(myEndPoint.x, myEndPoint.y);
                addPencilShape();
                myStartPoint = myEndPoint;
                
                break;
            case LINE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                //remove previous one if mouse dragging and then draw new one shape
                removeLastMyShapes();
//                if (myShapes.size() != 0) {
//                    final AbstractTool t = myShapes.get(myShapes.size() - 1);
//                    if (t.getClass().equals(Line.class)) {
//                        myShapes.remove(myShapes.size() - 1);
//                    }
//                }
                //add shape
                addLineShape();
                break;
            case RECTANGLE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                //remove previous one if mouse dragging and then draw new one shape
                removeLastMyShapes();
                //add shape
                addRectangleShape();
                
                break;
            case ELLIPSE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                //remove previous one if mouse dragging and then draw new one shape
                removeLastMyShapes();
                //add shape
                addEllipseShape();
                break;
            default:
                break;
        }     
    }
    
    /**
     * remove the last shape from list if it is the same as current shape.
     */
    private void removeLastMyShapes() {
        if (myShapes.size() != 0) {  
            final AbstractTool t = myShapes.get(myShapes.size() - 1);
            if (t.getClass().getSimpleName().equals(myCurrentShape)) {
                myShapes.remove(myShapes.size() - 1);
            }
        }
    }
    
    /**
     * record the points base on different shapes when mouse dragged.
     * @param theEvent mouse event
     */
    private void recordMouseReleasedPoints(final MouseEvent theEvent) {
        switch (myCurrentShape) {
            case PENCIL:
                myEndPoint = (Point) theEvent.getPoint().clone();
                //a single click will not create a valid pencil path
                if (!(myStartPoint.x == myEndPoint.x && myStartPoint.y == myEndPoint.y)) {
                    myPencilPath.closePath();
                }
                myStartPoint = null;
                myEndPoint = null;
                break;
            case LINE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                //add shape
                addLineShape();
                myStartPoint = null;
                myEndPoint = null;
                break;
            case RECTANGLE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                addRectangleShape();
                myStartPoint = null;
                myEndPoint = null;
                break;
            case ELLIPSE:
                myEndPoint = (Point) theEvent.getPoint().clone();
                addEllipseShape();
                myStartPoint = null;
                myEndPoint = null;
                break;
            default:
                break;
        }
    }
    
    
    /**
     * Inner class to handle mouse movement.
     * @author fangchen
     * @version 1.0
     *
     */
    private class MouseListener extends MouseInputAdapter {
        /**
         * default constructor.
         */
        MouseListener() {
            super();
        }
                

        
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            MainPanel.setUndoButton(true); //undo button should be enabled
            recordMouseDraggedPoints(theEvent);
            repaint();
        }
        
//        @Override
//        public void mouseMoved(final MouseEvent theEvent) {
//           
//            myPointEnd = theEvent.getPoint();
//            //System.out.println("Moved:" + myPointStart + ", " + myPointEnd);
//           
//        }
        
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            recordMouseReleasedPoints(theEvent);
            repaint();
        }
        
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            MainPanel.setUndoButton(true);  //undo button should be enabled
            recordMousePressedPoints(theEvent);
        }
        
        
    } //inner class MouseListener

}
