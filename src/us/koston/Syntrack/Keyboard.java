/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.view.View.*;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.widget.*;

/**
 *
 * @author chris
 */
public class Keyboard {
    String TAG = "ULTRA COMPOSER KYBOARD";
    ImageView im_keyboard;
    Synthesizer synth = Workstation.getInstance().synth;
    Activity ma;
    Button bt_octup, bt_octdown, bt_edit_synth;
    TextView tv_oct;
    RadioGroup rg_nl;
    
    int octave = 4;
    int note_length = 4;
    
    Sequencer seq;
    
    public Keyboard(Activity ma) {
        this.ma  = ma;
        this.im_keyboard = (ImageView)ma.findViewById(R.id.im_keyboard);
        
        this.bt_octdown = (Button)ma.findViewById(R.id.bt_octdown);
        this.bt_octup = (Button)ma.findViewById(R.id.bt_octup);
        this.tv_oct = (TextView)ma.findViewById(R.id.tv_oct);
        this.rg_nl = (RadioGroup)ma.findViewById(R.id.rg_nl);
        
        initListeners();
    }

    private void initListeners() {
        im_keyboard.setOnTouchListener(new OnTouchListener() {          
            public boolean onTouch(View v, MotionEvent e) {                  
                int action = e.getAction() & MotionEvent.ACTION_MASK; 
                
                if (action == android.view.MotionEvent.ACTION_DOWN) {                    
                    float x = e.getX();
                    float y = e.getY();               
                    float w = v.getWidth();
                    int white_map[] = {1, 3, 5, 6, 8, 10, 12, 13};
                    int black_map[] = {2, 4, -1, 7, 9, 11, -1, 14};

                    // white key pressed
                    int n = 0, k = 0;
                    if (y > 50) {
                        n = (int)x / 40;
                        sendNoteOn(white_map[n]);
                    } else {
                        n = (int)(x - 20) / 40;
                        k = black_map[n];
                        if (k != -1) {
                            sendNoteOn(k);
                        }
                    }
                }
                
                if (action == android.view.MotionEvent.ACTION_UP ||
                    action == android.view.MotionEvent.ACTION_POINTER_UP ||
                    action == android.view.MotionEvent.ACTION_CANCEL    ) {
                    sendNoteOff();
                }
                
                return true;
            }         
        });
        
        
        bt_octdown.setOnClickListener(new OnClickListener() {          
            public void onClick(View v) {
                octave = (octave > 0 ? octave-=1:0);
                tv_oct.setText(octave + "");
            }         
        });
        
        bt_octup.setOnClickListener(new OnClickListener() {          
            public void onClick(View v) {
                octave = (octave < 9 ? octave+=1:9);
                tv_oct.setText(octave + "");
            }         
        });
        
        rg_nl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int i) {
                switch (i) {
                    case R.id.rb_nl_1:
                        note_length = 16;
                        break;
                    case R.id.rb_nl_2:
                        note_length = 8;
                        break;
                    case R.id.rb_nl_4:
                        note_length = 4;
                        break;
                    case R.id.rb_nl_8:
                        note_length = 2;
                        break;
                    case R.id.rb_nl_16:
                        note_length = 1;
                        break;          
                    default:
                        note_length = 2;
                }
            }
        });
    }
    
    private void sendNoteOn(int keynum) {
        int note_number = octave * 12 + keynum - 1;
        Note n = new Note(note_number, note_length);
        //seq.recordNote(n);
        synth.noteOn(n);
        //synth.device.track.stop();
    }
    
    private void sendNoteOff() {
        synth.noteOff();
    }
}
