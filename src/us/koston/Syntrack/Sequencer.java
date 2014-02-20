/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.util.Log;
import android.app.Activity;
import android.widget.*;
import android.view.View;
import android.view.View.*;

/**
 *
 * @author chris
 */
public class Sequencer implements Runnable {

    ScrollView sc_track1;
    TextView tv_score;
    ToggleButton bt_play;
    Sequence seq[] = new Sequence[4];
    Synthesizer synth;
    int selectedTrack = 0;
    int headPosition = 0;
    
    public Sequencer(Activity ma, Synthesizer synth) {
        seq[0] = new Sequence();
        seq[1] = new Sequence();
        seq[2] = new Sequence();
        seq[3] = new Sequence();
        
        this.tv_score = (TextView)ma.findViewById(R.id.tv_score);
        this.sc_track1 = (ScrollView)ma.findViewById(R.id.sc_track1);
        this.bt_play = (ToggleButton)ma.findViewById(R.id.bt_play);
        
        this.synth = synth;
        
        initListeners();
    }
    
    private void initListeners() {
        bt_play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (bt_play.isChecked()) {
                    play();
                }
            }
        });
    }
    
    public void run() {
        
    }
    
    synchronized public void play() {
        for (Object o : seq[0]) {
            Note n = (Note)o;
            if (n != null) {
                //synth.playNote(n);
            }
            
            try {Thread.sleep(125);} catch (Exception e) {};
        }
    }
    
    private void updateDisplay() {
        String s = "";
        
        for (Object o : seq[0]) {
            Note n = (Note)o;
            if (n != null) {
                s += n.name + n.octave + "\n";
            } else {
                s += "---\n";
            }
        }
        
        tv_score.setText(s);
    }
    
    public void recordNote(Note n) {
        headPosition = seq[selectedTrack].insertNote(n, headPosition);
        updateDisplay();
    }
    
}
