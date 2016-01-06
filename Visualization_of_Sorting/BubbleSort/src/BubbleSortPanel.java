import java.awt.Color;
import java.awt.Graphics;

public class BubbleSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	
	public BubbleSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);
	}
	
	@Override
	public void reset() {
	}

	public void run(){
		/*
		 * In each loop of i, we 'pop up' the largest number to the right most of the list
		 * by swapping the adjacent element list[j] and list[j+1]
		 */
		try{
		for (int i=0; i<=list.length-2; i++)
			for(int j=0;j<=list.length-i-2;j++){
				repaint();
				Thread.sleep(4 * sleepTime);
				if(list[j]>list[j+1])
				{
					int m = list[j+1];               //swap the adjacent elements
					list[j+1] = list[j];
					list[j] = m;
					repaint();
					Thread.sleep(4 * sleepTime);
				}
			}
		}catch(InterruptedException e){}
		//repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / size;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / size;
		for (int i = 0; i < list.length; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
		}
	}
}

