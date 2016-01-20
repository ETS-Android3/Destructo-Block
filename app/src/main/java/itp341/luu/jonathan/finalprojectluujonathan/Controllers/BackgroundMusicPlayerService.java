package itp341.luu.jonathan.finalprojectluujonathan.Controllers;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import itp341.luu.jonathan.finalprojectluujonathan.R;

public class BackgroundMusicPlayerService extends Service implements MediaPlayer.OnCompletionListener{
    public MediaPlayer media;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    //Start music and make it loop
    @Override
    public void onCreate(){
        media = MediaPlayer.create(this, R.raw.perion);
        media.setOnCompletionListener(this);
        media.setLooping(true);
    }

    //If music is not playing, start the music again
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (!media.isPlaying()){
            media.start();
        }
        return START_STICKY;
    }

    //Stop music when application is destroyed
    public void onDestroy(){
        if (media.isPlaying()){
            media.stop();
        }
        media.release();
    }

    public void onCompletion(MediaPlayer mp){
        stopSelf();
    }

}
