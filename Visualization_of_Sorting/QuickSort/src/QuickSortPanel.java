import java.awt.Color;
import java.awt.Graphics;

public class QuickSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	private int redColumn = -1;  //the pivot
	
	public QuickSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);
	}

	@Override
	public void reset() {
		redColumn = -1;
	}

	@Override
	public void run() {
		/*
		 * It is a Divide-and-Conquer algorithm. Quick Sort divides the elements by their value, 
		 * it rearranges a give array A[0,...n-1] to achieve its partition, a situation 
		 * where all the elements before position s are smaller than or equal to A[s] 
		 * and all the elements after position s are greater than or equal to A[s]. ([1])
		 */
		try {
			quicksort(0, list.length - 1);			
		} catch (InterruptedException e) {
		}
	}
	
	private void quicksort(int l, int r) throws InterruptedException {
		Thread.sleep(sleepTime);
		repaint();
		
		if(l<r){
			int pivot = Partition(l, r); // find the pivot A[s], and split the A with that pivot
			redColumn = pivot;
			repaint();
			quicksort(l, pivot - 1);// do Quick Sort for A[l...s-1]
			quicksort(pivot + 1, r);// do Quick Sort for A[s+1..r]
		}
	}
	
	private int Partition(int l, int r) throws InterruptedException{
		int pivot_value = list[l];
		redColumn = l;
		repaint();
		int i=l;
		int j=r+1;
		do {
			repaint();
			do {
				i = i + 1;
				Thread.sleep(4 * sleepTime);
			} while ((list[i] < pivot_value) && (i < list.length - 1)); // the first scan, stop
																		// when find list[i]>=p,
																		// but we need limit i
																		// is not "out bound"

			do {
				j = j - 1;
				Thread.sleep(4 * sleepTime);
			}
			while ((list[j] > pivot_value) && (j >= 0));	//the second scan, stop when find A[j]<=p
			repaint();
			swap(i, j); // swap A[i],A[j] for the case "no crossing"
		} while (i < j);
		
		swap(i, j);// undo last swap when i>=j
		swap(l, j);// swap A[l], A[j]
		
		redColumn = j;
		repaint();
		return j; //return the pivot
	}
	
	public void swap(int i, int j) { // exchange the element
		// at position i,j in
		// the array A[]
		int m;
		m = list[i];
		list[i] = list[j];
		list[j] = m;
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