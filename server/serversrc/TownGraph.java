package serversrc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// import serversrc.Route;
// import serversrc.Town;

public class TownGraph implements Serializable {
    // use HashMap to store edges in graph
    private Map<Town, List<Route>> map;

    // HashMap to store relations between Towns
    private Map<Town, List<Town>> townMap;

    public TownGraph() {
        this.map = new HashMap<>();
        this.townMap = new HashMap<>();
    }

    // adds vertex to graph
    public void addVertex(Town t) {
        map.put(t, new LinkedList<>());
    }

    // adds edge (route) between source and destination
    public void addEdge(Route pRoute) {
        Town source = pRoute.getSource();
        Town dest = pRoute.getDest();
        // add towns if not included yet in map
        if (!map.containsKey(source)) {
            addVertex(source);
        }
        if (!map.containsKey(dest)) {
            addVertex(dest);
        }
        // add towns if not included in townMap
        if (!townMap.containsKey(source)) {
            townMap.put(source, new LinkedList<>());
        }
        if (!townMap.containsKey(dest)) {
            townMap.put(dest, new LinkedList<>());
        }
        // add Route to list of routes for the towns given
        map.get(source).add(pRoute);
        map.get(dest).add(pRoute);
        // add Towns to town relation map
        townMap.get(source).add(dest);
        townMap.get(dest).add(source);
    }

    // to add list of edges
    public void addEdges(List<Route> li) {
        for (Route r : li) {
            addEdge(r);
        }
    }

    // function to check wether edge exist or not between 2 towns
    public boolean hasEdge(Town s, Town d) {
        return townMap.get(s).contains(d);
    }

    // function to get the route between 2 towns
    // @pre town s and d needs to have an existing edge
    public Route getRoute(Town s, Town d) {
        assert hasEdge(s, d);
        List<Route> routes = map.get(s);
        // loop through routes to find the right one
        for (Route r : routes) {
            Town source = r.getSource();
            Town dest = r.getDest();
            // return if d matches one of the towns in route
            if (d.equal(source) || d.equal(dest)) {
                return r;
            }
        }
        return null;
    }

    // returns shortest distance from source town to dest town
    public int getDistanceAway(Town s, Town d) {
        int output = 0;
        // return 0 if they're the same town
        if (s.getTownName().equals(d.getTownName())) {
            output = 0;
        }
        // otherwise find shortest path
        else {
            // number of towns
            int v = ServerGame.towns.size();
            Town pred[] = new Town[v];
            int dist[] = new int[v];

            ArrayList<Town> townsList = new ArrayList<>();
            ArrayList<ArrayList<Town>> adj = new ArrayList<>();

            // do adjacency list
            for (Map.Entry<Town, List<Town>> entry : townMap.entrySet()) {
                townsList.add(entry.getKey());
                ArrayList<Town> temp = new ArrayList<>(entry.getValue());
                adj.add(temp);
            }

            // there's always a path in the whole graph so this shouldn't happen
            if (BFS(adj, s, d, v, pred, dist, townsList) == false) {
                return -1;
            }
            // linked list to store path
            LinkedList<Town> path = new LinkedList<Town>();
            Town crawl = d;
            path.add(crawl);
            while (pred[townsList.indexOf(crawl)] != null) {
                path.add(pred[townsList.indexOf(crawl)]);
                crawl = pred[townsList.indexOf(crawl)];
            }
            output = dist[townsList.indexOf(d)];
        }
        return output;
    }

    // a modified version of BFS that stores predecessor
    // of each vertex in array pred
    // and its distance from source in array dist
    private static boolean BFS(ArrayList<ArrayList<Town>> adj, Town src,
            Town dest, int v, Town pred[], int dist[], ArrayList<Town> pTownsList) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Town type
        LinkedList<Town> queue = new LinkedList<Town>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = null;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[pTownsList.indexOf(src)] = true;
        dist[pTownsList.indexOf(src)] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            Town u1 = queue.remove();
            // index of u1
            int u = pTownsList.indexOf(u1);
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[pTownsList.indexOf(adj.get(u).get(i))] == false) {
                    visited[pTownsList.indexOf(adj.get(u).get(i))] = true;
                    dist[pTownsList.indexOf(adj.get(u).get(i))] = dist[u] + 1;
                    pred[pTownsList.indexOf(adj.get(u).get(i))] = u1;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (adj.get(u).get(i).getTownName().equalsIgnoreCase(dest.getTownName()))
                        return true;
                }
            }
        }
        return false;
    }
}
