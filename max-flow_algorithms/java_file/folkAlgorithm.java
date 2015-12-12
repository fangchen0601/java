/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author XinhelovesMom
 */

import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

public class folkAlgorithm {
    // residual graph
    SimpleGraph RG;

    HashSet<String> backEdges = new HashSet();
    DFS dfsimpl;
    Vertex source;
    Vertex target;
    List<Edge> path = new LinkedList();
    Double maxFlow;
    
    public folkAlgorithm (SimpleGraph RG, Vertex source,Vertex target) {
        this.RG = RG;
        this.source = source;
        this.target = target;
        maxFlow = 0.0;
        //System.out.println("--------------------------------------------------------------------");
        //System.out.println("---------- Starting FuldFolkson Algorithm Implementation -----------");
        //System.out.println("--------------------------------------------------------------------");
    }
 
    public Double FoldFulkImpl() {
        dfsimpl = new DFS(RG,source,target);
        // if there is an augmenting path in graph
        path = dfsimpl.findPath();
//        System.out.println(path.size());
        String n;
        while(!path.isEmpty()) {
            Iterator i;
            Double value;
            //System.out.println("The Avaliable Augmenting Path is:");
            //path.forEach(ele->System.out.print(ele.getName()+"->"));
            //System.out.println();
            //System.out.println("The Flow is: "+dfsimpl.bottleneck);
            maxFlow = maxFlow + dfsimpl.bottleneck;
            for(Edge e:path) {
                value = (Double) e.getData();
                Vertex v1 = e.getFirstEndpoint();
                Vertex v2 = e.getSecondEndpoint();
                e.setData(value - dfsimpl.bottleneck);
                n = (String) e.getName();
                Iterator k = RG.incidentEdges(v2);
                Edge e2;
                boolean check = false;
                //String name = ""+v2.getName()+"_"+v1.getName()+"_backwardedge";
                while(k.hasNext()) {
                    e2 = (Edge) k.next();
                    Vertex temp;
                    temp = e2.getSecondEndpoint();
                    //String n2 = (String)e2.getName();
                    if(temp.getName().equals(v1.getName()) && e2.getName()=="backwardedge" && v2==RG.opposite(temp,e2)) {
                        e2.setData((double) e2.getData() + dfsimpl.bottleneck);
                        check = true;
                    }
                }
                
                if(!check) RG.insertEdge(v2, v1, dfsimpl.bottleneck, "backwardedge");
                //if this edge is not backward edge then check if it contains the backward
                // edge of itself 

            }
            //System.out.println(maxFlow);
            dfsimpl = new DFS(RG,source,target);
            path = dfsimpl.findPath();
        }
        // System.out.println("No Augmenting Path in the Residual Graph");
        //System.out.println("----------------- PROCESS COMPLETED -----------------");
        return maxFlow;
    }
    
    
    
}
