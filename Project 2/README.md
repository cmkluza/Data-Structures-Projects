# Project 2 - Fun with Numbers

### Description
This project familiarized me with stacks and queues by having me create a Reverse Polish Notation (RPN) calculator for complex numbers that performed various mathematical operations.

### Given Files
* [App1.java](src/App1.java)
* [App2.java](src/App2.java)
* [Variables.java](src/Variables.java)
* [EasyIn.java](src/EasyIn.java)
* [StdDraw.java](src/StdDraw.java)

### Modified Files
* [App3.java](src/App3.java)
* [App4.java](src/App4.java)
* [Complex.java](src/Complex.java)
* [Queue.java](src/Queue.java)
* [Stack.java](src/Stack.java)
* [StackCalc.java](src/StackCalc.java)

### Application Descriptions
#### [App 1](src/App1.java)
A live RPN calculator for complex numbers, where data lies on a stack. Each number entered is pushed, and each operation pops the necessary number of operators and pushes the result. Numbers must be entered individualls (e.g. press `1` then enter, `2` then enter, `+` then enter, to perform 1 + 2). Complex numbers are entered with a comma, as `Re,Im` (e.g. `1,2` would be `1 + 2i`).

#### [App 2](src/App2.java)
Similar to App 1 but adds functionality to store variables, e.g. `x = 1` or `y = 1,2` will create variables that you can use in operations. `list` will list all variables. `reset` removes existing variables.

#### [App 3](src/App3.java)
Utilizes a queue to store arguments in a formula as they're entered, and displays the formula in infix notation as it's being made. `run` executes the formula and returns the result.

#### [App 4](src/App4.java)
Similar to App 3, but uses `plot` to create a graph of the function. Only possible varaible is `x` and only real functions are considered.
