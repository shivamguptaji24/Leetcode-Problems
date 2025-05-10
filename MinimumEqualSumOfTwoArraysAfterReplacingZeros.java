/*
Daily Question :
2918 - Minimum Equal Sum Of Two Arrays After Replacing Zeros

You are given two arrays nums1 and nums2 consisting of positive integers.
You have to replace all the 0's in both arrays with strictly positive integers such that the sum of elements of both arrays becomes equal.
Return the minimum equal sum you can obtain, or -1 if it is impossible. 

Example 1:

Input: nums1 = [3,2,0,1,0], nums2 = [6,5,0]
Output: 12
Explanation: We can replace 0's in the following way:
- Replace the two 0's in nums1 with the values 2 and 4. The resulting array is nums1 = [3,2,2,1,4].
- Replace the 0 in nums2 with the value 1. The resulting array is nums2 = [6,5,1].
Both arrays have an equal sum of 12. It can be shown that it is the minimum sum we can obtain.

Example 2:

Input: nums1 = [2,0,2,0], nums2 = [1,4]
Output: -1
Explanation: It is impossible to make the sum of both arrays equal.
 

Constraints:

1 <= nums1.length, nums2.length <= 105
0 <= nums1[i], nums2[i] <= 106
*/

class Solution {
  public long minSum(int[] nums1, int[] nums2) {
    final long sum1 = Arrays.stream(nums1).asLongStream().sum();
    final long sum2 = Arrays.stream(nums2).asLongStream().sum();
    final long zero1 = Arrays.stream(nums1).filter(num -> num == 0).count();
    final long zero2 = Arrays.stream(nums2).filter(num -> num == 0).count();
    if (zero1 == 0 && sum1 < sum2 + zero2)
      return -1;
    if (zero2 == 0 && sum2 < sum1 + zero1)
      return -1;
    return Math.max(sum1 + zero1, sum2 + zero2);
  }
}

/*
Visualization of the above code
 Let's visualize what this Java code does with a clear example and explanation.

---

🎯 Goal of the Code:

You're given two arrays `nums1` and `nums2`, each containing integers from 0 to 9 (inclusive).

* `0` means the value is missing, and you can replace it with any value from 1 to 9 (minimum being 1).
* Your task is to replace each 0 with 1 (the minimum possible) to minimize the total sum of both arrays.
* After this, both arrays must have equal total sum.
* You need to return the minimum possible equal sum of both arrays, or `-1` if it's not possible.

---

📌 Code Breakdown:

```
final long sum1 = Arrays.stream(nums1).asLongStream().sum();
final long sum2 = Arrays.stream(nums2).asLongStream().sum();
```

🟢 Compute sum of both arrays (including 0s).

```
final long zero1 = Arrays.stream(nums1).filter(num -> num == 0).count();
final long zero2 = Arrays.stream(nums2).filter(num -> num == 0).count();
```

🔵 Count number of `0`s in each array (which can be replaced with 1s).

```
if (zero1 == 0 && sum1 < sum2 + zero2) return -1;
if (zero2 == 0 && sum2 < sum1 + zero1) return -1;
```

🔴 Check if it's impossible to equalize the sum by replacing 0s with `1`. If one array has no 0s and can't catch up to the other even if the other replaces all 0s with 1, return `-1`.

```
return Math.max(sum1 + zero1, sum2 + zero2);
```

🟡 Compute final sum by replacing each 0 with 1 and take the larger sum (both will become equal).

---

✅ Example Walkthrough

Example 1:

```
nums1 = [0, 2, 3]  
nums2 = [1, 1, 4]
```

Step 1:
`sum1 = 0 + 2 + 3 = 5`
`sum2 = 1 + 1 + 4 = 6`

Step 2:
`zero1 = 1` (one zero in nums1)
`zero2 = 0` (no zero in nums2)

Step 3:
Check:

```
if (zero2 == 0 && sum2 < sum1 + zero1)
=> if (0 == 0 && 6 < 5 + 1) → false
```

So, continue.

Step 4:
Replace zero in `nums1` with 1 → new sum1 = 5 + 1 = 6
sum2 = 6

✅ Return `6` as both are equal now.

---

Example 2:

```
nums1 = [0, 1]
nums2 = [9, 9]
```

`sum1 = 1`, `zero1 = 1` → possible max sum = 1 + 1 = 2
`sum2 = 18`, `zero2 = 0`

Check:

```
if (zero1 == 0 && sum1 < sum2 + zero2) → false  
if (zero2 == 0 && sum2 < sum1 + zero1)
=> if (0 == 0 && 18 < 1 + 1) → false
```

But:

* `sum1 + zero1 = 2`
* `sum2 = 18`
  → 2 ≠ 18

❌ Can't make sums equal

🔴 Return `-1`

---

📌 Summary Table

| nums1      | nums2      | Return |
| ---------- | ---------- | ------ |
| [0, 2, 3] | [1, 1, 4] | 6      |
| [0, 1]    | [9, 9]    | -1     |
| [1, 2]    | [0, 0, 0] | 6      |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 3ms runtime which is the most used time in this problem.
*/

class Solution {
    public long minSum(int[] nums1, int[] nums2) {
        long c = 0;
        long s =0;
        long cc = 0;
        long ss =0;
        for(int i=0; i<nums1.length; i++){
            if(nums1[i] == 0){
                c++;
            }
            s += nums1[i];
        }
        for(int i=0; i<nums2.length ; i++){
            if(nums2[i] == 0){
                cc++;
            }
            ss += nums2[i];
        }
        if(c==0 && cc ==0){
            if(s != ss) return -1;
            else{
                return s;
            }
        }
        if(c==0 || cc ==0){
            if(c == 0){
                if(s< ss+cc) return -1;
            }
            else{
                if(ss< s+c) return -1;
            }
        }

        if(c +s > cc+ss){
            return c+s;
        }
        else{
            return cc+ss;
        }
    }
}

/*
Visualization of the above code
 Let's walk through and visualize this version of the `minSum` function with a breakdown and examples.

---

🎯 Purpose of the Code

This function is designed to take two integer arrays `nums1` and `nums2`, where:

* Elements can include `0` (placeholders for missing values).
* You can replace each `0` with 1 (the smallest possible non-zero digit).
* You must make the sum of both arrays equal.
* If it's impossible to do so, return `-1`.
* If possible, return the minimum possible equal sum after replacing all `0`s with `1`s.

---

🧠 Step-by-Step Breakdown

```
long c = 0, s = 0;
long cc = 0, ss = 0;
```

* `c` → count of zeros in `nums1`
* `s` → sum of non-zero elements in `nums1`
* `cc` → count of zeros in `nums2`
* `ss` → sum of non-zero elements in `nums2`

---

🌀 Step 1: Loop through both arrays

```
for (int i = 0; i < nums1.length; i++) {
    if (nums1[i] == 0) c++;
    s += nums1[i];
}
for (int i = 0; i < nums2.length; i++) {
    if (nums2[i] == 0) cc++;
    ss += nums2[i];
}
```

---

🛑 Step 2: Both arrays have no zeros

```
if (c == 0 && cc == 0) {
    if (s != ss) return -1;
    else return s;
}
```

* If both arrays have no `0`s:

  * If sums `s` and `ss` are equal → return that sum.
  * Otherwise, return `-1` because nothing can be changed.

---

⚠️ Step 3: One array has no zeros

```
if (c == 0 || cc == 0) {
    if (c == 0) {
        if (s < ss + cc) return -1;
    } else {
        if (ss < s + c) return -1;
    }
}
```

* If only one array has zeros:

  * You can only change the array with zeros.
  * Try replacing all 0s with 1 → see if that’s enough to make both sums equal.
  * If not, return `-1`.

---

✅ Step 4: Return the larger possible sum after replacing all 0s with 1

```
if (c + s > cc + ss) {
    return c + s;
} else {
    return cc + ss;
}
```

* Replace all zeros with `1` → final sum = count of 0s + original sum.
* Return the maximum of the two final sums, because both arrays must be adjusted to that.

---

✅ Example 1

```
nums1 = [0, 1, 2]
nums2 = [3, 1]
```

* `nums1`:

  * zeros = 1 → c = 1
  * sum = 3 → s = 3

* `nums2`:

  * zeros = 0 → cc = 0
  * sum = 4 → ss = 4

Now:

* `c == 1`, `cc == 0` → only `nums1` can be changed
* Can we reach `ss = 4` from `s = 3`?

  * Max possible with 1 zero → `3 + 1 = 4` ✅

Final result:
`max(3 + 1, 4) = 4` → ✅ return `4`

---

❌ Example 2

```
nums1 = [0, 0]
nums2 = [9, 9]
```

* `nums1`: c = 2, s = 0 → max sum = 2
* `nums2`: cc = 0, ss = 18

Now:

* `cc == 0` and `ss = 18`, `s + c = 2`
* Since 2 < 18 → ❌ impossible

Return `-1`

---

📊 Summary Table

| nums1      | nums2   | Return | Reason                 |
| ---------- | ------- | ------ | ---------------------- |
| [0, 1, 2] | [3, 1] | 4      | Balancing possible     |
| [0, 0]    | [9, 9] | -1     | Too much difference    |
| [1, 2]    | [1, 2] | 3      | Already equal          |
| [1, 2]    | [1, 3] | -1     | Can't change any value |

---
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem.
*/

class Solution {
    public long minSum(int[] nums1, int[] nums2) {
        long sum1 = 0;
        long zeroCount1 = 0;

        for (int n: nums1) {
            sum1 += (long) n;
            zeroCount1 += n == 0 ? 1 : 0;
        }

        long sum2 = 0;
        long zeroCount2 = 0;

        for (int n: nums2) {
            sum2 += (long) n;
            zeroCount2 += n == 0 ? 1 : 0;
        }

        long minSum1 = sum1+zeroCount1;
        long minSum2 = sum2+zeroCount2;

        if (minSum1<minSum2 && zeroCount1 == 0) {
            return -1;
        }

        if (minSum1>minSum2 && zeroCount2 == 0) {
            return -1;
        }

        return Math.max(minSum1, minSum2);
    }
}

/*
Visualization of the above code
 
