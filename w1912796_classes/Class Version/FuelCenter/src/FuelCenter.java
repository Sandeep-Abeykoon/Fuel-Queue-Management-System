import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FuelCenter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int fuelStock = 6600;

        FuelQueue queue1 = new FuelQueue();
        FuelQueue queue2 = new FuelQueue();
        FuelQueue queue3 = new FuelQueue();
        FuelQueue queue4 = new FuelQueue();
        FuelQueue queue5 = new FuelQueue();
        FuelQueue temp = new FuelQueue();       // For sorting purpose
        CircularQueue circularQueue = new CircularQueue();  //Circular queue

        // Creating an array of fuel queue objects.
        FuelQueue[] queues = {queue1, queue2, queue3, queue4, queue5};

        mainLoop:
        while (true) {
            //User Menu
            System.out.println("""
                    \n
                    --------------------MENU-----------------------
                    \n\t100 / VFQ ----> View all Fuel Queues
                    \t101 / VEQ ----> View all Empty Queues
                    \t102 / ACQ ----> Add customer to a Queue
                    \t103 / RCQ ----> Remove a customer from a Queue
                    \t104 / PCQ ----> Remove a served customer
                    \t105 / VCS ----> View Customers Sorted in alphabetical order
                    \t106 / SPD ----> Store Program Data into file
                    \t107 / LPD ----> Load Program Data from file
                    \t108 / STK ----> View Remaining Fuel Stock
                    \t109 / AFS ----> Add Fuel Stock
                    \t110 / IFQ ----> View Queue Income
                    \t999 / EXT ----> Exit the Program
                    
                    ----------------------------------------------
                   """);

            System.out.println(fuelStock <= 500 ? "WARNING !!!... THE FUEL STOCK IS VERY LOW ( " + fuelStock + " Liters )\n" : "");
            // Getting user option
            System.out.print("Enter your option : ");
            String option = input.next();

            switch (option.toUpperCase()) {
                case "100", "VFQ" -> viewQueues(queues, false, input, circularQueue);
                case "101", "VEQ" -> viewQueues(queues, true, input, circularQueue);
                case "102", "ACQ" -> fuelStock = addCustomer(queues, input, fuelStock, circularQueue);
                case "103", "RCQ" -> fuelStock = removeCustomer(queues, input, fuelStock, circularQueue);
                case "104", "PCQ" -> remServedCustomer(queues, input,circularQueue);
                case "105", "VCS" -> sorting(queues, temp, input);
                case "106", "SPD" -> storeData(queues, fuelStock, input, circularQueue);
                case "107", "LPD" -> fuelStock = loadData(queues, fuelStock, input, circularQueue);
                case "108", "STK" -> fuelManage(fuelStock, false, input);
                case "109", "AFS" -> fuelStock = fuelManage(fuelStock, true, input);
                case "110", "IFQ" -> viewIncome(queues, input);
                case "999", "EXT" -> {
                    System.out.println("\nGood bye!!!...\nProgram terminated!!!.");
                    break mainLoop;
                }
                default -> System.out.println("\nPlease enter a valid option!!!....");
            }
        }
    }

    /**
     * This method takes an integer user input within a specific range and returns the int value
     * @param prompt   displays when getting the user input
     * @param input    is the Scanner object
     * @param endRange is the input validation upper range
     * @return returns the validated integer output
     */
    private static int intValidation(String prompt, Scanner input, int endRange) {
        int intValue;

        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                intValue = input.nextInt();
                input.nextLine();

                if (intValue >= 1 & intValue <= endRange) {
                    break;
                } else if (1 != endRange) {
                    System.out.println("Out of range!!!. Only values from " + 1 + " to " + endRange + " is allowed....\n");
                } else {
                    System.out.println("Invalid Input!!!. Only " + 1 + " is allowed\n");
                }
            } else {
                System.out.println("Invalid input!!! Only numbers are allowed....\n");
                input.next();
            }
        }
        return intValue;
    }

    /**
     * This method is to validate the String inputs
     * @param prompt Is the String variable which has the string to display when getting the input
     * @param input is a Scanner object
     * @return returns the validated string
     */
    private static String stringValidation(String prompt, Scanner input) {
        String stringValue = null;

        while (true) {
            try {
                System.out.print(prompt);       //Customer name cannot be completely numerical
                stringValue = input.next();
                input.nextLine();
                Integer.parseInt(stringValue);
                System.out.println("Invalid input (Input cannot be completely a number....\n");
            } catch (Exception e) {
                break;
            }
        }
        return stringValue;
    }

    /**
     * This method is to find the shortest queue and the first empty passenger position
     * @param array is a FuelQueue object array which contains all the fuel queues.
     * @return returns a int array which contains the queue index and the passenger index
     */
    private static int[] shortestQueue(FuelQueue[] array) {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (FuelQueue fuelQueue : array) {
            indexList.add(fuelQueue.firstEmptyPassenger());
        }
        int minLength = Collections.min(indexList);
        return new int[]{((indexList.indexOf(minLength))), minLength};
    }

    /**
     * This method prints all the fuel queues or "Empty" queues
     * @param array  is a FuelQueue object array which contains all the fuel queues.
     * @param filter A boolean parameter to check whether to filter the "Empty" queue slots or not
     * @param input  is a Scanner object
     */
    private static void viewQueues(FuelQueue[] array, Boolean filter, Scanner input, CircularQueue circularQueue) {
        int queueCount = 1;
        System.out.println(filter ? "\n\t\t\t\t\t\t---------All the Empty Queues available----------\n" : "\n\t\t\t\t\t\t----------All the Fuel Queues available----------\n");
        System.out.println(("-".repeat(100)));

        for (FuelQueue queue : array) {
            queue.viewQueue(filter, queueCount);
            queueCount++;
        }
        System.out.print("\n");
        circularQueue.viewQueue();
        System.out.println("\n");
        intValidation("Enter 1 to continue to the main menu : ", input, 1);
    }

    /**
     * This method is to add a new customer to a fuel queue
     * @param array     is a FuelQueue object array which contains all the fuel queues.
     * @param input     is a Scanner object
     * @param fuelStock is an integer value which holds the current fuel stock
     * @return returns the updated fuel stock
     */
    private static int addCustomer(FuelQueue[] array, Scanner input, int fuelStock, CircularQueue circularQueue) {
        System.out.println("\n-------- ADD A CUSTOMER --------\n");

        if ((array[((array.length) - 1)].isFull()) && circularQueue.isFull()) {
            System.out.println("All the queues and the Waiting queue is full at the moment. Please try again later...\n");

        }else{
            String firstName = stringValidation("Enter the customer first name : ", input).toUpperCase();
            String lastName = stringValidation("Enter the customer last name : ", input).toUpperCase();
            System.out.print("Enter the customer vehicle number : ");
            String vehicleNo = input.nextLine();
            int noOfLiters = intValidation("Enter the number of liters of fuel required : ", input, fuelStock);

            if(!(array[((array.length) - 1)].isFull())) {
                int[] shortestQueueArray = shortestQueue(array);
                FuelQueue shortestQueue = array[shortestQueueArray[0]];
                Passenger passenger = shortestQueue.passenger(shortestQueueArray[1]);
                passenger.setPassenger(firstName,lastName,vehicleNo,noOfLiters);
                System.out.println("The customer, " + firstName + " " + lastName + " is successfully added to Queue " + (shortestQueueArray[0] + 1) + "\n");
            }else{
                System.out.println("\nAll the queues are full. The passenger is added to the waiting queue...\n");
                circularQueue.enQueue(firstName,lastName,vehicleNo,noOfLiters);
            }
            fuelStock -= noOfLiters;
        }
        if (intValidation("1 ----> Go to main menu\n2 ----> Add another customer\n\tOption :  ", input, 2) == 2) {
            fuelStock = addCustomer(array, input, fuelStock, circularQueue);
        }
        return fuelStock;
    }

    /**
     * This method is to remove a customer from any location of any fuel queue
     * @param array     is a FuelQueue object array which contains all the fuel queues.
     * @param input     is a Scanner object
     * @param fuelStock is an integer value which holds the current fuel stock
     * @return returns the updated fuel stock
     */
    private static int removeCustomer(FuelQueue[] array, Scanner input, int fuelStock, CircularQueue circularQueue) {
        int location;
        System.out.println("\n-------- REMOVE A CUSTOMER --------\n");

        int queueNum = intValidation("Enter the Queue number (1/2/3/4/5) : ", input, 5);
        FuelQueue fuelQueue = array[queueNum - 1];

        if (fuelQueue.isEmpty()) {
            System.out.println("The Queue " + queueNum + " have no customers to remove....\n");
        } else {
            location = intValidation("Enter the the location of the passenger to be removed (1 - 6) : ", input, 6);
            Passenger passenger = fuelQueue.passenger(location);

            if (passenger.isEmpty()) {
                System.out.println("The location " + location + " was already empty....\n");
            } else {
                fuelQueue.removePassenger(location);
                System.out.println("The customer of the location " + location + " is successfully removed...\n");
                fuelStock = (Math.min((fuelStock + passenger.getLitresRequired()), 6600));
                waitingQueueTransfer(array, circularQueue);
            }
        }
        if (intValidation("1 ----> Main Menu\n2 ----> Remove another customer\n\tOption : ", input, 2) == 2) {
            removeCustomer(array, input, fuelStock, circularQueue);
        }
        return fuelStock;
    }

    /**
     * This method is to transfer passengers from the circular queue to the main queues
     * @param array is a FuelQueue object array which contains all the fuel queues.
     * @param circularQueue is a Circular queue object
     */
    private static void waitingQueueTransfer(FuelQueue[] array, CircularQueue circularQueue){
        if(!(circularQueue.isEmpty())) {
            int[] shortestQueueArray = shortestQueue(array);
            FuelQueue shortestQueue = array[shortestQueueArray[0]];
            Passenger PosPassenger = shortestQueue.passenger(shortestQueueArray[1]);
            circularQueue.deQueue(PosPassenger);
        }
    }

    /**
     * This method checks and removes the very first customer of a queue
     * @param array is a FuelQueue object array which contains all the fuel queues.
     * @param input is a Scanner object
     */
    private static void remServedCustomer(FuelQueue[] array, Scanner input, CircularQueue circularQueue) {
        int queueNum = intValidation("Enter the Queue number (1/2/3/4/5) : ", input, 5);

        //Assumes that the first customer of a queue is the served customer.
        if (array[queueNum - 1].isEmpty()) {
            System.out.println("No served customers can be found in queue " + queueNum + "\n");
        } else {
            array[queueNum - 1].addSale();
            array[queueNum - 1].removePassenger(1);
            System.out.println("The served customer of queue " + queueNum + " is removed\n");
            waitingQueueTransfer(array, circularQueue);
        }
        if (intValidation("1 ----> Main Menu\n2 ----> Remove another served customer\n\tOption : ", input, 2) == 2) {
            remServedCustomer(array, input, circularQueue);
        }
    }

    /**
     * This method is to sort the available customers of all the queues separately and display the sorted data
     * @param array is a FuelQueue object array which contains all the fuel queues.
     */
    private static void sorting(FuelQueue[] array, FuelQueue temp, Scanner input) {
        // Checking whether the arrays have customers before sorting (increases optimization)
        boolean sort = false;
        for (FuelQueue queue : array) {
            if (!(queue.isEmpty())) {
                sort = true;
                break;
            }
        }
        if (sort) {
            System.out.println("\n\t\t\t\t\t---------- All the Queues Sorted ----------\n");
            System.out.println(("-".repeat(100)));
            int queueCount = 1;
            for (FuelQueue queue : array) {
                if (queue.isEmpty()) {
                    queue.viewQueue(false, queueCount);
                } else {
                    for (int i = 1; i <= queue.passengers().length; i++) {
                        Passenger passenger = queue.passenger(i);
                        temp.passenger(i).setPassenger(passenger.getFirstName(), passenger.getLastName(), passenger.getVehicleNo(), passenger.getLitresRequired());
                    }
                    temp.sortQueue();
                    temp.viewQueue(false, queueCount);
                }
                queueCount++;
            }
        } else {
            System.out.println("There are no any customers in the Queues to be sorted...\n");
            intValidation("Enter 1 to continue to the main menu : ", input, 1);
        }
    }

    /**
     * This method extracts the data from the arrays and writes them to a text file
     * Writes the current fuel quantity to the text file
     * @param array     is a FuelQueue object array which contains all the fuel queues.
     * @param fuelStock is an integer value which holds the current fuel stock
     * @param input     is a Scanner object
     */
    private static void storeData(FuelQueue[] array, int fuelStock, Scanner input, CircularQueue circularQueue) {
        try {
            FileWriter fileWriter = new FileWriter("fuelCenter.txt");       //creating a file object
            // Writing the data in the arrays to the file
            for (FuelQueue queue : array) {
                queue.writeData(fileWriter);
            }
            //Writing the current fuel stock
            fileWriter.write(fuelStock + "\n");
            circularQueue.writeData(fileWriter);
            fileWriter.close();   // closing the file writer
            System.out.println("The data has been successfully written to the file....\n");
        } catch (IOException e) {
            System.out.println("Ooops!!!... an error occurred while  writing data to the file\n");
        }
        intValidation("Enter 1 to continue to the main menu : ", input, 1);
    }

    /**
     * This method reads the data from a text file and updates the arrays
     * updates the fuel stock to the fuelStock variable
     * @param array     is a FuelQueue object array which contains all the fuel queues.
     * @param fuelStock is an integer value which holds the current fuel stock
     * @param input     is a Scanner object
     * @return returns the new updated fuel stock to the program
     */
    private static int loadData(FuelQueue[] array, int fuelStock, Scanner input, CircularQueue circularQueue) {
        try {
            File fileObject = new File("fuelCenter.txt");
            Scanner fileReader = new Scanner(fileObject);

            while (fileReader.hasNextLine()) {
                // Updating the arrays from the file in the data
                for (FuelQueue queue : array) {
                    queue.loadData(fileReader);
                }
                fuelStock = Integer.parseInt(fileReader.nextLine());    // Updating the fuel stock from the file
                circularQueue.loadData(fileReader);
            }
            fileReader.close();
            System.out.println("The data has been successfully loaded from the file....\n");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file ");
        }
        intValidation("Enter 1 to continue to the main menu : ", input, 1);
        return fuelStock;
    }

    /**
     * This method is to view the current fuel quantity or add the fuel quantity
     * @param fuelStock is an integer value which holds the current fuel stock
     * @param add_fuel  is a boolean parameter which selects whether to add fuel or not
     * @param input     is a Scanner object
     * @return returns the updated fuel stock to the program
     */
    private static int fuelManage(int fuelStock, boolean add_fuel, Scanner input) {

        System.out.println("\nThe current remaining fuel quantity ----> " + fuelStock); //Current fuel stock
        // if the fuel level is less than or equal 500 Liters a warning message will be displayed
        System.out.println(fuelStock <= 500 ? "WARNING !!!... THE FUEL STOCK IS VERY LOW ( " + fuelStock + " Liters )" : "");

        if (add_fuel) {       // If fuel adding needs to be done
            int newFuel = intValidation("Enter the fuel stock to be added : ", input, 6600);
            if ((fuelStock + newFuel) <= 6600) {
                fuelStock += newFuel;
                System.out.println("The new fuel quantity has been successfully added....");
            } else {
                System.out.println("\nThe fuel quantity exeeds the tank capacity !!!. Do you want to try again?");
                if (intValidation("1 ----> No\n2 ----> Yes\n\tOption : ", input, 2) == 2) {
                    fuelManage(fuelStock, true, input);
                }
            }
        }
        intValidation("\nEnter 1 to continue to the main menu : ", input, 1);
        return fuelStock;
    }

    /**
     * This method is to display incomes of all the queues
     * @param array is a FuelQueue object array which contains all the fuel queues.
     * @param input is a Scanner object
     */
    private static void viewIncome(FuelQueue[] array, Scanner input) {
        System.out.println("\n\t\t---------- View Queue Incomes ----------\n");
        for(int i = 0; i < array.length; i++){
            System.out.println("The income of the Queue " + (i + 1) + " is : " + array[i].viewSale());
        }
        intValidation("\nEnter 1 to continue to the main menu : ", input, 1);
    }
}











