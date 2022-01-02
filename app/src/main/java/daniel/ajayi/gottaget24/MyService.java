package daniel.ajayi.gottaget24;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import static daniel.ajayi.gottaget24.MainActivity.musicMuted;
import static daniel.ajayi.gottaget24.MainActivity.musicPlaying;

public class MyService extends Service {


    MediaPlayer player;

    @Nullable

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("made it","to service");

        Log.i("asdfasdf","asdfasdfasdfasdf");

        if (musicMuted) {

            return START_STICKY;

        }

        if (MainActivity.hardBeatOn) {

            player = MediaPlayer.create(this, R.raw.trap_loop);

        } else {

            player = MediaPlayer.create(this, R.raw.puzzle_loop);

        }

//        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        player.setVolume(50,50);

        player.setLooping(true);

        player.start();

        return START_STICKY;

    }

    public boolean isPlaying() {

        if (player == null) {

            return false;

        }

        return player.isPlaying();

    }

    @Override

    public void onDestroy() {

        super.onDestroy();

        player.stop();

    }

}
