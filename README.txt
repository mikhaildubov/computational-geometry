The project contains simple implementations and visualization tools for basic computational geometry algorithms in Java. The current version includes:

* Two segments intersection determining
* Any segments intersection determining
   + Sweeping line algorithm - O(n*lg(n))
   + Naive algorithm - O(n^2)
* Convex hull construction
   + Graham's scan - O(n*lg(n))
   + Jarvis' march - O(n*h)
* Closest points pair determining 
   + Divide&Conquer - O(n*lg(n))
   + Naive algorithm - O(n^2) 
* Polygon triangulation
   + "Ear clipping" (Van Gogh) algorithm - O(n^3) / O(n^2)
   + Divide&Conquer - O(n^4)


The algorithms are described in the following books:

* "Introduction to Algorithms" by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein
* "Computational Geometry: Algorithms and Applications" by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars
