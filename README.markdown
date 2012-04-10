# Computational geometry in Java

The project contains both implementations and visualization tools for basic computational geometry algorithms in two-dimensional space. These algorithms are implemented in Java programming language and are visualized using the Swing libraries.<br><br>

## List of implemented algorithms:



1. __Two segments intersection determining__
 * Algorithm using the cross product - *__O(1)__*<br><br>
2. __Any segments intersection determining__
 * Sweeping line algorithm - *__O(n&#183;lg(n))__*
 * Naive algorithm - *__O(n<sup>2</sup>)__*<br><br>
3. __Convex hull construction__
 * Graham's scan - *__O(n&#183;lg(n))__*
 * Jarvis' march - *__O(n&#183;h)__*<br><br>
4. __Closest points pair determining__
 * Divide&Conquer - *__O(n&#183;lg(n))__*
 * Naive algorithm - *__O(n<sup>2</sup>)__*<br><br>
5. __Polygon triangulation__
 * "Ear clipping" (Van Gogh) algorithm (improved) - *__O(n<sup>2</sup>)__*
 * "Ear clipping" (Van Gogh) algorithm (naive) - *__O(n<sup>3</sup>)__*
 * Primitive Divide&Conquer algorithm - *__O(n<sup>4</sup>)__*<br><br>
6. __Polygon triangulation__
 * Brute force "Edge flipping" algorithm (improved)
7. __Halfplanes intersection__
 * Incremental algorithm - *__O(n<sup>2</sup>)__*<br><br><br>

## Books where the algorithms are described:
![CLRS](http://mitpress.mit.edu/images/products/books/9780262033848-medium.jpg "Introduction to algorithms. Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein")&nbsp;
![deBerg](http://www.cs.uu.nl/geobook/cover3small.jpg "Computational Geometry: Algorithms and applications. Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars")&nbsp;
![Skiena](http://www.cs.sunysb.edu/~algorith/video-lectures/cover2.jpg "Programming Challenges. Steven S. Skiena, Miguel Revilla")&nbsp;

* __"Introduction to Algorithms"__ by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clifford Stein
* __"Computational Geometry: Algorithms and Applications"__ by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars
* __"Programming Challenges"__ by Steven S. Skiena and Miguel Revilla