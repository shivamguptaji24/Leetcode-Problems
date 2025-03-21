/*
Daily Question :
2115 - Find All Possible Recipes from Given Supplies

You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes.
You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.
Return a list of all the recipes that you can create. You may return the answer in any order.
Note that two recipes may contain each other in their ingredients.

Example 1:

Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".

Example 2:

Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".

Example 3:

Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".
 

Constraints:

n == recipes.length == ingredients.length
1 <= n <= 100
1 <= ingredients[i].length, supplies.length <= 100
1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
All the values of recipes and supplies combined are unique.
Each ingredients[i] does not contain any duplicate values.
*/

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();
        Set<String> available = new HashSet<>(Arrays.asList(supplies));
        Queue<String> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();

        // Step 1: Initialize graph and in-degree count
        for(int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            inDegree.put(recipe, ingredients.get(i).size());  // Number of ingredients needed

            for(String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipe);
            }
        }

        // Step 2: Add all initial supplies to the queue
        queue.addAll(available);

        // Step 3: Process the queue (Topological Sorting)
        while(!queue.isEmpty()) {
            String curr = queue.poll();

            // If curr is a recipe, add to result
            if(inDegree.containsKey(curr)) {
                result.add(curr);
            }

            // Process dependent recipes
            if(graph.containsKey(curr)) {
                for(String next : graph.get(curr)) {
                    inDegree.put(next, inDegree.get(next) - 1);
                    if(inDegree.get(next) == 0) {
                        queue.offer(next);  // Recipe can now be made
                    }
                }
            }
        }

        return result;
    }
}

/*
Visualization of the above code
 Here's a visualization of the topological sorting approach used in the code:

---

Example Input
```
recipes = ["bread", "sandwich", "burger"];
ingredients = [["yeast", "flour"], ["bread", "meat"], ["sandwich", "meat", "bread"]];
supplies = ["yeast", "flour", "meat"];
```

---

Step 1: Build Graph and In-Degree Map
We represent the dependencies using a graph and an in-degree map.

| Ingredient/Recipe | Dependencies (in-degree) | Recipes Dependent on It |
|------------------|------------------------|-------------------------|
| `yeast`         | 0                        | `bread`                 |
| `flour`         | 0                        | `bread`                 |
| `meat`          | 0                        | `sandwich`, `burger`    |
| `bread`         | 2                        | `sandwich`, `burger`    |
| `sandwich`      | 2                        | `burger`                |
| `burger`        | 3                        | None                    |

Graph Representation (Directed Edges):
```
yeast  → bread
flour  → bread
bread  → sandwich
meat   → sandwich
bread  → burger
sandwich → burger
meat   → burger
```

---

Step 2: Initialize Queue with Available Supplies
Initially available supplies: `["yeast", "flour", "meat"]`  
We enqueue them.

Queue (BFS Processing):  
```
[yeast, flour, meat]
```

---

Step 3: Process the Queue
We dequeue elements and process dependencies.

Processing `yeast` and `flour`:
- `yeast` and `flour` both lead to `bread`.
- Since `bread` had an initial `inDegree = 2`, after removing both `yeast` and `flour`, `inDegree[bread] = 0`.
- `bread` can now be created! ✅  
- Enqueue `bread`.

Queue after processing:
```
[meat, bread]
```

Processing `meat`:
- `meat` leads to `sandwich` and `burger`.
- `inDegree[sandwich] = 2 - 1 = 1`
- `inDegree[burger] = 3 - 1 = 2`
- No new recipes can be created yet.

Queue remains:
```
[bread]
```

Processing `bread`:
- `bread` is needed for `sandwich` and `burger`.
- `inDegree[sandwich] = 1 - 1 = 0`** → **`sandwich` can be created! ✅  
- Enqueue `sandwich`.
- `inDegree[burger] = 2 - 1 = 1`

Queue after processing:
```
[sandwich]
```

Processing `sandwich`:
- `sandwich` is needed for `burger`.
- `inDegree[burger] = 1 - 1 = 0` → `burger` can be created! ✅  
- Enqueue `burger`.

Queue after processing:
```
[burger]
```

Processing `burger`:
- No dependencies left.

---

Final Output
All recipes that can be created:
```
["bread", "sandwich", "burger"]
```

---

Graph Evolution (Visualization)
Here’s a step-by-step visualization:

Graph Before Processing
```
(yeast)    (flour)    (meat)
   ↓          ↓         ↓
  bread  → sandwich → burger
             ↑       ↑    ↑
             └───────┘    |
                          └───┘
```
> `yeast`, `flour`, and `meat` are available.

---

After Processing `yeast` and `flour`
```
(yeast)    (flour)    (meat)
   ✅         ✅         ↓
  bread  → sandwich → burger
             ↑       ↑    ↑
             └───────┘    |
                          └───┘
```
> `bread` is now available.

---

After Processing `bread`
```
(yeast)    (flour)    (meat)
   ✅         ✅         ✅
  bread  → sandwich → burger
              ✅      ↑    ↑
                     └────┘
```
> `sandwich` is now available.

---

After Processing `sandwich`
```
(yeast)    (flour)    (meat)
   ✅         ✅         ✅
  bread  → sandwich → burger
              ✅        ✅
```
> `burger` is now available.

---

Final Takeaways
✅ Graph traversal ensures dependencies are resolved in the right order.  
✅ Breadth-First Search (BFS) is used to process elements layer by layer.  
✅ Efficiently handles recipes that depend on each other.  
*/
