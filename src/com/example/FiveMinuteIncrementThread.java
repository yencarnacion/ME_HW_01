package com.example;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yamir
 */


public class FiveMinuteIncrementThread extends Thread {
    private volatile boolean shouldRun = false;
    private volatile int delay = 0;
    private volatile Date timeToPrint = null;
    
    private void calculateNextTime(){
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH);
        int date = rightNow.get(Calendar.DATE);
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);
        int second = rightNow.get(Calendar.SECOND);
        int milisecond = rightNow.get(Calendar.MILLISECOND);
        
        if(((minute%5) == 0) && (second==0)) {
            delay = 0;
            Calendar timeToPrintCal = Calendar.getInstance();
            timeToPrintCal.set(year, month, date, hour, minute, second);
            timeToPrint = new Date(timeToPrintCal.getTimeInMillis());
        } else {
            int minuteDelay = 4 - (minute % 5);
            int secondDelay = 60 - second;
            delay = (minuteDelay*60)+secondDelay;
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.set(year, month, date, hour, minute, second);
            long tmpCalMillis = tmpCalendar.getTimeInMillis();
            long tmpCalToPrintMillis = tmpCalMillis + delay*1000; 
            timeToPrint = new Date(tmpCalToPrintMillis);
        }
    }
    @Override
    public void start() {
        calculateNextTime();
        shouldRun=true;
        super.start();
    }

    @Override
    public void run() {

        while(shouldRun){
            System.out.println("Delay to next five:"+delay);
            try {
                Thread.sleep(1000*delay);
            } catch (InterruptedException ex) {
            }
            System.out.print(new Date());
            calculateNextTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    public void stop(){
        System.out.println("Destroying Five Thread");
        shouldRun=false;
        interrupt();
    }
    
}
