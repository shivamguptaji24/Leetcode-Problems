/*
Daily Question :
1123 - Lowest Common Ancestor Of Deepest Leaves

Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.
Recall that:
The node of a binary tree is a leaf if and only if it has no children
The depth of the root of the tree is 0. if the depth of a node is d, the depth of each of its children is d + 1.
The lowest common ancestor of a set S of nodes, is the node A with the largest depth such that every node in S is in the subtree with root A.

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4]
Output: [2,7,4]
Explanation: We return the node with value 2, colored in yellow in the diagram.
The nodes coloured in blue are the deepest leaf-nodes of the tree.
Note that nodes 6, 0, and 8 are also leaf nodes, but the depth of them is 2, but the depth of nodes 7 and 4 is 3.

Example 2:

Input: root = [1]
Output: [1]
Explanation: The root is the deepest node in the tree, and it's the lca of itself.

Example 3:

Input: root = [0,1,3,null,2]
Output: [2]
Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.
 

Constraints:

The number of nodes in the tree will be in the range [1, 1000].
0 <= Node.val <= 1000
The values of the nodes in the tree are unique.
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return dfs(root).node;
    }

    private Pair dfs(TreeNode node) {
        if (node == null) return new Pair(null, 0);
        
        Pair left = dfs(node.left);
        Pair right = dfs(node.right);

        if (left.depth == right.depth) {
            return new Pair(node, left.depth + 1);
        } else if (left.depth > right.depth) {
            return new Pair(left.node, left.depth + 1);
        } else {
            return new Pair(right.node, right.depth + 1);
        }
    }

    private static class Pair {
        TreeNode node;
        int depth;
        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}

/*
Visualization of the above code
 Let's visualize how the optimized code works with an example.

---

Example Tree
Input:  
```
       3
      / \
     5   1
    / \  / \
   6  2 0  8
     / \
    7   4
```
Tree Representation (as an array):  
`root = [3,5,1,6,2,0,8,null,null,7,4]`

Step-by-Step Execution
1. DFS Traversal (Bottom-Up Approach)
   - Traverse to the leaf nodes first.
   - Compute depth and find the LCA.

---

Recursive Calls Breakdown
Step 1: Leaf Nodes Processing
- `(6, depth = 1)`, `(0, depth = 1)`, and `(8, depth = 1)` are leaf nodes.
- `(7, depth = 1)` and `(4, depth = 1)` are also leaf nodes.

Step 2: Process Their Parents
- Node `2` has children `(7, depth = 1)` and `(4, depth = 1)`.
  - Both have equal depth → LCA is `2`, depth = `2`.

Step 3: Process Internal Nodes
- Node `5` has `(6, depth = 1)` and `(2, depth = 2)`.
  - Right subtree is deeper → LCA remains `2`, depth = `3`.

- Node `1` has `(0, depth = 1)` and `(8, depth = 1)`.
  - Both have equal depth → LCA is `1`, depth = `2`.

Step 4: Process Root
- Root `3` has `(5, depth = 3)` and `(1, depth = 2)`.
  - Left subtree is deeper → LCA remains `2`.

---

Final Output
```
Output: [2,7,4]
```
- The deepest nodes are `7` and `4`.
- The Lowest Common Ancestor (LCA) of these nodes is 2.

---

Flow of Recursive Calls (Tree Traversal)
```
dfs(3)
│
├── dfs(5)
│   ├── dfs(6) → (6, depth = 1)
│   ├── dfs(2)
│   │   ├── dfs(7) → (7, depth = 1)
│   │   ├── dfs(4) → (4, depth = 1)
│   │   └── return (2, depth = 2)
│   └── return (2, depth = 3)
│
├── dfs(1)
│   ├── dfs(0) → (0, depth = 1)
│   ├── dfs(8) → (8, depth = 1)
│   └── return (1, depth = 2)
│
└── return (2, depth = 3)
```

This efficiently finds the LCA of the deepest leaves in `O(N)` time!
*/
