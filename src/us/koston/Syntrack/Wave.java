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
public class Wave {
    public static final int samplerate = 44100;
    public static final float samplerate_f = 44100f;
    public static final int samplerate_quarter  = samplerate / 4;
    public static final int samplerate_half     = samplerate / 2;
    public static final int samplerate_3quarter = 3 * samplerate / 4;
    public static final double angle_ratio =  2 * Math.PI / (double)samplerate;
    
    public static short sqr[] = new short[samplerate];
    public static short sin[] = new short[samplerate];
    public static short tri[] = new short[samplerate];
    public static short saw[] = new short[samplerate];
    
    private static int tmpint;
    
    static {
        for (int i = 0; i < samplerate; i++) {
            
            // square
            sqr[i] = (i < samplerate_half ? Short.MAX_VALUE:Short.MIN_VALUE);
            
            // sinus
            double angle = i * angle_ratio;
            sin[i] = (short)(Short.MAX_VALUE * Math.sin(angle));
            
            
            
            
            // triangle
            // 0-1/4
            if (i >= 0 && i < samplerate_quarter) {
                tri[i] = (short)(Short.MAX_VALUE * i/samplerate_quarter);                
            }
            // 1/4-3/4
            if (i >= samplerate_quarter && i < samplerate_3quarter) {
                tri[i] = (short)(-Short.MAX_VALUE * i/samplerate_quarter + 2*Short.MAX_VALUE);
            }
            // 3/4-1
            if (i >= samplerate_3quarter && i <= samplerate) {
                tri[i] = (short)(Short.MAX_VALUE * i/samplerate_quarter - Short.MAX_VALUE * samplerate_3quarter);
            }
            
            
            
            
            // sawtooth
            // 0-1/2
            if (i >= 0 && i < samplerate_half) {
                saw[i] = (short)(Short.MAX_VALUE * i/samplerate_half);                
            }
            // 1/2-1
            if (i >= samplerate_half && i <= samplerate) {
                saw[i] = (short)(Short.MAX_VALUE * i/samplerate_half - Short.MAX_VALUE * samplerate_3quarter);
            }
            
        }
    }

    
    public static short getSin(float i) {
        return Wave.sin[(int)i];
    }
    
    public static short getSqr(float i) {
        return Wave.sqr[(int)i];
    }
    
    public static short getTri(float i) {
        return Wave.tri[(int)i];
    }
    
    public static short getSaw(float i) {
        return Wave.saw[(int)i];
    }
    
    public static float incPos(float oldf, float offset) {
        float f = oldf + offset; 
        if (f >= 0) {
            return f % Wave.samplerate;
        } else {
            return Wave.samplerate + (f % Wave.samplerate);
        } 
    }
    
}
