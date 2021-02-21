

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

/*
 * SD2x Homework #6
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the signature of any of the methods!
 */

public class GraphUtils {

	public static int minDistance(Graph graph, String src, String dest) {
        if(graph == null || src == null || dest == null) return -1;
        if(!graph.containsElement(src) || !graph.containsElement(dest)) return -1;
        if(src.equals(dest)) return 0;

        Queue<Node> toExplore = new LinkedList<Node>(); 
        HashMap<Node,Integer> explored = new HashMap<Node, Integer>();
        toExplore.add(graph.getNode(src));
        explored.put(graph.getNode(src),0);
        while(!toExplore.isEmpty()){
            for(Edge e : graph.getNodeEdges(toExplore.poll())){    
                if(e.getDestination().getElement().equals(dest)) return explored.get(e.getSource()) + 1;
                if(explored.containsKey(e.getDestination())) continue;
                toExplore.add(e.getDestination());
                explored.put(e.getDestination(), explored.get(e.getSource()) + 1);
            }
        }
		return -1; 
    }
	

	public static Set<String> nodesWithinDistance(Graph graph, String src, int distance) {
        HashMap<String, Integer> nodes = new HashMap<String, Integer>();
        Queue<Node> toExplore = new LinkedList<Node>(); 

        if(graph == null || src == null) return null;
        if(!graph.containsElement(src)) return null;
        if(distance < 1) return null;
        nodes.put(src, 0);
        toExplore.add(graph.getNode(src));

        while(!toExplore.isEmpty()){
            for(Edge e : graph.getNodeEdges(toExplore.poll())){
                if(nodes.containsKey(e.getDestination().getElement())) continue;
                if(nodes.get(e.getSource().getElement()) == distance) continue;
                nodes.put(e.getDestination().getElement(), nodes.get(e.getSource().getElement()) + 1);
                toExplore.add(e.getDestination());
            }
        }
        nodes.remove(src);
		return nodes.keySet(); 
    }


	public static boolean isHamiltonianPath(Graph g, List<String> values) {
        if(g == null || values == null) return false;
        if(values.isEmpty()) return false;
        if(!values.get(0).equals(values.get(values.size() - 1))) return false;
        if(g.getNumNodes() != values.size() - 1) return false;
        HashSet<String> visited = new HashSet<String>();
        visited.add(values.get(0));
        for(int i = 1; i < values.size() - 1; i++){
            if(visited.contains(values.get(i))) return false;
            if(!g.getNodeNeighbors(g.getNode(values.get(i))).contains(g.getNode(values.get(i + 1)))) return false; 
        }
		return true; 
    }
	
}
