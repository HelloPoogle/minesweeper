# Minesweeper
> Replica of the classic Microsoft original game written in Java

![MIT License](https://img.shields.io/badge/License-MIT-lightgrey.svg?style=for-the-badge)
![Version 1.0.0](https://img.shields.io/badge/Version-1.0.0-lightgrey.svg?style=for-the-badge)
![Stability Stable](https://img.shields.io/badge/Stability-Stable-lightgrey.svg?style=for-the-badge)

<p align="center" >
	<img src="assets/images/animation_1.gif" width="33%" />
	<img src="assets/images/animation_2.gif" width="33%" />
	<img src="assets/images/animation_3.gif" width="33%" />
</p>

### About
Minesweeper is game originally developed my Microsoft. The objective of the game is to find the empty squares while avoiding the mines. The faster you clear the board, the better your score. The rules in Minesweeper are simple: Uncover a mine and the game ends, uncover an empty square and you keep playing, uncover a number and it tells you how many mines lay hidden in the eight surrounding squares - information you use to deduce which nearby squares are safe to click. Right click to flag a potential mine or set a question mark. Press the smiley to reset.

### Authors
This project was created by **Rafael Grigorian** and **Marek Rybakiewicz**. It was originally assigned for _Project #02_ for the class _CS342 (Software Design)_ at the _University of Illinois at Chicago_.

### Building & Executing
Maven is used as the build system for this project. In order to build the source files, package them into a jar file, and preform unit testing, run `mvn package`.  In order to skip unit testing, simply run `mvn package -DskipTests`. In order to launch Minesweeper, run `java -cp target/minesweeper-1.0.0.jar com.minesweeper.MineSweeper`.
