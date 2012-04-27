<h1>Computational geometry in Java</h1>

The project contains both implementations and visualization tools for basic computational geometry algorithms in two-dimensional space. These algorithms are implemented in Java programming language and are visualized using the Swing libraries. This is a NetBeans 7.0.1 project. <br><br>

<h2>List of implemented algorithms:</h2>



<h3>Two segments intersection</h3>
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

<h3>Any segments intersection</h3>
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

<h3>Convex hull construction</h3>
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

<h3>Closest points pair</h3>
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

<h3>Polygon triangulation</h3>
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

<h3>Point set Delaunay triangulation</h3>
<table border = "0" width = "100%">
<tr>
<td valign = "top" width = "50%">
<ul>
 <li>Brute force "Edge flipping" algorithm
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://i080.radikal.ru/1204/5a/ea82371dc55e.png"/>
</td>
</tr>
<tr>
<td valign = "top" width = "50%">
<ul>
 <li>3D Terrain construction via VRML
</ul>
</td>
<td align = "right" width = "50%">
<img src = "http://s58.radikal.ru/i160/1204/96/217993ca4cbd.jpg"/>
</td>
</tr>
</table><br>

<h3>Halfplanes intersection</h3>
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

<h2>Reference books:</h2>
<table border = "0" width = "100%">
<td align = "center" valign = "bottom" width = "20%"><img src = "http://s019.radikal.ru/i609/1204/24/afb4964e38ad.jpg"/></td>
<td align = "center" valign = "bottom" width = "20%"><img src = "http://s019.radikal.ru/i603/1204/e1/5cf25625ecf9.jpg"/></td>
<td align = "center" valign = "bottom" width = "20%"><img src = "http://s006.radikal.ru/i214/1204/98/0369cc3394c5.jpg"/></td>
<td align = "center" valign = "bottom" width = "20%"><img src = "http://s019.radikal.ru/i616/1204/b7/1efa57c26710.jpg"/></td>
<td align = "center" valign = "bottom" width = "20%"><img src = "http://s019.radikal.ru/i642/1204/b5/6dd87557c44d.jpg"/></td>
</table>

* __"Introduction to Algorithms"__ by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
* __"Computational Geometry: Algorithms and Applications"__ by Mark de Berg, Otfried Cheong, Marc van Kreveld and Mark Overmars
* __"The Algorithm Design Manual"__ by Steven S. Skiena
* __"Programming Challenges"__ by Steven S. Skiena and Miguel Revilla
* __"Axioms and hulls"__ by Donald E. Knuth