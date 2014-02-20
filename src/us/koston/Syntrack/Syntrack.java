package us.koston.Syntrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.*;
import android.content.Context;
import android.util.Log;
import android.media.AudioManager;
import android.content.Intent;
import android.view.View.*;
import android.view.View;
import android.widget.*;


public class Syntrack extends Activity
{       
    
    Button bt_edit_synth;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.bt_edit_synth = (Button)findViewById(R.id.bt_edit_synth);
        
        AudioManager au_man = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int max_vol = au_man.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cur_vol = au_man.getStreamVolume(AudioManager.STREAM_MUSIC);
        au_man.setStreamVolume(AudioManager.STREAM_MUSIC, max_vol / 2, 0);
        
        Log.v("COMPOSER", "Max volume: " + max_vol + " Current volume: " + cur_vol);
        
        initListeners();
        //drawWave();
        
        Keyboard kb = new Keyboard(this);
    }
    
    private void initListeners() {
        final Activity ma = this;
        bt_edit_synth.setOnClickListener(new OnClickListener() {          
            public void onClick(View v) {
                Intent i = new Intent(ma, SynthesizerActivity.class);
                ma.startActivity(i);
            }         
        });        
    }
    
    private void drawWave() {
        ImageView im = (ImageView)findViewById(R.id.im_1);
        Bitmap bmp = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        
        
        float y;
        VCO v = new VCO();
        //v.remixWaves();
        
        int px, py, last_px = -10, last_py = 0;
        short wave[] = v.getWave();
        for (int x = 0; x < wave.length; x++) {
            y = wave[x];
            px = 200*x/wave.length;
            py = (int)(-50*y/Short.MAX_VALUE + 50);
            c.drawLine(last_px, last_py, px, py, p);
            last_px = px;
            last_py = py;
        }

        im.setImageBitmap(bmp);
    }
        
}
