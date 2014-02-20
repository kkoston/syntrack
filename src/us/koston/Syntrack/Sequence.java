/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import java.util.ArrayList;
import java.util.Iterator;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 *
 * @author chris
 */ 
public class Sequence implements Iterable {
    private ArrayList<Note> t_notes = new ArrayList();
    private int tempo;
    private String TAG = "Ringtone Composer";

    public Sequence() {
        
    }
    
    public void createFromRTTTL(String tune_str, int tempo) {
        this.tempo = tempo;
        
        try {
            tune_str = tune_str.toUpperCase();
            tune_str = tune_str.replaceAll(" ", "");
            
            String tune_str_arr[] = tune_str.split(",");
            for (int i = 0; i < tune_str_arr.length; i++) {
                String n = tune_str_arr[i];
                Log.v(TAG, "parsing str: " + n);
                
                Pattern pattern = Pattern.compile("(\\d{0,1})(\\.{0,1})([a-zA-Z])(\\d{0,1})");
                Matcher matcher = pattern.matcher(n);
                
                int dur = 4;
                float freq = 30;
                int octave = 7;
                String note_name;
                
                note_name = matcher.group(3);
                
                if (!matcher.group(1).equals("")) {
                    dur = Integer.parseInt(matcher.group(1));
                }
                
                if (matcher.group(2).equals(".")) {
                    dur += (dur / 2);
                }
                
                if (!matcher.group(4).equals("")) {
                    octave = octave + Integer.parseInt(matcher.group(4));
                }
                
                
                if (note_name.equals("-")) {
                    freq = 0;
                } else {
                    int note_name_number = Pitch.getNoteNameNumberByName(note_name);
                    int noteabs = 12 * (octave - 1) + note_name_number;
                    freq = (float)Pitch.getFreqByNoteAbs(noteabs);
                }
  
                //t_notes.add(new Note(freq, dur));
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stacktrace = sw.toString();
            Log.v(TAG , "Error while parsing tune string. " + stacktrace);
            System.exit(0);
        }
    }
    
    public Iterator<Note> iterator() {
        Iterator<Note> iter = t_notes.iterator();
        return iter;
    }
    
    public int insertNote(Note n, int position) {
        int ticks = 16/n.dur;

        for (int i = 0; i < n.dur; i++) {
            if (position == t_notes.size()) {
                if (i == 0) {
                    t_notes.add(n);
                } else {
                    t_notes.add(null);
                }
            } else {
                if (i == 0) {
                    t_notes.set(position, n);
                }
            }
            position++;
        }

    
        return position;
    }
}
