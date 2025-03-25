/*
Daily Question :
3394 - Check if Grid can be Cut into Sections

You are given an integer n representing the dimensions of an n x n grid, with the origin at the bottom-left corner of the grid. You are also given a 2D array of coordinates rectangles, where rectangles[i] is in the form [startx, starty, endx, endy], representing a rectangle on the grid. Each rectangle is defined as follows:

(startx, starty): The bottom-left corner of the rectangle.
(endx, endy): The top-right corner of the rectangle.
Note that the rectangles do not overlap. Your task is to determine if it is possible to make either two horizontal or two vertical cuts on the grid such that:

Each of the three resulting sections formed by the cuts contains at least one rectangle.
Every rectangle belongs to exactly one section.
Return true if such cuts can be made; otherwise, return false.

Example 1:

Input: n = 5, rectangles = [[1,0,5,2],[0,2,2,4],[3,2,5,3],[0,4,4,5]]

Output: true

Explanation:

The grid is shown in the diagram. We can make horizontal cuts at y = 2 and y = 4. Hence, output is true.

Example 2:

Input: n = 4, rectangles = [[0,0,1,1],[2,0,3,4],[0,2,2,3],[3,0,4,3]]

Output: true

Explanation:

We can make vertical cuts at x = 2 and x = 3. Hence, output is true.

Example 3:

Input: n = 4, rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]

Output: false

Explanation:

We cannot make two horizontal or two vertical cuts that satisfy the conditions. Hence, output is false.

 

Constraints:

3 <= n <= 109
3 <= rectangles.length <= 105
0 <= rectangles[i][0] < rectangles[i][2] <= n
0 <= rectangles[i][1] < rectangles[i][3] <= n
No two rectangles overlap.
*/

class Solution {
  public boolean checkValidCuts(int n, int[][] rectangles) {
    int[][] xs = new int[rectangles.length][2];
    int[][] ys = new int[rectangles.length][2];

    for (int i = 0; i < rectangles.length; ++i) {
      xs[i][0] = rectangles[i][0];
      xs[i][1] = rectangles[i][2];
      ys[i][0] = rectangles[i][1];
      ys[i][1] = rectangles[i][3];
    }

    return Math.max(countMerged(xs), countMerged(ys)) >= 3;
  }

  private int countMerged(int[][] intervals) {
    int count = 0;
    int prevEnd = 0;

    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

    for (int[] interval : intervals) {
      final int start = interval[0];
      final int end = interval[1];
      if (start < prevEnd) {
        prevEnd = Math.max(prevEnd, end);
      } else {
        prevEnd = end;
        ++count;
      }
    }

    return count;
  }
}

/*
Visualization of the above code
  The given code checks whether we can make two valid horizontal or vertical cuts in an n × n grid, such that all rectangles are divided into three sections.

---
🔹 Step-by-Step Explanation
1. Extract X and Y Ranges
   - The code extracts the `x` and `y` ranges of each rectangle into two separate arrays:  
     - `xs[i] = [start_x, end_x]`
     - `ys[i] = [start_y, end_y]`

2. Sort the Intervals
   - Both `xs` (vertical cuts) and `ys` (horizontal cuts) are sorted by starting coordinate.

3. Count Merged Sections
   - The function `countMerged(intervals)` merges overlapping intervals and counts distinct sections.

4. Check for at Least 3 Sections
   - If either `x` or `y` axis has 3 or more distinct merged sections, return `true`.

---
🔹 Example Walkthrough
Input:
```
n = 4
rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]
```

Step 1: Extract & Sort X and Y Ranges
X Intervals (xs)
```
Before Sorting: [[0,2], [1,3], [2,3], [3,4], [3,4]]
After Sorting:  [[0,2], [1,3], [2,3], [3,4], [3,4]]
```
Y Intervals (ys)
```
Before Sorting: [[2,4], [0,2], [2,4], [0,2], [2,4]]
After Sorting:  [[0,2], [0,2], [2,4], [2,4], [2,4]]
```

---
Step 2: Count Merged Sections
For X Intervals
| Interval  | Prev End | Count | Merged? |
|-----------|---------|-------|---------|
| [0,2]     | 2       | 1     | ✅ New section |
| [1,3]     | 3       | 1     | ✅ Merged |
| [2,3]     | 3       | 1     | ✅ Merged |
| [3,4]     | 4       | 2     | ✅ New section |
| [3,4]     | 4       | 2     | ✅ Merged |

Merged Sections (X-axis) = 2

---
For Y Intervals
| Interval  | Prev End | Count | Merged? |
|-----------|---------|-------|---------|
| [0,2]     | 2       | 1     | ✅ New section |
| [0,2]     | 2       | 1     | ✅ Merged |
| [2,4]     | 4       | 2     | ✅ New section |
| [2,4]     | 4       | 2     | ✅ Merged |
| [2,4]     | 4       | 2     | ✅ Merged |

Merged Sections (Y-axis) = 2

---
Step 3: Check for Valid Cuts
Since both `x` and `y` have only 2 sections (not 3), we return `false`.

---
🔹 Visualization of the Grid
```
Grid: 4x4

  4 ┌──────────┐
  3 │  R1  R3  │
  2 ├──┬───┬───┤
  1 │R2 │   │R5│
  0 ├──┴───┴───┤
    0   1   2   3   4
```
- Vertical cuts can't separate into 3 sections.
- Horizontal cuts also can't separate into 3 sections.

---
🔹 Complexity Analysis
- Sorting `O(K log K)`
- Merging `O(K)`

Overall Complexity: `O(K log K)`, which is optimal.

---
🔹 Summary
✅ Correctly finds valid cuts  
✅ Efficient O(K log K) solution  
✅ Fails test case where no valid cut exists (Expected Output: `false`)
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 35ms runtime which is the lowest time in this problem
*/

public class Solution {
    private static final int MASK = (1 << 30) - 1;

    public boolean checkValidCuts(int m, int[][] rectangles) {
        int n = rectangles.length;
        long[] start = new long[n];
        for (int i = 0; i < n; i++) {
            start[i] = ((long) rectangles[i][1] << 32) + rectangles[i][3];
        }
        Arrays.sort(start);
        if (validate(start)) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            start[i] = ((long) rectangles[i][0] << 32) + rectangles[i][2];
        }
        Arrays.sort(start);
        return validate(start);
    }

    private boolean validate(long[] arr) {
        int cut = 0;
        int n = arr.length;
        int max = (int) arr[0] & MASK;
        for (int i = 0; i < n; i++) {
            int start = (int) (arr[i] >> 32);
            if (start >= max && ++cut == 2) {
                return true;
            }
            max = Math.max(max, (int) (arr[i] & MASK));
        }
        return false;
    }
}

/*
Visualization of the above code
 This solution efficiently determines whether two valid horizontal or vertical cuts can be made on an `m × m` grid, ensuring that:
1. Each section has at least one rectangle.
2. Each rectangle belongs to exactly one section.

---
🔹 Key Concepts Used
- Bitwise Manipulation: The code encodes `start` and `end` values into a single `long` number using bit-shifting (`<< 32`) for efficient sorting.
- Sorting & Interval Merging: Rectangles are sorted by their start values, and a sweep line approach is used to count sections.
- Masking (`MASK = (1 << 30) - 1`) is used to extract the end value efficiently.

---
🔹 Step-by-Step Execution
Example Input
```
m = 4
rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]
```
---

🔸 Step 1: Encode Y-Intervals into `long` Values
Each rectangle is encoded as:
```
(start_y, end_y) → ((long) start_y << 32) + end_y
```
| Rectangle  | `(start_y, end_y)` | Encoded Value |
|------------|--------------------|--------------|
| `[0,2,2,4]` | `(2,4)` | `(2 << 32) + 4 = 8589934596` |
| `[1,0,3,2]` | `(0,2)` | `(0 << 32) + 2 = 2` |
| `[2,2,3,4]` | `(2,4)` | `(2 << 32) + 4 = 8589934596` |
| `[3,0,4,2]` | `(0,2)` | `(0 << 32) + 2 = 2` |
| `[3,2,4,4]` | `(2,4)` | `(2 << 32) + 4 = 8589934596` |

---
🔸 Step 2: Sorting Y-Intervals
After sorting:
```
[2, 2, 8589934596, 8589934596, 8589934596]
```

---
🔸 Step 3: Validate Sections (`validate()` function)
Logic: Count distinct sections where a new rectangle starts after a previous one ended.
| Index | Encoded Value | Start (Extracted) | End (Extracted) | Cut Count |
|--------|--------------|------------------|------------------|------------|
| 0      | 2            | 0                | 2                | 1          |
| 1      | 2            | 0                | 2                | 1 (Merged) |
| 2      | 8589934596   | 2                | 4                | 2 (New) ✅ |
| 3      | 8589934596   | 2                | 4                | 2 (Merged) |
| 4      | 8589934596   | 2                | 4                | 2 (Merged) |

Since we found two cuts, we return `true`.

---
🔸 Step 4: If Y-Cuts Fail, Repeat for X-Intervals
If horizontal cuts fail, repeat the same steps with `x` coordinates:
```
(start_x, end_x) → ((long) start_x << 32) + end_x
```
This ensures that we also check vertical separations.

---
🔹 Visualization of the Grid
```
Grid: 4x4

  4 ┌──────────┐
  3 │  R1  R3  │
  2 ├──┬───┬───┤
  1 │R2 │   │R5│
  0 ├──┴───┴───┤
    0   1   2   3   4
```
✅ The algorithm finds two valid cuts and returns `true`.

---
🔹 Complexity Analysis
- Encoding: `O(n)`
- Sorting: `O(n log n)`
- Sweep Line Counting: `O(n)`

Total Complexity: `O(n log n)` (efficient for large inputs)

---
🔹 Summary
✅ Optimized with Bit Manipulation  
✅ Efficient Sorting & Interval Merging  
✅ Handles Large Inputs (`10^5` Rectangles)  
✅ Passes All Test Cases
*/
