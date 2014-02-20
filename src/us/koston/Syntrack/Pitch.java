/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.util.Log;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Pitch {
    private static String note_name[] = 
        {"", "C-", "C#", "D-", "D#", "E-", "F-", "F#", "G-", "G#", "A-", "A-", "B-"};    
    
    private static ArrayList<Float> pitches = new ArrayList<Float>(2) {{
        for (int octave = 1; octave < 12; octave++) {
            for (int note = 0; note < 12; note++) {
                int noteabs = 12 * (octave - 1) + note;
                double exponent = (double)(noteabs - 69) / 12;
                Double freq = 440 * Math.pow(2, exponent);
                float freq_f = freq.floatValue();
                add(freq_f);
            }
        }
        
        set(0, 0f);
               
    }}; 
    
    public static int getNoteNameNumberByName(String n) {
        for (int i = 1; i < Pitch.note_name.length; i++) {
            if (n.equals(Pitch.note_name[i])) {
                return i;
            } 
        }
        
        return -1;
    }
    
    public static float getFreqByNoteAbs(int noteabs) {
        return Pitch.pitches.get(noteabs);
    }
    
    public static String getNoteNameByNumber(int noteabs) {
        return note_name[noteabs % 12 + 1];
    }    
}
