import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;

public class QuickSort {

	public static void main(String[] args) {
		// sort a given array by Quick Sort
		//int[] A = new int[] {1,3,5,7,9,2,5,8,6,0};  //the data set
		
		
		Double[] A = chooseDataSet();   		//select a data set and return the Array with test data
	
		
		System.out.println("press any key to start...");
		Console c = System.console();
		c.readLine();
		
		int n = A.length;
		
		long startTime = System.nanoTime();     //write down the start time
		System.out.println("StartTime: " + startTime);
		
		Quick_Sort(A,0,n-1);
		
		long endTime = System.nanoTime();       //write down the end time
		System.out.println("End  Time: " + endTime);
		
		
		long runningTime = endTime - startTime;  //duration of sorting
		System.out.println("Running Time(nanosecond): " + runningTime);
		
		System.out.println("press any key to exit...");
		c.readLine();
		
		
	}
	
	public static void Quick_Sort(Double[] A, int l, int r){
		int s;
		if(l < r){
			
			s = Partition(A,l,r);  //find the pivot A[s], and split the A with that pivot
			
			Quick_Sort(A,l,s-1);//do Quick Sort for A[l...s-1]		
			
			Quick_Sort(A,s+1,r);//do Quick Sort for A[s+1..r]
		}
	}

	public static int Partition(Double[] A, int l, int r){
		Double p = A[l];
		int i;
		i=l; 
		int j;
		j=r+1;
		do{
			do{
				i = i + 1;
			}
			while((A[i]<p)&&(i<A.length-1));    //the first scan, stop when find A[i]>=p, but we need limit i is not "out bound"
			
			do{
				j = j -1;
			}
			//while(A[j]>p);    //the second scan, stop when find A[j]<=p
			while((A[j]>p)&&(j>=0));
			
			swap(A,i,j);       //swap A[i],A[j] for the case "no crossing"   
		}
		while(i<j);
		
		swap(A,i,j);//undo last swap when i>=j
		swap(A,l,j);//swap A[l], A[j]
		
		return j;
	}
	
	public static void swap(Double[] A, int i, int j){    //exchange the element at position i,j in the array A[]
		Double m;
		m = A[i];
		A[i] = A[j];
		A[j] = m;
	}

public static Double[] chooseDataSet(){
		
		Double[] A; //the array with data set
		Double v;  //the value to put into A[n]
		
		System.out.println("Please choose data set: ");  
		System.out.println("[1]    ds1_a: {1,2,....,1000}");
		System.out.println("[2]    ds1_b: {1,2,....,50000}");
		System.out.println("[3]    ds1_c: {1,2,....,100000}");
		System.out.println("[4]    ds2_a: {1000,999,бн.1}");
		System.out.println("[5]    ds2_b: {50000,49999,бн.1}");
		System.out.println("[6]    ds2_c: {100000,99999,бн.1}");
		System.out.println("[7]    ds3_a: 3D_spatial_network.txt, length=1000");
		System.out.println("[8]    ds3_b: 3D_spatial_network.txt, length=50000");
		System.out.println("[9]    ds3_c: 3D_spatial_network.txt, length=100000");
		System.out.println("[10]   ds4_a: follower_followee.csv,  length=1000");
		System.out.println("[11]   ds4_b: follower_followee.csv,  length=50000");
		System.out.println("[12]   ds4_c: follower_followee.csv,  length=100000");
		
		Console c = System.console();
		String choice = c.readLine();
		
		switch(choice){
		case "1":
			System.out.println("this is ds1_a: {1,2,....,1000}");  
			A = new Double[1000];
			for(int i = 0;i<A.length;i++){
				A[i] = i+1.0;
			}
			return A;
		case "2":
			System.out.println("this is ds1_b: {1,2,....,50000}");  
			A = new Double[50000];
			for(int i = 0;i<A.length;i++){
				A[i] = i+1.0;
			}
			return A;
		case "3":
			System.out.println("this is ds1_c: {1,2,....,100000}");  
			A = new Double[100000];
			for(int i = 0;i<A.length;i++){
				A[i] = i+1.0;
			}
			return A;
		case "4":
			System.out.println("this is ds2_a: {1000,999,бн.1}");  
			A = new Double[1000];
			v = 1000.0;
			for(int i = 0;i<A.length;i++){
				A[i] = v;
				v--;
			}
			return A;
		case "5":
			System.out.println("this is ds2_b: {50000,49999бн.1}");  
			A = new Double[50000];
			v = 50000.0;
			for(int i = 0;i<A.length;i++){
				A[i] = v;
				v--;
			}
			return A;
		case "6":
			System.out.println("this is ds2_c: {100000,99999,бн.1}");  
			A = new Double[100000];
			v = 100000.0;
			for(int i = 0;i<A.length;i++){
				A[i] = v;
				v--;
			}
			return A;
		case "7":
			System.out.println("this is ds3_a: 3D_spatial_network.txt, length=1000");
			A = new Double[1000];
			String filename = "C:\\workspace\\dataset\\3D_spatial_network.txt";
			File f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				for(int i=0;i<A.length;i++){
					String line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into 4 string, like 144552912,9.3498486,56.7408757,17.0527715677876
						A[i] = Double.parseDouble(result[1]);	   //we need the second column value, like 9.3498486		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		case "8":
			System.out.println("this is ds3_b: 3D_spatial_network.txt, length=50000");
			A = new Double[50000];
			filename = "C:\\workspace\\dataset\\3D_spatial_network.txt";
			f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				for(int i=0;i<A.length;i++){
					String line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into 4 string, like 144552912,9.3498486,56.7408757,17.0527715677876
						A[i] = Double.parseDouble(result[1]);	   //we need the second column value, like 9.3498486		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		case "9":
			System.out.println("this is ds3_c: 3D_spatial_network.txt, length=100000");
			A = new Double[100000];
			filename = "C:\\workspace\\dataset\\3D_spatial_network.txt";
			f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				for(int i=0;i<A.length;i++){
					String line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into 4 string, like 144552912,9.3498486,56.7408757,17.0527715677876
						A[i] = Double.parseDouble(result[1]);	   //we need the first column value, like 9.3498486		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		case "10":
			System.out.println("this is ds4_a: follower_followee.csv,  length=1000");
			A = new Double[1000];
			filename = "C:\\workspace\\dataset\\follower_followee.csv";
			f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				for(int i=0;i<A.length;i++){
					line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into string
						A[i] = Double.parseDouble(result[0]);	   //we need the first column value, like 000000169496		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		case "11":
			System.out.println("this is ds4_b: follower_followee.csv,  length=50000");
			A = new Double[50000];
			filename = "C:\\workspace\\dataset\\follower_followee.csv";
			f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				for(int i=0;i<A.length;i++){
					line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into string
						A[i] = Double.parseDouble(result[0]);	   //we need the first column value		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		case "12":
			System.out.println("this is ds4_c: follower_followee.csv,  length=100000");
			A = new Double[100000];
			filename = "C:\\workspace\\dataset\\follower_followee.csv";
			f = new File(filename);
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				for(int i=0;i<A.length;i++){
					line = br.readLine();                   //read a line from file
					if(line != null){
						String[] result = line.split(",");         //split the line into string
						A[i] = Double.parseDouble(result[0]);	   //we need the first column value		
					}
					else{
						A[i] = 0.0;
					}
				}
				br.close();
			}
			catch(Exception e){}
			finally{
			}
			return A;
		default:
			System.out.println("invalid option, exit...");
			System.exit(1);
				
		}
		
		A = new Double[1];
		return A;
	}


}
