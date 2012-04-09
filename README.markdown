# Computational geometry in Java

The project contains both implementations and visualization tools for basic computational geometry algorithms in Java.

## List of implemented algorithms:



* __Two segments intersection determining__
   + Algorithm using the cross product - *__O(1)__*
* __Any segments intersection determining__
   + Sweeping line algorithm - *__O(n&#183;lg(n))__*
   + Naive algorithm - *__O(n<sup>2</sup>)__*
* __Convex hull construction__
   + Graham's scan - *__O(n&#183;lg(n))__*
   + Jarvis' march - *__O(n&#183;h)__*
* __Closest points pair determining__
   + Divide&Conquer - *__O(n&#183;lg(n))__*
   + Naive algorithm - *__O(n<sup>2</sup>)__*
* __Polygon triangulation__
   + "Ear clipping" (Van Gogh) algorithm (improved) - *__O(n<sup>2</sup>)__*
   + "Ear clipping" (Van Gogh) algorithm (naive) - *__O(n<sup>3</sup>)__*
   + Primitive Divide&Conquer algorithm - *__O(n<sup>4</sup>)__*
* __Halfplanes intersection__
   + Incremental algorithm - *__O(n<sup>2</sup>)__*

## Books where the algorithms are described:
![CLRS](http://mitpress.mit.edu/images/products/books/9780262033848-medium.jpg "Introduction to algorithms. Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein")
![deBerg](http://www.cs.uu.nl/geobook/cover3small.jpg "Computational Geometry: Algorithms and applications. Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars")
![Skiena](http://www.cs.sunysb.edu/~algorith/video-lectures/cover2.jpg "Programming Challenges. Steven S. Skiena, Miguel Revilla")

* __"Introduction to Algorithms"__ by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein
* __"Computational Geometry: Algorithms and Applications"__ by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars
* __"Programming Challenges"__ by Steven S. Skiena and Miguel Revilla
