/*
Daily Question :
2685 - Count the Number of Complete Components

You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1. You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting vertices ai and bi.
Return the number of complete connected components of the graph.
A connected component is a subgraph of a graph in which there exists a path between any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
A connected component is said to be complete if there exists an edge between every pair of its vertices.

 

Example 1:


Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
Output: 3
Explanation: From the picture above, one can see that all of the components of this graph are complete.

Example 2:


Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
Output: 1
Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.
 

Constraints:

1 <= n <= 50
0 <= edges.length <= n * (n - 1) / 2
edges[i].length == 2
0 <= ai, bi <= n - 1
ai != bi
There are no repeated edges.
*/

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        // Step 1: Build the adjacency list for the graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for(int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }

        for(int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        Set<Integer> visited = new HashSet<>();
        int completeComponents = 0;

        // Step 2: Traverse all nodes to find components
        for(int i = 0; i< n; i++) {
            if(!visited.contains(i)) {
                // Step 3: Perform BFS to explore the component
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited.add(i);

                Set<Integer> nodes = new HashSet<>();
                int edgeCount = 0;

                while(!queue.isEmpty()) {
                    int node = queue.poll();
                    nodes.add(node);
                    for(int neighbor : graph.get(node)) {
                        edgeCount++;  // Each edge counted twice
                        if(!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }

                // Step 4: Check if the component is complete
                int size = nodes.size();
                if(edgeCount / 2 == size * (size - 1) / 2) {
                    // Check expected edge count
                    completeComponents++;
                }
            }
        }

        return completeComponents;
    }
}

/*
Visualization of the above code
 To visualize the countCompleteComponents function, let’s break it down step by step with an example and illustrate how BFS explores the graph.

---

Example Walkthrough
Input
```
n = 6
edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
```

Graph Representation
We represent the graph as an adjacency list:
```
0 → {1, 2}
1 → {0, 2}
2 → {0, 1}
3 → {4, 5}
4 → {3}
5 → {3}
```
The nodes `{0,1,2}` form one connected component, and `{3,4,5}` form another.

---

Step-by-Step Execution
We will use BFS (Breadth-First Search) to find connected components and check if they are complete.

Step 1: Initialize Data Structures
- `graph`: Adjacency list representation of the graph.
- `visited`: Keeps track of visited nodes.
- `completeComponents`: Counter for the number of complete components.

---

Step 2: BFS Traversal
BFS on Component `{0,1,2}`
1. Start BFS from node `0`:
   ```
   Queue: [0]
   Visited: {0}
   ```
2. Visit `0`, add its neighbors `1` and `2`:
   ```
   Queue: [1, 2]
   Visited: {0,1,2}
   Edges: 3  (0-1, 0-2, 1-2)
   ```
3. Visit `1`, but its neighbors are already visited.
4. Visit `2`, but its neighbors are already visited.
5. BFS ends, nodes = {0,1,2}, edges = 3  
   Check completeness:
   ```
   Expected edges = (3 * (3 - 1)) / 2 = 3  ✅
   ```
   ✓ This is a complete component.

---

BFS on Component `{3,4,5}`
1. Start BFS from node `3`:
   ```
   Queue: [3]
   Visited: {0,1,2,3}
   ```
2. Visit `3`, add its neighbors `4` and `5`:
   ```
   Queue: [4, 5]
   Visited: {0,1,2,3,4,5}
   Edges: 2  (3-4, 3-5)
   ```
3. Visit `4`, but its only neighbor `3` is already visited.
4. Visit `5`, but its only neighbor `3` is already visited.
5. BFS ends, nodes = {3,4,5}, edges = 2  
   Check completeness:
   ```
   Expected edges = (3 * (3 - 1)) / 2 = 3 ❌ (Incomplete)
   ```
   ✗ This is NOT a complete component.

---

Final Output
- Complete components: `{0,1,2}` (✓)
- Incomplete components: `{3,4,5}` (✗)
- Result: `1`

---

Visual Representation of BFS Traversal
```
Graph:
    0
   / \
  1 - 2     3 - 4
             |
             5
```
---
✅ BFS explored `{0,1,2}` (Complete) → Counted as 1  
❌ BFS explored `{3,4,5}` (Incomplete) → Not counted  
---
Final Answer: `1`

---

Code Execution Flow Chart
```
1. Build Graph → Adjacency List ✅
2. Initialize Visited Set ✅
3. For each node:
   a. If not visited → Start BFS ✅
   b. Traverse component, count nodes & edges ✅
   c. Check completeness: edges == k * (k-1) / 2 ✅
   d. If complete → Increment count ✅
4. Return complete component count ✅
```
*/
