// S. Madoerin;
// 26.09.22

package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Steve Madoerin
 * @since 06.12.2022
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	private int treeNum = 0;
	
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		// ...
		KosarajuSharirAlgo(g);
	}

	private void KosarajuSharirAlgo(DirectedGraph<V> g)
	{
		List<V> reversedPostOrderList = getReversedPostOrder(g);
		DirectedGraph<V> reversedGraph = getReverseGraph(g);

		Set<V> visited = new TreeSet<>();
		for(V v : reversedPostOrderList)
		{
			if(!visited.contains(v))
			{
				this.comp.put(treeNum, new TreeSet<V>());
				visitDF(v, reversedGraph, visited);
				treeNum++;
			}
		}
	}

	private void visitDF(V v, DirectedGraph<V> g, Set<V> visited)
	{
		visited.add(v);
		this.comp.get(treeNum).add(v);

		for(var w : g.getSuccessorVertexSet(v))
		{
			if(!visited.contains(w))
				visitDF(w, g, visited);
		}
	}

	private List<V> getReversedPostOrder(DirectedGraph<V> g)
	{
		DepthFirstOrder<V> order = new DepthFirstOrder<>(g);

		List<V> list = new LinkedList<V>(order.postOrder());
		Collections.reverse(list);
		return list;
	}

	private DirectedGraph<V> getReverseGraph(DirectedGraph<V> g)
	{
		return g.invert();
	}
	
	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();

		 for(var entry : this.comp.entrySet())
		 {
			 sb.append("Component ").append(entry.getKey()).append(": ");
			 for(var v : entry.getValue())
			 {
				 sb.append(v).append(", ");
			 }
			 sb.append("\n");
		 }
		 return sb.toString();
	}
	
	/**
	 * Liest einen gerichteten Graphen von einer Datei ein. 
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextLine();
        sc.nextLine();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}
	
	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7, 
        	// Component 1: 8, 
            // Component 2: 1, 2, 3, 
            // Component 3: 4, 
	}
	
	private static void test2() throws FileNotFoundException {
		DirectedGraph<Integer> g = readDirectedGraph(new File("src/graph/mediumDG.txt"));
		System.out.println(g.getNumberOfVertexes());
		System.out.println(g.getNumberOfEdges());
		System.out.println(g);
		
		System.out.println("");
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println(sc.numberOfComp());  // 10
		System.out.println(sc);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		test1();
		test2();
	}
}
