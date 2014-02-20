/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

/**
 *
 * @author chris
 */
public class Note {
    float freq;
    int dur, number, octave;
    String name;
    
    public Note(int number, int dur) {
        this.freq   = Pitch.getFreqByNoteAbs(number);
        this.dur    = dur;
        this.number = number;
        this.octave = number / 12;
        this.name   = Pitch.getNoteNameByNumber(number);
    }
}
