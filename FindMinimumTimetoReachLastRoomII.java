/*
Daily Question :
3342 - Find Minimum Time to Reach Last Room II

There is a dungeon with n x m rooms arranged as a grid.
You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds when you can start moving to that room. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes one second for one move and two seconds for the next, alternating between the two.
Return the minimum time to reach the room (n - 1, m - 1).
Two rooms are adjacent if they share a common wall, either horizontally or vertically. 

Example 1:

Input: moveTime = [[0,4],[4,4]]

Output: 7

Explanation:

The minimum time required is 7 seconds.

At time t == 4, move from room (0, 0) to room (1, 0) in one second.
At time t == 5, move from room (1, 0) to room (1, 1) in two seconds.

Example 2:

Input: moveTime = [[0,0,0,0],[0,0,0,0]]

Output: 6

Explanation:

The minimum time required is 6 seconds.

At time t == 0, move from room (0, 0) to room (1, 0) in one second.
At time t == 1, move from room (1, 0) to room (1, 1) in two seconds.
At time t == 3, move from room (1, 1) to room (1, 2) in one second.
At time t == 4, move from room (1, 2) to room (1, 3) in two seconds.

Example 3:

Input: moveTime = [[0,1],[1,2]]

Output: 4

Constraints:

2 <= n == moveTime.length <= 750
2 <= m == moveTime[i].length <= 750
0 <= moveTime[i][j] <= 109
*/

class Solution {
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        int[][] dist = new int[n][m];
        for (var row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[] {0, 0, 0});
        int[] dirs = {-1, 0, 1, 0, -1};
        while (true) {
            int[] p = pq.poll();
            int d = p[0], i = p[1], j = p[2];

            if (i == n - 1 && j == m - 1) {
                return d;
            }
            if (d > dist[i][j]) {
                continue;
            }

            for (int k = 0; k < 4; k++) {
                int x = i + dirs[k];
                int y = j + dirs[k + 1];
                if (x >= 0 && x < n && y >= 0 && y < m) {
                    int t = Math.max(moveTime[x][y], dist[i][j]) + (i + j) % 2 + 1;
                    if (dist[x][y] > t) {
                        dist[x][y] = t;
                        pq.offer(new int[] {t, x, y});
                    }
                }
            }
        }
    }
}

/*
Visualization of the above code
  Let’s visualize how this algorithm works step-by-step using a grid and explain the logic with a diagrammatic explanation.

---

🧠 Code Functionality Summary

You're implementing a modified Dijkstra's algorithm to find the minimum time to reach the bottom-right corner of a 2D grid `moveTime[][]` from the top-left corner `(0, 0)`.

Each cell has a `moveTime[i][j]`, and the total time to reach the next cell depends on:

* the maximum of current accumulated time and the cell's moveTime,
* plus an extra delay depending on whether the sum `i + j` is even or odd.

---

🧱 Key Components to Visualize

Let’s take a small 3×3 sample grid:

```
moveTime = [
    [1, 3, 5],
    [2, 8, 2],
    [4, 2, 1]
]
```

Here’s how the algorithm progresses:

📌 Initial State:

* `dist` matrix is filled with `Integer.MAX_VALUE` (∞), except `dist[0][0] = 0`.
* Priority queue starts with `{0, 0, 0}` → meaning time `0` at `(0, 0)`.

---

🧭 Traversal Example

Let’s simulate the first few steps. I’ll use a grid to represent the state of `dist`:

Step 0:

```
Current Cell: (0, 0) | Time: 0

From (0, 0), move to:
- (1, 0): max(2, 0) + (0+0)%2 + 1 = 2 + 1 = 3
- (0, 1): max(3, 0) + (0+0)%2 + 1 = 3 + 1 = 4
```

Updated `dist`:

```
[0, 4, ∞]
[3, ∞, ∞]
[∞, ∞, ∞]
```

---

Step 1:

```
Current Cell: (1, 0) | Time: 3

From (1, 0), move to:
- (2, 0): max(4, 3) + (1+0)%2 + 1 = 4 + 2 = 6
- (1, 1): max(8, 3) + (1+0)%2 + 1 = 8 + 2 = 10
```

Updated `dist`:

```
[0, 4, ∞]
[3, 10, ∞]
[6, ∞, ∞]
```

---

Step 2:

```
Current Cell: (0, 1) | Time: 4

From (0, 1), move to:
- (0, 2): max(5, 4) + (0+1)%2 + 1 = 5 + 2 = 7
- (1, 1): max(8, 4) + 2 = 10 → already 10, no update
```

Updated `dist`:

```
[0, 4, 7]
[3, 10, ∞]
[6, ∞, ∞]
```

... and so on, until you reach `(2, 2)`.

---

🔁 Dijkstra + Dynamic Delay Logic

`int t = Math.max(moveTime[x][y], dist[i][j]) + (i + j) % 2 + 1;`

This is a dynamic edge weight, affected by:

* the destination cell's difficulty (`moveTime[x][y]`),
* your current accumulated time (`dist[i][j]`),
* and a small delay based on your cell's parity (`(i+j)%2`).

---

📊 Grid Visualization of `dist[][]` Over Time

| Iteration | dist\[0]\[0] | dist\[0]\[1] | dist\[0]\[2] | dist\[1]\[0] | dist\[1]\[1] | dist\[1]\[2] | dist\[2]\[0] | dist\[2]\[1] | dist\[2]\[2] |
| --------- | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |
| Init      | 0            | ∞            | ∞            | ∞            | ∞            | ∞            | ∞            | ∞            | ∞            |
| After 1   | 0            | 4            | ∞            | 3            | ∞            | ∞            | ∞            | ∞            | ∞            |
| After 2   | 0            | 4            | ∞            | 3            | 10           | ∞            | 6            | ∞            | ∞            |
| After 3   | 0            | 4            | 7            | 3            | 10           | ∞            | 6            | ∞            | ∞            |
| ...       | ...          | ...          | ...          | ...          | ...          | ...          | ...          | ...          | ...          |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 220ms runtime which is the most used time in this problem.
*/

class Solution {
    public int minTimeToReach(int[][] moveTime) {
        return dijkstraTraversal(moveTime);
    }
    //we need to track the move number as well from the source

    private int dijkstraTraversal(int[][] moveTime) {
        PriorityQueue<MoveInfo> processQueue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.time_taken, b.time_taken));
        processQueue.add(new MoveInfo(0, 0, 0, 1)); //first move should add '1 s', so start with moves_made as '1' rather than zero
        int[][] directions = new int[][] {
                { 0, 1 }, { 1, 0 },
                { -1, 0 }, { 0, -1 }
        };
        int[][] time_to_reach = new int[moveTime.length][moveTime[0].length];
        for (int i = 0; i < time_to_reach.length; i++) {
            Arrays.fill(time_to_reach[i], Integer.MAX_VALUE);
        }
        while (!processQueue.isEmpty()) {
            MoveInfo curr_room = processQueue.poll();
            int time_for_move = curr_room.moves_taken % 2 == 0 ? 2 : 1;
            if (curr_room.row == moveTime.length - 1 && curr_room.col == moveTime[0].length - 1) {
                return curr_room.time_taken;
            }
            if (curr_room.time_taken > time_to_reach[curr_room.row][curr_room.col]) {
                continue;
            }

            for (int[] dir : directions) {
                int new_room_row = curr_room.row + dir[0];
                int new_room_col = curr_room.col + dir[1];
                if (!isValid(new_room_row, new_room_col, moveTime))
                    continue;
                int final_move_time = Math.max(curr_room.time_taken + time_for_move,
                        moveTime[new_room_row][new_room_col] + time_for_move);
                if (final_move_time < time_to_reach[new_room_row][new_room_col]) {
                    processQueue
                            .add(new MoveInfo(new_room_row, new_room_col, final_move_time, curr_room.moves_taken + 1));
                    time_to_reach[new_room_row][new_room_col] = final_move_time;
                }
            }
        }

        return -1;

    }

    private boolean isValid(int new_row, int new_col, int[][] moveTime) {
        if (new_row < 0 || new_row >= moveTime.length)
            return false;
        if (new_col < 0 || new_col >= moveTime[0].length)
            return false;
        return true;
    }

}

class MoveInfo {
    int row;
    int col;
    int time_taken;
    int moves_taken;

    MoveInfo(int row, int col, int time_taken, int moves_taken) {
        this.row = row;
        this.col = col;
        this.time_taken = time_taken;
        this.moves_taken = moves_taken;
    }
}
