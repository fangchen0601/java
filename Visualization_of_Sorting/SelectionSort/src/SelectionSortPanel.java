import java.awt.Color;
import java.awt.Graphics;

public class SelectionSortPanel extends SortPanel{
	private static final long serialVersionUID = 1L;
	private int redColumn = -1;
	public SelectionSortPanel(String name, int sleepTime, int width, int height){
		super(name, sleepTime, width, height);
	}
	
	public void reset(){
		redColumn = -1;
	}
	
	public void run(){ 
		/*
		 * This is a Brute Force algorithm, we scan of whole elements of the array, 
		 * choose the smallest one and put it to the first position, then we scan the list, 
		 * start from the second elements of the array and find the smallest among the n-1 elements, 
		 * put it to the second position of the array. Repeat above till we put a right number into A[n-2]. ([1])
		 */
		
		try{
			for (int i=0; i<=list.length-2; i++){
				int min = i;
				redColumn = min;
				for(int j=i+1;j<=list.length-1;j++){
					repaint();
					Thread.sleep(4 * sleepTime);
					if(list[j] < list[min]){
						min = j;        //in each loop of i, we will find the smallest element index min
						redColumn = min;
						repaint();
					}
				}
				
				int m = list[min];      //swap list[i] and list[min]
				list[min] = list[i];    //swap list[i] and list[min]
				list[i] = m;            //swap list[i] and list[min]
				repaint();
				Thread.sleep(4 * sleepTime);					
			} //endfor
		}catch(InterruptedException e){}
		repaint();
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
		if(redColumn != -1) {  			//paint the pivot column
			g.setColor(Color.RED);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
		}
	}
}
