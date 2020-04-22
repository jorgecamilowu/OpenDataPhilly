# **OpenDataPhilly :** **Project Report**

*Authors*: Jorge Camilio Wu Zhang, Kelvin Cheung

The OpenDataPhilly portal makes over 300 data sets, applications and
APIs related to the city of Philadelphia available for free to
government officials, researchers, academics, and the general public so
that they can analyze and get an understanding of what is happening in
this vibrant city.

In this project, we worked with several types of data and data formats
in order to conduct data analysis and specifically hone our software
design skills. Highlights include:

  - > Implementing the strategy method to reduce code duplication

  - > Implementing singleton classes to control for a single class
    > instance

  - > Organize classes according to N-tier Architecture

  - > Applying a wide range of data structures

**Additional Feature**

In addition to computing average residential market value, total fines
per capita, etc … We were especially interested in computing the
<span class="underline">average parking ticket fine per person for
different categories of property value</span> by implementing the
following:

Categorize all the zipcode's total residential market value into low,
mid, and high tiers:

1.  > <span class="underline">low range</span>: less than or equal to
    > 250k

2.  > <span class="underline">mid range</span>: between 250k and 600k

3.  > <span class="underline">high range</span>: greater than 600k

For each category we calculated the ratio between fines committed over
its category total population and also the percentage of that total fine
amount over the three categories. What we found was quite interesting
and perhaps worth looking into:

![](imgs/avgfines.png)

The calculation uses data from the following data sets \[fields\]:

  - > Zip Code \[population\]

  - > Properties \[Property Value\]

  - > Parking Fines \[Ticket Amount\]

Firstly, we found the average property value for each zip code and
grouped them into low, medium or high based on market consensus for
these price ranges in Philadelphia. We then divided each category’s
total parking fines count by the total population of the zip codes
belonging to that category. The result is akin to “are richer
neighborhoods fined more often than poorer neighborhoods”

**Use of Data Structures**

Our OpenDataPhilly project uses several types of data structures. While
we’ve overloaded many methods to accept different argument param types,
we paid special attention to certain ones for efficiency’s sake.

Here are some data structures we paid special attention to and why we
chose them:

<table>
<thead>
<tr class="header">
<th><strong>Data Structure</strong></th>
<th><strong>Why we chose it</strong></th>
<th><strong>Classes</strong></th>
<th><strong>O(n)</strong></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<!-- <td><p><img src="imgs/treemap.png" style="width:0.1in;height:0.1in" /></p> -->
<p>TreeMap</p></td>
<td><ul>
<li><p>TreeMap preserves natural ordering of its elements</p></li>
<li><p>We wanted to display zip codes to the console in natural (sorted) order</p></li>
<li><p>We also needed to map zip-codes (key) to values</p></li>
<li><p>By using TreeMap, this allowed us to iterate through the keys in natural order, and display the corresponding value</p></li>
</ul>
<p><em>Alternative:</em> Use HashMap then sort the keySet. Then perform a lookup on the Map to get value, in sorted key order</p></td>
<td><ul>
<li><p>Processor</p></li>
<li><p>ConsoleWriter</p></li>
</ul>
<p>Methods:</p>
<ul>
<li><blockquote>
<p>calculateTotalFinesPerCapita</p>
</blockquote></li>
<li><blockquote>
<p>displayAns</p>
</blockquote></li>
</ul></td>
<td><ul>
<li><p><span class="underline">get:</span> log(n)</p></li>
<li><p><span class="underline">put:</span> log(n)</p></li>
</ul></td>
</tr>
<tr class="even">
<!-- <td><p><img src="imgs/linkedlist.png" style="width:0.1in;height:0.1in" /></p> -->
<p>LinkedList</p></td>
<td><ul>
<li><blockquote>
<p>LinkedList append is very fast, however get is very slow</p>
</blockquote></li>
<li><blockquote>
<p>While we did not need information on any specific property or fine, we wanted to preserve the OOP implementation of the property / fine itself.</p>
</blockquote></li>
<li><blockquote>
<p>By using LinkedList as a value for Map (key = zipCode), we were able to categorize each object by appending it to the correct hashcode</p>
</blockquote></li>
<li><blockquote>
<p>This zip code oriented design produces an organized Map. If there was a need to find a specific object belonging to a given zip, it would be very easy and fast to do so</p>
</blockquote></li>
</ul>
<p><em>Alternative:</em> There is no requirement to store the actual objects itself and categorize by zip code. However, since each object was already created, if we were to store them it would be by the fastest way possible (hence LinkedList). This design allows more flexibility in the future</p></td>
<td><ul>
<li><p>Processor</p></li>
</ul>
<p>Methods:</p>
<ul>
<li><p>none</p></li>
</ul></td>
<td><ul>
<li><p><span class="underline">get:</span></p></li>
</ul>
<p>O(n)</p>
<ul>
<li><p><span class="underline">addt:</span> O(1)</p></li>
</ul></td>
</tr>
<tr class="odd">
<!-- <td><p><img src="imgs/hashset.png" style="width:0.1in;height:0.1in" /></p> -->
<p>HashSet</p></td>
<td><ul>
<li><blockquote>
<p>HashSet enforces unique elements and has on average constant access time</p>
</blockquote></li>
<li><blockquote>
<p>We wanted to store allowed file formats in Main in order to route to appropriate reader:</p>
</blockquote></li>
</ul>
<p><img src="imgs/example.png" style="width:4.42708in;height:0.54167in" /></p>
<ul>
<li><blockquote>
<p>Since the file formats are unique and checking for file formats using .contains is very fast, we chose HashSet to hold allowable file formats</p>
</blockquote></li>
</ul>
<p><em>Alternative:</em> We considered using List to store file formats since Lists have .get in constant time with a known index. However, Lists have linear time for .contains to check for valid file format. Additionally, Lists do not enforce uniqueness. Because we do not need to retrieve the valid file formats, we decided HashSets are better here</p></td>
<td><ul>
<li><p>Main</p></li>
</ul>
<p>Methods:</p>
<ul>
<li><p>none</p></li>
</ul></td>
<td><ul>
<li><p><span class="underline">add:</span> O(1))</p></li>
<li><p><span class="underline">contains:</span> O(1)</p></li>
</ul></td>
</tr>
</tbody>
</table>
