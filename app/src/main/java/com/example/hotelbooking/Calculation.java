package com.example.hotelbooking;

public class Calculation {
   private int room,stayedDay,price;
   private double vat,total,serviceCharge,grandTotal;

    public double getVat() {
        return vat;
    }

    public double getTotal() {

        return total;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getStayedDay() {
        return stayedDay;
    }

    public void setStayedDay(int stayedDay) {
        this.stayedDay = stayedDay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public double calculate()
    {
        total=room*stayedDay*price;
        vat=13/100d*total;
        serviceCharge=10/100d*total;
        grandTotal=total+vat+serviceCharge;
        return grandTotal;
    }
}
