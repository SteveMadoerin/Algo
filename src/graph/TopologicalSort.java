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

		for(V v : g.getVertexSet())
		{
			deg.put(v, g.getInDegree(v));
			if(deg.get(v) == 0)
				q.add(v);
		}

		while (!q.isEmpty())
		{
			V v = q.remove();
			ts.add(v);
			for(V w : g.getSuccessorVertexSet(v))
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
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}
	}
}
