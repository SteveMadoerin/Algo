
package shortestPath;

//import directedGraph.*;
import graph.*;
import sim.SYSimulation;

import java.awt.*;
import java.util.*;
import java.util.List;

// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Steve Madörin
 * @since 06.01.2023
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; 		// Distanz für jeden Knoten
	Map<V,V> pred; 				// Vorgänger für jeden Knoten
	IndexMinPQ<V,Double> cand; 	// Kandidaten als PriorityQueue PQ

	Heuristic<V> heu;

	DirectedGraph<V> dirgra;

	V start;
	V goal;

	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h)
	{
		dist = new HashMap<>();
		pred = new HashMap<>();
		cand = new IndexMinPQ<>();

		this.heu = h == null ? (v,w) -> 0 : h;
		this.dirgra = g;
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim)
	{
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g)
	{
		this.start = s;
		this.goal = g;

		cand.clear(); // Liste ist leer

		for (var v: dirgra.getVertexSet())
		{
			dist.put(v, Double.POSITIVE_INFINITY);
			pred.put(v, null);
		}

		dist.put(s, 0.0);
		cand.add(s, 0.0 + heu.estimatedCost(s, g));

		while(!cand.isEmpty())
		{
			var v = cand.removeMin();

			if(sim != null)
			{
				sim.visitStation((int) v, Color.CYAN);
			}

			//Zielknoten erreicht
			if(v.equals(g))
			{
				return;
			}

			for(var w: dirgra.getSuccessorVertexSet(v))
			{
				if(dist.get(w) == Double.POSITIVE_INFINITY)
				{
					pred.put(w, v);
					dist.put(w, dist.get(v) + dirgra.getWeight(v, w));
					cand.add(w, dist.get(w) + heu.estimatedCost(w, g));
				}
				else if (dist.get(v) + dirgra.getWeight(v, w) < dist.get(w))
				{
					pred.put(w, v);
					dist.put(w, dist.get(v) + dirgra.getWeight(v, w));
					cand.change(w, dist.get(w) + heu.estimatedCost(w, g));
				}
			}
		}

	}


	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath()
	{
		List<V> list= new LinkedList<>();
		list.add(goal);

		var x = pred.get(goal);

		if( x == null )
		{
			throw new IllegalArgumentException();
		}

		while( x != null)
		{
			list.add(x);
			x = pred.get(x);
		}

		Collections.reverse(list);

		return Collections.unmodifiableList(list);
	}


	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance()
	{
		if (pred.get(goal) == null)
		{
			throw new IllegalArgumentException();
		}

		return dist.get(goal);
	}

}
