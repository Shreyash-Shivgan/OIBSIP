# Task 5 - Precision Stopwatch

A precision Stopwatch application for Android built with Java, XML, and standard Handler loops.

## Features
- **High-Precision Timing**: Updates digits every 10 milliseconds using a Handler/Runnable callback system.
- **Dynamic Lapping**: Record split and cumulative times dynamically. Laps are logged and displayed in a RecyclerView with newest laps appearing first.
- **Lifecycle-Safe Timer State**: Saves values using `onSaveInstanceState` and restores time Buffs and states on configuration changes (like rotations) without losing track of running clocks.
- **Background Protection**: Temporarily pauses UI thread execution in `onPause` and resynchronizes with `SystemClock.elapsedRealtime()` during `onResume` to preserve battery while maintaining timing accuracy.
- **Clean Button States**: Buttons automatically transition between Start/Pause/Reset/Lap functions depending on timer activity.

## Screenshots

<p align="center">
  <img src="asset/s1.jpg" width="180" />
  <img src="asset/s2.jpg" width="180" />
  <img src="asset/s3.jpg" width="180" />
  <img src="asset/s4.jpg" width="180" />
</p>

## Tech Stack
- **Languages**: Java, XML
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Layout**: ConstraintLayout, LinearLayout, RecyclerView, CardView

## Installation
1. Download or clone this folder.
2. Open Android Studio and select **Open**.
3. Select the `Android-Task5-Stopwatch` directory.
4. Let Gradle sync completely.
5. Build and run.

## Future Improvements
- Add dark/light mode toggle.
- Support lap export to CSV file.
- Add target time countdown timer alerts.
