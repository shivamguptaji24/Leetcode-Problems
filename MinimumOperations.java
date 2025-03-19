/*
Daily Question :
3191 - Minimum Operations to Make Binary Array Elements Equal to One

You are given a binary array nums.
You can do the following operation on the array any number of times (possibly zero):
Choose any 3 consecutive elements from the array and flip all of them.
Flipping an element means changing its value from 0 to 1, and from 1 to 0.
Return the minimum number of operations required to make all elements in nums equal to 1. If it is impossible, return -1.

Example 1:

Input: nums = [0,1,1,1,0,0]

Output: 3

Explanation:
We can do the following operations:

Choose the elements at indices 0, 1 and 2. The resulting array is nums = [1,0,0,1,0,0].
Choose the elements at indices 1, 2 and 3. The resulting array is nums = [1,1,1,0,0,0].
Choose the elements at indices 3, 4 and 5. The resulting array is nums = [1,1,1,1,1,1].

Example 2:

Input: nums = [0,1,1,1]

Output: -1

Explanation:
It is impossible to make all elements equal to 1. 

Constraints:

3 <= nums.length <= 105
0 <= nums[i] <= 1
*/

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int operations = 0;

        // Sliding window approach
        for(int i = 0; i < n; i++) {
            if(nums[i] == 0) {
                // If there are fewer than 3 remaining elements, impossible to fix
                if(i > n - 3) {
                    return -1;
                }

                // Perform flip operation
                nums[i] ^= 1;
                nums[i + 1] ^= 1;
                nums[i + 2] ^= 1;

                operations++;
            }
        }

        // Final check - ensure all elements are 1
        for(int num : nums) {
            if(num == 0) {
                return -1;
            }
        }

        return operations;
    }
}

/*
Visualization of the above code
  Let's visualize the given code step by step using the example:  

**Input:** `nums = [0, 1, 1, 1, 0, 0]`

---

### **Initial Array**
```
[ 0, 1, 1, 1, 0, 0 ]
```

---

### **Step 1: Encounter `0` at index 0**
- Since `nums[0] == 0`, perform the flip operation on indices **0, 1, and 2**.
- **Flip Operation:**  
```
nums[0] ^= 1 → 0 → 1  
nums[1] ^= 1 → 1 → 0  
nums[2] ^= 1 → 1 → 0
```

**Array after Step 1:**  
```
[ 1, 0, 0, 1, 0, 0 ]
```

**Operations Count:** `1`

---

### **Step 2: Encounter `0` at index 1**
- Since `nums[1] == 0`, perform the flip operation on indices **1, 2, and 3**.
- **Flip Operation:**  
```
nums[1] ^= 1 → 0 → 1  
nums[2] ^= 1 → 0 → 1  
nums[3] ^= 1 → 1 → 0
```

**Array after Step 2:**  
```
[ 1, 1, 1, 0, 0, 0 ]
```

**Operations Count:** `2`

---

Step 3: Encounter `0` at index 3
- Since `nums[3] == 0`, perform the flip operation on indices 3, 4, and 5.
- Flip Operation:  
```
nums[3] ^= 1 → 0 → 1  
nums[4] ^= 1 → 0 → 1  
nums[5] ^= 1 → 0 → 1
```

Array after Step 3:  
```
[ 1, 1, 1, 1, 1, 1 ]
```

Operations Count: `3`

---

Step 4: Final Check
- Now that the entire array contains only `1`s, no further operations are needed.

---

Final Output: `3`

---

Visual Summary

| Step | Array State            | Operations |
|------|------------------------|-------------|
| Start | `[0, 1, 1, 1, 0, 0]`   | 0           |
| Flip @ index 0 | `[1, 0, 0, 1, 0, 0]` | 1           |
| Flip @ index 1 | `[1, 1, 1, 0, 0, 0]` | 2           |
| Flip @ index 3 | `[1, 1, 1, 1, 1, 1]` | 3           |

✅ Final Output: `3`

---

Key Takeaway
This visualization shows how the sliding window efficiently tracks and flips elements with minimal operations. Each step directly follows the logic of flipping three consecutive elements when encountering a `0`.
*/

// More optimal code

class Solution {
    public int minOperations(int[] nums) {

        int count = 0;
        for(int i = 0;i < nums.length - 2;i++){

            if(nums[i] == 0){

                nums[i] = 1 - nums[i];
                nums[i + 1] = 1 - nums[i + 1];
                nums[i + 2] = 1 - nums[i + 2];
                count += 1;
            }
        }
        if(nums[nums.length - 1] == 0 || nums[nums.length - 2] == 0){

            return -1;
        }
        
        return count;
    }
}
