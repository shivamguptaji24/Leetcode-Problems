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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the lowest time in this problem
*/

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        int[] groupSize = new int[n];
        int[] groupEdges = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            groupSize[i] = 1;
        }

        for (int[] edge : edges) {
            int root1 = findRoot(parent, edge[0]);
            int root2 = findRoot(parent, edge[1]);
            // union two groups
            if (root1 != root2) {
                parent[root1] = root2;
                groupSize[root2] += groupSize[root1];
                groupEdges[root2] += groupEdges[root1];
            }
            // group adds edge by 1
            groupEdges[root2] += 1;
        }
        //  System.out.println(Arrays.toString(parent));
        //  System.out.println(Arrays.toString(groupSize));
        //  System.out.println(Arrays.toString(groupEdges));

        // check the complete groups by neededEdges size * (size - 1) / 2
        int count = 0;
        for (int i = 0; i < n; i++) {
            int size = groupSize[i], neededEdges = size * (size - 1) / 2;
            if (parent[i] == i && neededEdges == groupEdges[i]) {
                count += 1;
            }
        }
        return count;
    }

    private int findRoot(int[] parent, int cur) {
        while (cur != parent[cur]) {
            parent[cur] = parent[parent[cur]];
            cur = parent[cur];
        }
        return cur;
    }
}

/*
Visualization of the above code
 Let's visualize the execution of this Disjoint Set Union (DSU) approach with an example.

---

Example Input
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

---

Step-by-Step Execution
The code uses the Union-Find (DSU) with path compression to find connected components.

Step 1: Initialize DSU Arrays
```
parent = [0, 1, 2, 3, 4, 5]
groupSize = [1, 1, 1, 1, 1, 1]
groupEdges = [0, 0, 0, 0, 0, 0]
```

Each node is its own parent, has a size of 1, and zero edges counted initially.

---

Step 2: Process Edges
We iterate through the edges and perform Union-Find operations.

Processing edge [0,1]
1. Find root of `0`: `root1 = 0`
2. Find root of `1`: `root2 = 1`
3. Union: Make `1` the parent of `0`.
4. Update `groupSize[1] = 2` (group size increases).
5. Increase `groupEdges[1]` by 1.

```
parent = [1, 1, 2, 3, 4, 5]
groupSize = [1, 2, 1, 1, 1, 1]
groupEdges = [0, 1, 0, 0, 0, 0]
```

---

Processing edge [0,2]
1. Find root of `0` → `root1 = 1`
2. Find root of `2` → `root2 = 2`
3. Union: Make `2` the parent of `1`.
4. Update `groupSize[2] = 3`.
5. Increase `groupEdges[2]` by 1.

```
parent = [1, 2, 2, 3, 4, 5]
groupSize = [1, 2, 3, 1, 1, 1]
groupEdges = [0, 1, 2, 0, 0, 0]
```

---

Processing edge [1,2]
1. Find root of `1` → `root1 = 2`
2. Find root of `2` → `root2 = 2`  
   (Same component, just add an edge)
3. Increase `groupEdges[2]` by 1.

```
parent = [1, 2, 2, 3, 4, 5]
groupSize = [1, 2, 3, 1, 1, 1]
groupEdges = [0, 1, 3, 0, 0, 0]
```

---

Processing edge [3,4]
1. Find root of `3` → `root1 = 3`
2. Find root of `4` → `root2 = 4`
3. **Union: Make `4` the parent of `3`.
4. Update `groupSize[4] = 2`.
5. Increase `groupEdges[4]` by 1.

```
parent = [1, 2, 2, 4, 4, 5]
groupSize = [1, 2, 3, 1, 2, 1]
groupEdges = [0, 1, 3, 0, 1, 0]
```

---

Processing edge [3,5]
1. Find root of `3` → `root1 = 4`
2. Find root of `5` → `root2 = 5`
3. **Union:** Make `5` the parent of `4`.
4. Update `groupSize[5] = 3`.
5. Increase `groupEdges[5]` by 1.

```
parent = [1, 2, 2, 4, 5, 5]
groupSize = [1, 2, 3, 1, 2, 3]
groupEdges = [0, 1, 3, 0, 1, 2]
```

---

Step 3: Identify Complete Components
Now, we check if each component is complete by verifying:

\[
\text{neededEdges} = \frac{\text{size} \times (\text{size} - 1)}{2}
\]

1. Component `{0,1,2}`
   - Size: `3`
   - Edges: `3`
   - Needed Edges: `(3 * 2) / 2 = 3` ✅ → Complete
  
2. Component `{3,4,5}`
   - Size: `3`
   - Edges: `2`
   - Needed Edges: `(3 * 2) / 2 = 3` ❌ → Not Complete

---

Final Output
The number of complete connected components is 1.

Final Answer: `1`

---

Visual Representation of the Union-Find Approach
Initial Parent Array
```
0   1   2   3   4   5
|   |   |   |   |   |
0   1   2   3   4   5
```

After Merging (Union)
```
0 → 1 → 2      3 → 4 → 5
```
Final `parent` array:
```
0   1   2   3   4   5
|   |   |   |   |   |
1   2   2   4   5   5
```

Conclusion
- {0,1,2} is a complete component (all nodes fully connected).
- {3,4,5} is not complete (missing `4-5` edge).
- Final Output: `1`
*/
