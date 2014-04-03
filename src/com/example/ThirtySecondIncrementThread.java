/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yamir
 */
public class ThirtySecondIncrementThread extends Thread {
    
    private volatile boolean shouldRun = false;
    private volatile int evenDelay = 0;
    private volatile int oddDelay = 0;
    
    private void calculateNextOddEvenTime(){
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH);
        int date = rightNow.get(Calendar.DATE);
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);
        int second = rightNow.get(Calendar.SECOND);
        int milisecond = rightNow.get(Calendar.MILLISECOND);
        
        int delay = 0;
        if(((minute%2)==1) && ((minute%5) == 0) && (second==0)) {
            delay = 0;
        } else {
            int minuteDelay = 4 - (minute % 5);
            int secondDelay = 60 - second;
            delay = (minuteDelay*60)+secondDelay;
        }
        
            Calendar nextTime = Calendar.getInstance();
            nextTime.set(year, month, date, hour, minute, second);
            long tmpCalMillis = nextTime.getTimeInMillis();
            long tmpCalToPrintMillis = tmpCalMillis + delay*1000; 
            nextTime.setTimeInMillis(tmpCalToPrintMillis);
            if(nextTime.get(Calendar.MINUTE)%2==0){
                delay = delay + (5*60);
            }
            
            oddDelay = delay;
            evenDelay = delay + (5*60);
    }
    
    @Override
    public void start() {
        calculateNextOddEvenTime();
        shouldRun=true;
        super.start();
    }
    
    @Override
    public void run() {


        while(shouldRun){
            System.out.println("Delay to next thirty delay:"+oddDelay);
            try {
                Thread.sleep(1000*oddDelay);
            } catch (InterruptedException ex) {
            }
            for(int i=0; i<(5*60)-20; i++){
                if(i>0 && ((i%30)==0)){
                    System.out.println(new Date());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            calculateNextOddEvenTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    public void stop(){
        System.out.println("Destroying Thirty Thread");
        shouldRun=false;
        interrupt();
    }
    
}
