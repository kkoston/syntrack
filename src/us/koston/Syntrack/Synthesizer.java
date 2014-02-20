/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.util.Log;
import java.util.Queue;
import java.util.LinkedList;

/**
 *
 * @author chris
 */
public class Synthesizer implements Runnable {    
    Sequence tune;
    int total_buffs = 0;
    AndroidAudioDevice device = new AndroidAudioDevice();
    int tempo = 120;
    Queue<Note> notesQueue = new LinkedList<Note>();
    volatile boolean queueHasNotes = false;
    
    public VCO vco1 = new VCO();
    Note current_note = new Note(67, 4);
    boolean output_stopped = true;
    private final Object lock = new Object();
    int buff_size = 1024;
    short buff[] = new short[1024];
    
    public void setTune(Sequence t) {
        this.tune = t;
    }
    
    private void sendZeroBuff() {
        int k = 0;
        short buff[] = new short[1024];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 1024; j++) {
                buff[j] = 0;
            }
            device.writeSamples(buff);
        }
    }
    
    private void outputWave() {       
        int i, j = 0;
        
        vco1.setFreq(current_note.freq);

        while (!output_stopped) {
            buff[j] = vco1.getSample();
            j++;

            if (j == buff_size) {
                device.writeSamples(buff);
                j = 0;
            }
        }
        device.track.stop();
    }

    public void run( ) {
        (new Thread(vco1)).start();
        //android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        device = new AndroidAudioDevice();
        try {
            synchronized (lock) {
                while (true) {
                    lock.wait();
                    outputWave();
                }
            }
        } catch (Exception e) {
            Log.v("COMPOSER", "Exception while waiting for the lock " + e.getMessage());
        }
    }
    
    synchronized public void noteOn(Note n) {       
        synchronized (lock) {
            this.current_note = n;
            this.output_stopped = false;
            lock.notify();
        }
        Log.v("********", "NOTE ON, Freq: " + n.freq);
    }
    
    synchronized public void noteOff() {
        this.output_stopped = true;
        Log.v("********", "NOTE OFF");
    }
    
}
