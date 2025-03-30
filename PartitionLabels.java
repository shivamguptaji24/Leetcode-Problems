/*
Daily Question :
763 - Partition Labels

You are given a string s. We want to partition the string into as many parts as possible so that each letter appears in at most one part. For example, the string "ababcc" can be partitioned into ["abab", "cc"], but partitions such as ["aba", "bcc"] or ["ab", "ab", "cc"] are invalid.
Note that the partition is done so that after concatenating all the parts in order, the resultant string should be s.
Return a list of integers representing the size of these parts. 

Example 1:

Input: s = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.

Example 2:

Input: s = "eccbbbbdec"
Output: [10]
 

Constraints:

1 <= s.length <= 500
s consists of lowercase English letters.
*/

class Solution {
    public List<Integer> partitionLabels(String s) {
        List<Integer> result = new ArrayList<>();
        int[] lastIndex = new int[26];  // Stores the last index of each character

        // Step 1: Store the last occurrence of each character
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        // Step 2: Traverse the string and form partitions
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']); // Update end to the farthest occurrence of any character in the current partition
            if (i == end) { // If current index reaches the end, partition ends
                result.add(end - start + 1);
                start = i + 1;
            }
        }

        return result;
    }
}

/*
Visualization of the above code
 Let's take the first example:  
Input: `"ababcbacadefegdehijhklij"`

---

Step 1: Compute Last Occurrence of Each Character
We traverse the string and store the **last index** where each character appears.

| Character | Last Occurrence |
|-----------|----------------|
| a         | 8              |
| b         | 5              |
| c         | 7              |
| d         | 14             |
| e         | 15             |
| f         | 11             |
| g         | 13             |
| h         | 19             |
| i         | 22             |
| j         | 23             |
| k         | 20             |
| l         | 21             |

---

Step 2: Traverse the String and Form Partitions
We maintain two pointers:  
- `start`: Beginning of the current partition  
- `end`: Farthest index we must reach for the current partition

Traversal Steps
| Index | Character | Last Occurrence | Current `end` | Action |
|--------|------------|----------------|--------------|--------|
| 0      | a          | 8              | 8            | Continue |
| 1      | b          | 5              | 8            | Continue |
| 2      | a          | 8              | 8            | Continue |
| 3      | b          | 5              | 8            | Continue |
| 4      | c          | 7              | 8            | Continue |
| 5      | b          | 5              | 8            | Continue |
| 6      | a          | 8              | 8            | Continue |
| 7      | c          | 7              | 8            | Continue |
| 8      | a          | 8              | 8 (Reached)  | Partition ends, add size = 9 |
| 9      | d          | 14             | 14           | Continue |
| 10     | e          | 15             | 15           | Continue |
| 11     | f          | 11             | 15           | Continue |
| 12     | e          | 15             | 15           | Continue |
| 13     | g          | 13             | 15           | Continue |
| 14     | d          | 14             | 15           | Continue |
| 15     | e          | 15             | 15 (Reached) | Partition ends, add size = 7 |
| 16     | h          | 19             | 19           | Continue |
| 17     | i          | 22             | 22           | Continue |
| 18     | j          | 23             | 23           | Continue |
| 19     | h          | 19             | 23           | Continue |
| 20     | k          | 20             | 23           | Continue |
| 21     | l          | 21             | 23           | Continue |
| 22     | i          | 22             | 23           | Continue |
| 23     | j          | 23             | 23 (Reached) | Partition ends, add size = 8 |

---

Final Output
```
[9, 7, 8]
```
Partitions:
1. `"ababcbaca"` → Length 9
2. `"defegde"` → Length 7
3. `"hijhklij"` → Length 8

---

Visual Representation
```
s = "ababcbacadefegdehijhklij"
     |---------| |-------| |--------|
        (9)        (7)       (8)
```
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem
*/

class Solution {
    public List<Integer> partitionLabels(String s) {
        int[] lastOccurrence = new int[26];
        int idx = 0;
        for (char c : s.toCharArray()) {
            lastOccurrence[c-'a'] = idx;
            idx++;
        }

        List<Integer> ans = new ArrayList<>();
        idx = 0;
        int st = 0, n = s.length();
        while (idx < n) {
            st = getMax(s,lastOccurrence,idx);
            ans.add(st-idx+1);
            idx = st+1;
        }
        return ans;
    }
    private int getMax(String str, int[] lastOccurence, int s) {
        int l = Math.max(s,lastOccurence[str.charAt(s)-'a']), m = l;
        while (s <= Math.max(m,l)) {
            m = Math.max(m,lastOccurence[str.charAt(s)-'a']);
            s++;
        }
        return m;
    }
}
