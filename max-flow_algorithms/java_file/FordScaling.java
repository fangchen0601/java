import java.util.Iterator;
import java.util.Stack;
import java.io.*;
import java.util.*;

public class FordScaling {
	
	double maxflow=0;
	double min=1;
	
	public double ScalingFordFulkerson(SimpleGraph G){

		Stack st = new Stack();
        
        // !! Ford Fulkerson Scaling
        Vertex ver = null;
        Iterator q;
        q = G.vertices();
        if(q.hasNext())
        	ver = (Vertex) q.next();
        
        // set delta
        Iterator k = G.incidentEdges(ver);
        double max=0;
        while(k.hasNext()){
        	Edge e = (Edge)k.next();
        	if((double)e.getData()>max){
        		max = (double)e.getData();
        	}
        }
        
        double delta=1;
        while(delta<max){
        	delta*=2;
        }
        delta/=2;
        //System.out.println("delta is: "+delta);
        
        while(delta>=1){
        	min=delta;
	        while(min>0){
	        
		        Iterator i;
		        Vertex v = null;
		        
		        // initial
		        for (i= G.vertices(); i.hasNext(); ) {
		            v = (Vertex) i.next();
		            v.setData(false);
		        }

		        for (i= G.vertices(); i.hasNext(); ) {
		            v = (Vertex) i.next();
		            if(v.getName().equals("s")){
		            	break;
		            }
		        }
		        
		        v.setData(true);

		        min = DFS(G, v, st, delta);
		        
		        if(min>0){
		        	//System.out.println("flow is: "+min);
		        	maxflow += min;
		        	//System.out.println("max flow is now: "+maxflow);
		        }
		        
		        // update capacity
		        while(!st.isEmpty()){
		        	Boolean check = false;
		        	Edge e = (Edge)st.pop();
		        	e.setData((double)e.getData()-min);
		        	Vertex v1 = e.getFirstEndpoint();
		        	Vertex v2 = e.getSecondEndpoint();
		        	
		        	Iterator j = G.incidentEdges(v2);
		        	while(j.hasNext()){
		        		Edge f = (Edge)j.next();
		        		Vertex temp;
		        		temp = f.getSecondEndpoint();
		        		//if(v2==G.opposite(temp, f))
		        		if(temp.getName().equals(v1.getName()) && f.getName()!=e.getName() && v2==G.opposite(temp, f)){ // edge already exists
		        			f.setData((double)f.getData()+min);
		        			check=true;
		        			break;
		        		}

		        	}
		        	if(!check){
		        		G.insertEdge(v2, v1, min, "backward");
		        		//System.out.println("set backward "+v2.getName()+" "+v1.getName());
		        	}
		        }
	        }
	        delta/=2;
	        //System.out.println("delta is now: "+delta);
        }
        //System.out.println(maxflow);
        return maxflow;
	}

	public static double DFS(SimpleGraph G, Vertex v, Stack st, Double delta){
		
		Iterator i = G.incidentEdges(v);
		//System.out.println("New vertex " + v.getName());
		Vertex w;
		Edge e;
		v.setData(true);
		
		while(i.hasNext()){
			e = (Edge)i.next();
			w = e.getSecondEndpoint();
			
			if((double)e.getData()<delta || (Boolean)w.getData()){
				continue;
			}

			else /*if(!(Boolean)w.getData() && (double)e.getData()>=delta)*/{

				w.setData(true);
				double min;
					
				if(w.getName().equals("t") /*&& (double)e.getData()>=delta*/){
					st.push(e);
					//System.out.println("push :" + w.getName());
					////System.out.println("e is now "+e.getData());
					min = (double)e.getData();
					return min;
				}
				else /*if((double)e.getData()>=delta)*/{
					//double min;
					min = DFS(G, w, st, delta);
					if(min>0){
						st.push(e);
						//System.out.println("push :" + w.getName());
						if(min>(double)e.getData()){
							min = (double)e.getData();
						}
						
						return min;
					}
				}
			}
		}
		return 0;
	}
	
	/*
	public static void main (String args[]) {
		
        SimpleGraph G;
        GraphInput GInput = new GraphInput();
        G = new SimpleGraph();
        //GInput.LoadSimpleGraph(G, "C:\\Users\\gj94x_000\\Desktop\\graph\\n10-m10-cmin5-cmax10-f30.txt");
        //GInput.LoadSimpleGraph(G, "C:\\Users\\gj94x_000\\Desktop\\graph\\n100-m100-cmin10-cmax20-f949.txt");
        //GInput.LoadSimpleGraph(G, "C:\\Users\\gj94x_000\\Desktop\\graph\\test.txt");
        //GInput.LoadSimpleGraph(G, "C:\\Users\\gj94x_000\\Desktop\\graph\\smallMesh.txt");
        
        GInput.LoadSimpleGraph(G, "C:\\Users\\gj94x_000\\Desktop\\EdgeRandomGraph\\RandomGraph_v10_cmin1000.txt");
        
        FordScaling fs = new FordScaling();
        
        // Running Time
        long startTime = System.nanoTime();
        
        double maxflow = fs.ScalingFordFulkerson(G);
        
        long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;

		System.out.println("Run time: "+totalTime);
        System.out.println("max flow: "+maxflow);
	} */
	
}
