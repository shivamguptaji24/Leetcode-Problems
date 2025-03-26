/*
Daily Question :
2033 - Minimum Operations to Make a Uni-Value Grid

You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from any element in the grid.

A uni-value grid is a grid where all the elements of it are equal.

Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.

Example 1:

Input: grid = [[2,4],[6,8]], x = 2
Output: 4
Explanation: We can make every element equal to 4 by doing the following: 
- Add x to 2 once.
- Subtract x from 6 once.
- Subtract x from 8 twice.
A total of 4 operations were used.

Example 2:

Input: grid = [[1,5],[2,3]], x = 1
Output: 5
Explanation: We can make every element equal to 3.

Example 3:

Input: grid = [[1,2],[3,4]], x = 2
Output: -1
Explanation: It is impossible to make every element equal.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 105
1 <= m * n <= 105
1 <= x, grid[i][j] <= 104
*/

class Solution {
    public int minOperations(int[][] grid, int x) {
        int m = grid.length, n = grid[0].length;
        int size = m * n;
        int[] arr = new int[size];
        int index = 0;

        // Step 1: Flatten the 2D grid into a 1D array
        for(int[] row : grid) {
            for(int num : row) {
                arr[index++] = num;
            }
        }

        // Step 2: Check if transformation is possible
        int remainder = arr[0] % x;
        for(int num : arr) {
            if(num % x != remainder) {
                return -1;  // Impossible to make all elements
            }
        }

        // Step 3: Find the median using QuickSelect
        int median = quickSelect(arr, size / 2);

        // Step 4: Compute the total operations
        int operations = 0;
        for(int num : arr) {
            operations += Math.abs(num - median) / x;
        }

        return operations;
    }

    // QuickSelect algorithm to find k-th smallest element in O(n) average time
    private int quickSelect(int[] arr, int k) {
        int left = 0, right = arr.length - 1;
        Random rand = new Random();

        while(left < right) {
            int pivotIndex = partition(arr, left, right, rand.nextInt(right - left + 1) + left);
            if(pivotIndex == k) {
                return arr[k];
            } else if(pivotIndex < k) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }

        return arr[left];
    }

    // Lomuto partition scheme for QuickSelect
    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right); // Move pivot to end
        int storeIndex = left;
        
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex++, i);
            }
        }
        swap(arr, storeIndex, right); // Move pivot to its final place
        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/*
Visualization of the above code
 Let's break down and visualize this Java code step by step to see how it works.

---

🔹 Understanding the Code Flow
The goal is to convert the grid into a uni-value grid using minimum operations where each operation allows adding or subtracting `x` from an element.

---

💡 Example Walkthrough
Input:
```
grid = [[2,4],[6,8]], x = 2
```

Step 1️⃣: Flatten the 2D Grid into a 1D Array
We store all elements of `grid` into `arr[]`:
```
grid = [[2, 4],
        [6, 8]]

Flattened array (arr) → [2, 4, 6, 8]
```

Step 2️⃣: Check Feasibility
Each element's remainder when divided by `x` should be the same.
```
2 % 2 = 0
4 % 2 = 0
6 % 2 = 0
8 % 2 = 0
```
✅ Since all have remainder `0`, transformation is possible.

---

Step 3️⃣: Find the Median using QuickSelect
To minimize operations, we transform everything to the median.

Sorted `arr[]`:
```
Sorted: [2, 4, 6, 8]
Median = 4
```
Instead of sorting, we use QuickSelect to find the median in O(n) time.

---

Step 4️⃣: Calculate Total Operations
We calculate how many times we must add/subtract `x` to make all elements equal to the median (4).
```
Operations:
(4 - 2) / 2 = 1
(4 - 4) / 2 = 0
(6 - 4) / 2 = 1
(8 - 4) / 2 = 2

Total = 1 + 0 + 1 + 2 = 4
```
✅ Output: `4`

---

🔹 QuickSelect Visualization
Instead of sorting, QuickSelect helps us find the k-th smallest element efficiently.

Finding the median using QuickSelect:
1. Choose a random pivot (e.g., 6).
2. Partition the array so that:
   - Elements smaller than pivot are on the left.
   - Elements greater than pivot are on the right.
3. Check pivot position:
   - If it's at index `size/2`, it's the median.
   - If it's larger, search left; if smaller, search right.
4. Repeat until median is found.

---

🖼️ Visualization of Execution
```
Original Grid:
2   4
6   8

Flattened Array:
[2, 4, 6, 8]

Feasibility Check:
All elements % x = 0  ✅ Possible

Finding Median using QuickSelect:
[2, 4, 6, 8] → QuickSelect finds `4`

Operations to make all elements 4:
2 → 4   (1 operation)
4 → 4   (0 operations)
6 → 4   (1 operation)
8 → 4   (2 operations)

Total Operations = 4
```

---

🔹 Final Summary
1. Flatten the grid → Convert it into a list.
2. Check feasibility → If elements don’t have the same remainder modulo `x`, return `-1`.
3. Find the median efficiently → Use QuickSelect (O(m * n)) instead of sorting (O(m * n log m * n)).
4. Compute operations → Move all elements to the median.
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the lowest time in this problem
*/

class Solution {
    public int minOperations(int[][] grid, int x) {

        int m = grid.length, n = grid[0].length;

        int len = m * n;
        if(len < 2) return 0;
        
        int[] count = new int[10001];

        int totalSum = 0;
        int r = grid[0][0] % x;
        for(int i = 0; i < m; ++i){
            for(int j = 0; j < n; ++j){
                int v = grid[i][j];
                if(v % x != r) return -1;
                count[v]++;
            } 
        }
        
        len = (len + 1) / 2;
        int total = 0;
        int median = 0;

        for(int v = 0; v < count.length; ++v){
            if(count[v] == 0) continue;
            total += count[v];
            if(total >= len){
                median = v;
                break;
            }
        }
        
        int ans = 0;
        for(int v = 0; v <= median; ++v){
            if(count[v] == 0) continue;
            ans += count[v] * (median - v) / x;
        }

        for(int v = median + 1; v < count.length; ++v){
            if(count[v] == 0) continue;
            ans += count[v] * (v - median) / x;
        }        
        return ans;
    }
}

/*
Visualization of the above code
 
