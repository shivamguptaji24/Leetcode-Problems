/*
Daily Question :
2179 - Count Good Triplets in An Array

You are given two 0-indexed arrays nums1 and nums2 of length n, both of which are permutations of [0, 1, ..., n - 1].
A good triplet is a set of 3 distinct values which are present in increasing order by position both in nums1 and nums2. In other words, if we consider pos1v as the index of the value v in nums1 and pos2v as the index of the value v in nums2, then a good triplet will be a set (x, y, z) where 0 <= x, y, z <= n - 1, such that pos1x < pos1y < pos1z and pos2x < pos2y < pos2z.
Return the total number of good triplets. 

Example 1:

Input: nums1 = [2,0,1,3], nums2 = [0,1,2,3]
Output: 1
Explanation: 
There are 4 triplets (x,y,z) such that pos1x < pos1y < pos1z. They are (2,0,1), (2,0,3), (2,1,3), and (0,1,3). 
Out of those triplets, only the triplet (0,1,3) satisfies pos2x < pos2y < pos2z. Hence, there is only 1 good triplet.

Example 2:

Input: nums1 = [4,0,1,3,2], nums2 = [4,1,0,2,3]
Output: 4
Explanation: The 4 good triplets are (4,0,3), (4,0,2), (4,1,3), and (4,1,2).
 

Constraints:

n == nums1.length == nums2.length
3 <= n <= 105
0 <= nums1[i], nums2[i] <= n - 1
nums1 and nums2 are permutations of [0, 1, ..., n - 1].
*/

class Solution {
    public long goodTriplets(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] pos = new int[n];
        BinaryIndexedTree tree = new BinaryIndexedTree(n);
        for (int i = 0; i < n; ++i) {
            pos[nums2[i]] = i + 1;
        }
        long ans = 0;
        for (int num : nums1) {
            int p = pos[num];
            long left = tree.query(p);
            long right = n - p - (tree.query(n) - tree.query(p));
            ans += left * right;
            tree.update(p, 1);
        }
        return ans;
    }
}

class BinaryIndexedTree {
    private int n;
    private int[] c;

    public BinaryIndexedTree(int n) {
        this.n = n;
        c = new int[n + 1];
    }

    public void update(int x, int delta) {
        while (x <= n) {
            c[x] += delta;
            x += lowbit(x);
        }
    }

    public int query(int x) {
        int s = 0;
        while (x > 0) {
            s += c[x];
            x -= lowbit(x);
        }
        return s;
    }

    public static int lowbit(int x) {
        return x & -x;
    }
}

/*
Visualization of the above code
 Let's visualize and understand this optimized Java code which solves a variation of the "good triplets" problem using a Binary Indexed Tree (Fenwick Tree).

---

🔍 Problem Summary (Implicit from Code):

Given two permutations `nums1` and `nums2` of numbers `0` to `n-1`, a triplet `(i, j, k)` in `nums1` is called good if their positions in `nums2` follow the same relative order.

We want to count the number of such increasing triplets where:
- The elements appear in `nums1` as `(i, j, k)`
- And they appear in `nums2` in the same order as in `nums1`

---

📘 Example:

```
nums1 = [2, 0, 1, 3]
nums2 = [0, 1, 2, 3]
```

In `nums1`, we’re looking for triplets like `(2, 1, 3)` such that:
- Positions of 2, 1, 3 in `nums2` are also in increasing order.

---

🧠 Step-by-Step Logic

1. Build Position Mapping:
We first map each value in `nums2` to its 1-based index in the `pos[]` array:
```
for (int i = 0; i < n; ++i) {
    pos[nums2[i]] = i + 1; // 1-based indexing
}
```

For example:
```
nums2 = [0, 1, 2, 3]
=> pos = [1, 2, 3, 4]
```

---

2. Traverse `nums1` and Count Good Triplets

```
for (int num : nums1)
```

We process each element of `nums1` in order and consider it as the middle element `j` in a triplet `(i, j, k)`.

At each step:
- Let `p = pos[num]` → position of this number in `nums2`
- We want:
  - `left` = count of `nums1` elements (before current) with `pos < p`
  - `right` = count of elements after current with `pos > p`

```
long left = tree.query(p);  // prefix sum ≤ p
long right = n - p - (tree.query(n) - tree.query(p));
```

- `tree.query(p)` gives count of numbers with position ≤ `p`
- `tree.query(n) - tree.query(p)` gives count of numbers with position > `p`

Now:
```
ans += left * right;
```

Because for each valid `i` (on the left) and valid `k` (on the right), we form a triplet `(i, j, k)`.

Finally:
```
tree.update(p, 1); // Mark this number's position in BIT
```

---

🔄 Binary Indexed Tree (BIT)

The `BinaryIndexedTree` class helps maintain frequency of positions efficiently in O(log n) time.

- `update(p, 1)` → mark presence of number at position `p`
- `query(p)` → get count of numbers with position ≤ `p`

---

🖼️ Visualization with Example

Let’s take:
```
nums1 = [2, 0, 1, 3]
nums2 = [0, 1, 2, 3]
```

We map positions from `nums2`:
```
pos = [1, 2, 3, 4]
```

Now traverse `nums1`:

Step 1: num = 2 → p = 3
- `left = query(3) = 0` (nothing before)
- `right = 4 - 3 - (query(4) - query(3)) = 1 - 0 = 1`
- No valid triplets yet
- Update BIT at 3

Step 2: num = 0 → p = 1
- `left = query(1) = 0`
- `right = 4 - 1 - (query(4) - query(1)) = 3 - 1 = 2`
- Still no triplet
- Update BIT at 1

Step 3: num = 1 → p = 2
- `left = query(2) = 1` (0 is before at position 1)
- `right = 4 - 2 - (query(4) - query(2)) = 2 - 1 = 1`
- So we can form 1 triplet → `ans += 1 * 1 = 1`
- Update BIT at 2

Step 4: num = 3 → p = 4
- `left = query(4) = 3`
- `right = 4 - 4 - (query(4) - query(4)) = 0`
- No more triplets
- Done

✅ Final Answer: `1`

---

✅ Final Notes:

- Very efficient: O(n log n) using BIT
- Much faster than brute-force O(n³) or even O(n²) approaches
- Used in competitive programming/interviews for triplet or inversion problems

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 10ms runtime which is the lowest time in this problem.
*/

class Solution 
{
    public long goodTriplets(int[] nums1, int[] nums2) 
    {
        int n = nums1.length;
        int[] pos2 = new int[n], reversedIndexMapping = new int[n];
        
        for (int i = 0; i < n; i++) 
        {
            pos2[nums2[i]] = i;
        }
        for (int i = 0; i < n; i++) 
        {
            reversedIndexMapping[pos2[nums1[i]]] = i;
        }
        
        FenwickTree tree = new FenwickTree(n);
        long res = 0;
        for (int value = 0; value < n; value++) 
        {
            int pos = reversedIndexMapping[value];
            int left = tree.query(pos);
            tree.update(pos, 1);
            int right = (n - 1 - pos) - (value - left);
            res += (long) left * right;
        }
        return res;
    }

    private class FenwickTree 
    {
        private int[] tree;

        public FenwickTree(int size) 
        {
            this.tree = new int[size + 1];
        }

        public void update(int index, int delta) 
        {
            index++;
            while (index < tree.length) 
            {
                this.tree[index] += delta;
                index += index & -index;
            }
        }

        public int query(int index) 
        {
            index++;
            int res = 0;
            while (index > 0) 
            {
                res += this.tree[index];
                index -= index & -index;
            }
            return res;
        }
    }
}

/*
Visualization of the above code
 Let’s visualize and break down this optimized solution for counting "good triplets" using a Fenwick Tree (BIT), in a very intuitive and simple way.

---

🔍 Problem Summary:

Given two permutations `nums1` and `nums2`, you want to count the number of increasing triplets `(i, j, k)` such that:

- The elements in the triplet are from `nums1`.
- Their positions in `nums2` also maintain the same increasing order.

---

🧠 Intuition Behind the Code:

We are converting the problem into:
- A 1D inversion count style problem.
- We're processing elements based on position order in `nums2`, but actually iterating over `nums1`.

So let's go through the code line-by-line with inline visualization.

---

🪄 Step-by-Step Visualization:

✅ Sample Input:

```
nums1 = [2, 0, 1, 3]
nums2 = [0, 1, 2, 3]
```

📌 Step 1: Map `nums2` values to their indices

```
for (int i = 0; i < n; i++) {
    pos2[nums2[i]] = i;
}
```

This will give:
```
pos2 = [0, 1, 2, 3]  // pos2[value] = index in nums2
```

📌 Step 2: Convert `nums1` to positions in `nums2` using pos2

```
for (int i = 0; i < n; i++) {
    reversedIndexMapping[pos2[nums1[i]]] = i;
}
```

Walkthrough:
- `nums1[0] = 2` → `pos2[2] = 2` → `reversedIndexMapping[2] = 0`
- `nums1[1] = 0` → `pos2[0] = 0` → `reversedIndexMapping[0] = 1`
- `nums1[2] = 1` → `pos2[1] = 1` → `reversedIndexMapping[1] = 2`
- `nums1[3] = 3` → `pos2[3] = 3` → `reversedIndexMapping[3] = 3`

So:

```
reversedIndexMapping = [1, 2, 0, 3]
```

---

📈 Step 3: Main Triplet Counting Loop

```
for (int value = 0; value < n; value++) {
    int pos = reversedIndexMapping[value];
    int left = tree.query(pos);
    tree.update(pos, 1);
    int right = (n - 1 - pos) - (value - left);
    res += (long) left * right;
}
```

🔍 What’s happening here?

- We treat `value` as the middle of a triplet.
- `pos` = index in `nums1` where `nums2[value]` appears.
- `left` = number of values to the left of `pos` that have already appeared.
- `right` = number of values to the right of `pos` that will appear after this, but are greater in `nums2`.

🔁 Dry Run for our example:

```
reversedIndexMapping = [1, 2, 0, 3]
n = 4
```

🔹 value = 0 → pos = 1
- left = `tree.query(1)` = 0
- update BIT at 1
- right = (3 - 1) - (0 - 0) = 2
- `res += 0 * 2 = 0`

🔹 value = 1 → pos = 2
- left = `tree.query(2)` = 1 (one value inserted before)
- update BIT at 2
- right = (3 - 2) - (1 - 1) = 1
- `res += 1 * 1 = 1`

🔹 value = 2 → pos = 0
- left = `tree.query(0)` = 0
- update BIT at 0
- right = (3 - 0) - (2 - 0) = 1
- `res += 0 * 1 = 0`

🔹 value = 3 → pos = 3
- left = `tree.query(3)` = 3
- update BIT at 3
- right = (3 - 3) - (3 - 3) = 0
- `res += 3 * 0 = 0`

---

✅ Final Result:
```
res = 1
```

So only 1 good triplet exists, which maintains increasing order in both `nums1` and `nums2`.

---

🌳 FenwickTree (Binary Indexed Tree)

Efficiently supports:
- `update(index, delta)` in `O(log n)` – adds a value at index
- `query(index)` in `O(log n)` – prefix sum up to index

Used here to keep track of how many values appeared before the current `pos`.

---

✅ Summary

- 🧠 Uses clever mapping from `nums2` to index positions.
- 🔄 Transforms triplet problem into a 1D prefix-suffix combo using a BIT.
- 💡 Time complexity: O(n log n)
- 🎯 Space complexity: O(n)

---
*/
