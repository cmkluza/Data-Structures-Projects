import java.util.ArrayList;
import java.util.List;

//////////////////////////////////////////////////////////
//////// Extend the class Stack with Operation on complex
//////////////////////////////////////////////////////////
class StackCalc extends Stack {

    //////// variables (to be completed if needed)
    private List<Variables> variables = new ArrayList<Variables>();

    /////////////// Constructor
    public StackCalc(int maxSize) {
        super(maxSize);
    }

    /////////////////////////////////  
    ////////////// Operations
    /////////////////////////////////

    /* ============ BINARY OPERATIONS ============ */

    // addition
    public void add() {
        if (size() >= 2) {
            Complex z1 = (Complex) pop();
            Complex z2 = (Complex) pop();
            push(z1.plus(z2));
        }
    }

    // subtraction
    public void subtract(){
        if (size() >= 2) {
            Complex z1 = (Complex) pop();
            Complex z2 = (Complex) pop();
            push(z1.minus(z2));
        }
    }

    // multiplication
    public void multily() {
        if (size() >= 2) {
            Complex z1 = (Complex) pop();
            Complex z2 = (Complex) pop();
            push(z1.times(z2));
        }
    }

    // division
    public void divide() {
        if (size() >= 2) {
            Complex z1 = (Complex) pop();
            Complex z2 = (Complex) pop();
            push(z2.divides(z1));
        }
    }

    // power
    public void pow() {
        if (size() >= 2) {
            Complex z1 = (Complex) pop();
            Complex z2 = (Complex) pop();
            push(z1.pow(z2));
        }
    }

    /* ============ UNARY OPERATIONS ============ */

    // exponentiation
    public void exp() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.exp());
        }
    }

    // sine
    public void sin() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.sin());
        }
    }

    // cosine
    public void cos() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.cos());
        }
    }

    // tangent
    public void tan() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.tan());
        }
    }

    // square root
    public void sqrt() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.sqrt());
        }
    }

    // natural log
    public void log() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.log());
        }
    }

    // absolute value
    public void abs() {
        if (size() >= 1) {
            Complex z1 = (Complex) pop();
            push(z1.abs());
        }
    }

    /* ============ CONSTANTS ============ */

    // return pi number
    public void pi() {
        if (!isFull()) push(new Complex(Math.PI, 0));
    }

    // return e
    public void e() {
        if (!isFull()) push(new Complex(Math.E, 0));
    }

    // return i
    public void i() {
        if (!isFull()) push(new Complex(0, 1));
    }

    //////////////////////////////////////////
    //// Selection- RPN Operations on Stack
    /////////////////////////////////////////
    public void rpnCommand(String data) {
        // check to see if data is assigning a variable
        if (data.contains("=")) {
            // data is assigning a variable
            insertVars(data.split("=")[0].trim(),
                    Complex.valueOf(data.split("=")[1].trim()));
            return;
        }

        switch (data.toLowerCase()) {
            // binary operations
            case ("+"):
                add();
                break;
            case "-":
                subtract();
                break;
            case "*":
                multily();
                break;
            case "/":
                divide();
                break;
            case "pow":
            case "^":
                pow();
                break;

            // unary operations
            case "exp":
                exp();
                break;
            case "sqrt":
                sqrt();
                break;
            case "log":
                log();
                break;
            case "cos":
                cos();
                break;
            case "sin":
                sin();
                break;
            case "tan":
                tan();
                break;
            case "abs":
                abs();
                break;

            // constants
            case "e":
                e();
                break;
            case "pi":
                pi();
                break;
            case "i":
                i();
                break;

            // stack operations
            case "swap":
                swap();
                break;
            case "del":
                del();
                break;
            case "copy":
                copy();
                break;
            case "flush":
                flush();
                break;

            default: // data is not an operator, it is a complex number (or variable definition for App2,3,4)
                // check to see if it's a variable
                for (Variables var : variables) {
                    if (var.getName().equals(data)) {
                        // variable was found
                        push(var.getValue());
                        return;
                    }
                }
                // variable not found
                push(Complex.valueOf(data));
                break;
        }

    }

    //////////////////////////////////////////
    //// OTHER METHODS
    /////////////////////////////////////////    

    // insert a variable
    public void insertVars(String k, Complex v) {
        // make sure variable doesn't already exist; variables ARE case sensitive
        for (int i = 0; i < variables.size(); i++) {
            // if match is found, just update the value
            if (variables.get(i).getName().equals(k)) {
                variables.get(i).setValue(v);
                return;
            }
        }
        variables.add(new Variables(k, v));
    }

    // display variables
    public void displayVars() {
        if (variables.isEmpty()) {
            System.out.println("No Variable in the list!" +
                    "\nEnter/Update value for variables (if any) ... press enter to end:");
            return;
        }
        StringBuilder sb = new StringBuilder("Variables list:\n");
        for (Variables var : variables) {
            sb.append(var.getName()).append("=").append(var.getValue()).append("; ");
        }
        System.out.println(sb);
        System.out.println("Enter/Update value for variables (if any) ... press enter to end:");
    }

    // resets variables
    public void resetVars() {
        variables.clear();
    }

    // evaluates postfix
    public void evaluatePostfix(Queue inputQueue) {
        // create copy of queue so we don't remove original data
        Queue queue = new Queue(inputQueue);
        // cannot evaluate empty queue
        if (queue.isEmpty()) return;

        // continue evaluating until queue is empty
        while (!queue.isEmpty()) {
            rpnCommand((String) queue.peekFront());
            queue.dequeue();
        }
        System.out.println("Result is: " + pop());
    }

    // evaluates postfix and returns the result
    private Object evaluatePostfixReturn(Queue inputQueue) {
        // create copy of queue so we don't remove original data
        Queue queue = new Queue(inputQueue);
        // cannot evaluate empty queue
        if (queue.isEmpty()) return null;

        // continue evaluating until queue is empty
        while (!queue.isEmpty()) {
            rpnCommand((String) queue.peekFront());
            queue.dequeue();
        }
        return pop();
    }

    // converts postfix to infix and prints out the result - seems to be working
    public static void postfix2infix(Queue inputQueue) {
        // create replica queue to leave original data untouched
        Queue queue = new Queue(inputQueue);
        if (queue.isEmpty()) return;
        Stack stack = new StackCalc(100); // stack for storing stuff
        // while there's input in the queue, continue evaluating
        while (!queue.isEmpty()) {
            if (isOperator((String) queue.peekFront())) {
                String result = "";
                Object val1;
                Object val2;
                // item is an operator, form string representing appropriate operation
                switch (((String) queue.peekFront()).toLowerCase()) {
                    // binary operations - must have at least two values in stack, if not, ignore
                    case ("+"):
                        if (stack.size() < 2) return;
                        val2 = stack.pop();
                        val1 = stack.pop();
                        result = "(" + val1 + "+" + val2 + ")";
                        break;
                    case "-":
                        if (stack.size() < 2) return;
                        val2 = stack.pop();
                        val1 = stack.pop();
                        result = "(" + val1 + "-" + val2 + ")";
                        break;
                    case "*":
                        if (stack.size() < 2) return;
                        val2 = stack.pop();
                        val1 = stack.pop();
                        result = "(" + val1 + "*" + val2 + ")";
                        break;
                    case "/":
                        if (stack.size() < 2) return;
                        val2 = stack.pop();
                        val1 = stack.pop();
                        result = "(" + val1 + "/" + val2 + ")";
                        break;
                    case "pow":
                    case "^":
                        if (stack.size() < 2) return;
                        val2 = stack.pop();
                        val1 = stack.pop();
                        result = "(" + val1 + "^(" + val2 + "))";
                        break;

                    // unary operations - if stack is empty, ignore
                    case "exp":
                        if (stack.isEmpty()) return;
                        result = "e^(" + stack.pop() + ")";
                        break;
                    case "sqrt":
                        if (stack.isEmpty()) return;
                        result = stack.pop() + "^(1/2)";
                        break;
                    case "log":
                        if (stack.isEmpty()) return;
                        result = "log(" + stack.pop() + ")";
                        break;
                    case "cos":
                        if (stack.isEmpty()) return;
                        result = "cos(" + stack.pop() + ")";
                        break;
                    case "sin":
                        if (stack.isEmpty()) return;
                        result = "sin(" + stack.pop() + ")";
                        break;
                    case "tan":
                        if (stack.isEmpty()) return;
                        result = "tan(" + stack.pop() + ")";
                        break;
                    case "abs":
                        if (stack.isEmpty()) return;
                        result = "|" + stack.pop() + "|";
                        break;
                }
                stack.push(result);
            } else {
                // item is a value; check to see if it's a constant, otherwise just push object to stack
                if (isConstant((String) queue.peekFront())) {
                    switch (((String) queue.peekFront()).toLowerCase()) {
                        case "e":
                            stack.push("e");
                            break;
                        case "pi":
                            stack.push("pi");
                            break;
                        case "i":
                            stack.push(new Complex(0, 1));
                            break;
                    }
                } else {
                    // not a constant, check to see if number is in power of 10 form
                    if (((String) queue.peekFront()).contains("e")) {
                        String[] args = ((String) queue.peekFront()).split("e");
                        double num = Double.parseDouble(args[0]) * Math.pow(10, Double.parseDouble(args[1]));
                        stack.push(new Complex(num, 0));
                    } else {
                        stack.push(queue.peekFront());
                    }
                }
            }
            // remove item pushed
            queue.dequeue();
        }
        System.out.println("Infix: " + stack.pop());
    }

    // returns true if given string is an operator, false otherwise
    private static boolean isOperator(String str) {
        switch (str.toLowerCase()) {
            case ("+"):
            case "-":
            case "*":
            case "/":
            case "pow":
            case "^":
            case "exp":
            case "sqrt":
            case "log":
            case "cos":
            case "sin":
            case "tan":
            case "abs":
            case "swap":
            case "del":
            case "copy":
            case "flush":
                return true;
            default:
                return false;
        }
    }

    // returns true if given string is a constant, false otherwise
    private static boolean isConstant(String str) {
        switch (str.toLowerCase()) {
            case "e":
            case "i":
            case "pi":
                return true;
            default:
                return false;
        }
    }

    // processes a variable input
    public void addVariable(String data) {
        // if there's no equals sign, ignore
        if (!data.contains("=")) return;
        // parse and insert the variable
        insertVars(data.split("=")[0].trim(),
                Complex.valueOf(data.split("=")[1].trim()));
    }

    // plots data in a queue
    public void plot(Queue inputQueue, String data) {
        StdDraw.setCanvasSize(600, 600); // set canvas size
        // only possible variable is x, so if the queue does not contain x, ignore
        if (!inputQueue.contains("x")) return;
        // process data to get necessary values
        // assumption: data is in form lx LX nbp
        double lx, Lx, nbp;
        try {
            lx = Double.parseDouble(data.split(" ")[0]);
            Lx = Double.parseDouble(data.split(" ")[1]);
            nbp = Double.parseDouble(data.split(" ")[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        StdDraw.setXscale(lx, Lx); // set xscale
        StdDraw.setPenRadius(0.01); // seems to work well enough
        // determine step size
        double step = (Lx - lx) / nbp;
        // min and max values of y for scaling
        double min = lx, max = Lx;
        // loop through plotting
        for (double i = lx; i < Lx; i += step) {
            addVariable("x="+i);
            Object result = evaluatePostfixReturn(inputQueue);
            // somewhere along the line I started mixing Complex objects and doubles, so I check to make sure I'm
            // using the correct type
            if (result.getClass() == Double.class) {
                // check each value to get overall min and max for scaling
                if (min > (double) result) {
                    min = (double) result;
                } else if (max < (double) result) {
                    max = (double) result;
                }
                StdDraw.point(i, (double) result);
            } else {
                Complex z = Complex.valueOf((String) result);
                if (min > z.re()) {
                    min = z.re();
                } else if (max < z.re()) {
                    max = z.re();
                }
                StdDraw.point(i, z.re());
            }
        }
        // after plotting is done, correct yscale
        StdDraw.setYscale(min, max);
    }
}	



