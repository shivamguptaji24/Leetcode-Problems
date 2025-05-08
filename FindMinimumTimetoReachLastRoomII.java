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

/*
Visualization of the above code
  Let's visualize how this enhanced version of Dijkstra's algorithm works using the `MoveInfo` class that tracks:

* `row` and `col` – current grid position
* `time_taken` – total time to reach that cell
* `moves_taken` – number of steps made (used to determine move cost: 1s or 2s)

---

🧠 Summary of Logic

* The first move always takes 1 second.
* Every next move takes either:

  * 1 second if the total moves so far are odd (`moves_taken % 2 == 1`)
  * 2 seconds if the total moves are even.
* You're comparing the maximum of:

  * current accumulated time + move cost
  * moveTime for the next room + move cost

It’s essentially Dijkstra with dynamic move cost and grid-based traversal.

---

✅ Let's Visualize It with a 3x3 Example

```
moveTime = [
    [0, 1, 3],
    [2, 4, 2],
    [3, 1, 0]
]
```

Initial `time_to_reach[][]`:

```
[∞, ∞, ∞]
[∞, ∞, ∞]
[∞, ∞, ∞]
```

Start: `(0, 0)`, `time_taken = 0`, `moves_taken = 1` → pushed into priority queue.

---

🔁 Step-by-step Grid Movement

Step 1: From (0,0)

* Current Time: `0`, Moves: `1` → **cost = 1**
* Move to:

  * (0,1): max(0+1, 1+1) = 2
  * (1,0): max(0+1, 2+1) = 3

`time_to_reach`:

```
[0, 2, ∞]
[3, ∞, ∞]
[∞, ∞, ∞]
```

---

Step 2: From (0,1)

* Time = 2, Moves = 2 → cost = 2 (even move)
* Move to:

  * (0,2): max(2+2, 3+2) = 5
  * (1,1): max(2+2, 4+2) = 6

`time_to_reach`:

```
[0, 2, 5]
[3, 6, ∞]
[∞, ∞, ∞]
```

---

Step 3: From (1,0)

* Time = 3, Moves = 2 → cost = 2
* Move to:

  * (2,0): max(3+2, 3+2) = 5
  * (1,1): max(3+2, 4+2) = 6 (already present)

`time_to_reach`:

```
[0, 2, 5]
[3, 6, ∞]
[5, ∞, ∞]
```

---

Step 4: From (0,2)

* Time = 5, Moves = 3 → cost = 1
* Move to:

  * (1,2): max(5+1, 2+1) = 6

`time_to_reach`:

```
[0, 2, 5]
[3, 6, 6]
[5, ∞, ∞]
```

---

Step 5: From (2,0)

* Time = 5, Moves = 3 → cost = 1
* Move to:

  * (2,1): max(5+1, 1+1) = 6

`time_to_reach`:

```
[0, 2, 5]
[3, 6, 6]
[5, 6, ∞]
```

---

Step 6: From (1,2)

* Time = 6, Moves = 4 → cost = 2
* Move to:

  * (2,2): max(6+2, 0+2) = 8 → 🎯 Reached destination

`time_to_reach`:

```
[0, 2, 5]
[3, 6, 6]
[5, 6, 8]
```

---

📊 Final Output

```
return 8;
```

You reach the bottom-right `(2,2)` in 8 seconds considering:

* move parity
* moveTime per cell
* and optimal paths via Dijkstra's logic

---

🖼️ Want a Diagram?
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 28ms runtime which is the lowest time in this problem.
*/

/* Copyright (c) 2024 by https://leetcode.com/brinuke/. All rights reserved. */
class Solution {
	private static class Room implements Comparable<Room> {
		final int openTime;
		final boolean longStay;
		Room[] adjacent;
		Room next;

		Room() {
			openTime = Integer.MAX_VALUE;
			longStay = true;
		}

		Room(int openTime, boolean longStay) {
			this.openTime = openTime;
			this.longStay = longStay;
			next = this; // indicates that this Room hasn't been approached yet
		}

		@Override
		public int compareTo(Room other) {
			return openTime - other.openTime;
		}
	}

	private static final Room DUMMY_ROOM = new Room();

	private static Room initRooms(int[][] moveTime) {
		int n = moveTime.length;
		int m = moveTime[0].length;
		Room[][] rooms = new Room[n][m];
		for (int i = 0; i < n; i++) {
			int[] mtRow = moveTime[i];
			Room[] rRow = rooms[i];
			for (int j = 0; j < m; j++)
				rRow[j] = new Room(mtRow[j], ((i + j) & 1) == 0);
		}
		Room[] dummyRow = new Room[m];
		Arrays.fill(dummyRow, DUMMY_ROOM);
		Room[] prevRow = dummyRow;
		Room[] curRow = rooms[0];
		n--;
		m--;
		for (int i = 0; i <= n; i++) {
			Room[] nextRow = i < n ? rooms[i + 1] : dummyRow;
			Room prev = DUMMY_ROOM;
			Room cur = curRow[0];
			for (int j = 0; j <= m; j++) {
				Room next = j < m ? curRow[j + 1] : DUMMY_ROOM;
				cur.adjacent = new Room[] { prev, prevRow[j], next, nextRow[j] };
				prev = cur;
				cur = next;
			}
			prevRow = curRow;
			curRow = nextRow;
		}
		Room start = rooms[0][0];
		start.next = rooms[n][m]; // finish
		return start;
	}

	public static int minTimeToReach(int[][] moveTime) {
		Room start = initRooms(moveTime);
		Room finish = start.next;
		Queue<Room> waitingToEnter = new PriorityQueue<>();
		waitingToEnter.add(DUMMY_ROOM); // guard Room to prevent NPE
		start.next = null;
		Room exitingShortHead = start;
		Room exitingLongHead = null;
		int currentTime = 0;
		while (true) {
			Room exitingLongHeadNew = null;
			while (exitingShortHead != null) {
				for (Room adj : exitingShortHead.adjacent)
					if (adj.next == adj) {
						if (adj == finish)
							return Math.max(currentTime, finish.openTime) + (finish.longStay ? 2 : 1);
						if (adj.openTime <= currentTime) {
							if (adj.longStay) {
								adj.next = exitingLongHeadNew;
								exitingLongHeadNew = adj;
							} else {
								adj.next = exitingLongHead;
								exitingLongHead = adj;
							}
						} else {
							adj.next = null;
							waitingToEnter.offer(adj);
						}
					}
				exitingShortHead = exitingShortHead.next;
			}
			exitingShortHead = exitingLongHead;
			exitingLongHead = exitingLongHeadNew;
			int queueTime;
			while ((queueTime = waitingToEnter.peek().openTime) <= currentTime) {
				Room entering = waitingToEnter.poll();
				if (entering.longStay) {
					entering.next = exitingLongHead;
					exitingLongHead = entering;
				} else {
					entering.next = exitingShortHead;
					exitingShortHead = entering;
				}
			}
			if (++currentTime < queueTime && exitingShortHead == null && exitingLongHead == null)
				currentTime = queueTime;
		}
	}
}

/*
Visualization of the above code
  The provided Java code implements a customized version of Dijkstra's algorithm to determine the minimum time required to traverse from the top-left room to the bottom-right room in a grid-based dungeon. Each room has a specific earliest entry time, and the time to move between rooms varies based on certain conditions.

---

🧩 Core Concepts

* Room Representation: Each room is encapsulated in a `Room` object, which holds information about its earliest entry time (`openTime`), whether it requires a longer stay (`longStay`), and its adjacent rooms.

* Movement Rules:

  * Entry Time Constraint: You cannot enter a room before its `openTime`.
  * Stay Duration:

    * Short Stay: If `longStay` is `false`, the room requires a 1-second stay.
    * Long Stay: If `longStay` is `true`, the room requires a 2-second stay.

* Traversal Strategy: The algorithm simulates the passage of time, processing rooms that can be entered at the current time and updating the state accordingly.([Medium][1])

---

🔄 Step-by-Step Execution with Example

Consider the following `moveTime` grid:

```
int[][] moveTime = {
    {0, 4},
    {4, 4}
};
```



This grid represents a 2x2 dungeon where each cell indicates the earliest time you can enter that room.([Medium][1])

Initialization

* Room Grid Creation: Using `initRooms(moveTime)`, a grid of `Room` objects is created. Each room is initialized with its corresponding `openTime` and a `longStay` value determined by the parity of its coordinates (i.e., `(i + j) % 2 == 0`).

* Starting Point: The traversal begins at the top-left room `(0,0)`.([Medium][1])

Time Simulation Loop

The algorithm simulates each time unit, processing rooms that can be entered at the current time:

1. Time = 0:

   * Current Room: (0,0)
   * Adjacent Rooms: (0,1) and (1,0)
   * Processing:

     * (0,1): `openTime` is 4; cannot enter yet. Added to the waiting queue.
     * (1,0): `openTime` is 4; cannot enter yet. Added to the waiting queue.

2. Time = 1 to 3:

   * No rooms can be entered; time increments.

3. Time = 4:

   * Processing Waiting Queue:

     * (0,1) and (1,0): `openTime` is 4; can now enter.
     * Stay Duration: Determined by `longStay` value.

4. Subsequent Times:

   * The algorithm continues processing rooms as they become available, updating the current time, and checking for the destination room `(1,1)`.

Destination Reached

Once the bottom-right room `(1,1)` is reached, the algorithm returns the total time taken, considering all constraints.

---

📊 Visualization

Here's a simplified visualization of the grid with `openTime` values:

```
+-------+-------+
| (0,0) | (0,1) |
|   0   |   4   |
+-------+-------+
| (1,0) | (1,1) |
|   4   |   4   |
+-------+-------+
```



Each cell shows the coordinates and the earliest time you can enter that room.([Medium][1])

---

🧠 Key Takeaways

* Custom Dijkstra's Algorithm: The algorithm adapts Dijkstra's approach to handle time-based constraints and varying stay durations.

* Efficient Time Simulation: By simulating each time unit and processing rooms accordingly, the algorithm ensures that all constraints are respected.

* Scalability: This approach can handle larger grids and more complex `moveTime` configurations efficiently.

---
*/
