//////////////////////////////////////
///////   application class
/////////////////////////////////////
class App4 {

    public static void main(String[] args) {
        EasyIn easy = new EasyIn();
        StackCalc stack = new StackCalc(100);
        Queue queue = new Queue(100);
        String data;

        //////// Print Menu
        System.out.println();
        System.out.println("===============================================");
        System.out.println("==A RPN calculator for Function Plotting   ====");
        System.out.println("===============================================");
        System.out.println("");

        while (true) {
            queue.display();   // display the content of the queue
            StackCalc.postfix2infix(queue); // display infix
            System.out.print(">");
            data = easy.readString(); // read 'data' from input keyboard
            if (data.equals("reset")) {
                stack.resetVars(); // reset all variables
                queue.flush(); // flush the queue
            } else if (data.equals("plot")) {
                System.out.print("Enter values of: lx Lx nbp ");
                // get input representing what to plot
                data = easy.readString();
                // run plot
                stack.plot(queue, data);
            } else if (!data.equals("exit")) {
                // add data to the queue
                queue.enqueue(data); // add to queue
            } else break;
            System.out.println("==========================================================");
        }

    }
}
