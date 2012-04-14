# Computational geometry in Java

The project contains both implementations and visualization tools for basic computational geometry algorithms in two-dimensional space. These algorithms are implemented in Java programming language and are visualized using the Swing libraries. This is a NetBeans 7.0.1 project. <br><br>

## List of implemented algorithms:



### __Two segments intersection__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Algorithm using the cross product - <b><em>O(1)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s019.radikal.ru/i630/1204/d0/32c46068cad0.png"/>
</td>
</table><br>

### __Any segments intersection__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Sweeping line algorithm - <b><em>O(n&#183;lg(n))</em></b>
 <li>Naive algorithm - <b><em>O(n<sup>2</sup>)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s54.radikal.ru/i144/1204/81/406cd8516cc6.png"/>
</td>
</table><br>

### __Convex hull construction__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Graham's scan - <b><em>O(n&#183;lg(n))</em></b>
 <li>Jarvis' march - <b><em>O(n&#183;h)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s019.radikal.ru/i634/1204/9f/1aa976460cbe.png"/>
</td>
</table><br>

### __Closest points pair__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Divide&Conquer - <b><em>O(n&#183;lg(n))</em></b>
 <li>Naive algorithm - <b><em>O(n<sup>2</sup>)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s019.radikal.ru/i601/1204/05/bb15b09ddb9a.png"/>
</td>
</table><br>

### __Polygon triangulation__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>"Ear clipping" (Van Gogh) algorithm (improved) - <b><em>O(n<sup>2</sup>)</em></b>
 <li>"Ear clipping" (Van Gogh) algorithm (naive) - <b><em>O(n<sup>3</sup>)</em></b>
 <li>Primitive Divide&Conquer algorithm - <b><em>O(n<sup>4</sup>)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s019.radikal.ru/i630/1204/51/974473c1af22.png"/>
</td>
</table><br>

### __Point set Delaunay triangulation__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Brute force "Edge flipping" algorithm
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://i080.radikal.ru/1204/5a/ea82371dc55e.png"/>
</td>
</table><br>

### __Halfplanes intersection__
<table border = "0" width = "100%">
<td valign = "top" width = "50%">
<ul>
 <li>Incremental algorithm - <b><em>O(n<sup>2</sup>)</em></b>
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s019.radikal.ru/i618/1204/4e/8e367bf8d9be.png"/>
</td>
</table><br><br><br>

## Reference books:
![CLRS](http://websupport1.citytech.cuny.edu/faculty/dkahrobaei/MAT2540_files/image004.jpg "Introduction to algorithms. Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein")&nbsp;&nbsp;
![deBerg](http://www.cs.uu.nl/geobook/cover3small.jpg "Computational Geometry: Algorithms and applications. Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars")&nbsp;&nbsp;
![Skiena](http://www.cs.sunysb.edu/~algorith/images/cover-new.jpg "The Algorithm Design Manual. Steven S. Skiena")&nbsp;&nbsp;
![SkienaRevilla](http://www.cs.sunysb.edu/~algorith/video-lectures/cover2.jpg "Programming Challenges. Steven S. Skiena, Miguel Revilla")&nbsp;&nbsp;
![Knuth](http://www.g.dk/images/ml/9783540/9783540556114_ml.jpg "Axioms and Hulls. Donald E. Knuth")&nbsp;&nbsp;

* __"Introduction to Algorithms"__ by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
* __"Computational Geometry: Algorithms and Applications"__ by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars
* __"The Algorithm Design Manual"__ by Steven S. Skiena
* __"Programming Challenges"__ by Steven S. Skiena and Miguel Revilla
* __"Axioms and hulls"__ by Donald E. Knuth