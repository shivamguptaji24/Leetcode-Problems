/*
Daily Question :
3108 - Minimum Cost Walk in Weighted Graph

There is an undirected weighted graph with n vertices labeled from 0 to n - 1.
You are given the integer n and an array edges, where edges[i] = [ui, vi, wi] indicates that there is an edge between vertices ui and vi with a weight of wi.
A walk on a graph is a sequence of vertices and edges. The walk starts and ends with a vertex, and each edge connects the vertex that comes before it and the vertex that comes after it. It's important to note that a walk may visit the same edge or vertex more than once.
The cost of a walk starting at node u and ending at node v is defined as the bitwise AND of the weights of the edges traversed during the walk. In other words, if the sequence of edge weights encountered during the walk is w0, w1, w2, ..., wk, then the cost is calculated as w0 & w1 & w2 & ... & wk, where & denotes the bitwise AND operator.
You are also given a 2D array query, where query[i] = [si, ti]. For each query, you need to find the minimum cost of the walk starting at vertex si and ending at vertex ti. If there exists no such walk, the answer is -1.
Return the array answer, where answer[i] denotes the minimum cost of a walk for query i.

Example 1:

Input: n = 5, edges = [[0,1,7],[1,3,7],[1,2,1]], query = [[0,3],[3,4]]

Output: [1,-1]

Explanation:


To achieve the cost of 1 in the first query, we need to move on the following edges: 0->1 (weight 7), 1->2 (weight 1), 2->1 (weight 1), 1->3 (weight 7).

In the second query, there is no walk between nodes 3 and 4, so the answer is -1.

Example 2:

Input: n = 3, edges = [[0,2,7],[0,1,15],[1,2,6],[1,2,1]], query = [[1,2]]

Output: [0]

Explanation:


To achieve the cost of 0 in the first query, we need to move on the following edges: 1->2 (weight 1), 2->1 (weight 6), 1->2 (weight 1).

 

Constraints:

2 <= n <= 105
0 <= edges.length <= 105
edges[i].length == 3
0 <= ui, vi <= n - 1
ui != vi
0 <= wi <= 105
1 <= query.length <= 105
query[i].length == 2
0 <= si, ti <= n - 1
si != ti
*/

class UnionFind {
    private final int[] p;
    private final int[] size;

    public UnionFind(int n) {
        p = new int[n];
        size = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    public boolean union(int a, int b) {
        int pa = find(a), pb = find(b);
        if (pa == pb) {
            return false;
        }
        if (size[pa] > size[pb]) {
            p[pb] = pa;
            size[pa] += size[pb];
        } else {
            p[pa] = pb;
            size[pb] += size[pa];
        }
        return true;
    }

    public int size(int x) {
        return size[find(x)];
    }
}

class Solution {
    private UnionFind uf;
    private int[] g;

    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        uf = new UnionFind(n);
        for (var e : edges) {
            uf.union(e[0], e[1]);
        }
        g = new int[n];
        Arrays.fill(g, -1);
        for (var e : edges) {
            int root = uf.find(e[0]);
            g[root] &= e[2];
        }
        int m = query.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; ++i) {
            int s = query[i][0], t = query[i][1];
            ans[i] = f(s, t);
        }
        return ans;
    }

    private int f(int u, int v) {
        if (u == v) {
            return 0;
        }
        int a = uf.find(u), b = uf.find(v);
        return a == b ? g[a] : -1;
    }
}

/*
Visualiztion of the above code
 Let's break down the provided code with clear explanations and a visual representation for better understanding. The code uses **Union-Find (Disjoint Set Union)** to efficiently manage connected components and calculate the **minimum AND value** for given queries.

---

📌 Step 1: Understanding the Union-Find Class
The `UnionFind` class is designed to efficiently manage disjoint sets (connected components).

```java
class UnionFind {
    private final int[] p;    // Parent array (tracks the leader of each set)
    private final int[] size; // Size array (tracks the size of each set)

    public UnionFind(int n) {
        p = new int[n];
        size = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = i;        // Each node is its own parent (initially)
            size[i] = 1;     // Each set starts with size 1
        }
    }

    public int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]); // Path compression for efficiency
        }
        return p[x];
    }

    public boolean union(int a, int b) {
        int pa = find(a), pb = find(b);
        if (pa == pb) {
            return false; // Already connected
        }
        if (size[pa] > size[pb]) {
            p[pb] = pa;            // Merge smaller set into the larger one
            size[pa] += size[pb];
        } else {
            p[pa] = pb;
            size[pb] += size[pa];
        }
        return true;
    }

    public int size(int x) {
        return size[find(x)]; // Return the size of the component
    }
}
```

---

📌 Step 2: Solution Logic (`minimumCost` Method)
The `minimumCost` method processes the graph and queries.

```java
class Solution {
    private UnionFind uf;
    private int[] g;  // Stores minimum AND value for each connected component

    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        uf = new UnionFind(n);

        // Step 1: Build Union-Find structure
        for (var e : edges) {
            uf.union(e[0], e[1]);
        }

        // Step 2: Initialize 'g' array with -1 (no AND values assigned yet)
        g = new int[n];
        Arrays.fill(g, -1);

        // Step 3: Compute AND values for each connected component
        for (var e : edges) {
            int root = uf.find(e[0]);
            g[root] &= e[2];  // Minimum AND value for this component
        }

        // Step 4: Process queries
        int m = query.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; ++i) {
            int s = query[i][0], t = query[i][1];
            ans[i] = f(s, t);
        }

        return ans;
    }

    // Step 5: Logic for answering queries
    private int f(int u, int v) {
        if (u == v) return 0; // Same node → AND value = 0
        int a = uf.find(u), b = uf.find(v);
        return a == b ? g[a] : -1;  // If connected, return AND value; else -1
    }
}
```

---

📊 Visual Representation

Input Example
```
n = 3
edges = [[0,2,7],[0,1,15],[1,2,6],[1,2,1]]
queries = [[1, 2]]
```

Step 1: Union-Find Initialization
```
Nodes:  [ 0, 1, 2 ]
Parent: [ 0, 1, 2 ]  --> Each node is its own parent
```

Step 2: Union Operations
- Union(0, 2) → `Parent[2] = 0`
- Union(0, 1) → `Parent[1] = 0`
- Union(1, 2) → Already connected, skip.

Updated Parent Array:
```
Nodes:  [ 0, 1, 2 ]
Parent: [ 0, 0, 0 ]  --> All nodes are now part of the same component
```

Step 3: Computing Minimum AND Values
- Component 0 (with nodes 0, 1, 2) → AND Value = `7 & 15 & 6 & 1 = 0`

Step 4: Processing Queries
- Query(1, 2) → Both nodes belong to component 0 → Answer = `0`

Final Output: `[0]`

---

🚀 Complexity Analysis
- Time Complexity: `O(n + m)` — Efficiently processes nodes, edges, and queries.
- Space Complexity: `O(n)` — For Union-Find arrays and AND value tracking.

---

🔎 Key Learning Points
✅ Union-Find effectively tracks connected components.  
✅ Efficient `find()` with path compression ensures optimal performance.  
✅ The `g[]` array efficiently tracks the minimum AND value for each connected component.
*/
