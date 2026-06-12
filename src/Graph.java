import java.util.ArrayList;
import java.util.List;



/*
 * Weighted, DIRECTED graph -- scaffolding for the Dijkstra assignment.
 * Adjacency-list representation: for each vertex u, a list of outgoing edges.
 * This is the data structure your Dijkstra reads; it is NOT the algorithm.
 *
 * Your Dijkstra will use exactly three things from here:
 *   g.size()          -> number of vertices (they are ints 0..size-1)
 *   g.neighbors(u)    -> the outgoing edges of u
 *   e.to, e.weight    -> the endpoint and weight of an edge
 */
public class Graph {

    /** A directed, weighted edge u -> to with the given weight. */
    public static class Edge {
        public final int to;
        public final int weight;
        public Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    private final int n;
    private final String[] labels;
    private final List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        this.labels = new String[n];
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            labels[i] = Integer.toString(i);   // default label is the vertex number
            adj.add(new ArrayList<>());
        }
    }

    public int size() { return n; }

    public void setLabel(int v, String name) { labels[v] = name; }
    public String label(int v) { return labels[v]; }

    /** Add a directed edge u -> v with weight w. Dijkstra requires w >= 0. */
    public void addEdge(int u, int v, int w) {
        if (w < 0)
            throw new IllegalArgumentException(
                "Dijkstra requires non-negative weights; got " + w + " on edge " + u + "->" + v);
        adj.get(u).add(new Edge(v, w));
    }

    /** Outgoing edges of u (empty list if none). */
    public List<Edge> neighbors(int u) { return adj.get(u); }

    /** Print the adjacency list with weights -- the "display the graph" requirement. */
    public void print() {
        System.out.println("Graph: " + n + " vertices, directed, weighted (edge weights in parentheses)");
        for (int u = 0; u < n; u++) {
            StringBuilder sb = new StringBuilder("  ").append(label(u)).append(" ->");
            List<Edge> es = adj.get(u);
            if (es.isEmpty()) sb.append(" (no outgoing edges)");
            else for (Edge e : es) sb.append(' ').append(label(e.to)).append('(').append(e.weight).append(')');
            System.out.println(sb);
        }
    }
}
