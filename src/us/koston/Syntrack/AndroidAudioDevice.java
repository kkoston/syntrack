/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.koston.Syntrack;

import android.media.AudioTrack;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.util.Log;

/**
 *
 * @author chris
 */
public class AndroidAudioDevice {
    public AudioTrack track;

    public AndroidAudioDevice() {
      int minSize = AudioTrack.getMinBufferSize( 
              44100, 
              AudioFormat.CHANNEL_CONFIGURATION_MONO, 
              AudioFormat.ENCODING_PCM_16BIT 
      );


      track = new AudioTrack(
              AudioManager.STREAM_MUSIC, 
              44100,                                       
              AudioFormat.CHANNEL_CONFIGURATION_MONO, 
              AudioFormat.ENCODING_PCM_16BIT, 
              minSize, 
              AudioTrack.MODE_STREAM
      );

      track.setStereoVolume(1f, 1f);

      
    }	     
   
    public void writeSamples(short[] samples) 
    {	
        track.write(samples, 0, samples.length);
        if (track.getPlayState() !=  AudioTrack.PLAYSTATE_PLAYING) {   
            track.play();
        }
    }


    public void stop() {
        Log.v("**** ", " STOPPED!");
        track.stop();
        track.release();
    }
}
