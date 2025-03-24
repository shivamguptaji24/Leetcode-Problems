/*
Daily Question :
3169 - Count Days Without Meetings

You are given a positive integer days representing the total number of days an employee is available for work (starting from day 1). You are also given a 2D array meetings of size n where, meetings[i] = [start_i, end_i] represents the starting and ending days of meeting i (inclusive).
Return the count of days when the employee is available for work but no meetings are scheduled.
Note: The meetings may overlap.
 

Example 1:

Input: days = 10, meetings = [[5,7],[1,3],[9,10]]

Output: 2

Explanation:

There is no meeting scheduled on the 4th and 8th days.

Example 2:

Input: days = 5, meetings = [[2,4],[1,3]]

Output: 1

Explanation:

There is no meeting scheduled on the 5th day.

Example 3:

Input: days = 6, meetings = [[1,6]]

Output: 0

Explanation:

Meetings are scheduled for all working days.

 

Constraints:

1 <= days <= 109
1 <= meetings.length <= 105
meetings[i].length == 2
1 <= meetings[i][0] <= meetings[i][1] <= days
*/

class Solution {
    public int countDays(int days, int[][] meetings) {
        // Sort meetings by start time
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));

        int occupiedDays = 0;
        int prevEnd = 0;

        for(int[] meeting : meetings) {
            int start = Math.max(prevEnd + 1, meeting[0]);  // Avoid counting overlap twice
            int end = meeting[1];

            if(start <= end) {  // If there are new occupied days
                occupiedDays += (end - start + 1);
                prevEnd = end;
            }
        }

        return days - occupiedDays;
    }
}

/*
Visualization of the above code
 Let's visualize the optimal approach step by step with an example.

---

Example
Input:
```
days = 10
meetings = [[5,7], [1,3], [9,10]]
```

---

Step 1: Sorting the Meetings
First, we sort the meetings based on the `start` day.

| Before Sorting | After Sorting |
|--------------------|------------------|
| `[[5,7], [1,3], [9,10]]` | `[[1,3], [5,7], [9,10]]` |

---

Step 2: Processing Meetings and Tracking Occupied Days
We initialize:
- `prevEnd = 0` (Keeps track of the last occupied day)
- `occupiedDays = 0` (Counts total occupied days)

Processing Each Meeting
| Meeting | Start (Max of prevEnd+1 and meeting start) | End | New Occupied Days | `occupiedDays` (Cumulative) | `prevEnd` (Updated) |
|---------|----------------------------------------------|-----|------------------|----------------|----------------|
| [1,3] | `max(0+1, 1) = 1` | `3` | `3 - 1 + 1 = 3` | `3` | `3` |
| [5,7] | `max(3+1, 5) = 5` | `7` | `7 - 5 + 1 = 3` | `6` | `7` |
| [9,10] | `max(7+1, 9) = 9` | `10` | `10 - 9 + 1 = 2` | `8` | `10` |

---

Step 3: Calculate Available Days
```
availableDays = days - occupiedDays
```
\[
10 - 8 = 2
\]

Final Answer: `2` available days.

---

Visual Representation of Days

We represent days from 1 to 10, marking meetings as `X` and available days as `_`:

```
1   2   3   4   5   6   7   8   9   10
X   X   X   _   X   X   X   _   X   X
```

Available days: `4` and `8` ✅

---

Summary
✔ Sorted the meetings to process them efficiently  
✔ Tracked occupied days dynamically to avoid iterating over large `days` values  
✔ Final count of available days is computed in `O(N log N)`  
*/

/*-------------------------------------------------------------------------------------------------------------------------*/

/*
This is the solution that takes only 15ms runtime which is the lowest time in this problem
*/

class Solution {
    public int countDays(int days, int[][] meetings) {
        int freeDays = days;
        TreeMap<Integer, Integer> meetingDays = new TreeMap<>();
        for (int[] meeting : meetings) {
            int start = meeting[0];
            int end = meeting[1];
            // System.out.printf("Meeting: (%d, %d)\n", start, end);
            int overlapDays = 0;

            Map.Entry<Integer, Integer> previousMeetingDays = meetingDays.floorEntry(start);
            if (previousMeetingDays != null && previousMeetingDays.getValue() >= start - 1) {
                if (previousMeetingDays.getValue() >= end) continue; // Complete overlap
                overlapDays = previousMeetingDays.getValue() - previousMeetingDays.getKey() + 1;
                // System.out.printf("Overlapping previous: (%d, %d)\n", previousMeetingDays.getKey(), previousMeetingDays.getValue());
                start = previousMeetingDays.getKey();
            }

            Map.Entry<Integer, Integer> nextMeetingDays = meetingDays.ceilingEntry(start + 1);
            while (nextMeetingDays != null && nextMeetingDays.getKey() <= end + 1) {
                meetingDays.remove(nextMeetingDays.getKey());
                overlapDays += nextMeetingDays.getValue() - nextMeetingDays.getKey() + 1;
                // System.out.printf("Overlapping next: (%d, %d)\n", nextMeetingDays.getKey(), nextMeetingDays.getValue());
                if (nextMeetingDays.getValue() >= end) {
                    end = nextMeetingDays.getValue();
                    break;
                }
                nextMeetingDays = meetingDays.ceilingEntry(start + 1);
            }

            meetingDays.put(start, end);
            freeDays -= (end - start + 1) - overlapDays;
            // System.out.printf("Result: (%d, %d)\n", start, end);
            // System.out.printf("FreeDays - ((%d - %d + 1) - %d) = %d\n\n", end, start, overlapDays, freeDays);
            if (freeDays == 0) break;
        }
        return freeDays;

    }
}

/*
Visualization of the above code
 Let's walk through the code execution step by step with an example.

---

Example
Input:
```
days = 10
meetings = [[5,7], [1,3], [9,10]]
```

---

Step 1: Initialize Variables
- `freeDays = 10` (Total days initially)
- `TreeMap<Integer, Integer> meetingDays = new TreeMap<>();`
  - This will store merged meeting ranges (start → end).

---

Step 2: Processing Each Meeting
We iterate over the `meetings` array one by one.

---

Processing Meeting [5,7]
- `start = 5`, `end = 7`
- Checking Overlapping Meetings
  - Previous (`floorEntry`): None
  - Next (`ceilingEntry`): None
- No overlap → Add to TreeMap
  ```
  meetingDays.put(5, 7);
  ```
- Reduce free days:  
  ```
  freeDays -= (7 - 5 + 1) = 10 - 3 = 7
  ```

TreeMap after processing [5,7]
```
{ 5 → 7 }
```

---

Processing Meeting [1,3]
- `start = 1`, `end = 3`
- Checking Overlapping Meetings
  - Previous (`floorEntry`): None
  - Next (`ceilingEntry`): None
- No overlap → Add to TreeMap
  ```
  meetingDays.put(1, 3);
  ```
- Reduce free days:
  ```
  freeDays -= (3 - 1 + 1) = 7 - 3 = 4
  ```

TreeMap after processing [1,3]
```
{ 1 → 3, 5 → 7 }
```

---

Processing Meeting [9,10]
- `start = 9`, `end = 10`
- Checking Overlapping Meetings
  - Previous (`floorEntry`): None
  - Next (`ceilingEntry`): None
- No overlap → Add to TreeMap
  ```
  meetingDays.put(9, 10);
  ```
- Reduce free days:
  ```
  freeDays -= (10 - 9 + 1) = 4 - 2 = 2
  ```

TreeMap after processing [9,10]
```
{ 1 → 3, 5 → 7, 9 → 10 }
```

---

Step 3: Return the Result
```
return freeDays; // 2
```

---

Final Visualization of Days

We represent days from 1 to 10, marking meetings as `X` and available days as `_`:

```
1   2   3   4   5   6   7   8   9   10
X   X   X   _   X   X   X   _   X   X
```
✅ Available days: `4` and `8`  
✅ Final Output: `2`

---

Summary
✔ Uses a TreeMap to efficiently merge meeting intervals  
✔ Tracks `freeDays` dynamically without iterating over all days  
✔ Final count of available days is computed in `O(N log N)`  
*/
