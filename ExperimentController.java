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
    catch(java.lang.AssertionError e){
      System.err.println("Warning not all nodes connected.");
      System.exit(-1);
    }
  }
  
  
  
  
  /**
   * This method runs all of the simulation
   * 
   */
  public void run(){
    large();
    small();
    medium();
  }
  
  private void large(){
    for (int i=1;i<4;i++){
      String paths = "testData/large-"+i+"/500-100000.pyg";
      System.out.println("easy Large " + i);
      System.err.println("easy Large " + i);
      String loc = "testData/large-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("medium Large " + i);
      System.err.println("medium Large " + i);
      loc = "testData/large-"+i+"/medium/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("hard Large " + i);
      System.err.println("hard Large " + i);
      loc = "testData/large-"+i+"/hard/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  private void medium(){
    for (int i=1;i<4;i++){
      String paths = "testData/medium-"+i+"/100-1000.pyg";
      System.out.println("easy Medium " + i);
      System.err.println("easy Medium " + i);
      String loc = "testData/medium-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("medium Medium " + i);
      System.err.println("medium Medium " + i);
      loc = "testData/medium-"+i+"/medium/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("hard Medium " + i);
      System.err.println("hard Medium " + i);
      loc = "testData/medium-"+i+"/hard/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  private void small(){
    for (int i=1;i<4;i++){
      String paths = "testData/small-"+i+"/25-100.pyg";
      System.out.println("easy Small " + i);
      System.err.println("easy Small " + i);
      String loc = "testData/small-"+i+"/easy/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("medium Small " + i);
      System.err.println("mediumsy Small " + i);
      loc = "testData/small-"+i+"/medium/locations.txt";
      System.err.println(solveTime(paths,loc));
      System.out.println("hard Small " + i);
      System.err.println("hard Small " + i);
      loc = "testData/small-"+i+"/hard/locations.txt";
      System.err.println(solveTime(paths,loc));
    }
  }
  
  
  public static void main(String[] args){
    ExperimentController e = new ExperimentController();
    e.run();

  }
  
  
  
}
