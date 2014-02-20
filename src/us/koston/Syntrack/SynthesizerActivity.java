/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.SeekBar.*;


/**
 *
 * @author chris
 */
public class SynthesizerActivity extends Activity {
    SeekBar sb_osc1_sin_amp, sb_osc1_sqr_amp, sb_osc1_tri_amp, sb_osc1_saw_amp;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.synth);
        
        Keyboard kb = new Keyboard(this);
        
        sb_osc1_sin_amp = (SeekBar)findViewById(R.id.sb_osc1_sin_amp);
        sb_osc1_sqr_amp = (SeekBar)findViewById(R.id.sb_osc1_sqr_amp);
        sb_osc1_tri_amp = (SeekBar)findViewById(R.id.sb_osc1_tri_amp);
        sb_osc1_saw_amp = (SeekBar)findViewById(R.id.sb_osc1_saw_amp);
        
        initListeners();
    }
    
    public class AmpChangeListener implements OnSeekBarChangeListener {
        public void OnSeekBarChangeListener() {
            
        }
        
        public void onProgressChanged(SeekBar s, int val, boolean fromUser) {
            Synthesizer synth = Workstation.getInstance().synth;
            if (fromUser) {
                synth.vco1.sin_amp = sb_osc1_sin_amp.getProgress();
                synth.vco1.sqr_amp = sb_osc1_sqr_amp.getProgress();
                synth.vco1.tri_amp = sb_osc1_tri_amp.getProgress();
                synth.vco1.saw_amp = sb_osc1_saw_amp.getProgress();
            }
        } 
        
        public void onStopTrackingTouch(SeekBar s) {

        }
        
        public void onStartTrackingTouch(SeekBar s) {

        }
    }    
    
    private void initListeners() {
        AmpChangeListener ampChangeListener = new AmpChangeListener();
        
        sb_osc1_sin_amp.setOnSeekBarChangeListener(ampChangeListener);
        sb_osc1_sqr_amp.setOnSeekBarChangeListener(ampChangeListener);
        sb_osc1_tri_amp.setOnSeekBarChangeListener(ampChangeListener);
        sb_osc1_saw_amp.setOnSeekBarChangeListener(ampChangeListener);
    }
}
