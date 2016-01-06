import java.awt.Color;
import java.awt.Graphics;

public class MergeSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	public MergeSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);
	}
	
	public void reset(){
		
	}
	
	public void run() {
		/*
		 * The merge sort is a Divide-and-Conquer algorithm. It breaks a given array A[0....n-1] 
		 * into two parts: A[0...n/2-1] and A[n/2....n-1], sorts each of them and merge them together.
		 * This kind of algorithm divides a big problem into two sub problem, each size is the half of the previous one. ([1])
		 */
		try {
			Merge_Sort(0, list.length - 1);
		} catch (InterruptedException e) {
		}
		repaint();
	}
	
	public void Merge_Sort(int start, int end) throws InterruptedException {
		
		if((end - start) > 0) {  //if the give array has only 1 element, will do nothing
			Merge_Sort(start, start + (end - start) / 2); //deal with the first half
			Merge_Sort(start + (end - start) / 2 + 1, end); //deal with the second half
			Merge(start, start + (end - start) / 2, start + (end - start) / 2 + 1, end);
		}
		
	}
	
	public void Merge(int start1, int end1, int start2, int end2) throws InterruptedException {
		int[] list1 = new int[end1 - start1 + 1];   //the fist half
		int[] list2 = new int[end2 - start2 + 1];   //the second half
		int[] tmp = new int[list1.length + list2.length];
		System.arraycopy(list, start1, list1, 0, list1.length);
		System.arraycopy(list, start2, list2, 0, list2.length);
		Thread.sleep(2 * sleepTime);
		repaint();
	    int i = 0;
	    int j = 0;
	    int k = 0;

		while (i < list1.length && j < list2.length) {
			Thread.sleep(2 * sleepTime);
			repaint();
			if (list1[i] < list2[j]) {
				tmp[k] = list1[i];
				k++;
				i++; 
			} else {
				tmp[k] = list2[j];
				k++;
				j++;
			}
			Thread.sleep(2 * sleepTime);
			repaint();
		}

		//when list1 or list2 is finished to copy to tmp, copy the rest of the non-empty array to tmp
		if(i==list1.length){   //list1 has been fully copied to tmp, copy list2 to tmp
			for (; k<tmp.length;k++)
				{tmp[k] = list2[j];j=j+1;repaint();}
			}
					
		else{              //C has been fully copied to tmp, copy list1 to tmp
			for (;k<tmp.length;k++)
				{tmp[k] = list1[i];i=i+1;repaint();}
			}

		for (i = 0; i < tmp.length; i++) {   //finally copy the tmp[] to list[]
			list[start1 + i] =  tmp[i];
			Thread.sleep(2 * sleepTime);
			repaint();
		}
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
