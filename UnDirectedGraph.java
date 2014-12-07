import java.util.*;
/**
 * Create a undirected graph.
 * @author Greg Flynn
 */
public class UnDirectedGraph<K,E>{
  
  

  private AbstractList<UnDirectedGraphNode> nodes = new ArrayList<UnDirectedGraphNode>();
  private AbstractList<UnDirectedGraphNode> station = new ArrayList<UnDirectedGraphNode>();
  private AbstractList<UnDirectedGraphNode> depot = new ArrayList<UnDirectedGraphNode>();
  
  /**
   * Create a new UnDirectedGraph
   * @param k the key for the first vertex
   * @param e the element for the first vertex
   */
  public UnDirectedGraph(K k, E e){
    UnDirectedGraphNode n = createNewNode(k,e);
    nodes.add(n);
  }
  
  /**
   * Get the graph size
   * @return the size of the graph
   */
  public int size(){
    return nodes.size();
  }
  
  /**
   * Add a new node to the UnDirectedGraph.  Only unique vertex key 
   * can be added so if it is not a unique key then false is returned
   * @param k the key for the new vertex
   * @param e the element for the new vertex
   * @return was the add sucsessful?
   */
  public boolean addNode(K k, E e){
    if(contains(k)) return false;
    UnDirectedGraphNode n = createNewNode(k,e);
    nodes.add(n);
    return true;
  }
  
  /**
   * Create a new node and return it
   */
  private UnDirectedGraphNode createNewNode(K k, E e){
    UnDirectedGraphNode n = new UnDirectedGraphNode();
    n.setKey(k);
    n.setElement(e);
    return n;
  }
      
  /**
   * Shows if a key is in the graph already
   */
  private boolean contains(K k){
    for(UnDirectedGraphNode n : nodes){
      if(n.getKey().equals(k)) return true;
    }
    return false;
  }
  
  /**
   * Check that all of the vertex are connected to something
   * @return are all the nodes connected to something
   */
  public boolean allConnected(){
    for(UnDirectedGraphNode n : nodes){
      if(n.connectionCount()==0) return false;
    }
    return true;
  }
  
  /**
   * Print out the vertex and type, if there is a gas station or depot.
   */
  public void displayNodes(){
    for(UnDirectedGraphNode n : nodes){
      System.out.println(n.getKey()+":"+n.getElement());
    }
  }
  
  /**
   * Connect two vertex together.  If the key
   * doesn't exits or both keys are the same
   * this method returns false
   * @param k1 one vertex key
   * @param k2 the other vertex key
   * @param w the weight of the connection
   * @return was the connection sucsessful
   */
  public boolean addEdge(K k1, K k2, int w){
    // check that k1 and k2 are different
    if(k1.equals(k2)) return false;
    
    //Find the vertex with the key
    UnDirectedGraphNode n1 = findNode(k1);
    UnDirectedGraphNode n2 = findNode(k2);
    
    if(n1 == null || n2 == null) return false;
    
    // add the edges in
    n1.addEdge(n2,w);
    n2.addEdge(n1,w);
    return true;
  }
  
  /*
   * Convert a key into a node
   */
  private UnDirectedGraphNode findNode(K key){
    for(UnDirectedGraphNode n : nodes){      
      if(n.getKey().equals(key)) return n;
    }
    return null;
  }
  
  
  /**
   * Set a node to a gas station
   * @param key the key to search by
   * @param element the element to set the graph to
   * @return was the element change?
   */
  public boolean setNodeStation(K key, E element){
    //if(!depot.contains(findNode(key))) return false;
    station.add(findNode(key));
    findNode(key).setElement(element);
    return true;
  }
    
  /**
   * Set a node to a gas depot
   * @param key the key to search by
   * @param element the element to set the graph to
   * @return was the element change?
   */
  public boolean setNodeDepot(K key, E element){
    //if(!station.contains(findNode(key))) return false;
    depot.add(findNode(key));    
    findNode(key).setElement(element);
    return true;
  }
  
  /**
   * Solve all of the cities.  Print out to stdout
   */
  public void optimiseWorld(){
    for(UnDirectedGraphNode pump : station)      
      //displayPath(optimiseStation(pump));
      pathLength(optimiseStation(pump));
  }
  
  /**
   * Print out just each length of the quickest route to a depot from a station
   */
  public void pathLength(ArrayList<UnDirectedGraphNode> path){
    if(path.size()==0) {
      return;
    }
    System.out.print(path.get(0).getKey()+" ");
    System.out.println(path.get(0).getDistance());
  }
    
  private void displayPath(ArrayList<UnDirectedGraphNode> path){
    if(path.size()==0) {
      return;
    }
    for(UnDirectedGraphNode pathVertex : path){
        System.out.print(pathVertex.getKey()+ " ");
      }
    System.out.println(path.get(0).getDistance());
  }
  
  
  private ArrayList<UnDirectedGraphNode> optimiseStation(UnDirectedGraphNode station){
    int min = Integer.MAX_VALUE;
    ArrayList<UnDirectedGraphNode> path = new ArrayList<UnDirectedGraphNode>();
    computePath(station);
    for(UnDirectedGraphNode depotTarget : depot){
        int distance = getShortestPathToWeight(depotTarget);
        if(distance<min){
          min = distance;
          path = findShortestPath(station.getKey(),depotTarget.getKey());
        }
      }
    
    Collections.reverse(path);
    return path;
  }
  
  /**
   * Find the shortest path from one vertex to another
   * @param k1 the start vertex
   * @param k2 the end vertex
   * @return an arraylist of nodes traveled through
   */
  public ArrayList<UnDirectedGraphNode> findShortestPath(K k1, K k2){
    resetDistances();
    UnDirectedGraphNode start = findNode(k1);
    UnDirectedGraphNode end = findNode(k2);
    computePath(start);
    return getShortestPathTo(end);
  }
  
  /**
   * Convert a path of nodes to a path of keys
   * @param k1 the start vertex
   * @param k2 the end vertex
   * @return an arrayList of keys traveled through
   */
  public ArrayList<K> findShortestPathKeys(K k1, K k2){
    ArrayList<K> path = new ArrayList<K>();
    ArrayList<UnDirectedGraphNode> nodes = findShortestPath(k1,k2);
    for(UnDirectedGraphNode n: nodes){
      path.add(n.getKey());
    }
    return path;
  }
  
  /**
   * Convert a path of nodes to a path of elements
   * @param k1 the start vertex
   * @param k2 the end vertex
   * @return an arrayList of elements traveled through
   */
  public ArrayList<E> findShortestPathElements(K k1, K k2){
    ArrayList<E> path = new ArrayList<E>();
    ArrayList<UnDirectedGraphNode> nodes = findShortestPath(k1,k2);
    for(UnDirectedGraphNode n: nodes){
      path.add(n.getElement());
    }
    return path;
  }
  
    
  /**
   * compute the path from one node to everything.
   * Referenced from 
   * http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map
   * and
   * http://en.literateprograms.org/index.php?title=Special%3aDownloadCode/Dijkstra%27s_algorithm_%28Java%29&oldid=15444
   * and
   * http://en.literateprograms.org/Dijkstra%27s_algorithm_(Java)
   * @param n the node to start from
   */ 
  private void computePath(UnDirectedGraphNode n){
    if (n==null) return;
    n.setDistance(0);
    PriorityQueue<UnDirectedGraphNode> nodeQueue = new PriorityQueue<UnDirectedGraphNode>();
    nodeQueue.add(n);
    while (!nodeQueue.isEmpty()){
      UnDirectedGraphNode next = nodeQueue.poll(); // the next node to explore
      // Explore each edge
      for (Edge adj : next.adjVertex()){
        //Select the next node
        UnDirectedGraphNode adjNode = adj.target();
        int weight = adj.getWeight(); // distance
        int totalDistanceToVertex = next.getDistance()+weight; // total distance from start
        
        if(totalDistanceToVertex < adjNode.getDistance()){ // if shorter path found
          nodeQueue.remove(next);
          // build the path
          adjNode.setDistance(totalDistanceToVertex); // set new distance
          adjNode.setPrevious(next);
          nodeQueue.add(adjNode);
        }
      }
    }
  }
  
    /**
   * compute the quickest way from one 
   * Referenced from 
   * http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map
   * and
   * http://en.literateprograms.org/index.php?title=Special%3aDownloadCode/Dijkstra%27s_algorithm_%28Java%29&oldid=15444
   * and
   * http://en.literateprograms.org/Dijkstra%27s_algorithm_(Java)
   * @param n the node to start from
   */ 
  private ArrayList<UnDirectedGraphNode> getShortestPathTo(UnDirectedGraphNode target)
    {
        ArrayList<UnDirectedGraphNode> path = new ArrayList<UnDirectedGraphNode>();
        for (UnDirectedGraphNode vertex = target; vertex != null; vertex = vertex.getPrevious()){
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }
  
  private int getShortestPathToWeight(UnDirectedGraphNode target)
    {
        return target.getDistance();
    }
  
  /**
   * Set the distance between nodes to effectivly infinity
   */
  private void resetDistances(){
    for (UnDirectedGraphNode n : nodes){
      n.setDistance(Integer.MAX_VALUE);
    }
    for (UnDirectedGraphNode n : nodes){
      n.setPrevious(null);
    }
  }
  
  
/****************************************************************************************************************************/
  
  
  /**
   * A class for each vertex of the graph
   */
  class UnDirectedGraphNode implements Comparable<UnDirectedGraphNode>{
    
    private K key;
    private E element;
    private AbstractList<Edge> connections;
    private int connectionCount = 0;
    private int distance = Integer.MAX_VALUE;
    private UnDirectedGraphNode previous;
    
    
    /**
     * Compare the distance between two vertex
     */
    public int compareTo(UnDirectedGraphNode other)
    {
        return Double.compare(distance, other.getDistance());
    }
        
    /**
     * Create a new node
     */
    public UnDirectedGraphNode(){
      connections = new LinkedList<Edge>();
    }
    
    /**
     * Set the key to a value
     * @param key the key of the vertex
     */
    public void setKey(K key){
      this.key = key;
    }
    
    /**
     * Get the key of the vertex
     * @return the key of the vertex
     */
    public K getKey(){
      return this.key;
    }
    
    /**
     * Set the element to a value
     * @param element the element of the vertex
     */
    public void setElement(E element){
      this.element = element;
    }
    
    /**
     * Get the element stored at this vertex
     * @return the element of the vertex
     */
    public E getElement(){
      return this.element;
    }
    
    /**
     * Add a connection
     * @param n the node to connect
     * @param w the weight of the connections
     */
    public void addEdge(UnDirectedGraphNode n, int w){
      Edge e = new Edge();
      e.connect(n);
      e.setWeight(w);
      connections.add(e);
      connectionCount += 1;
    }
    
    /**
     * Check how many edges the vertex has
     * @return the connection count
     */
    public int connectionCount(){
      return this.connectionCount;
    }
  
    /**
     * Set the distance from the starting vertex
     * @param n the distance traveled to the vertex
     */
    public void setDistance(int n){
      this.distance = n;
    }
    
    /**
     * Get the distance from the starting vertex
     * @return the distance traveled to here
     */
    public int getDistance(){
      return this.distance;
    }
      
    /**
     * Get a list of adjacent nodes
     */
    public AbstractList<Edge> adjVertex(){
      return this.connections;
    }
    
    /**
     * Get the previous node on a path
     * @return the node before
     */
    public UnDirectedGraphNode getPrevious(){
      return this.previous;
    }
    
    /**
     * Set the previous node on a path
     * @param previous the node before
     */
    public void setPrevious(UnDirectedGraphNode previous){
      this.previous = previous;
    }
    
  } 
    
/****************************************************************************************************************************/
  
  /**
   * A class for connecting one node to another.
   * This class is stored in an array list for each 
   * vertex.  The values stored are weights and what
   * node is conencted.
   * 
   */
  class Edge{
    private UnDirectedGraphNode n;
    private int weight;
    
    /**
     * Create a new connection
     */
    public Edge(){
    }
    
    /**
     * Connects two nodes.
     * @param n
     */
    public void connect(UnDirectedGraphNode n){
      this.n = n;
    }
    
    /**
     * Set the weight of the edge
     * @param weight the weight of the edge
     */
    public void setWeight(int weight){
      this.weight = weight;
    }
    
    /**
     * Get the weight of the edge
     * @return the weight
     */
    public int getWeight(){
      return this.weight;
    }
    /**
     * Get the vertex that the edge points too
     * @return the next vertex
     */
    public UnDirectedGraphNode target(){
      return this.n;
    }
    
      
  }
  
}
