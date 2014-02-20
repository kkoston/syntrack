/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.util.Log;

/**
 *
 * @author chris
 */
public class VCO implements Runnable {
    private float sin_freq;
    private float sqr_freq;
    private float tri_freq;
    private float saw_freq;
    
    private float sin_pos = 0;
    private float sqr_pos = 0;
    private float tri_pos = 0;
    private float saw_pos = 0;
    
    public float sin_amp = 0.5f;
    public float sqr_amp = 0f;
    public float tri_amp = 0f;
    public float saw_amp = 0f;
    
    public float sin_pha = 0;
    public float sqr_pha = 0;
    public float tri_pha = 0;
    public float saw_pha = 0;
    
    private short wave[] = new short[Wave.samplerate];
    
    
    public VCO() {

    }
    
    public void run() {
        
    }
    
    public void setFreq(float freq) {
        this.sin_freq = freq;
        this.sqr_freq = freq;
        this.tri_freq = freq;
        this.saw_freq = freq + 3f;       
    }

    synchronized public short getSample() {
        short res = 0;
        
        res = (short)(
               (
                sin_amp * Wave.getSaw(sin_pos) +
                sqr_amp * Wave.getSqr(sqr_pos) +
                tri_amp * Wave.getTri(tri_pos) +
                saw_amp * Wave.getSaw(saw_pos)
               ) / 
               (
                sin_amp + 
                sqr_amp + 
                tri_amp + 
                saw_amp
               )
        );
        
        sin_pos = Wave.incPos(sin_pos, sin_freq);
        sqr_pos = Wave.incPos(sqr_pos, sqr_freq);
        tri_pos = Wave.incPos(tri_pos, tri_freq);
        saw_pos = Wave.incPos(saw_pos, saw_freq);
        
        return res;
    }
    
    synchronized public short[] getWave() {
        short res[] = new short[0];
        float j = 0;
        int i = 0;
        

        
        return res;
    }
}
