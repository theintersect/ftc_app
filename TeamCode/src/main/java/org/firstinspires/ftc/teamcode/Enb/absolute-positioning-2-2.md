# Absolute Cartesian Positioning

Date: 2/2/19

Members present: Frank Zheng, Anton Lin

Documented by: Frank Zheng

## Goal

Normally, our autonomous opmode consists of a series of rotations and drives. However, this strategy requires a lot of manual calculations and is generally imprecise, even if the rotations and drives are flawless.

To remedy this issue, we decided to implement an **absolute cartesian positioning** system in our autonomous code. This way, we simply tell the robot to move from one coordinate to another and the robot will do the calculations for us (i.e. move from (0, 0) to (18, 34)).

We started this system a few months ago. For some background, please read the ENB entry _Robot & Field Absolute Positioning_ documented by Anton.

## Testing

After creating the system detailed in Anton's ENB entry, we designed a few tests:

```kotlin
// Diamond
field
    .initialize(0.0, 0.0, angle=0.0)
    .moveTo(12.0, 0.0)
    .moveTo(0.0, 12.0)
    .moveTo(-12.0, 0.0)
    .moveTo(0.0, -12.0)
    .moveTo(0.0, 0.0)

// Our actual depot-side auto path
field
    .initialize(18.0, -18.0, angle=135.0)
    .moveTo(48.0, -24.0)
    .moveTo(56.0, -56.0)
```

As we tested, we ran into the following issues.

1. **The robot was always rotating until the front of the robot faced the next location.** Sometimes, this is okay, but other times, it would be faster to rotate until the _back_ of the robot was facing the next location. or instance, if our robot was facing in the positive x direction at (0, 0), and we wanted to move to (-12, 0), the robot would do a full 180 degree turn and drive 12 inches forward, when it could simply not rotate and drive 12 inches backward.

2. **We don't always want to turn the short way around.** This issue arises after fixing number 1. Sometimes, we want to get our robot to move a certain direction, and that may involve doing a full 180 degree turn.

3. **Robot heading and a cartesian coordinate system don't match up.** With the IMU we were using, counterclockwise is a negative heading, and clockwise is a positive heading. However, with cartesian coordinates, clockwise (an angle under the x-axis) is negative, and counterclockwise is positive.

4. **Starting at an angle offsets the IMU reading.** In autonomous, the robot is going to start facing a heading of 135°. However, the IMU reads 0°. This gets especially confusing considering problem #2.

None of these problems were particularly difficult. #3 and #4 was just a matter of taking out a piece of paper and doing the math. For #1 and #2, we slightly changed our `Field` class. Instead of using

```kotlin
fun moveTo(x: Double, y: Double) { ... }
```

We changed our function to

```kotlin
fun moveTo(x: Double, y: Double, direction: Direction) { ... }
```

This way, we specify whether or not we want to go _forward_ or _backward_ when we're moving to a position. So if we want our arm facing the front at the end, we specify `Direction.FORWARD` when using absolute positioning.

## Next Steps

We ran some final tests, and our code works fine. Of course, the next steps are to implement our absolute positioning system into our autonomous code and see if it is consistent enough.
