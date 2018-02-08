//////////////////////////////////////
///////   application class
/////////////////////////////////////
class App3 {

    public static void main(String[] args) {
        StackCalc mystack = new StackCalc(100); // 100 max capacity
        Queue myqueue = new Queue(100);         // 100 max capacity
        EasyIn easy = new EasyIn();
        String data;


        //////// Print Menu
        System.out.println();
        System.out.println("===============================================");
        System.out.println("==A RPN calculator for Formula Evaluations ====");
        System.out.println("===============================================");
        System.out.println("");


        while (true) {
            myqueue.display();   // display the content of the queue
            StackCalc.postfix2infix(myqueue); // display infix
            System.out.print(">");
            data = easy.readString(); // read 'data' from input keyboard
            if (data.equals("reset")) {
                mystack.resetVars(); // reset all variables
                myqueue.flush(); // flush the queue
            } else if (data.equals("run")) {
                // display variables
                mystack.displayVars();
                // continue taking input for variables until input is empty (i.e. user hit enter)
                while (!data.trim().isEmpty()) {
                    data = easy.readString();
                    // add/update variable
                    mystack.addVariable(data);
                }
                // run evaluation
                mystack.evaluatePostfix(myqueue);
            } else if (!data.equals("exit")) {
                // add data to the queue
                myqueue.enqueue(data); // add to queue
            } else break;
            System.out.println("==========================================================");
        }

        System.out.println("Thanks for using the RPN calculator");

    }
}

