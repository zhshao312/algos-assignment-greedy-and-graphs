/**
 * Public Transit
 * Author: Zihao and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

  /**
   * The algorithm that could solve for shortest travel time from a station S
   * to a station T given various tables of information about each edge (u,v)
   *
   * @param S the s th vertex/station in the transit map, start From
   * @param T the t th vertex/station in the transit map, end at
   * @param startTime the start time in terms of number of minutes from 5:30am
   * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
   * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
   * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
   * @return shortest travel time between S and T
   */
  public int myShortestTravelTime(
    int S,
    int T,
    int startTime,
    int[][] lengths,
    int[][] first,
    int[][] freq
  ) {
    // Your code along with comments here. Feel free to borrow code from any
    // of the existing method. You can also make new helper methods.
	   int numVertices = lengths[0].length;


	    int[] times = new int[numVertices];


	    Boolean[] processed = new Boolean[numVertices];



	    for (int v = 0; v < numVertices; v++) {
	      times[v] = Integer.MAX_VALUE;
	      processed[v] = false;
	    }

	    times[S] = 0;

	    int trainPass=0;
	    int totalTime=0;
	    for (int count = 0; count < numVertices - 1 ; count++) {

	      startTime=startTime+totalTime; // everytime viste a new node update the start time

	      int u = findNextToProcess(times, processed);
	      processed[u] = true;

	      for (int v = 0; v < numVertices; v++) {

	        if(!processed[v] && first[u][v]!=0 ){  //if stop can be visted 
	          trainPass=first[u][v];

	          while(trainPass<startTime){      //get the actual time of train that passenger can take on
	            trainPass=trainPass+freq[u][v];
	          }


	           int waitTime= trainPass-startTime;   // get the totalweight
	           totalTime=lengths[u][v]+waitTime;

	        }

	        if (!processed[v] && lengths[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+totalTime < times[v]) {

	          times[v] = times[u] + totalTime;   

	        }


	      }
	    }


	    return times[T];
	  }

  /**
   * Finds the vertex with the minimum time from the source that has not been
   * processed yet.
   * @param times The shortest times from the source
   * @param processed boolean array tells you which vertices have been fully processed
   * @return the index of the vertex that is next vertex to process
   */
  public int findNextToProcess(int[] times, Boolean[] processed) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;

    for (int i = 0; i < times.length; i++) {
      if (processed[i] == false && times[i] <= min) {
        min = times[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public void printShortestTimes(int times[]) {
    System.out.println("Vertex Distances (time) from Source");
    for (int i = 0; i < times.length; i++)
        System.out.println(i + ": " + times[i] + " minutes");
  }

  /**
   * Given an adjacency matrix of a graph, implements
   * @param graph The connected, directed graph in an adjacency matrix where
   *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
   * @param source The starting vertex
   */
  public void shortestTime(int graph[][], int source) {
    int numVertices = graph[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[source] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
          times[v] = times[u] + graph[u][v];
        }
      }
    }

    printShortestTimes(times);
  }

  public static void main (String[] args) {
    /* length(e) */
    int lengthTimeGraph[][] = new int[][]{
      {0, 4, 0, 0, 0, 0, 0, 8, 0},
      {4, 0, 8, 0, 0, 0, 0, 11, 0},
      {0, 8, 0, 7, 0, 4, 0, 0, 2},
      {0, 0, 7, 0, 9, 14, 0, 0, 0},
      {0, 0, 0, 9, 0, 10, 0, 0, 0},
      {0, 0, 4, 14, 10, 0, 2, 0, 0},
      {0, 0, 0, 0, 0, 2, 0, 1, 6},
      {8, 11, 0, 0, 0, 0, 1, 0, 7},
      {0, 0, 2, 0, 0, 0, 6, 7, 0}
    };
    FastestRoutePublicTransit t = new FastestRoutePublicTransit();
    t.shortestTime(lengthTimeGraph, 1);


    // You can create a test case for your implemented method for extra credit below
    int test[][] = new int[][]{
      {0, 15, 0, 0, 0, 0, 0, 28, 0},
      {15, 0, 18, 0, 0, 0, 0, 25, 0},
      {0, 18, 0, 17, 0, 24, 0, 0, 12},
      {0, 0, 17, 0, 19, 23, 0, 0, 0},
      {0, 0, 0, 19, 0, 20, 0, 0, 0},
      {0, 0, 24, 23, 20, 0, 22, 0, 0},
      {0, 0, 0, 0, 0, 22, 0, 21, 16},
      {28, 25, 0, 0, 0, 0, 21, 0, 27},
      {0, 0, 12, 0, 0, 0, 16, 27, 0}
    };


    FastestRoutePublicTransit myTest = new FastestRoutePublicTransit();
    int shortestTravelTime = myTest.myShortestTravelTime(1, 5, 90, lengthTimeGraph, test, lengthTimeGraph);
    System.out.print("the shortest time: "+shortestTravelTime+" minutes");


  }
 }