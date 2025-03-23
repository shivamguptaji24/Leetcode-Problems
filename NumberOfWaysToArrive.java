/*
Daily Question :
1976 - Number of Ways to Arrive at Destination

You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.
You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 109 + 7.
 

Example 1:


Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
Output: 4
Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
The four ways to get there in 7 minutes are:
- 0 ➝ 6
- 0 ➝ 4 ➝ 6
- 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
- 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6

Example 2:

Input: n = 2, roads = [[1,0,10]]
Output: 1
Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
 

Constraints:

1 <= n <= 200
n - 1 <= roads.length <= n * (n - 1) / 2
roads[i].length == 3
0 <= ui, vi <= n - 1
1 <= timei <= 109
ui != vi
There is at most one road connecting any two intersections.
You can reach any intersection from any other intersection.
*/

class Solution {
    public int countPaths(int n, int[][] roads) {
        int MOD = 1_000_000_007;

        // Step 1: Build the graph as an adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        for (int[] road : roads) {
            int u = road[0], v = road[1], time = road[2];
            graph.get(u).add(new int[]{v, time});
            graph.get(v).add(new int[]{u, time});
        }

        // Step 2: Use Dijkstra's algorithm with path counting
        long[] dist = new long[n];  // Shortest distance array
        int[] ways = new int[n];    // Number of shortest paths array
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1; // One way to reach node 0

        // Min-heap (priority queue) storing {time, node}
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[]{0, 0}); // {time, node}

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long time = top[0];
            int node = (int) top[1];

            // If we already processed this node with a smaller time, skip it
            if (time > dist[node]) continue;

            for (int[] neighbor : graph.get(node)) {
                int next = neighbor[0];
                long newTime = time + neighbor[1];

                if (newTime < dist[next]) {
                    dist[next] = newTime;
                    ways[next] = ways[node]; // Reset ways to the new shortest path count
                    pq.add(new long[]{newTime, next});
                } else if (newTime == dist[next]) {
                    ways[next] = (ways[next] + ways[node]) % MOD; // Add new ways
                }
            }
        }

        return ways[n - 1]; // Number of ways to reach destination in shortest time
    }
}

/*
Visualization of the above code
 Let's break down the execution of the Dijkstra’s Algorithm with Path Counting using an example and visualize each step.

---

Example Input
```
n = 7
roads = [
  [0,6,7], [0,1,2], [1,2,3], [1,3,3], [6,3,3],
  [3,5,1], [6,5,1], [2,5,1], [0,4,5], [4,6,2]
]
```

Graph Representation
We first build an adjacency list representation of the graph:

```
    (0)
   / |  \
  /  |   \
(1)  (4)  (6)
  \  /  \  /
   (2)   (3)
      \  /
      (5)
```

Each edge represents a road with a travel time:

```
0 --(7)--> 6
0 --(2)--> 1
0 --(5)--> 4
1 --(3)--> 2
1 --(3)--> 3
6 --(3)--> 3
3 --(1)--> 5
6 --(1)--> 5
2 --(1)--> 5
4 --(2)--> 6
```

---

Step-by-Step Execution of Dijkstra’s Algorithm
Initialization
- `dist[]`: Stores the shortest time to each intersection  
  → `dist = [0, ∞, ∞, ∞, ∞, ∞, ∞]`  
- `ways[]`: Stores the number of shortest paths to each node  
  → `ways = [1, 0, 0, 0, 0, 0, 0]`  
- Priority Queue (`pq`) → Min-heap to always process the node with the smallest time first.  
  → `pq = [(0, 0)]` (Starting from node `0` with `time = 0`)

---

Step 1: Process Node 0
- Current node = `0`, Time = `0`
- Update its neighbors:
  - `0 → 6` (7 min) → Update `dist[6] = 7`, `ways[6] = 1`
  - `0 → 1` (2 min) → Update `dist[1] = 2`, `ways[1] = 1`
  - `0 → 4` (5 min) → Update `dist[4] = 5`, `ways[4] = 1`
- Updated Arrays:
  - `dist = [0, 2, ∞, ∞, 5, ∞, 7]`
  - `ways = [1, 1, 0, 0, 1, 0, 1]`
- Priority Queue: `pq = [(2,1), (5,4), (7,6)]`

---

Step 2: Process Node 1
- Current node = `1`, Time = `2`
- Update its neighbors:
  - `1 → 2` (2 + 3 = 5 min) → Update `dist[2] = 5`, `ways[2] = 1`
  - `1 → 3` (2 + 3 = 5 min) → Update `dist[3] = 5`, `ways[3] = 1`
- Updated Arrays:
  - `dist = [0, 2, 5, 5, 5, ∞, 7]`
  - `ways = [1, 1, 1, 1, 1, 0, 1]`
- Priority Queue: `pq = [(5,4), (5,2), (5,3), (7,6)]`

---

Step 3: Process Node 4
- Current node = `4`, Time = `5`
- Update its neighbors:
  - `4 → 6` (5 + 2 = 7 min)
  - `dist[6] == 7` (same as before) → Increment `ways[6] += ways[4]`  
  - `ways[6] = 1 + 1 = 2`
- Updated Arrays:
  - `dist = [0, 2, 5, 5, 5, ∞, 7]`
  - `ways = [1, 1, 1, 1, 1, 0, 2]`
- Priority Queue: `pq = [(5,2), (5,3), (7,6)]`

---

Step 4: Process Node 2
- Current node = `2`, Time = `5`
- Update its neighbor:
  - `2 → 5` (5 + 1 = 6 min) → Update `dist[5] = 6`, `ways[5] = 1`
- Updated Arrays:
  - `dist = [0, 2, 5, 5, 5, 6, 7]`
  - `ways = [1, 1, 1, 1, 1, 1, 2]`
- Priority Queue: `pq = [(5,3), (6,5), (7,6)]`

---

Step 5: Process Node 3
- Current node = `3`, Time = `5`
- Update its neighbors:
  - `3 → 5` (5 + 1 = 6 min)
  - `dist[5] == 6` → Increment `ways[5] += ways[3]`
  - `ways[5] = 1 + 1 = 2`
- Updated Arrays:
  - `dist = [0, 2, 5, 5, 5, 6, 7]`
  - `ways = [1, 1, 1, 1, 1, 2, 2]`
- Priority Queue: `pq = [(6,5), (7,6)]`

---

Step 6: Process Node 5
- Current node = `5`, Time = `6`
- Update its neighbor:
  - `5 → 6` (6 + 1 = 7 min)
  - `dist[6] == 7` → Increment `ways[6] += ways[5]`
  - `ways[6] = 2 + 2 = 4`
- Updated Arrays:
  - `dist = [0, 2, 5, 5, 5, 6, 7]`
  - `ways = [1, 1, 1, 1, 1, 2, 4]`
- Priority Queue: `pq = [(7,6)]`

---

Step 7: Process Node 6
- Current node = `6`, Time = `7`
- All nodes processed! ✅

---

Final Answer
The number of ways to reach node `6` in the shortest time is:  
```
Output: 4
```
✅ Paths:
1. `0 → 6`
2. `0 → 4 → 6`
3. `0 → 1 → 2 → 5 → 6`
4. `0 → 1 → 3 → 5 → 6`

---

Conclusion
- Shortest travel time to node `n-1 (6)` = `7 min`
- Total paths using shortest time = 4
- Time Complexity: \( O((V + E) \log V) \) (Efficient)
- Space Complexity: \( O(V + E) \) (Optimal)
*/
