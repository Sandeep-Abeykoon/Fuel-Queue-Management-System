package com.example.fuelcentergui;

public class Passenger {
    private String firstName;
    private String lastName;
    private String vehicleNo;
    private int litresRequired;

    //Constructor
    public Passenger(){
        super();
        this.firstName = "empty";
        this.lastName = "empty";
        this.vehicleNo = "empty";
        this.litresRequired = 0;
    }

    //Getter and Setter Methods
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String secondName){
        this.lastName = secondName;
    }

    public String getVehicleNo(){
        return vehicleNo;
    }
    public void setVehicleNo(String vehicleNo){
        this.vehicleNo = vehicleNo;
    }

    public int getLitresRequired(){
        return litresRequired;
    }
    public void setLitresRequired(int litresRequired){
        if(litresRequired >= 0) {
            this.litresRequired = litresRequired;
        }else{
            throw new IllegalArgumentException("Number of litres cannot be a negative value");
        }
    }

    public void setPassenger(String firstName, String lastName, String vehicleNo, int litresRequired){
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleNo = vehicleNo;
        this.litresRequired = litresRequired;
    }

    public boolean isEmpty(){
        return this.firstName.equals("empty");
    }

    public void removePassenger(){
        this.firstName = "empty";
        this.lastName = "empty";
        this.vehicleNo = "empty";
        this.litresRequired = 0;
    }

}
