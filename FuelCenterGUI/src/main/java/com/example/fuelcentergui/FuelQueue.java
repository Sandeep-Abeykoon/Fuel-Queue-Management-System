package com.example.fuelcentergui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FuelQueue {
    private static final double fuelPrice = 430.0;
    private final Passenger firstPassenger;
    private final Passenger sixthPassenger;
    private final Passenger[] passengers;
    private double income;


    //Constructor
    public FuelQueue() {
        super();
        this.firstPassenger = new Passenger();
        Passenger secondPassenger = new Passenger();
        Passenger thirdPassenger = new Passenger();
        Passenger fourthPassenger = new Passenger();
        Passenger fifthPassenger = new Passenger();
        this.sixthPassenger = new Passenger();
        this.passengers = new Passenger[]{firstPassenger, secondPassenger, thirdPassenger, fourthPassenger, fifthPassenger, sixthPassenger};
        this.income = 0;
    }

    public Passenger passenger(int passenger) {
        if (passenger >= 1 && passenger <= 6) {
            return passengers[passenger - 1];
        } else {
            throw new IllegalArgumentException("Customer out of bound");
        }
    }

    public Passenger[] passengers() {
        return passengers;
    }

    public void viewQueue(boolean filter, int queueCount) {

        System.out.printf("%58s", "QUEUE " + queueCount + "\n\n");
        System.out.println("\t\t\t\tFIRST NAME\t\t\tSECOND NAME\t\t\tVEHICLE NO\t\t\tNO. OF LITERS REQUIRED");
        int customerCount = 1;
        for (Passenger passenger : this.passengers) {
            System.out.print("PASSENGER " + customerCount + "\t\t ");
            if (filter) {
                System.out.printf(passenger.getFirstName().equals("empty") ? "\t\t\t\t\t\t\tE\tM\tP\tT\tY\n" : "\n");
            } else {
                System.out.printf("%-20s%-20s%-25s%-4d\n", passenger.getFirstName(), passenger.getLastName(), passenger.getVehicleNo(), passenger.getLitresRequired());
            }
            customerCount++;
        }
        System.out.println("\n" + ("-".repeat(100)));
    }

    public boolean isFull() {
        return !this.sixthPassenger.isEmpty();
    }

    public boolean isEmpty() {
        return firstPassenger.isEmpty();
    }

    public int firstEmptyPassenger() {
        int index;
        for (index = 0; index < this.passengers.length; index++) {
            if (passengers[index].isEmpty()) {
                break;
            }
        }
        return (index + 1);
    }

    public void removePassenger(int passenger) {
        shiftDown(passenger);
    }

    public void shiftDown(int startShiftIndex) {
        for (int i = startShiftIndex; i < (passengers.length); i++) {
            passenger(i).setFirstName(passenger(i + 1).getFirstName());
            passenger(i).setLastName(passenger(i + 1).getLastName());
            passenger(i).setVehicleNo(passenger(i + 1).getVehicleNo());
            passenger(i).setLitresRequired(passenger(i + 1).getLitresRequired());
        }
        sixthPassenger.removePassenger();
    }

    public void sortQueue() {
        for (int i = 0; i < (passengers.length - 1); i++) {
            for (int j = (i + 1); j < (passengers.length); j++) {
                Passenger LPassenger = passengers[i];   //Lower passenger
                Passenger HPassenger = passengers[j];   //Higher Passenger
                if ((LPassenger.getFirstName().compareTo(HPassenger.getFirstName())) > 0) {
                    String tempFirstName = passengers[i].getFirstName();
                    String tempLastName = passengers[i].getLastName();
                    String tempVehicleNo = passengers[i].getVehicleNo();
                    int tempLiters = passengers[i].getLitresRequired();
                    passenger(i + 1).setPassenger(passenger(j + 1).getFirstName(), passenger(j + 1).getLastName(), passenger(j + 1).getVehicleNo(), passenger(j + 1).getLitresRequired());
                    passenger(j + 1).setPassenger(tempFirstName, tempLastName, tempVehicleNo, tempLiters);
                }
            }
        }
    }

    public void writeData(FileWriter writer) {
        try {
            for (Passenger passenger : passengers) {
                writer.write(passenger.getFirstName() + " " + passenger.getLastName() + " " + passenger.getVehicleNo() + " " + passenger.getLitresRequired() + "\n");
            }
            writer.write((String.valueOf(this.income)));
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(Scanner fileReader) {
        for (Passenger passenger : passengers) {
            String[] word = fileReader.nextLine().split(" ");
            passenger.setPassenger(word[0], word[1], word[2], Integer.parseInt(word[3]));
        }
        this.income = Double.parseDouble(fileReader.nextLine());
    }

    public void addSale() {
        this.income = (this.income + (fuelPrice * (this.passenger(1).getLitresRequired())));
    }

    public double viewSale() {
        return this.income;
    }
}