import java.lang.*;
import java.io.*;
import java.util.*;


//////////////////////////////////////
///////   application class
/////////////////////////////////////
class App2 {

    public static void main(String[] args) {
        StackCalc mystack = new StackCalc(100); // 100 max capacity
        EasyIn easy = new EasyIn();
        String data;

        //////// Print Menu
        System.out.println();
        System.out.println("===============================================");
        System.out.println("========= A RPN calculator using Variables=====");
        System.out.println("===============================================");
        System.out.println("");


        while (true) {
            mystack.display();   // display the content of the stack
            System.out.print(">");
            data = easy.readString(); // read 'data' from input keyboard
            if (data.equals("list")) mystack.displayVars();      // list all current variables
            else if (data.equals("reset")) mystack.resetVars(); // reset all variables
            else if (!data.equals("exit")) mystack.rpnCommand(data); // mystack processes the RPN Command 'data'
            else break;
            System.out.println("==========================================================");
        }

        System.out.println("Thanks for using the RPN calculator");

    }
}

