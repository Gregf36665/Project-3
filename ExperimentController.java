/**
 * This class runs the test
 * @author Greg Flyn
 */
public class ExperimentController{
  
  /**
   * The constructor makes an instance of the class
   */
  public ExperimentController(){
  }
  
  private long solveTime(String paths, String locations){
    long startTime = System.nanoTime();
    solve(paths,locations);
    long stopTime = System.nanoTime();
    return stopTime-startTime;
  }
  
  private void solve(String paths, String locations){
    try{
    Cities f = new Cities();  
    f.insertFile(paths);
    assert(f.graph.allConnected());
    f.addNodeLocations(locations);
    f.solve();
    }
    catch(java.lang.ArrayIndexOutOfBoundsException e){
      System.err.println("Warning not enough files specified.  Need 2");
      System.exit(-1);
    }
    catch(java.lang.AssertionError e){
      System.err.println("Warning not all nodes connected.");
      System.exit(-2);
    }
  }
  
  
  
  
  /**
   * This method runs all of the simulation
   * 
   */
  public void run(){
    medium();
    

  }
  
  private void large(){
    for (int i=1;i<4;i++){
      System.out.println("easy Large " + i);
      String paths = "testData/large-"+i+"/500-100000.pyg";
      String loc = "testData/large-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  private void medium(){
    for (int i=1;i<4;i++){
      System.out.println("easy Medium " + i);
      String paths = "testData/medium-"+i+"/100-1000.pyg";
      String loc = "testData/medium-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  private void small(){
    for (int i=1;i<4;i++){
      System.out.println("easy Small " + i);
      String paths = "testData/small-"+i+"/25-100.pyg";
      String loc = "testData/small-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  
  public static void main(String[] args){
    ExperimentController e = new ExperimentController();
    e.run();

  }
  
  
  
}
