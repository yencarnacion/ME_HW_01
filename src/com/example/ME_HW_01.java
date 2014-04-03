/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example;

import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Yamir
 */
public class ME_HW_01 extends MIDlet {
    private FiveMinuteIncrementThread fiveThread;
    private ThirtySecondIncrementThread thirtyThead;
    
    public void startApp() {
        fiveThread = new FiveMinuteIncrementThread();
        fiveThread.start();
        thirtyThead = new ThirtySecondIncrementThread();
        thirtyThead.start();        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        fiveThread.stop();
        thirtyThead.stop();
    }
}
