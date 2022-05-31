TODO
    - Unittests
    - Maybe use java.util.prefs.Preferences instead of Settings ?

ALGORITHM
One is to create an app that shows an intersected circles that have their intersected areas (arcs) removed.

1. Create an array of a following struct:
[
	1. Coordinates of a circle.
	2. Array of a circles that intersect with such circle.
	3. Array of a beginning angle and ending angle of an intersecting circles, so called anglePair.
]

2. Create an array of objects of that struct.

3. Check intersections
	
	3.1 Check if there are circles thoroughly immersed inside other circles and mark them "Excluded". This is done for these circles coudn't be regarded when checking intersection.
	�(n^2)

	3.2 Creating new anglePair
	�(1)

	3.3 Pass through all the circles �(n)
		3.3.1 Pass through all the circles
			3.3.1.1 Check if they intersect
				If so, add an arc, having an intersected areas excluded
			If a circle is not intersected, make it visible thoroughly
		3.3.2 
	�(n)



4. Find out which of them intersect and put intersecting arcs to a separate array for each circle.



    //** List of circles that will not be regarded when checking an intersection.
    //** This list is populated with a circles that reside inside of any other circle
    public ArrayList<AnglePair> lstExclusion = new ArrayList<>();