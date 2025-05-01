/*
Daily Question :
2071 - Maximum Number Of Tasks You Can Assign

You have n tasks and m workers. Each task has a strength requirement stored in a 0-indexed integer array tasks, with the ith task requiring tasks[i] strength to complete. The strength of each worker is stored in a 0-indexed integer array workers, with the jth worker having workers[j] strength. Each worker can only be assigned to a single task and must have a strength greater than or equal to the task's strength requirement (i.e., workers[j] >= tasks[i]).
Additionally, you have pills magical pills that will increase a worker's strength by strength. You can decide which workers receive the magical pills, however, you may only give each worker at most one magical pill.
Given the 0-indexed integer arrays tasks and workers and the integers pills and strength, return the maximum number of tasks that can be completed. 

Example 1:

Input: tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
Output: 3
Explanation:
We can assign the magical pill and tasks as follows:
- Give the magical pill to worker 0.
- Assign worker 0 to task 2 (0 + 1 >= 1)
- Assign worker 1 to task 1 (3 >= 2)
- Assign worker 2 to task 0 (3 >= 3)

Example 2:

Input: tasks = [5,4], workers = [0,0,0], pills = 1, strength = 5
Output: 1
Explanation:
We can assign the magical pill and tasks as follows:
- Give the magical pill to worker 0.
- Assign worker 0 to task 0 (0 + 5 >= 5)

Example 3:

Input: tasks = [10,15,30], workers = [0,10,10,10,10], pills = 3, strength = 10
Output: 2
Explanation:
We can assign the magical pills and tasks as follows:
- Give the magical pill to worker 0 and worker 1.
- Assign worker 0 to task 0 (0 + 10 >= 10)
- Assign worker 1 to task 1 (10 + 10 >= 15)
The last pill is not given because it will not make any worker strong enough for the last task.
 

Constraints:

n == tasks.length
m == workers.length
1 <= n, m <= 5 * 104
0 <= pills <= m
0 <= tasks[i], workers[j], strength <= 109
*/

class Solution {
  public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
    int ans = 0;
    int l = 0;
    int r = Math.min(tasks.length, workers.length);

    Arrays.sort(tasks);
    Arrays.sort(workers);

    while (l <= r) {
      final int m = (l + r) / 2;
      if (canComplete(tasks, workers, pills, strength, m)) {
        ans = m;
        l = m + 1;

      } else {
        r = m - 1;
      }
    }

    return ans;
  }

  // Returns true if we can finish k tasks.
  private boolean canComplete(int[] tasks, int[] workers, int pillsLeft, int strength, int k) {
    // k strongest workers
    TreeMap<Integer, Integer> sortedWorkers = new TreeMap<>();
    for (int i = workers.length - k; i < workers.length; ++i)
      sortedWorkers.merge(workers[i], 1, Integer::sum);

    // Out of the k smallest tasks, start from the biggest one.
    for (int i = k - 1; i >= 0; --i) {
      // Find the first worker that has strength >= tasks[i].
      Integer lo = sortedWorkers.ceilingKey(tasks[i]);
      if (lo != null) {
        sortedWorkers.merge(lo, -1, Integer::sum);
        if (sortedWorkers.get(lo) == 0) {
          sortedWorkers.remove(lo);
        }
      } else if (pillsLeft > 0) {
        // Find the first worker that has strength >= tasks[i] - strength.
        lo = sortedWorkers.ceilingKey(tasks[i] - strength);
        if (lo != null) {
          sortedWorkers.merge(lo, -1, Integer::sum);
          if (sortedWorkers.get(lo) == 0) {
            sortedWorkers.remove(lo);
          }
          --pillsLeft;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }

    return true;
  }
}

/*
Visualization of the above code
 This code is solving a greedy + binary search + simulation problem where you need to assign as many tasks to workers as possible, with limited use of pills that temporarily boost a worker's strength.

Let’s break it down step-by-step and visualize the flow of the program:

---

🔧 Problem Concept
- You have:
  - An array of `tasks` with required strength.
  - An array of `workers` with their strength.
  - A number of `pills` that can give a temporary boost of `strength`.

- Objective: Assign as many tasks as possible to the workers. One worker can do at most one task. You can give a pill to a worker to temporarily boost their strength by `strength`.

---

🚀 Overall Strategy
1. Sort both `tasks` and `workers`.
2. Use binary search on the number of tasks to try assigning (`k`) from `0` to `min(tasks.length, workers.length)`.
3. For each `k`, check using a helper `canComplete` if it’s possible to assign `k` tasks with available pills and worker strengths.

---

🔄 Binary Search Logic

```
int l = 0;
int r = Math.min(tasks.length, workers.length);

while (l <= r) {
  int m = (l + r) / 2;
  if (canComplete(..., m)) {
    ans = m;       // possible to assign m tasks
    l = m + 1;     // try for more
  } else {
    r = m - 1;     // try fewer
  }
}
```

- We search for the maximum number of assignable tasks (`m`) using `canComplete`.

---

🤖 `canComplete()` Function Logic
This function checks if we can assign exactly `k` tasks with the given number of pills and strength boost.

Step-by-step:
1. Take the k strongest workers using a `TreeMap` for efficient access.
2. From the k hardest tasks (i.e., the largest `k` from sorted `tasks`), try assigning:
   - Preferably to a worker strong enough without a pill.
   - If not, try assigning to a worker who can complete it with a pill (`worker >= task - strength`).
   - If neither is possible, return false.

Example of logic:
```
task[i] = 10
strength = 5
pillsLeft > 0
→ We can give pill to any worker with strength ≥ 10 - 5 = 5
```

TreeMap usage:
- `.ceilingKey(x)` gets the smallest worker ≥ x.
- `.merge(..., -1, Integer::sum)` decreases the count of that strength.
- Remove the worker from the map if count becomes 0.

---

📊 Visualization Summary

```
Binary Search Range: [0 .. min(#tasks, #workers)]

For each mid (m):
 └── can we assign 'm' hardest tasks to 'm' strongest workers?
      ├── YES → try more: l = m + 1
      └── NO  → try fewer: r = m - 1

Inside canComplete(m):
 └── Create TreeMap of k strongest workers
 └── Loop over k hardest tasks (from hardest to easiest)
     ├── Try worker ≥ task[i]
     ├── Else, try worker ≥ task[i] - strength (use pill)
     └── Else, return false

Update ans = max assignable tasks found.
```

---
*/
