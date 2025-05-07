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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 7ms runtime which is the most used time in this problem.
*/

import java.util.*;

class Tuple implements Comparable<Tuple> {
    int row, col, time;

    public Tuple(int row, int col, int time) {
        this.row = row;
        this.col = col;
        this.time = time;
    }

    @Override
    public int compareTo(Tuple t2) {
        return this.time - t2.time;  // PriorityQueue orders by minimum time
    }
}

class Solution {
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        
        // Distance array to store minimum time to reach each room
        int[][] dis = new int[n][m];
        
        // Initialize distance array to "infinity"
        for (int i = 0; i < n; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        }
        
        // Start from (0,0) at time = 0
        dis[0][0] = 0;
        
        // PriorityQueue to process rooms based on minimum time
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        pq.add(new Tuple(0, 0, 0));
        
        // Directions: up, right, down, left
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};
        
        // Dijkstra's Algorithm
        while (!pq.isEmpty()) {
            Tuple t = pq.poll();
            int row = t.row;
            int col = t.col;
            int time = t.time;

            // If we have reached the bottom-right corner, return the time
            if (row == n - 1 && col == m - 1) {
                return time;
            }

            // Explore all 4 directions (up, right, down, left)
            for (int i = 0; i < 4; i++) {
                int nrow = row + drow[i];
                int ncol = col + dcol[i];

                // Check if within bounds
                if (nrow >= 0 && ncol >= 0 && nrow < n && ncol < m) {
                    // Calculate the time to enter the next room
                    int newTime = Math.max(time, moveTime[nrow][ncol]) + 1;

                    // If we can reach this room earlier, update and push it into the queue
                    if (newTime < dis[nrow][ncol]) {
                        dis[nrow][ncol] = newTime;
                        pq.add(new Tuple(nrow, ncol, newTime));
                    }
                }
            }
        }
        
        return -1;  // If no path exists, which shouldn't happen based on the problem statement.
    }
}

/*
Visualization of the above code
  Let's visualize how this Java Dijkstra-based algorithm works on a 2D grid (`moveTime`) to calculate the minimum time to reach the bottom-right cell from the top-left cell.

---

🧠 What's Happening in the Code?

You are solving a grid navigation problem using Dijkstra’s algorithm. Each cell in `moveTime` represents the minimum time you are allowed to enter that cell.

At each step:

* You can move in 4 directions.
* You can only enter a cell after waiting for `moveTime[x][y]`, then spend +1 time to move.

---

🗺️ Example Grid (Let's Visualize)

Suppose:

```
moveTime = {
  {0, 2, 1},
  {1, 5, 2},
  {4, 6, 1}
}
```

```
Each cell value = minimum time allowed to enter
(0,0) is the start, (2,2) is the goal
```

---

⛳ Goal:

Reach from (0, 0) → (2, 2) in minimum time.

---

🧭 Algorithm Flow Visualization (Step-by-step with queue):

Initialization:

* `dis[0][0] = 0`
* `pq = [(0,0,0)]`

---

Step 1:

Dequeue `(0,0)` at time = 0
Neighbors:

* `(1,0)`: `max(0, moveTime[1][0]) + 1 = max(0,1)+1 = 2`
* `(0,1)`: `max(0, moveTime[0][1]) + 1 = max(0,2)+1 = 3`

Enqueue:

* `(1,0,2)`
* `(0,1,3)`

---

Step 2:

Dequeue `(1,0)` at time = 2
Neighbors:

* `(2,0)`: `max(2,4)+1 = 5`
* `(1,1)`: `max(2,5)+1 = 6`

Enqueue:

* `(2,0,5)`
* `(1,1,6)`

---

Step 3:

Dequeue `(0,1)` at time = 3
Neighbors:

* `(0,2)`: `max(3,1)+1 = 4` ✅ (note: max is 3)
* `(1,1)`: Already visited with smaller time (6 vs 6) → skip

Enqueue:

* `(0,2,4)`

---

Step 4:

Dequeue `(0,2)` at time = 4
Neighbors:

* `(1,2)`: `max(4,2)+1 = 5`

Enqueue:

* `(1,2,5)`

---

Step 5:

Dequeue `(1,2)` at time = 5
Neighbors:

* `(2,2)`: `max(5,1)+1 = 6` ✅ GOAL!

Return 6 ← ✅ Minimum time to reach (2,2)

---

✅ Final Output:

```
Minimum time to reach (2,2): 6
```

---

🔄 Summary Diagram of Time Flow:

```
Grid: moveTime
[0, 2, 1]
[1, 5, 2]
[4, 6, 1]

Time of arrival:
[0, 3, 4]
[2, 6, 5]
[5, ∞, 6] ← result
```

---

📌 Key Points:

* You're using a classic Dijkstra's shortest path technique on a grid with a custom weight function:

  ```
  newTime = max(currentTime, moveTime[x][y]) + 1
  ```
* The `PriorityQueue` ensures that we always process the lowest-time path first.
* Very efficient for real-time grid traversal problems with wait constraints.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 4ms runtime which is the lowest time in this problem.
*/

// class Solution {
//     public int minTimeToReach(int[][] moveTime) {
//         PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2]- b[2]);

//         pq.add(new int[]{0, 0, 0});
//         Set<String> visited = new HashSet<>();
//         visited.add("0 0");

//         int n = moveTime.length;
//         int m = moveTime[0].length;
//         int[] dx = {1, -1, 0, 0};
//         int[] dy = {0, 0, 1, -1};
//         while(!pq.isEmpty()) {
//             int[] current = pq.poll();

//             if (current[0] == (n - 1) && current[1] == (m-1)) {
//                 return current[2];
//             }

//             for(int i=0;i<4;i++) {
//                 int x = current[0] + dx[i];
//                 int y = current[1] + dy[i];

//                 String xy = x + " " + y;
//                 if (valid(x, y, n, m) && !visited.contains(xy)) {
//                     int waitTime = Math.max(moveTime[x][y] - current[2], 0);

//                     pq.add(new int[]{x, y, waitTime + current[2] + 1});
//                     visited.add(xy);
//                 }
//             }
//         }

//         return -1;
//     }

//     private boolean valid(int x, int y, int n, int m) {
//         return x >= 0 && x < n && y >= 0 && y < m;
//     }
// }

class Solution {
	private static class Room implements Comparable<Room> {
		final int openTime;
		Room[] adjacent;
		Room next;

		Room() {
			openTime = Integer.MAX_VALUE;
		}

		Room(int openTime) {
			this.openTime = openTime;
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
				rRow[j] = new Room(mtRow[j]);
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
		Room exitingHead = start;
		int currentTime = 0;
		while (true) {
			Room exitingHeadNew = null;
			while (exitingHead != null) {
				for (Room adj : exitingHead.adjacent)
					if (adj.next == adj) {
						if (adj == finish)
							return Math.max(currentTime, finish.openTime) + 1;
						if (adj.openTime <= currentTime) {
							adj.next = exitingHeadNew;
							exitingHeadNew = adj;
						} else {
							adj.next = null;
							waitingToEnter.offer(adj);
						}
					}
				exitingHead = exitingHead.next;
			}
			exitingHead = exitingHeadNew;
			int queueTime;
			while ((queueTime = waitingToEnter.peek().openTime) <= currentTime) {
				Room entering = waitingToEnter.poll();
				entering.next = exitingHead;
				exitingHead = entering;
			}
			if (++currentTime < queueTime && exitingHead == null)
				currentTime = queueTime;
		}
	}
}

/*
Visualization of the above code
  Let's delve into the provided Java code, which implements a modified version of Dijkstra's algorithm to determine the minimum time required to traverse a grid from the top-left corner to the bottom-right corner. This algorithm accounts for specific constraints related to each cell's accessibility based on time.

---

🧠 Understanding the Algorithm

Problem Statement

Given a 2D grid `moveTime`, where each cell `(i, j)` contains a value representing the earliest time you can enter that cell, the objective is to find the minimum time to move from the starting cell `(0, 0)` to the destination cell `(n-1, m-1)`. Movement is allowed in four directions: up, down, left, and right. Each move takes 1 unit of time, but you can only enter a cell if the current time is greater than or equal to its `moveTime` value.

Key Components

1. Room Class: Represents each cell in the grid. It contains:

   * `openTime`: The earliest time the room can be entered.
   * `adjacent`: An array of adjacent rooms (up to four).
   * `next`: A pointer used to build a linked list of rooms to be processed.

2. DUMMY\_ROOM: A sentinel `Room` object used to simplify boundary conditions.

3. initRooms Method: Initializes the grid of `Room` objects based on the `moveTime` matrix and sets up the adjacency relationships between rooms.

4. minTimeToReach Method: Implements the modified Dijkstra's algorithm using a priority queue to process rooms based on their `openTime`.

---

🔄 Step-by-Step Execution

Let's consider an example `moveTime` grid:

```
moveTime = {
  {0, 2, 1},
  {1, 5, 2},
  {4, 6, 1}
}
```

Initialization

* Create a grid of `Room` objects corresponding to each cell in `moveTime`.
* Set up the adjacency for each room (up, down, left, right).
* Define the starting room as `rooms[0][0]` and the destination room as `rooms[2][2]`.

Algorithm Execution

1. Start at Room (0,0):

   * Current time: 0
   * `openTime`: 0
   * Since current time ≥ `openTime`, we can enter.
   * Add adjacent rooms to the processing queue if they haven't been visited and their `openTime` ≤ current time.

2. Process Next Rooms:

   * Increment current time by 1.
   * For each room in the processing queue:

     * If current time ≥ `openTime`, enter the room.
     * Add unvisited adjacent rooms to the queue based on their `openTime`.

3. Repeat:

   * Continue this process, incrementing the current time and processing rooms accordingly, until the destination room is reached.

---

📊 Visualization of Time Flow

Here's a representation of the minimum time to reach each cell:

```
[0, 3, 4]
[2, 6, 5]
[5, ∞, 6]
```

* `0`: Starting point.
* `∞`: Unreachable at the current step.
* The numbers represent the earliest time each cell can be entered.

---

✅ Final Output

The minimum time to reach the destination cell `(2,2)` is 6.

---

📌 Key Observations

* This implementation avoids revisiting rooms by marking them as visited once they're added to the processing queue.
* The use of a priority queue ensures that rooms are processed in order of their earliest possible entry time.
* The algorithm efficiently handles the constraint that each room can only be entered at or after its specified `openTime`.

---
*/
