# Implementing Absolute Cartesian Positioning into Autonomous

Date: 2/3/19

Members present: Frank Zheng, Anton Lin

Documented by: Frank Zheng

## Goal

Yesterday, we tested our absolute cartesian positioning system. Today, we implemented our positioning system into a full autonomous run. 

See _2/2 - Absolute Cartesian Positioning_ documented by me and _11/25 - Robot & Field Absolute Positioning_ documented by Anton for background on absolute positioning.

## (almost) Full Auto

Writing autonomous was relatively straightforward - after all, we already had all the functions. 

For sampling, we rotate to 3 different angles until we found the gold mineral. Pretty straightforward. Here's the best part: instead of specifying an exact distance to move forward and knock the mineral, we simply have the robot move to either `(48.0, -24.0)`, `(36.0, -36.0)`, or `(24.0, -48.0)`, which are the exact field locations of the minerals. This way, we don't have to do ANY calculations to determine how far and where we drive. We don't even have to worry about if our robot heading unexpectedly shifts.

The rest of the program was pretty much just:
```kotlin
field.moveTo(56.0, -56.0) // Depot
dt.rotateTo(-45.0) // Marker dumping position
/* DUMP MARKER */
field.moveTo(24.0, -66.0) // Move here to avoid knocking mineral closest to the wall
field.moveTo(-35.0, -66.0) // Crater
```

Although this seems really simple, it worked flawlessly, showing just how convenient absolute positioning is.

## Next Steps

We tested our autonomous about 10 times at different positions, and it consistently scored 50 points. However, we still have not implemented landing because our hardware is not yet ready. Unfortunately, landing could be a real problem, messing with our absolute positioning as the heading and coordinate of where the robot lands is somewhat unpredictable.