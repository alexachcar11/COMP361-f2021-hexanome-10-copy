package serversrc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// import serversrc.Route;
// import serversrc.Town;

public class TownGraph {
    // use HashMap to store edges in graph
    private Map<Town, List<Route> > map;

    // HashMap to store relations between Towns
    private Map<Town, List<Town> > townMap;

    public TownGraph(){
        this.map = new HashMap<>();
        this.townMap = new HashMap<>();
    }
    
    // adds vertex to graph
    public void addVertex(Town t){
        map.put(t, new LinkedList<>());
    }

    // adds edge (route) between source and destination
    public void addEdge(Route pRoute){
        Town source = pRoute.getSource();
        Town dest = pRoute.getDest();
        // add towns if not included yet in map
        if (!map.containsKey(source)){
            addVertex(source);
        }
        if (!map.containsKey(dest)){
            addVertex(dest);
        }
        // add towns if not included in townMap
        if (!townMap.containsKey(source)){
            townMap.put(source, new LinkedList<>());
        }
        if (!townMap.containsKey(dest)){
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
    public void addEdges(List<Route> li){
        for (Route r: li){
            addEdge(r);
        }
    }

    // function to check wether edge exist or not between 2 towns
    public boolean hasEdge(Town s, Town d){
        return townMap.get(s).contains(d);
    }

    // function to get the route between 2 towns
    // @pre town s and d needs to have an existing edge
    public Route getRoute(Town s, Town d){
        assert hasEdge(s, d);
        List<Route> routes = map.get(s);
        // loop through routes to find the right one
        for (Route r: routes){
            Town source = r.getSource();
            Town dest = r.getDest();
            // return if d matches one of the towns in route
            if (d.equal(source)||d.equal(dest)){
                return r;
            }
        }
        return null;
    }
}
