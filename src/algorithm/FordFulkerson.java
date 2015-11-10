/* written by chchao Nov. 9, 2015*/

package algorithm;
import java.util.Iterator;
import java.util.Stack;
import graphCode.*;

public class FordFulkerson {
	
	//current assumption: getFirstEndpoint goes to getSecondEndpoint(it's a direction)
	private static double dfs(SimpleGraph G, Vertex v, Stack path){				
		v.setData(true);
		Iterator j;
		Edge e;
		Vertex w;
		
		for (j = G.incidentEdges(v); j.hasNext();) {
			e = (Edge)j.next();
			double value = (double)e.getData();
			if(value > 0){
				w = e.getSecondEndpoint();
				
				Boolean isDiscovered = (Boolean)(w.getData());
				if(isDiscovered){
					continue;
				}
				
				if("t".equals(w.getName())){
					path.push(e);
					return value;
				}
				
				double capacity = dfs(G, w, path);
				if(capacity>0){
					path.push(e);
					return Math.min(capacity, value);
				}
			}
		}
	
		return 0;
	}
	
	private static void setAllVertexUndiscovered(SimpleGraph G){
		Iterator i;
		
		for (i= G.vertices(); i.hasNext();) {
            Vertex v = (Vertex) i.next();
            v.setData(false);
		}
	} 
	
	private static void updateEdgeValue(SimpleGraph G, Edge e, double value){
		Iterator i;
		String name = (String) e.getFirstEndpoint().getName();
		
		// minus value from edge e
		e.setData((double)e.getData()-value);
		
		// add value to the reversed edge or create one 
		i = G.incidentEdges(e.getSecondEndpoint());
		if(null==e.getName()){
			while(i.hasNext()){
				Edge f = (Edge)i.next();
	
				if(f.getSecondEndpoint().getName().equals(name) 
						&& null != f.getName()){
					f.setData((double)f.getData()+value);
					return;
				}
			}
			
			G.insertEdge(e.getSecondEndpoint(), e.getFirstEndpoint(), value, "reverse");
			return;
		}
		else{
			while(i.hasNext()){
				Edge f = (Edge)i.next();
	
				if(f.getSecondEndpoint().getName().equals(f.getName()) 
						&& null == f.getName()){
					e.setData((double)e.getData()+value);
					return;
				}
			}
		}
	}
	
	public static double FordFulkerson(SimpleGraph G){
		Iterator i;
		Vertex v = null;
		Edge e = null;
		double totalCapacity=0, capacity=0;
		Stack<Edge> path = new Stack<Edge>(); 
		
		i = G.vertices();
		if(i.hasNext())
			v= (Vertex) i.next();
		   
		setAllVertexUndiscovered(G);
   
		for(;(capacity = dfs(G, v, path))!=0;
    		totalCapacity += capacity, 
    		path.clear(), 
    		setAllVertexUndiscovered(G)){
	   
			System.out.print(capacity+" ");
   
			if(capacity!=0 && !path.isEmpty()){	    	   
				while(!path.isEmpty()){
					e = path.pop();
					updateEdgeValue(G, e, capacity);
					System.out.print(e.getFirstEndpoint().getName()+ "->");
				}
				System.out.println("t");
			}
		}
		return totalCapacity;
	}
	
	public static void main (String args[]) {
		SimpleGraph G;
		G = new SimpleGraph();
		
		GraphInput.LoadSimpleGraph(G, "src/test01.txt");//"src/graphGenerationCode/Random/n10-m10-cmin5-cmax10-f30.txt");
		
//		Iterator i;
//		Vertex v = null;
//		Edge e;
//	   
//	   for (i= G.vertices(); i.hasNext(); ) {
//	       v = (Vertex) i.next();
//	       System.out.println("Vertex "+v.getName());
//	       v.setData(false);
//	       Iterator j;
//	       
//	       for (j = G.incidentEdges(v); j.hasNext();) {
//	           e = (Edge) j.next();
//	           if(e.getFirstEndpoint().getName().equals(v.getName()))
//	        	   System.out.println("  edge " + e.getName() + " to " + e.getSecondEndpoint().getName() + " " + e.getData());// + " " + t.getMax() + " " + t.getUsed());
//	       }
//	       
//	   }
	   
	   System.out.println(FordFulkerson(G));
	   
//	   for (i= G.vertices(); i.hasNext(); ) {
//	       v = (Vertex) i.next();
//	       System.out.println("Vertex "+v.getName());
//	       v.setData(false);
//	       Iterator j;
//	       
//	       for (j = G.incidentEdges(v); j.hasNext();) {
//	           e = (Edge) j.next();
//	           if(e.getFirstEndpoint().getName().equals(v.getName()))
//	        	   System.out.println("  edge " + e.getName() + " to " + e.getSecondEndpoint().getName() + " " + e.getData());// + " " + t.getMax() + " " + t.getUsed());
//	       }
//	       
//	   }
	}
	
}
