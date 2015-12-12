


import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DFS {
    private Hashtable<Vertex,Boolean> marked;
    public Double bottleneck;
    public Vertex source;
    public Vertex target;
    public SimpleGraph G;
    
    // constructor of deapth first search implementation
    public DFS(SimpleGraph G, Vertex s, Vertex t) { 
        this.G = G;
        source = s;
        target = t;
        marked = new Hashtable();
        bottleneck = Double.MAX_VALUE;
        Iterator i;
        Vertex v;
        for (i = this.G.vertices();i.hasNext();) {
            v = (Vertex) i.next();
            marked.put(v, false);
        }
        marked.put(source,true);
        
    }
    public LinkedList<Edge> dfsImpl(SimpleGraph G,Vertex s,Vertex v) {
        if(s == v) {
            return new LinkedList();
        }
        
        LinkedList<Edge> path = null;
        Iterator j;
        for(j=G.incidentEdges(s);j.hasNext();) {
            Edge e = (Edge) j.next();
            if((Double) e.getData() > 0.0 && e.getFirstEndpoint() == s) {
                Vertex adjv = G.opposite(s, e);
                if(!(boolean) marked.get(adjv)) {
//                    bottleneck = Math.min
                    marked.put(adjv,true);
                    path = dfsImpl(G,adjv,target);
                    if(path!=null) {
                        path.addFirst(e);
                        bottleneck = Math.min(bottleneck,(Double) e.getData());
                        // System.out.println("path: "+e.getName()+" added");
                        return path;
                    }
                }
            }
        }
        return null;
    }
    
    public List<Edge> findPath() {
        List<Edge> out = dfsImpl(G,source,target);
        if(out == null) {
            out = new LinkedList();
        }
        return out;
    }
    
//    public static void main(String args[]) {
//        SimpleGraph G = new SimpleGraph();
//        LoadSimpleGraph(G, "/Users/XinhelovesMom/NetBeansProjects/FoldFulkAlgorithm/src/foldfulkalgorithm/20v-3out-4min-355max.txt");
//        Vertex source = (Vertex) G.vertexList.getFirst();
//        Vertex target = (Vertex) G.vertexList.getLast();
//        Iterator k;
//        Vertex ee;
//        for(k = G.vertices();k.hasNext();) {
//            ee = (Vertex) k.next();
//            if(ee.getName().equals("t")) target = ee;
//            else if(ee.getName().equals("s")) source = ee;
//        }
////        System.out.println(target.getName());
//        DFS dfs = new DFS(G,source,target);
//        List<Edge> path = dfs.findPath();
//        for(Edge e:path) {
//            System.out.print(e.getName()+"->");
//        }
//        System.out.println();
//        System.out.println(path.get(path.size()-1).getName());
//    }
}
