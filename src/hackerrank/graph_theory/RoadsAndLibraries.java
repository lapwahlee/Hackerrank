package hackerrank.graph_theory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Determine the minimum cost to provide library access to all citizens of HackerLand. There are N cities numbered from 1 to N. 
Currently there are no libraries and the cities are not connected. Bidirectional roads may be built between any city pair listed in cities. 
A citizen has access to a library if:

Their city contains a library.
They can travel by road from their city to a city containing a library.

Example

The following figure is a sample map of HackerLand where the dotted lines denote possible roads:

     1
    / |\ 
   /  | \
  7  3 -- 2

5 -- 6 -- 8

c_road = 2
c_lib = 3
cities [[1,7],[1,3],[1,2],[2,3],[5,6],[6,8]]

The cost of building any road is cc_road=2, and the cost to build a library in any city is c_lib=3. 
Build 5 roads at a cost of 5x2=10 and 2 libraries for a cost of 6. One of the available roads in the cycle 1->2->3->1  is not necessary.

There are q queries, where each query consists of a map of HackerLand and value of c_lib and c_road. 
For each query, find the minimum cost to make libraries accessible to all the citizens.

Function Description

Complete the function roadsAndLibraries in the editor below.
roadsAndLibraries has the following parameters:

int n: integer, the number of cities
int c_lib: integer, the cost to build a library
int c_road: integer, the cost to repair a road
int cities[m][2]: each cities[i] contains two integers that represent cities that can be connected by a new road
Returns
- int: the minimal cost

Input Format

The first line contains a single integer q, that denotes the number of queries.

The subsequent lines describe each query in the following format:
- The first line contains four space-separated integers that describe the respective values of n, m, c_lib and c_road, the number of cities, 
number of roads, cost of a library and cost of a road.
- Each of the next m lines contains two space-separated integers, u[i] and v[i], 
that describe a bidirectional road that can be built to connect cities u[i] and v[i].

Constraints
- 1 <= q <= 10
- 1 <= n <= 10^5
- 0 <= m <= min(10^5, n*(n-1)/2) 
- 1 <= c_road, c_lib <= 10^5
- 1 <= u[i], v[i] <= n
- Each road connects two distinct cities

Each road connects two distinct cities.
Sample Input

STDIN       Function
-----       --------
2           q = 2
3 3 2 1     n = 3, cities[] size m = 3, c_lib = 2, c_road = 1
1 2         cities = [[1, 2], [3, 1], [2, 3]]
3 1
2 3
6 6 2 5     n = 6, cities[] size m = 6, c_lib = 2, c_road = 5
1 3         cities = [[1, 3], [3, 4],...]
3 4
2 4
1 2
2 3
5 6

Sample Output
4
12

Explanation

Perform the following q=2 queries:
HackerLand contains n=3 cities and can be connected by m=3 bidirectional roads. The price of building a library is c_lib=2 and 
the price for repairing a road is c_road=1.

(1)->2->3

The cheapest way to make libraries accessible to all is to:

Build a library in city 1 at a cost of x=2.
Build the road between cities 1 and 2 at a cost of y=1.
Build the road between cities 2 and 3 at a cost of y=1.
This gives a total cost of 2+1+1=4. Note that the road between cities 3 and 1 does not need to be built because each is connected to city 2.

In this scenario it is optimal to build a library in each city because the cost to build a library is less than the cost to build a road.

(1) (2) (3) (4) (5) (6)

There are 6 cities, so the total cost 6x2 is 12. 
 */
public class RoadsAndLibraries {
	
    public static class Edge {
        public int u, v;
        public long w;
        public Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static class UnionFind {
        private int parent[];
        private int rank[];

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int findSet(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public void union(int a, int b) {
            int setA = findSet(a);
            int setB = findSet(b);

            if (rank[setA] > rank[setB]) {
                parent[setB] = setA;
            } else {
                parent[setA] = setB;
                if (rank[setA] == rank[setB]) {
                    rank[setB] += 1;
                }
            }
        }

        public boolean connected(int a, int b) {
            return (findSet(a) == findSet(b));
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long n = in.nextLong();
            long m = in.nextLong();
            long lib = in.nextLong();
            long road = in.nextLong();
            
            List<Edge> edges = new ArrayList<>();
            for(int a1 = 0; a1 < m; a1++){
                int city_1 = in.nextInt();
                int city_2 = in.nextInt();
                edges.add(new Edge(city_1, city_2, road));
            }
            
            UnionFind uf = new UnionFind((int)(n + 1));
            long minCost = n * lib;
            long roadCosts = 0;
            long libCosts = n * lib;
            
            for (Edge edge : edges) {
                if (!uf.connected(edge.u, edge.v)) {
                    uf.union(edge.u, edge.v);
                    roadCosts += road;
                    libCosts -= lib;
                    minCost = Math.min(minCost, roadCosts + libCosts);
                }
            }
            
            System.out.println(minCost);
        }
    }

}
