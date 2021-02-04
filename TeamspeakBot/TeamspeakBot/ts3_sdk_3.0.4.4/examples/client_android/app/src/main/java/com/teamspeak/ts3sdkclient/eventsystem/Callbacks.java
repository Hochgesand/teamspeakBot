package com.teamspeak.ts3sdkclient.eventsystem;

import java.util.Vector;

/**
 * TeamSpeak 3 sdk client sample
 *
 * Copyright (c) 2007-2017 TeamSpeak-Systems
 *
 * @author Anna
 * Creation date: 08.02.17
 *
 * Event Callback handler class
 * Instances can register their callback method implementation with registerCallbacks here
 * Events generated by the ts3clientlib will trigger the fireEvent method to reach the event to the callback methods in the registered instances
 */
public class Callbacks {
    private static Callbacks instance;
    private static Vector<IEventListener> eventRegister = new Vector<IEventListener>();

    private Callbacks(){
    }

    public static Callbacks getInstance(){
        if(instance == null)
            instance = new Callbacks();
        return instance;
    }

    public synchronized static void fireEvent(IEvent e){
        Vector<IEventListener> copy;

        synchronized (eventRegister) {
            copy = (Vector<IEventListener>) eventRegister.clone();
        }
        for (IEventListener x : copy) {
            // Log.d(TAG, e.toString());
            instance.execute(x, e);
        }
    }

    private synchronized void execute(IEventListener x, IEvent e) {
        x.onTS3Event(e);
    }

    public void registerCallbacks(IEventListener listener){
        if (!eventRegister.contains(listener))
            eventRegister.add(listener);
    }

    public void unregisterCallbacks(IEventListener listener){
        eventRegister.remove(listener);
    }
}