import java.awt.Graphics;
import java.awt.Color;

public class InsertionSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	
	public InsertionSortPanel(String name, int sleepTime, int width, int height){
		super(name, sleepTime, width, height);
	}
	
	public void reset(){
		
	}
	
	public void run(){
		/*
		 * This is a kind of Decrease-and-conquer algorithm. Assume we have already had a sorted array A[0....n-2], 
		 * and we need to insert A[n-1], then we can let j=n-2....0 and compare A[j] with A[n-1] till we find the 
		 * appropriate position and insert A[n- 1], then we finish the sorting of n numbers. ([1])
		 */
		try{
			for (int i=1; i<=list.length-1; i++){
				int j = i-1;
				int v = list[i];                //in this sub problem, list[i] is what we want to insert
				while ((j>=0) && (v < list[j])){
					Thread.sleep(4 * sleepTime);
					repaint();
					list[j+1] = list[j];
					j = j-1;
				}
				list[j+1] = v;
			} //endfor
		}
		catch(InterruptedException e){}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / size;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / size;
		for(int i=0; i<list.length;i++)  //paint the whole list
		{
			g.setColor(Color.WHITE);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
		}
	}
}
