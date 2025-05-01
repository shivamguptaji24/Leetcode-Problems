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

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 66ms runtime which is the second lowest time in this problem.
*/

class Solution {

    public int maxTaskAssign(
        int[] tasks,
        int[] workers,
        int pills,
        int strength
    ) {
        int n = tasks.length, m = workers.length;
        Arrays.sort(tasks);
        Arrays.sort(workers);
        int left = 1, right = Math.min(m, n), ans = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (check(tasks, workers, pills, strength, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    // Check if pills and strength can be used in mid tasks
    private boolean check(
        int[] tasks,
        int[] workers,
        int pills,
        int strength,
        int mid
    ) {
        int p = pills;
        int m = workers.length;
        Deque<Integer> ws = new ArrayDeque<>();
        int ptr = m - 1;
        // Enumerate each task from largest to smallest
        for (int i = mid - 1; i >= 0; --i) {
            while (ptr >= m - mid && workers[ptr] + strength >= tasks[i]) {
                ws.addFirst(workers[ptr]);
                --ptr;
            }
            if (ws.isEmpty()) {
                return false;
            } else if (ws.getLast() >= tasks[i]) {
                // If the largest element in the deque is greater than or equal to tasks[i]
                ws.pollLast();
            } else {
                if (p == 0) {
                    return false;
                }
                --p;
                ws.pollFirst();
            }
        }
        return true;
    }
}

/*
Visualization of the above code
 Let's visualize how this optimized version of the `maxTaskAssign` algorithm works — it's a binary search + greedy + deque solution.

---

🔧 Problem Recap
- Assign as many tasks as possible to available workers.
- Each task requires a minimum strength.
- Workers can take a pill to get a temporary strength boost.
- You must maximize the number of tasks that can be assigned.

---

🧠 Core Idea
Use binary search to find the maximum number of tasks (`mid`) you can assign.

For each `mid`, you check if it's possible to assign `mid` tasks using the current number of pills and boost strength using a helper function `check()`.

---

🔍 Binary Search Range
```
int left = 1, right = Math.min(m, n), ans = 0;
```
We search for the maximum number of tasks from `1` to `min(#workers, #tasks)`.

---

🧪 check() Function Details
Purpose: Can we assign `mid` tasks with the available pills and strength?

Step-by-step:

```
Deque<Integer> ws = new ArrayDeque<>();
int ptr = m - 1;
```

- The `ptr` starts at the strongest worker.
- `ws` (deque) stores workers who might be able to complete the current task with or without a pill.

Now loop through the `mid` hardest tasks (from hardest to easiest):
```
for (int i = mid - 1; i >= 0; --i)
```

📦 While loop
```
while (ptr >= m - mid && workers[ptr] + strength >= tasks[i]) {
    ws.addFirst(workers[ptr]);
    --ptr;
}
```
This collects all workers who can potentially do the task (even with a pill).  
They are added to the front of the deque.

✅ Assignment Decision
```
if (ws.isEmpty()) {
    return false;
}
```
If there are no available workers for the current task → not possible.

```
else if (ws.getLast() >= tasks[i]) {
    ws.pollLast(); // Assign without pill
}
```
If the strongest available worker is enough → assign without pill.

```
else {
    if (p == 0) return false; // No pills left
    --p;
    ws.pollFirst(); // Use pill on weakest suitable worker
}
```
If not strong enough, try to use a pill on the weakest worker who, with the pill, can now do the task.

---

📊 Visualization Flowchart

```
Start Binary Search (1..min(tasks, workers))
|
|-- mid = (left + right)/2
|   |
|   |-- check(mid tasks can be assigned?)
|       |
|       |-- For i = mid-1 .. 0 (hardest to easiest tasks)
|       |   |
|       |   |-- While workers[ptr] + strength ≥ task[i]
|       |   |     Add to deque
|       |
|       |   |-- If deque empty → return false
|       |   |-- If strongest worker ≥ task[i] → assign, pop back
|       |   |-- Else if pill available:
|       |         Use on weakest suitable worker (pop front), pills--
|       |   |-- Else → return false
|       |
|       |-- All tasks assigned → return true
|
|-- If check(mid) true → ans = mid, left = mid + 1
|-- Else → right = mid - 1
```

---

✅ Key Optimizations
- `Deque` gives O(1) access to both weakest and strongest candidates.
- No need for `TreeMap` → much faster.
- Tasks are processed greedily from hardest to easiest to ensure we match the most difficult ones first.

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 40ms runtime which is the lowest time in this problem.
*/

class Solution {
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {

        int m = tasks.length, n = workers.length;
        Arrays.sort(tasks);
        Arrays.sort(workers);

        int l = 0, r = Math.min(m, n);
        while(l <= r){
            int mid = l + r >> 1;
            if(check(tasks, workers, pills, strength, mid, n - mid))
                l = mid+1;
            else
                r = mid-1;
        }

        return r;
    }
    
    public boolean check(int[] tasks, int[] workers, int pills, int strength, int mid, int start){
        
        int[] que = new int[mid];
        int write = 0, read = 0; 

        for(int i = 0, j = 0; i < mid; i++){
            int curStrength  = workers[start + i];
            if(read == write){

                if(curStrength >= tasks[j]){
                    j++;
                    continue;
                }

                if(pills == 0)
                    return false;
                
                curStrength  += strength;
                pills--;

                while(j < mid && curStrength >= tasks[j])
                    que[write++] = tasks[j++];
                
                if(read == write)
                    return false;
                write--;
                
            }else{

                if(curStrength >= que[read]){
                    read++;
                    continue;
                }

                if(pills == 0)
                    return false;
                
                curStrength  += strength;
                pills--;

                while(j < mid && curStrength >= tasks[j])
                    que[write++] = tasks[j++];
                
                write--;
            }
        }

        return read == write;        
    }
}

/*
Visualization of the above code
 Let's visualize and understand how this version of `maxTaskAssign` works — it's a binary search + greedy + simulated queue approach to solve the task assignment problem.

---

🧠 Goal
You want to assign as many tasks as possible to available workers.

- Each task has a strength requirement.
- Workers have a base strength.
- You can give a limited number of pills, each increasing strength by `strength`.

---

🔍 Step-by-Step Visualization

🔁 Binary Search Setup
```
int l = 0, r = Math.min(m, n);
```
We search for the maximum number of tasks that can be assigned.

🧪 Binary Search Loop
```
while (l <= r) {
    int mid = l + r >> 1; // Check if 'mid' tasks can be done
    if (check(..., mid, n - mid))
        l = mid + 1;
    else
        r = mid - 1;
}
return r;
```
We keep trying higher `mid` if it’s feasible to assign `mid` tasks.

---

✅ `check()` Method Logic

👇 Arguments
- `mid`: How many tasks we're trying to assign
- `start`: Index in workers array: the strongest `mid` workers (from `n - mid` to `n-1`)

---

⚙️ High-Level Plan in `check()`
- Simulate assigning `mid` tasks.
- If a worker can't handle a task directly:
  - Try using a pill.
  - Use greedy strategy: assign the hardest task that can now be handled.
- Use a simulated queue (`que[]`) to store extra tasks a worker can do after taking a pill.

---

🧪 Walkthrough
Let's walk through a few key parts:

1. Loop through `mid` workers
```
for (int i = 0, j = 0; i < mid; i++) {
    int curStrength = workers[start + i]; // Strongest worker
```

2. If queue is empty (no leftover tasks from previous workers)
```
if (read == write) {
    if (curStrength >= tasks[j]) {
        j++; // Assign without pill
        continue;
    }
    if (pills == 0) return false;
    
    curStrength += strength;
    pills--;
    
    // Add all new tasks this worker can now do
    while (j < mid && curStrength >= tasks[j])
        que[write++] = tasks[j++];
    
    if (read == write) return false; // Still nothing they can do
    write--; // Simulate assigning one
}
```

3. If queue has tasks from previous workers
```
else {
    if (curStrength >= que[read]) {
        read++; // Assign from queue
        continue;
    }

    if (pills == 0) return false;

    curStrength += strength;
    pills--;

    while (j < mid && curStrength >= tasks[j])
        que[write++] = tasks[j++];

    write--; // Simulate assigning one task
}
```

---

📦 Final Condition
```
return read == write;
```
We succeed only if all tasks in the queue are processed.

---

🧠 Summary of Key Concepts

| Concept         | Role                                                                 |
|----------------|----------------------------------------------------------------------|
| `binary search`| Determines max number of tasks assignable                            |
| `start`        | Uses strongest `mid` workers only                                     |
| `queue`        | Stores leftover assignable tasks after using a pill                  |
| `greedy`       | Always tries to assign the hardest task a worker can handle          |
| `read/write`   | Pointers simulate a real queue of pending tasks                      |

---

📊 Flowchart (Simplified)

```
Start Binary Search on [0, min(tasks, workers)]
|
|-- For each mid:
|    |
|    |-- Pick strongest `mid` workers (from n - mid to n - 1)
|    |
|    |-- For each worker:
|        |
|        |-- If task can be done → assign
|        |-- Else if pills > 0:
|              Take pill, gain strength
|              Try to assign as many new tasks as possible
|        |-- Else → return false
|
|-- If all tasks done → mid is valid
|
|-- Binary search continues
```

---
*/
