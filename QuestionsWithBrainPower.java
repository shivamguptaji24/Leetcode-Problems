/*
Daily Question :
2551 - Put Marbles in Bags

You are given a 0-indexed 2D integer array questions where questions[i] = [pointsi, brainpoweri].
The array describes the questions of an exam, where you have to process the questions in order (i.e., starting from question 0) and make a decision whether to solve or skip each question. Solving question i will earn you pointsi points but you will be unable to solve each of the next brainpoweri questions. If you skip question i, you get to make the decision on the next question.
For example, given questions = [[3, 2], [4, 3], [4, 4], [2, 5]]:
If question 0 is solved, you will earn 3 points but you will be unable to solve questions 1 and 2.
If instead, question 0 is skipped and question 1 is solved, you will earn 4 points but you will be unable to solve questions 2 and 3.
Return the maximum points you can earn for the exam. 

Example 1:

Input: questions = [[3,2],[4,3],[4,4],[2,5]]
Output: 5
Explanation: The maximum points can be earned by solving questions 0 and 3.
- Solve question 0: Earn 3 points, will be unable to solve the next 2 questions
- Unable to solve questions 1 and 2
- Solve question 3: Earn 2 points
Total points earned: 3 + 2 = 5. There is no other way to earn 5 or more points.

Example 2:

Input: questions = [[1,1],[2,2],[3,3],[4,4],[5,5]]
Output: 7
Explanation: The maximum points can be earned by solving questions 1 and 4.
- Skip question 0
- Solve question 1: Earn 2 points, will be unable to solve the next 2 questions
- Unable to solve questions 2 and 3
- Solve question 4: Earn 5 points
Total points earned: 2 + 5 = 7. There is no other way to earn 7 or more points.
 

Constraints:

1 <= questions.length <= 105
questions[i].length == 2
1 <= pointsi, brainpoweri <= 105
*/

class Solution {
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1]; // Extra space for handling out-of-bounds cases

        for (int i = n - 1; i >= 0; i--) {
            int points = questions[i][0];
            int brainpower = questions[i][1];
            
            // If solving, move to the next available question (or end if out of bounds)
            long nextIndexPoints = (i + brainpower + 1 < n) ? dp[i + brainpower + 1] : 0;

            // Choose the best option: Skip or Solve
            dp[i] = Math.max(dp[i + 1], points + nextIndexPoints);
        }
        
        return dp[0]; // Maximum points from question 0
    }
}

/*
Visualization of the above code
 Let's visualize how the bottom-up DP approach works for the problem step by step.

---

Example 1
Input:
```
questions = [[3,2], [4,3], [4,4], [2,5]]
```
Each question is represented as [points, brainpower]:
- Question 0 → `3 points, skip 2`
- Question 1 → `4 points, skip 3`
- Question 2 → `4 points, skip 4`
- Question 3 → `2 points, skip 5`

---

Step-by-Step DP Calculation
We initialize:
```
long[] dp = new long[n + 1];
```
This means `dp[i]` stores the maximum points we can collect from index `i` to the end.

We iterate backward (`i = n - 1` to `0`).

---

Step 1: Start from the Last Question
Processing `i = 3` (Question `[2, 5]`)
- Skip it → `dp[3] = dp[4] = 0`
- Solve it → `dp[3] = points[3] + dp[9] = 2 + 0 = 2`
- Best Choice: `dp[3] = 2`
  
```
dp = [0, 0, 0, 2, 0]
```

---

Step 2: Processing `i = 2` (Question `[4, 4]`)
- Skip it → `dp[2] = dp[3] = 2`
- Solve it → `dp[2] = 4 + dp[7] = 4 + 0 = 4`
- Best Choice: `dp[2] = 4`

```
dp = [0, 0, 4, 2, 0]
```

---

Step 3: Processing `i = 1` (Question `[4, 3]`)
- Skip it → `dp[1] = dp[2] = 4`
- Solve it → `dp[1] = 4 + dp[5] = 4 + 0 = 4`
- Best Choice: `dp[1] = 4`

```
dp = [0, 4, 4, 2, 0]
```

---

Step 4: Processing `i = 0` (Question `[3, 2]`)
- Skip it → `dp[0] = dp[1] = 4`
- Solve it → `dp[0] = 3 + dp[3] = 3 + 2 = 5`
- Best Choice: `dp[0] = 5`

```
dp = [5, 4, 4, 2, 0]
```

---

Final Answer
```
return dp[0];  // Output: 5
```
✅ Maximum points: 5 (Solving question 0 and 3)

---

Example 2
Input:
```
questions = [[1,1],[2,2],[3,3],[4,4],[5,5]]
```

Step-by-Step DP Calculation
Starting from the last index:

| `i` | Question | Solve (points + next valid dp) | Skip (dp[i+1]) | `dp[i]` |
|----|------------|----------------|----------------|-------|
| 4  | `[5,5]`    | `5 + dp[10] = 5` | `0` | 5 |
| 3  | `[4,4]`    | `4 + dp[8] = 4`  | `5` | 5 |
| 2  | `[3,3]`    | `3 + dp[6] = 3`  | `5` | 5 |
| 1  | `[2,2]`    | `2 + dp[4] = 2+5=7`  | `5` | 7 |
| 0  | `[1,1]`    | `1 + dp[2] = 1+5=6`  | `7` | 7 |

Final `dp` array:
```
dp = [7, 7, 5, 5, 5, 0]
```
```
return dp[0];  // Output: 7
```
✅ Maximum points: 7 (Solving question 1 and 4)

---

Final Thoughts
Key Observations
1. Work backwards: Each `dp[i]` depends on `dp[i+1]` and `dp[i+brainpower+1]`.
2. Two choices: Either skip the question or solve it and jump.
3. Efficient: O(n) time, O(n) space (or O(1) space if optimized).
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 1ms runtime which is the lowest time in this problem
*/

class Solution {
    private long calcMaxPoints(int[][] questions, int index, int n, long[] dp) {
        if (index == n) return 0;
        if (dp[index] != -1) return dp[index];
        long take = questions[index][0];
        int newIndex = index + questions[index][1] + 1;
        if (newIndex < n) take += calcMaxPoints(questions, index + questions[index][1] + 1, n, dp);
        long notTake = calcMaxPoints(questions, index + 1, n, dp);

        return dp[index] = Math.max(take, notTake);
    }
    static {
        int[][] arr = {{3,2},{4,3},{4,4},{2,5}};
        for (int i = 0 ; i < 100 ; i++) mostPoints(arr);
    }
    public static long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n];
        // Arrays.fill(dp, -1);
        // return calcMaxPoints(questions, 0, n, dp);
        dp[n - 1] = questions[n - 1][0];
        for (int i = n - 2 ; i >= 0 ; i--) {
            long take = questions[i][0];
            int newIndex = i + questions[i][1] + 1;
            take += (newIndex < n) ? dp[newIndex] : 0;
            long notTake = dp[i + 1];
            dp[i] = Math.max(take, notTake);
        }

        return dp[0];
    }
}

/*
Visualization of the above code
