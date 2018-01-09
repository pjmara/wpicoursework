import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GraphSearchEngineImpl implements GraphSearchEngine {
	private HashMap<Node, Integer> distanceFromS;

	public List<Node> findShortestPath(Node s, Node t) {
		ArrayList<Node> path = new ArrayList<Node>();
		distanceFromS = new HashMap<Node, Integer>();
		int distance = getDistanceBetweenNodes(s, t);
		if (distance == -1) return null; //Return null if there's no such path
		if (distance == 0) {
			path.add(s);
			return path;
		}
		path = getPathBetweenNodes(s, t, distance);
		return path;
	}

	/**
	 * Find the distance between the two nodes
	 * If there's no path, return 0
	 * @param s the starting Node
	 * @param t the Node the method is looking for
	 * @return the distance between the two nodes, if there's no path / s and t are the same, return 0
	 */
	private int getDistanceBetweenNodes(Node s, Node t) {
		if (s.equals(t)) return 0;
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		ArrayDeque<Node> nodesToVisit = new ArrayDeque<Node>();
		distanceFromS.put(s, 0);
		nodesToVisit.add(s);
		while (!nodesToVisit.isEmpty()) {
			Node currentNode = nodesToVisit.poll();
			visitedNodes.add(currentNode);
			for (Node n: currentNode.getNeighbors()) {
				if (!visitedNodes.contains(n) && !nodesToVisit.contains(n)) {
					nodesToVisit.add(n);
					distanceFromS.put(n, (distanceFromS.get(currentNode) + 1));
					if (distanceFromS.containsKey(t)) break;
				}
			}
			if (distanceFromS.containsKey(t)) break;
		}
		if (distanceFromS.containsKey(t)) {
			return distanceFromS.get(t);
		} else {
			return -1;
		}
	}
	
	/**
	 * return the shortest path between the nodes
	 * @param s the starting Node
	 * @param t the targeting Node
	 * @param distance the known distance between the two nodes
	 * @return An ArrayList that contains the path between the two nodes
	 */
	private ArrayList<Node> getPathBetweenNodes(Node s, Node t, int distance){
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(0,t);
		Set<Node> nodes = distanceFromS.keySet();
		for (int i = distance -1 ; i > 0; i --) {
			for (Node n: nodes) {
				if (path.get(0).getNeighbors().contains(n) && distanceFromS.get(n) == i) {
					path.add(0, n);
					break;
				}
			}
		}
		path.add(0,s);
		return path;
	}
}
