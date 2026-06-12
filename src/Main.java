import java.util.List;

/*
 * Dijkstra test harness (scaffolding -- NOT the graded algorithm).
 * It builds weighted directed graphs, calls YOUR Dijkstra, then displays and
 * verifies the results. The algorithm itself lives in Dijkstra.java (your code).
 *
 * Covers the assignment's Testing section:
 *   - Graph A: 10 vertices, multi-hop paths, an unreachable vertex, and
 *              non-obvious shortest paths (the cheap route is not the direct edge).
 *   - Graph B: 5 vertices, small enough to verify entirely by hand.
 * For each graph it prints the graph, the distance to every vertex, and the
 * reconstructed shortest path to three targets, checking distances against
 * known answers computed independently.
 */
public class Main {

    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        runCase("GRAPH A  (10 vertices: non-obvious paths + an unreachable vertex)",
                buildGraphA(), 0,
                new int[]{0, 2, 1, 3, 6, 8, 10, 11, 13, INF},   // known distances from vertex 0
                new int[]{1, 7, 9},                             // targets: non-obvious, long multi-hop, unreachable
                new String[]{
                    "0->1 costs 4 directly, but the shortest distance to 1 is 2 via 0->2->1 (non-obvious).",
                    "vertex 9 has no incoming edges, so it is unreachable from source 0."
                });

        runCase("GRAPH B  (5 vertices: small, fully hand-verifiable)",
                buildGraphB(), 0,
                new int[]{0, 2, 3, 5, 6},
                new int[]{2, 3, 4},
                new String[]{
                    "0->2 costs 5 directly, but the shortest distance to 2 is 3 via 0->1->2 (non-obvious)."
                });

        System.out.println("Legend: distances are total path cost from the source; 'OK' means the");
        System.out.println("value matches the independently-computed known answer.");
    }

    static void runCase(String title, Graph g, int source, int[] expected, int[] targets, String[] notes) {
        System.out.println("======================================================================");
        System.out.println(title);
        System.out.println("======================================================================");
        g.print();
        if (notes != null) for (String note : notes) System.out.println("  note: " + note);
        System.out.println();

        Dijkstra d = new Dijkstra();
        d.run(g, source);

        // graceful behavior while Dijkstra is still a skeleton
        if (d.dist == null) {
            System.out.println("  Dijkstra.run() is not implemented yet -- fill in Dijkstra.java.");
            System.out.println("  Once run() populates dist[] and prev[], this harness prints the");
            System.out.println("  distance table and the shortest paths automatically.\n");
            return;
        }

        System.out.println("distances from source " + g.label(source) + ":");
        boolean allOk = true;
        for (int v = 0; v < g.size(); v++) {
            String got = d.dist[v] == INF ? "unreachable" : Integer.toString(d.dist[v]);
            String exp = expected[v] == INF ? "unreachable" : Integer.toString(expected[v]);
            boolean ok = d.dist[v] == expected[v];
            allOk &= ok;
            System.out.printf("  %-3s : %-12s (expected %-12s) %s%n",
                    g.label(v), got, exp, ok ? "OK" : "<-- MISMATCH");
        }
        System.out.println();

        System.out.println("shortest paths from " + g.label(source) + ":");
        for (int t : targets) {
            if (d.dist[t] == INF) {
                System.out.printf("  to %-3s : no path (unreachable)%n", g.label(t));
                continue;
            }
            List<Integer> path = d.pathTo(t);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(g.label(path.get(i)));
            }
            long w = pathWeight(g, path);                    // sanity-check the reconstruction
            String warn = (w == d.dist[t]) ? "" : "   <-- path weight " + w + " != distance " + d.dist[t];
            System.out.printf("  to %-3s : cost %-3d  [%s]%s%n", g.label(t), d.dist[t], sb, warn);
        }
        System.out.println();
        System.out.println(allOk
                ? "RESULT: every distance matches the known answers.\n"
                : "RESULT: one or more distances are wrong -- see the MISMATCH lines above.\n");
    }

    /** Sum edge weights along a reconstructed path; -1 if the path uses a missing edge. */
    static long pathWeight(Graph g, List<Integer> path) {
        long sum = 0;
        for (int i = 0; i + 1 < path.size(); i++) {
            int u = path.get(i), v = path.get(i + 1), w = -1;
            for (Graph.Edge e : g.neighbors(u)) if (e.to == v) { w = e.weight; break; }
            if (w < 0) return -1;
            sum += w;
        }
        return sum;
    }

    // ---------------- test graphs ----------------

    // 10 vertices. Shortest distances from 0: [0,2,1,3,6,8,10,11,13,INF].
    // The fast route to most vertices threads 0->2->1->3->4->5->7->8, never the
    // tempting direct edges (0->1=4, 1->4=7). Vertex 9 only points outward -> unreachable.
    static Graph buildGraphA() {
        Graph g = new Graph(10);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(2, 1, 1);
        g.addEdge(2, 3, 5);
        g.addEdge(1, 3, 1);
        g.addEdge(3, 4, 3);
        g.addEdge(1, 4, 7);
        g.addEdge(4, 5, 2);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 6, 4);
        g.addEdge(5, 7, 3);
        g.addEdge(6, 7, 2);
        g.addEdge(7, 8, 2);
        g.addEdge(9, 8, 1);   // 9 -> 8 only; nothing points to 9, so 9 is unreachable from 0
        return g;
    }

    // 5 vertices. Shortest distances from 0: [0,2,3,5,6]. Small enough to trace by hand.
    static Graph buildGraphB() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 5);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 3, 2);
        g.addEdge(3, 4, 1);
        return g;
    }
}
