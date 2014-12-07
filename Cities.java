import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Create a network of cities depots and stations
 * @author Greg Flynn
 */
public class Cities{
  private int vertexA, vertexB;
  private int weight;
  
  public UnDirectedGraph<Integer,String> graph;
  
  public Cities(){
    
  }
  
  /**
   * Read in a file and insert the information into the graph.
   * It is assumed that each city is on its own line.
   * @param fileName the name of the file to load in
   * @return was the insert of the entire file sucsessful
   */
  public boolean insertFile(String fileName){
   try{
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = reader.readLine();
      while(line!=null){
        Scanner sc = new Scanner(line);
        readVertex(sc);
        insertIntoGraph();
        line = reader.readLine();
      }
      return true;
   }
   catch(java.io.FileNotFoundException e){
     System.err.println(e.getMessage());
     return false;
   }
   catch(java.io.IOException e){
     System.err.println(e.getMessage());
     return false;
   }
   catch(java.util.InputMismatchException e){
     System.err.println(e.getMessage());
     return false;
   }
   
  }
  
  private void readVertex(Scanner sc){
    vertexA = sc.nextInt();
    sc.next();
    vertexB = sc.nextInt();
    weight = sc.nextInt();
  }
  
  private void insertIntoGraph(){
    if(graph == null) graph = new UnDirectedGraph<Integer,String>(vertexA,"Nothing");
    else graph.addNode(vertexA,"Nothing");
    graph.addNode(vertexB,"Nothing");
    graph.addEdge(vertexA,vertexB,weight);
  }
  
  public boolean addNodeLocations(String fileName){
   try{
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = reader.readLine();
      while(line!=null){
        Scanner sc = new Scanner(line);
        String type = sc.next();
        if(type.equals("depot")){
          addDepot(sc.nextInt());          
        }
        else if(type.equals("station")){
          addStation(sc.nextInt());
        }
        line = reader.readLine();
      }
      return true;
   }
   catch(java.io.FileNotFoundException e){
     System.err.println(e.getMessage());
     return false;
   }
   catch(java.io.IOException e){
     System.err.println(e.getMessage());
     return false;
   }
   catch(java.util.InputMismatchException e){
     System.err.println(e.getMessage());
     return false;
   }
   
  }
  
  private void addStation(int key){
    graph.setNodeStation(key,"Station");
  }
  
  private void addDepot(int key){
    graph.setNodeDepot(key,"Depot");
  }
  
  
  public void showAllCities(){
    graph.displayNodes();
  }
  
  public void solve(){
    graph.optimiseWorld();
  }
 
}
