
import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends JApplet {

	private static final long serialVersionUID = 1L;
	private SortPanel panel;
	private static int size = 100;
	private int sleepTime = 2;
	private String animationName = "";

	public Main() {
		setLayout(new GridLayout(1, 1, 0, 0));
		SortPanelsHolder sortPanelHolder = new SortPanelsHolder();
		sortPanelHolder.setLayout(new  GridLayout(0, 1, 0, 0));
		sortPanelHolder.setBackground(Color.BLACK);
		sortPanelHolder.setForeground(Color.BLACK);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		panel = new BubbleSortPanel(" Bubble Sort ", sleepTime, screenSize.width/2 , screenSize.height/2 );
		panel.setVisible(false);
		sortPanelHolder.add(panel);
		add(sortPanelHolder);
	}
	
	class SortPanelsHolder extends JPanel {
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.RED);
			Font animationNameFont = new Font(Font.MONOSPACED, Font.BOLD, 50);
			FontMetrics animationNameFontFontMetrix = getFontMetrics(animationNameFont);
			g.setFont(animationNameFont);
			int x = (getWidth() - animationNameFontFontMetrix.stringWidth(animationName)) / 2;
			int y = (getHeight() - animationNameFontFontMetrix.getLeading()) / 2;
			g.drawString(animationName, x, y);
		}
	}
	
	public void beginAnimation(String animationName, int[] list) {
		try {
			
			this.animationName = animationName;
			repaint();
			Thread.sleep(2000);
			this.animationName = "";
			repaint();
			
			panel.setList(list);
			panel.setVisible(true);
			
			Thread.sleep(1000);
			
			long startTime = System.currentTimeMillis(); 
			ExecutorService exe = Executors.newSingleThreadExecutor();
			exe.execute(panel);
			exe.shutdown();
			while(!exe.isTerminated()) {
				Thread.sleep(100);
			}
			long endTime= System.currentTimeMillis();
			
			Thread.sleep(1000);
			panel.setVisible(false);
			
			long interval = endTime - startTime;
			this.animationName = "run time=" + interval;  //show the result
			repaint();
			Thread.sleep(2000);
			this.animationName = "";
			repaint();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Bubble Sort");
		Main main = new Main();
		frame.add(main);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		int[] list = new int[size];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = i + 1;
		}
		for (int i = 0; i < list.length; i++) {
			int index = (int) (Math.random() * list.length);
			int temp = list[i];
			list[i] = list[index];
			list[index] = temp;
		}
		main.beginAnimation("Random", list);
		
		
		String filename = "src/3D_spatial_network.txt";
		File f = new File(filename);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < list.length; i++) {
				String line = br.readLine(); // read a line from file
				if (line != null) {
					String[] result = line.split(","); // split the line
														// into 4 string,
														// like
														// 144552912,9.3498486,56.7408757,17.0527715677876
					list[i] = Integer.parseInt(result[1]); // we need the
															// second column
															// value, like
															// 9.3498486
				} else {
					list[i] = 0;
				}
			}
			br.close();
		} catch (Exception e) {
		} finally {
		}
		main.beginAnimation("3D_spatial_network", list);
	}
}