import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;

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
 *
 *   It was actually interesting that this video came out right before I started this assignment.
 *   https://www.youtube.com/watch?v=kS-CGkiPetQ
 *
 */
public class Dijkstra {

    public static final int INF = Integer.MAX_VALUE;

    public int[] dist;   // filled by run(): shortest distance source -> v  (INF if unreachable)
    public int[] prev;   // filled by run(): predecessor of v on a shortest path (-1 if none)

    // this stores the "state" with a vertex and a current distance for that vertex which follows the 4minute mark on the veritasium video
    private static class State implements Comparable<State> {

        // comparing distances at given vertex
        int distance;
        int vertex;

        //current state constructor
        State(int vertex, int distance) {
            this.distance = distance;
            this.vertex = vertex;
        }

        //compare states
        @Override
        public int compareTo(State other) {
            return Integer.compare(this.distance, other.distance) ;
        }
    }

    // Steps 1-4 (DIJKSTRA-STEPS.md): initialize dist[]/prev[], then use a
    // PriorityQueue (min-heap) to repeatedly pull the closest unsettled vertex
    // and relax its outgoing edges, recording predecessors as distances improve.
    public void run(Graph g, int source) {
        // TODO(you): implement Dijkstra. Read g.size(), g.neighbors(u), e.to, e.weight.
        //            Leaving dist null for now -- the harness prints a friendly
        //            "not implemented yet" until you fill this in.
        // gets the size of the graph so we know what size of arrays we need
        int n = g.size();
        // make the distances array
        dist = new int[n];
        //make the previous node array
        prev = new int[n];

        //build the distance and previous node arrays
        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            prev[i] = -1;
        }

        // initial distance is 0
        dist[source] = 0;
        PriorityQueue<State> pq = new PriorityQueue<>();
        // add states (distance ond node to the queue to look through)
        pq.add(new State(source, 0));

        // look through each map node in the queue and update the states
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int u = cur.vertex;

            if (cur.distance != dist[u]) {
                continue;
            }

            //relaxation
            for (Graph.Edge e : g.neighbors(u)){
                int v = e.to;
                int w = e.weight;

                if (dist[u] != INF && dist[u] + w < dist[v]){
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                    pq.add(new State(v, dist[v]));
                }
            }

        }

    // dist[u] + w < dist[v]



    }

    // Step 5: walk prev[] from target back to the source, then reverse so the
    // list reads source..target. Return an empty list if target is unreachable.
    public List<Integer> pathTo(int target) {

        List<Integer> path = new ArrayList<>();

        if (dist == null || prev == null) {
            return path;
        }

        if (target < 0 || target >= dist.length) {
            return path;
        }

        if (dist[target] == INF) {
            return path;
        }

        int cur = target;
        while (cur != -1) {
            path.add(cur);
            cur = prev[cur];
        }


        Collections.reverse(path);


        return path;
    }
}

// more write up: in the video it talks about creating paths from each point, start and finish and finding the node that both paths hit first,
// so being able to reverse one end or the other to reconnect them is helpful...
// this drained my brain