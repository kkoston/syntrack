/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

/**
 *
 * @author chris
 */
public class Workstation {
    public static final Workstation instance = new Workstation();
    
    public Synthesizer synth;
    public Keyboard kboard;
    public Sequencer seq;  
    
    public Workstation() {
        synth   = new Synthesizer();
        //seq     = new Sequencer(synth); 
        
        
        new Thread(synth).start();
        new Thread(seq).start();      
    }
    
    public static Workstation getInstance() {
        return instance;
    }
}
