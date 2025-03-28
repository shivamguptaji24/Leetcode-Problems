/*
Daily Question :
2503 - Maximum Number Of Points From Grid Queries

You are given an m x n integer matrix grid and an array queries of size k.
Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:
If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
Otherwise, you do not get any points, and you end this process.
After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.
Return the resulting array answer.

Example 1:

Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
Output: [5,8,1]
Explanation: The diagrams above show which cells we visit to get points for each query.

Example 2:

Input: grid = [[5,2,1],[1,1,2]], queries = [3]
Output: [0]
Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
 

Constraints:

m == grid.length
n == grid[i].length
2 <= m, n <= 1000
4 <= m * n <= 105
k == queries.length
1 <= k <= 104
1 <= grid[i][j], queries[i] <= 106
*/

class Solution {
    public int[] maxPoints(int[][] grid, int[] queries) {
                int m = grid.length, n = grid[0].length;
        int[] answer = new int[queries.length];
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        // Sort queries with their original indices
        int[][] indexedQueries = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            indexedQueries[i] = new int[]{queries[i], i};
        }
        Arrays.sort(indexedQueries, Comparator.comparingInt(a -> a[0]));
        
        // Min-Heap to process grid cells in increasing order of values
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{grid[0][0], 0, 0});
        
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        
        int points = 0;
        int index = 0;
        
        while (index < queries.length) {
            int query = indexedQueries[index][0];
            int queryIndex = indexedQueries[index][1];

            // Expand cells that have a value < query
            while (!minHeap.isEmpty() && minHeap.peek()[0] < query) {
                int[] cell = minHeap.poll();
                int value = cell[0], row = cell[1], col = cell[2];
                points++; // A new cell is visited
                
                // Try moving in all 4 directions
                for (int[] dir : directions) {
                    int newRow = row + dir[0], newCol = col + dir[1];
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;
                        minHeap.offer(new int[]{grid[newRow][newCol], newRow, newCol});
                    }
                }
            }
            
            answer[queryIndex] = points; // Store the answer for this query
            index++;
        }
        
        return answer;
    }
}
