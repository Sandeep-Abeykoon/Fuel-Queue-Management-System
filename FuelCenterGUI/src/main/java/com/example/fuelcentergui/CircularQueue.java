package com.example.fuelcentergui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CircularQueue {
    private final Passenger[] cQueue;
    private final int passengerCount = 8;
    private int front = -1;
    private int rear = -1;

    public CircularQueue() {
        super();
        Passenger passenger1 = new Passenger();
        Passenger passenger2 = new Passenger();
        Passenger passenger3 = new Passenger();
        Passenger passenger4 = new Passenger();
        Passenger passenger5 = new Passenger();
        Passenger passenger6 = new Passenger();
        Passenger passenger7 = new Passenger();
        Passenger passenger8 = new Passenger();
        cQueue = new Passenger[]{passenger1, passenger2, passenger3, passenger4, passenger5, passenger6, passenger7, passenger8};
    }

    public void enQueue(String firstName, String lastName, String vehicleNumber, int fuel) {
        if ((this.front == -1) && (this.rear == -1)) {
            front = 0;
            rear = 0;
            cQueue[rear].setPassenger(firstName, lastName, vehicleNumber, fuel);
        } else if ((rear + 1) % passengerCount == front) {
            System.out.println("Sorry...the waiting queue is full at the moment");
        } else {
            rear = ((rear + 1) % passengerCount);
            cQueue[rear].setPassenger(firstName, lastName, vehicleNumber, fuel);
        }
    }

    public void deQueue(Passenger setPassenger) {
        Passenger passenger = cQueue[front];
        if (front == rear) {
            setPassenger.setFirstName(passenger.getFirstName());
            setPassenger.setLastName(passenger.getLastName());
            setPassenger.setVehicleNo(passenger.getVehicleNo());
            setPassenger.setLitresRequired(passenger.getLitresRequired());
            front = -1;
            rear = -1;
        } else {
            setPassenger.setFirstName(passenger.getFirstName());
            setPassenger.setLastName(passenger.getLastName());
            setPassenger.setVehicleNo(passenger.getVehicleNo());
            setPassenger.setLitresRequired(passenger.getLitresRequired());
            front = ((front + 1) % passengerCount);
        }
    }

    public boolean isFull() {
        return ((rear + 1) % passengerCount == front);
    }

    public boolean isEmpty() {
        return (this.front == -1) && (this.rear == -1);
    }

    public void viewQueue() {
        System.out.printf("%58s", "WAITING QUEUE \n\n");
        System.out.println("\t\t\t\tFIRST NAME\t\t\tSECOND NAME\t\t\tVEHICLE NO\t\t\tNO. OF LITERS REQUIRED");
        if (isEmpty()) {
            System.out.println("\nThe waiting queue is empty at the moment...\n");
        } else {
            int pointer = this.front;
            Passenger passenger = cQueue[pointer];
            int customerCount = 1;
            while (pointer <= rear) {
                System.out.print("PASSENGER " + customerCount + "\t\t ");
                System.out.printf("%-20s%-20s%-25s%-4d\n", passenger.getFirstName(), passenger.getLastName(), passenger.getVehicleNo(), passenger.getLitresRequired());
                pointer = ((pointer + 1) % passengerCount);
                customerCount++;
            }
        }
        System.out.println("\n" + ("-".repeat(100)));
    }

    public void writeData(FileWriter writer) {
        try {
            for (Passenger passenger : cQueue) {
                writer.write(passenger.getFirstName() + " " + passenger.getLastName() + " " + passenger.getVehicleNo() + " " + passenger.getLitresRequired() + "\n");
            }
            writer.write(String.valueOf(this.front));
            writer.write("\n");
            writer.write(String.valueOf(this.rear));
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(Scanner fileReader) {
        for (Passenger passenger : cQueue) {
            String[] word = fileReader.nextLine().split(" ");
            passenger.setPassenger(word[0], word[1], word[2], Integer.parseInt(word[3]));
        }
        this.front = Integer.parseInt(fileReader.nextLine());
        this.rear = Integer.parseInt(fileReader.nextLine());
    }
}