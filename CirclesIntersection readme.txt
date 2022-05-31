Project name: 	Circles Intersection 
Version kind: 	release
IDE used:	NetBeans v7.3.1
Release Date:	2014-08-22
Author:		Yanchenko Vlad


DESCRIPTION: 	
        This program draws a circles that have an intersected areas removed, having one of a circles attached to a mouse cursor.
        It has been made due to an inspiration of a hyperspace console of a homeworld 1(2) PC game. 

OPERATING:
        Redrawing a field of an arcs all over again.
        Moving one circle around with a mouse.
        Changing a radius of a circle attached to a mouse cursor.

USER INTERACTION:
        Keyboard:
		- Any key
			Redraws and reinitiates the field of an arcs all over again
	Mouse:
		- Left mouse button
			Redraws and reinitiates the field of an arcs all over again
		- Wheel
			Increasing / decreasing a diameter of a regarded circle
                - Moving a mouse around
                        Redraws and reinitiates the field of an arcs all over again, with a 

AMBITION:	
	- Move a timeSpent Jlabel to the left and make it gray.
	- Make it fullscreen.
	- Replace a O(n2) loop that makes all the circles not "excluded" with a list.
	- Java nanosecs

ISSUES:
	- On a slow PCs there are exceptions described below.
	- 2016-01-27 NullPointerException in Arcs.java, AnglePairComparator class at line return Double.compare(oAnglePair1.angleBegin, oAnglePair2.angleBegin);
	- 2016-01-27 ConcurrentModificationException in Arcs.java, in checkIntersection() method, at line for (ArrayList<AnglePair> anglePairsListArray1 : anglePairsListArray).
	- 2014-08-22 AngleMeasurement project shows that arc of 359-360 degrees also performs such bug, this is why I suppose it is a Java's drawback and cannot fixed within a program, it should be changed in an API. Due to this reason, program could be set to a version 1.0
        - 2014-08-22 When a circle has a large radius, its arcs are not precisely shown. This is because of a drawArc method having an integer parameters for a beginAngle and endAngle. This might be resolved using Arc2D, link for it - http://stackoverflow.com/questions/14666018/java-graphics-drawarc-with-high-precision
        - 2014-06-06 Last arc of a circle doesn't fit to the first one. It's like it is shifted to the left for one pixel. That is distinctly seen on a picture. I have no idea on how to get a reason of why it is drawn like that. Due to this problem, I put this program a version 0.99 .



Releases

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
	- Added an inreactivity feature
            - Dragging a mouse around with one of a circles attached to its cursor.
            - Increasing / decreasing a radius of a circle attached to mouse cursor.        

2014-06-18 v1.0
	- Release version of a program with reinitiating and redrawing of a canvas.