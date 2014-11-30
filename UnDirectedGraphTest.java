import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class UnDirectedGraphTest extends TestCase {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  public void testDuplicate() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(!g.addNode(5,5));
  }
  
  public void testInsert() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
  }
  
  
  public void testSize() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(g.size()==2);
  }
    
  public void testConnect() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(g.addEdge(3,5,10));
  }
  
  public void testBadConnect() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(g.addEdge(3,5,10));
    assert(!g.addEdge(4,5,9));
  }
  
  public void testNegConnectWeight() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(g.addEdge(3,5,-9));
  }
  
  public void testAllConnected() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(g.addEdge(5,3,9));
    assert(g.allConnected());
  }
    
  public void testAllConnectedFail() {
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(g.addNode(3,3));
    assert(!g.allConnected());
  }
    
  public void testConnectToSelf(){
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(5,5);
    assert(!g.addEdge(5,5,10));
  }
  
  
  public void testQuickestRouteBasic(){
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(1,1);
    g.addNode(2,2);
    g.addNode(3,3);
    g.addEdge(1,2,1);
    g.addEdge(1,3,1);
    g.addEdge(2,3,1);
    assert(g.findShortestPathKeys(1,2).size()==2);
    assert(g.findShortestPathKeys(1,2).get(0)==1);
    assert(g.findShortestPathKeys(1,2).get(1)==2);    
  }
  
   public void testQuickestRouteFail(){
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(1,1);
    g.addNode(2,2);
    g.addNode(3,3);
    g.addEdge(1,2,1);
    g.addEdge(1,3,1);
    g.addEdge(2,3,1);
    assert(g.findShortestPathKeys(1,4).size()==0); 
  }
   
  public void testQuickestRouteMedium(){
    UnDirectedGraph<Integer,Integer> g = new UnDirectedGraph<Integer,Integer>(1,1);
    g.addNode(2,2);
    g.addNode(3,3);
    g.addNode(4,4);
    g.addNode(5,5);
    g.addEdge(3,4,3);
    g.addEdge(1,2,10);
    g.addEdge(2,4,1);
    g.addEdge(4,3,3);
    g.addEdge(4,4,3);
    g.addEdge(1,3,5);
    g.addEdge(1,5,15);
    g.addEdge(4,5,6);
    assert(g.findShortestPathKeys(1,5).size()==4);
    assert(g.findShortestPathKeys(1,5).get(0)==1);
    assert(g.findShortestPathKeys(1,5).get(1)==3);
    assert(g.findShortestPathKeys(1,5).get(2)==4);
    assert(g.findShortestPathKeys(1,5).get(3)==5);    
  } 
}
