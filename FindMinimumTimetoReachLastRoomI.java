/*
Daily Question :
3341 - Find Minimum Time to Reach Last Room I

There is a dungeon with n x m rooms arranged as a grid.
You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds when you can start moving to that room. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes exactly one second.
Return the minimum time to reach the room (n - 1, m - 1).
Two rooms are adjacent if they share a common wall, either horizontally or vertically. 

Example 1:

Input: moveTime = [[0,4],[4,4]]

Output: 6

Explanation:

The minimum time required is 6 seconds.

At time t == 4, move from room (0, 0) to room (1, 0) in one second.
At time t == 5, move from room (1, 0) to room (1, 1) in one second.

Example 2:

Input: moveTime = [[0,0,0],[0,0,0]]

Output: 3

Explanation:

The minimum time required is 3 seconds.

At time t == 0, move from room (0, 0) to room (1, 0) in one second.
At time t == 1, move from room (1, 0) to room (1, 1) in one second.
At time t == 2, move from room (1, 1) to room (1, 2) in one second.

Example 3:

Input: moveTime = [[0,1],[1,2]]

Output: 3

Constraints:

2 <= n == moveTime.length <= 50
2 <= m == moveTime[i].length <= 50
0 <= moveTime[i][j] <= 109
*/

class Solution {
  public int minTimeToReach(int[][] moveTime) {
    return dijkstra(moveTime, new Pair<>(0, 0),
                    new Pair<>(moveTime.length - 1, moveTime[0].length - 1));
  }

  private int dijkstra(int[][] moveTime, Pair<Integer, Integer> src, Pair<Integer, Integer> dst) {
    final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    final int m = moveTime.length;
    final int n = moveTime[0].length;
    int[][] dist = new int[m][n];
    Arrays.stream(dist).forEach(A -> Arrays.fill(A, Integer.MAX_VALUE));

    dist[0][0] = 0;
    Queue<Pair<Integer, Pair<Integer, Integer>>> minHeap =
        new PriorityQueue<>(Comparator.comparingInt(Pair::getKey)) {
          { offer(new Pair<>(dist[0][0], src)); } // (d, u)
        };

    while (!minHeap.isEmpty()) {
      final int d = minHeap.peek().getKey();
      final Pair<Integer, Integer> u = minHeap.poll().getValue();
      if (u.equals(dst))
        return d;
      final int i = u.getKey();
      final int j = u.getValue();
      if (d > dist[i][j])
        continue;
      for (int[] dir : DIRS) {
        final int x = i + dir[0];
        final int y = j + dir[1];
        if (x < 0 || x == m || y < 0 || y == n)
          continue;
        final int newDist = Math.max(moveTime[x][y], d) + 1;
        if (newDist < dist[x][y]) {
          dist[x][y] = newDist;
          minHeap.offer(new Pair<>(newDist, new Pair<>(x, y)));
        }
      }
    }

    return -1;
  }
}

/*
Visualization of the above code
  Let’s visualize how the `minTimeToReach` function works using Dijkstra's algorithm on a 2D grid. This method is used to find the minimum time to reach the bottom-right corner from the top-left corner of a grid where:

* Each cell in the `moveTime` matrix indicates the minimum time you must wait before moving into that cell.
* You can move in 4 directions: up, down, left, right (no diagonals).
* Moving takes 1 time unit, but you must wait if the time is less than `moveTime[x][y]`.

---

🔍 Key Concepts in Code:

1. Input:

```
int[][] moveTime = {
  {0, 2, 1},
  {1, 5, 2},
  {4, 6, 1}
};
```

2. Start: Top-left (0,0)

End: Bottom-right (2,2)

---

📈 Visualization Step-by-Step:

Let’s say `moveTime` is like this:

```
+---+---+---+
| 0 | 2 | 1 |
+---+---+---+
| 1 | 5 | 2 |
+---+---+---+
| 4 | 6 | 1 |
+---+---+---+
```

Each cell holds the minimum time you must reach before stepping into that cell.

---

🔄 How Dijkstra Works Here:

Step 0:

* Start at (0,0) at time 0.
* Available move options: right (0,1) and down (1,0)

Step 1: Move to (1,0)

* Time to reach: `max(moveTime[1][0], 0) + 1 = max(1, 0) + 1 = 2`

Step 2: Move to (0,1)

* Time to reach: `max(2, 0) + 1 = 3`

Step 3: Continue expanding the lowest time paths...

* For each neighbor cell `(x, y)`, you calculate:

  ```
  newDist = max(moveTime[x][y], currentTime) + 1
  ```

  So you wait if needed, then add 1 to move in.

---

✅ Goal:

The algorithm keeps visiting the shortest-time cells using a min-heap (priority queue), like Dijkstra's algorithm, until it reaches the destination cell `(m-1, n-1)`.

---

🧠 Example Movement Summary:

If starting from (0,0), and moving optimally, your path could be:

* (0,0) → (1,0) → (2,0) → (2,1) → (2,2)

At each move, we compute:

```
newTime = max(moveTime[nextCell], currentTime) + 1
```

The goal is to minimize total time to reach the last cell.

---
*/
