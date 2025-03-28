/*
Daily Question :
2503 - Maximum Number Of Points From Grid Queries

You are given an m x n integer matrix grid and an array queries of size k.
Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:
If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
Otherwise, you do not get any points, and you end this process.
After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.
Return the resulting array answer.

Example 1:

Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
Output: [5,8,1]
Explanation: The diagrams above show which cells we visit to get points for each query.

Example 2:

Input: grid = [[5,2,1],[1,1,2]], queries = [3]
Output: [0]
Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
 

Constraints:

m == grid.length
n == grid[i].length
2 <= m, n <= 1000
4 <= m * n <= 105
k == queries.length
1 <= k <= 104
1 <= grid[i][j], queries[i] <= 106
*/

class Solution {
    public int[] maxPoints(int[][] grid, int[] queries) {
                int m = grid.length, n = grid[0].length;
        int[] answer = new int[queries.length];
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        // Sort queries with their original indices
        int[][] indexedQueries = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            indexedQueries[i] = new int[]{queries[i], i};
        }
        Arrays.sort(indexedQueries, Comparator.comparingInt(a -> a[0]));
        
        // Min-Heap to process grid cells in increasing order of values
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{grid[0][0], 0, 0});
        
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        
        int points = 0;
        int index = 0;
        
        while (index < queries.length) {
            int query = indexedQueries[index][0];
            int queryIndex = indexedQueries[index][1];

            // Expand cells that have a value < query
            while (!minHeap.isEmpty() && minHeap.peek()[0] < query) {
                int[] cell = minHeap.poll();
                int value = cell[0], row = cell[1], col = cell[2];
                points++; // A new cell is visited
                
                // Try moving in all 4 directions
                for (int[] dir : directions) {
                    int newRow = row + dir[0], newCol = col + dir[1];
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;
                        minHeap.offer(new int[]{grid[newRow][newCol], newRow, newCol});
                    }
                }
            }
            
            answer[queryIndex] = points; // Store the answer for this query
            index++;
        }
        
        return answer;
    }
}

/*
Visualization of the above code
 We will walk through the execution of the code with the given input example:

---

Input:
```
grid = [[1,2,3],
        [2,5,7],
        [3,5,1]]

queries = [5,6,2]
```

---

Step 1: Sorting Queries
We sort the `queries` array while keeping track of their original indices:
```
Sorted Queries (with indices):
[
  (2, 2),  // query=2, original index=2
  (5, 0),  // query=5, original index=0
  (6, 1)   // query=6, original index=1
]
```
Now we process them in order: `2 → 5 → 6`.

---

Step 2: Min-Heap Initialization
We initialize a Min-Heap with the top-left cell `(0,0)` having value `1`:
```
Min-Heap: [(1,0,0)]  // (value, row, col)
Visited Matrix:
[
  [✔,  ,  ],
  [  ,  ,  ],
  [  ,  ,  ]
]
Points Collected: 0
```

---

Step 3: Processing Queries
We process queries in sorted order.

Query = 2
We process all cells with values less than 2:
1. Pop `(1,0,0)`: Value 1 < 2, so +1 point.
2. Expand to adjacent cells:
   - Push `(2,0,1)`
   - Push `(2,1,0)`

```
Min-Heap: [(2,0,1), (2,1,0)]
Visited Matrix:
[
  [✔, ✔,  ],
  [✔,  ,  ],
  [  ,  ,  ]
]
Points Collected: 1
```
Since all remaining heap values are ≥2, we stop.
```
Answer[2] = 1
```

---

Query = 5
We process all cells with values less than 5:
1. Pop `(2,0,1)`: Value 2 < 5, +1 point.
2. Expand to:
   - Push `(3,0,2)`
   - Push `(5,1,1)`

3. Pop `(2,1,0)`: Value 2 < 5, +1 point.
4. Expand to:
   - Push `(3,2,0)`

5. Pop `(3,0,2)`: Value 3 < 5, +1 point.
6. Expand to:
   - Push `(7,1,2)`

7. Pop `(3,2,0)`: Value 3 < 5, +1 point.
8. Expand to:
   - Push `(5,2,1)`

Heap Status:
```
Min-Heap: [(5,1,1), (5,2,1), (7,1,2)]
Visited Matrix:
[
  [✔, ✔, ✔],
  [✔, ✔, ✔],
  [✔, ✔,  ]
]
Points Collected: 5
```
```
Answer[0] = 5
```

---

Query = 6
We process all cells with values less than 6:
1. Pop `(5,1,1)`: Value 5 < 6, +1 point.
2. Expand to:
   - Push `(5,2,1)`

3. Pop `(5,2,1)`: Value 5 < 6, +1 point.
4. Expand to:
   - Push `(1,2,2)`

5. Pop `(1,2,2)`: Value 1 < 6, +1 point.
6. Expand to:
   - Push `(7,1,2)`

Heap Status:
```
Min-Heap: [(7,1,2)]
Visited Matrix:
[
  [✔, ✔, ✔],
  [✔, ✔, ✔],
  [✔, ✔, ✔]
]
Points Collected: 8
```
```
Answer[1] = 8
```

---

Final Answer
After processing all queries:
```
Output: [5,8,1]
```

---

Visualization Summary
1. Process queries in increasing order.
2. Expand grid cells using a min-heap.
3. Only count new cells that satisfy query conditions.

This approach avoids redundant checks and ensures optimal processing.

---

🚀 Efficiency and Optimization
- Sorting Queries: \(O(k \log k)\)
- Heap Operations: \(O(mn \log(mn))\)
- Total Complexity: \(O(mn \log(mn) + k \log k)\)

✅ Handles large inputs efficiently!
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 41ms runtime which is the lowest time in this problem
*/

class Solution {
    int count=0;
    public void bfs(int[][] grid, int val, int x,int y, PriorityQueue<int[]> pq){
        if(x<0 || y<0 || x==grid.length || y==grid[0].length){
            return;
        }
        if(grid[x][y]>0){
            if(grid[x][y]<val){
                count++;
                grid[x][y]=-1; // visited
                bfs(grid,val,x+1,y,pq);
                bfs(grid,val,x-1,y,pq);
                bfs(grid,val,x,y+1,pq);
                bfs(grid,val,x,y-1,pq);
            }else{
                pq.add(new int[]{x,y, grid[x][y]});
                grid[x][y]=0;
            }
        }
        while(!pq.isEmpty()){
            int[] top = pq.peek();
            if(top[2]<val){
                pq.remove();
                grid[top[0]][top[1]]=top[2];
                bfs(grid,val,top[0],top[1],pq);
            }else{
                break;
            }
        }
    }
    public int[] maxPoints(int[][] grid, int[] queries) {
        int[] ans = new int[queries.length];
        List<int[]> queryIndex = new ArrayList<>();
        for(int i=0;i<queries.length;i++){
            queryIndex.add(new int[]{i,queries[i]});
        }
        Collections.sort(queryIndex,(a,b)->a[1]-b[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[2]-b[2]);
        for(int[] q:queryIndex){
            bfs(grid,q[1],0,0,pq);
            ans[q[0]]=count;
        }
        return ans;
    }
}

/*
Visualization of the above code
 This code solves the problem using Breadth-First Search (BFS) with a priority queue (min-heap) to efficiently traverse the grid and determine how many points can be collected for each query. Let's go step by step to understand and visualize how it works.

---

Understanding the Approach
1. Sorting Queries:  
   - Queries are processed in ascending order to ensure that smaller queries are handled first.
   
2. BFS with a Min-Heap (`PriorityQueue`):  
   - The search starts from the top-left corner `(0,0)`.
   - Cells with values less than the query value are counted and marked as visited (`-1`).
   - Higher value cells are stored in a priority queue and revisited later.

3. Efficient Expansion:  
   - If a cell is less than the query, explore its four neighbors recursively.
   - If a cell is greater than or equal to the query, it is pushed into the priority queue for later expansion.

---

Example Walkthrough
Input:
```
grid = [[1,2,3],
        [2,5,7],
        [3,5,1]]

queries = [5,6,2]
```

---

Step 1: Sorting Queries
We sort `queries` in ascending order and keep track of their original indices:
```
Sorted Queries:
[
  (2, 2),  // query=2, original index=2
  (5, 0),  // query=5, original index=0
  (6, 1)   // query=6, original index=1
]
```

---
Step 2: Processing Queries Using BFS
We now process queries in sorted order.

Query = 2
- Start BFS from (0,0)
- Expand Cells with Value < 2:
  - `grid[0][0] = 1` → Count +1, mark as visited (`-1`)
  - Try to expand in 4 directions:
    - Right: `grid[0][1] = 2` (≥ 2, push to priority queue)
    - Down: `grid[1][0] = 2` (≥ 2, push to priority queue)
- Heap State: `[(0,1,2), (1,0,2)]`
- Answer for Query 2: `ans[2] = 1`

```
Updated Grid:
[[-1, 2, 3],
 [ 2, 5, 7],
 [ 3, 5, 1]]
```

---

Query = 5
- Expand Cells with Value < 5:
  - `grid[0][1] = 2` → Count +1, mark as visited
  - `grid[1][0] = 2` → Count +1, mark as visited
  - `grid[0][2] = 3` → Count +1, mark as visited
  - `grid[2][0] = 3` → Count +1, mark as visited
- Heap State: `[(1,1,5), (2,1,5), (1,2,7)]`
- Answer for Query 5: `ans[0] = 5`

```
Updated Grid:
[[-1, -1, -1],
 [-1,  5, 7],
 [-1,  5, 1]]
```

---

Query = 6
- Expand Cells with Value < 6:
  - `grid[1][1] = 5` → Count +1, mark as visited
  - `grid[2][1] = 5` → Count +1, mark as visited
  - `grid[2][2] = 1` → Count +1, mark as visited
- Heap State: `[(1,2,7)]`
- Answer for Query 6: `ans[1] = 8`

```
Final Grid:
[[-1, -1, -1],
 [-1, -1,  7],
 [-1, -1, -1]]
```

---

Final Output
```
ans = [5, 8, 1]
```

---

Key Observations
1. Sorting queries first ensures efficient grid traversal.
2. Priority queue (Min-Heap) keeps track of unexplored cells to optimize performance.
3. Avoids redundant traversal by marking visited cells with `-1`.
4. Time Complexity: \( O(mn \log(mn) + k \log k) \) ensures it runs efficiently even for large inputs.

---

✅ This approach is optimal for handling large grids efficiently! 🚀
*/
