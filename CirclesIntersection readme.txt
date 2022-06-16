Project name: 	Circles Intersection 
Version kind: 	release
IDE used:	    NetBeans v7.3.1 (as of now IntelliJ Idea v2021.2)
Release Date:	2014-08-22 (last on 2022-06-07)
Author:		    Yanchenko Vlad

TODO
        - Unittests
        - Move all the hardcoded values into Settings.java
        - Maybe use java.util.prefs.Preferences instead of Settings ?
        - Maybe there is way to refine an algorithm ?

DESCRIPTION: 	
        This program draws a circles that have an intersected areas removed, having one of a circles attached to a mouse cursor.
        It was inspired by a hyperspace mode in a homeworld 1(2) PC game.

OPERATING:
        Redrawing a field of an arcs all over again.
        Moving one circle around with a mouse.
        Changing a radius of a circle attached to a mouse cursor.
        Entering, exiting full screen.
        Toggling gradient, arcs(circles) drawing mode, scaling, rotating, dragging.

USER INTERACTION:
        Keyboard:
		    - Space - Redraws and reinitiates the field of an arcs all over again
			- A     - Toggles antialiasing
			- F     - Toggles fullscreen
			- G     - Toggles gradient
			- M     - Toggles arcs(circles) drawing mode
			- CTRL + Mouse wheel
			        - Triggers arcs(circles) rotation mode (using mouse wheel)
			- SHIFT + Mouse wheel
			        - Triggers arcs(circles) rotation mode (using mouse wheel)
	    Mouse:
		    - Left mouse button
			    Redraws and reinitiates the field of an arcs all over again
		    - Wheel
			    - No any key down   - Increase / decrease a diameter of a regarded circle
			    - CTRL down         - Triggers arcs(circles) rotation mode (using mouse wheel)
			    - SHIFT down        - Triggers arcs(circles) rotation mode (using mouse wheel)
            - Moving a mouse around
                Redraws and reinitiates the field of an arcs all over again
            - Drag and drop
                Dragging all arcs(circles)

AMBITION:
    - Implement dragging of arcs
    - Implement an arcs(circles) moving in clockwise or counter-clockwise direction relatively to a selected circle
    by a mouse wheel move.
	- Move a timeSpent Jlabel to the left and make it gray. (declined, as of now, this label is located on a left-hand
	corner of screen and grayed)
	- Make it fullscreen. (implemented)
	- Replace a O(n2) loop that makes all the circles not "excluded" with a list.
	- Java millisecs for one frame. (implemented)
	- Add FPS counter. (cannot be done, since app doesn't draw canvas constantly, only when there was a mouse cursor or
	wheel moved or keyboard key pushed)

ISSUES:
    - 2022-06-17 Moving frame.dispose() out of if statement breaks toggling full screen mode. So code duplication cannot be refactored.
    - 2016-06-07 When drag and drop, right after drop, all arcs turn to a blue color (not only a "mouse-selected" one)
	- On a slow PCs there are exceptions described below.
	- 2016-01-27 NullPointerException in Arcs.java, AnglePairComparator class at line return Double.compare(oAnglePair1.angleBegin, oAnglePair2.angleBegin);
	- 2016-01-27 ConcurrentModificationException in Arcs.java, in checkIntersection() method, at line for (ArrayList<AnglePair> anglePairsListArray1 : anglePairsListArray).
	- 2014-08-22 AngleMeasurement project shows that arc of 359-360 degrees also performs such bug, this is why I suppose it is a Java's drawback and cannot fixed within a program, it should be changed in an API. Due to this reason, program could be set to a version 1.0
        - 2014-08-22 When a circle has a large radius, its arcs are not precisely shown. This is because of a drawArc method having an integer parameters for a beginAngle and endAngle. This might be resolved using Arc2D, link for it - http://stackoverflow.com/questions/14666018/java-graphics-drawarc-with-high-precision
        - 2014-06-06 Last arc of a circle doesn't fit to the first one. It's like it is shifted to the left for one pixel. That is distinctly seen on a picture. I have no idea on how to get a reason of why it is drawn like that. Due to this problem, I put this program a version 0.99 .


Releases

2022-06-07 v1.3
    - New mouse features
        Dragging, rotating, scaling the arcs(circles)

2022-06-03 v1.2
    - New keyboard keys
        A - toggle antialiasing
        F - toggle fullscreen
        G - toggle gradient for circles
        M - toggle mode for drawing ( 1 - bold arcs with dashed arcs, 2 - only bold arcs, 3 - only dashed circles )
        Space - toggle circles rescattering
    - Mouse cursor hid
    - Classes size and interdependencies decreased
    - Callbacks to paint component
    - Label for a time spent to render one frame (upper left-hand corner)
    - JLables replaced with drawString()

2016-01-27 v1.14
	- Drawing an arcs method drawArc() is replaced with a suitable method draw(new Arc2D(...)). It improved the look of the arc at their edges.
	- Changed a colors of an arcs and circles.
	- Removed some code that is obsolete.

2016-01-27 v1.13
	- Added an info about a spent time for 1 frame and a projected frames per second at the upper left hand corner.

2014-09-11 v1.12
	- Diameter of a regarded circle is now restricted to be less than 20 pixels.

2014-09-10 v1.11
	- Removed an issue of not appearing of a regarded circle at the mouse cursor.

2014-08-22 v1.1
	- Added an interactivity feature
            - Dragging a mouse around with one of a circles attached to its cursor.
            - Increasing / decreasing a radius of a circle attached to mouse cursor.        

2014-06-18 v1.0
	- Release version of a program with reinitiating and redrawing of a canvas.




Algorithm
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
    	O(n^2)

    	3.2 Creating new anglePair
    	O(1)

    	3.3 Pass through all the circles O(n)
    		3.3.1 Pass through all the circles
    			3.3.1.1 Check if they intersect
    				If so, add an arc, having an intersected areas excluded
    			If a circle is not intersected, make it visible thoroughly
    		3.3.2
    	O(n)



    4. Find out which of them intersect and put intersecting arcs to a separate array for each circle.



        //** List of circles that will not be regarded when checking an intersection.
        //** This list is populated with a circles that reside inside of any other circle
        public ArrayList<AnglePair> lstExclusion = new ArrayList<>();