// S. Madoerin;
// 6.12.22

package graph;

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Steve Madoerin
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...
	//int[] inDegree; // Anzahl noch nicht besuchter Vorgänger
	//Queue<V> q;
	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
        // ...
		topSort(g);
    }
    
	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }

	private List<V> topSort(DirectedGraph<V> g)
	{
		Queue<V> q = new LinkedList<>();
		Map<V, Integer> deg = new TreeMap<>();

		for(var v : g.getVertexSet())
		{
			deg.put(v, g.getInDegree(v));
			if(deg.get(v) == 0)
				q.add(v);
		}

		while (!q.isEmpty())
		{
			V v = q.remove();
			ts.add(v);
			for(var w : g.getSuccessorVertexSet(v))
			{
				deg.put(w, deg.get(w) - 1);
				if (deg.get(w) == 0)
					q.add(w);
			}
		}

		if(ts.size() != g.getNumberOfVertexes())
			return null;
		else
			return ts;
	}
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null)
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]

		DirectedGraph<String> g2 = new AdjacencyListDirectedGraph<>();
		g2.addEdge("Unterhose", "Hose");
		g2.addEdge("Socken", "Schuhe");
		g2.addEdge("Unterhemd", "Hemd");
		g2.addEdge("Hose", "Schuhe");
		g2.addEdge("Hose","Guertel");
		g2.addEdge("Hemd", "Pulli");
		g2.addEdge("Pulli", "Mantel");
		g2.addEdge("Guertel", "Mantel");
		g2.addEdge("Schuhe", "Handschuhe");
		g2.addEdge("Mantel", "Schal");
		g2.addEdge("Schal", "Handschuhe");
		g2.addEdge("Muetze", "Handschuhe");

		TopologicalSort<String> ts2 = new TopologicalSort<>(g2);

		if (ts2.topologicalSortedList() != null)
			System.out.println(ts2.topologicalSortedList());

		g2.addEdge("Schal", "Hose");

		TopologicalSort<String> ts3 = new TopologicalSort<>(g2);

		if (ts3.topologicalSortedList() != null)
			System.out.println(ts3.topologicalSortedList());


	}
}
