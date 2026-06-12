import java.util.ArrayList;
import java.util.List;

/*
 * YOUR deliverable -- implement Dijkstra's shortest-path algorithm here.
 *
 * The test harness (Main.java) only touches the contract below:
 *   - it calls run(g, source) once,
 *   - then reads the public dist[] array,
 *   - and calls pathTo(target) to print each shortest path.
 * How you compute them is entirely yours. Step-by-step help: DIJKSTRA-STEPS.md.
 *
 * Contract you must satisfy:
 *   dist[v] = shortest total weight from source to v, or INF if v is unreachable.
 *   prev[v] = the vertex immediately before v on a shortest path, or -1 if none.
 *   pathTo(v) = the vertex sequence source..v (inclusive), or an empty list if unreachable.
 */
public class Dijkstra {

    public static final int INF = Integer.MAX_VALUE;

    public int[] dist;   // filled by run(): shortest distance source -> v  (INF if unreachable)
    public int[] prev;   // filled by run(): predecessor of v on a shortest path (-1 if none)

    // Steps 1-4 (DIJKSTRA-STEPS.md): initialize dist[]/prev[], then use a
    // PriorityQueue (min-heap) to repeatedly pull the closest unsettled vertex
    // and relax its outgoing edges, recording predecessors as distances improve.
    public void run(Graph g, int source) {
        // TODO(you): implement Dijkstra. Read g.size(), g.neighbors(u), e.to, e.weight.
        //            Leaving dist null for now -- the harness prints a friendly
        //            "not implemented yet" until you fill this in.
    }

    // Step 5: walk prev[] from target back to the source, then reverse so the
    // list reads source..target. Return an empty list if target is unreachable.
    public List<Integer> pathTo(int target) {
        // TODO(you): reconstruct the path from prev[].
        return new ArrayList<>();
    }
}
