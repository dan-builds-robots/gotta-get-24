package daniel.ajayi.gottaget24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import daniel.BluetoothConnect;
import daniel.TrainMode;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    private ImageView singlePlayerPlayBtn;

    AlarmManager alarmManager;

    Calendar calendar;

    public static MediaPlayer hardBeat;

    public static MediaPlayer gameMusic;

    public static boolean switchingBetweenActivities = false;

    private TextView spTrainTxt;

    private TextView spMenuTxt;

    private TextView highScoreTxt;

    private TextView gottaGetGold;

    private TextView gottaGetWhite;

    int notifCount = 0;

    private TextView spPlayTxt;

    private ImageView trainBtn;

    private ImageView settingsBtn;

    ImageView singlePlayerMenu;
//
//    boolean spMenuOpen;
//
//    boolean mpMenuOpen;
//
//    boolean namePanelOpen;
//
//    boolean settingsOpen;

    private ImageView spX;

    final public int v = View.VISIBLE;

    final public int i = View.INVISIBLE;

    public static SharedPreferences prefs;

    public static int highScore;

    private ImageView highScoreBox;

    private ImageView twoPlBtn;

    private ImageView threePlBtn;

    private ImageView fourPlBtn;

    private TextView mpMenuTxt;

    private ImageView connectBtn;

    private TextView connectBtnTxt;

    private ImageView mpX;

    private TextView chooseNameTxt;

    private EditText nameBox;

    private ImageView doneBtn;

    private TextView doneBtnTxt;

    public static String username;

    public static int numPlayers;

    public static int gameMode;

    public static final int TRAIN = 0;

    public static final int PLAY = 1;

    private boolean aboutToPlayMultiplayer;

    private ImageView black;

    private ImageView sfxBtn;

    private ImageView musicBtn;

    private ImageView hardBeatBtn;

    private TextView settingsSfxTxt;

    private TextView settingsMusicTxt;

    private TextView settingsTxt;

    private EditText settingsNameBox;

    private ImageView saveSettingsBtn;

    private TextView saveSettingsBtnTxt;

    private GridLayout settingsGridLayout;

    public static boolean musicMuted;

    public static boolean hardBeatOn;

    public boolean sfxMuted = false;

    MediaPlayer tapSfx;

    final int REGULAR = 0;

    final int HARD_BEAT = 1;

//    MediaPlayer[][] music;

    public static double pos;

    public static boolean musicPlaying = false;

    public static boolean musicCurrentlyPlaying = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences("dan.ajayi.gottaget24", Context.MODE_PRIVATE);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        pos = 0;

//        music = new MediaPlayer[2][2];
//
//        music[REGULAR][0] = MediaPlayer.create(this, R.raw.puzzle);
//
//        music[REGULAR][1] = MediaPlayer.create(this, R.raw.puzzle);
//
//        music[HARD_BEAT][0] = MediaPlayer.create(this, R.raw.flute_tingz2);
//
//        music[HARD_BEAT][1] = MediaPlayer.create(this, R.raw.jungle_mission);

        settingsGridLayout = findViewById(R.id.settingsGridLayout);

        settingsTxt = findViewById(R.id.sMenuTxt);

        settingsMusicTxt = findViewById(R.id.muteMusicTxt);

        settingsSfxTxt = findViewById(R.id.muteSfxTxt);

        black = findViewById(R.id.black);

        sfxBtn = findViewById(R.id.muteSfxBtn);

        musicBtn = findViewById(R.id.muteMusicBtn);

        hardBeatBtn = findViewById(R.id.hardBeatBtn);

        settingsNameBox = findViewById(R.id.settingsEditTextName);

        saveSettingsBtn = findViewById(R.id.saveSettingsBtn);

        saveSettingsBtnTxt = findViewById(R.id.saveSettingsBtnTxt);

        numPlayers = -1;

        hardBeat = MediaPlayer.create(this,R.raw.trap_loop);

        hardBeat.setVolume(50,50);

        gameMusic = MediaPlayer.create(this,R.raw.puzzle_loop);

        gameMusic.setVolume(50,50);

        tapSfx = MediaPlayer.create(this,R.raw.tap3);

        tapSfx.setVolume(140,140);

        musicMuted = prefs.getBoolean("musicMuted",false);

        sfxMuted = prefs.getBoolean("sfxMuted",false);

        hardBeatOn = prefs.getBoolean("hardBeat",true);

//        hardBeat.setLooping(true);
//
//        if (!hardBeat.isPlaying() && !musicMuted && hardBeatOn) {
//
//           hardBeat.start();
//
//        } else if (!musicMuted && !gameMusic.isPlaying()) {
//
//            gameMusic.start();
//
//        }

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.HOUR, 16);

        highScore = prefs.getInt("highScore",-1);

        username = prefs.getString("username","username");

        twoPlBtn = findViewById(R.id.twoPlayerBtn);

        threePlBtn = findViewById(R.id.threePlayerBtn);

        fourPlBtn = findViewById(R.id.fourPlayerBtn);

        singlePlayerPlayBtn = findViewById(R.id.spPlayBtn);

        spTrainTxt = findViewById(R.id.spTrainTxt);

        spMenuTxt = findViewById(R.id.spMenuTxt);

        highScoreTxt = findViewById(R.id.highScoreTxt);

        gottaGetGold = findViewById(R.id.gottaGetGold);

        gottaGetWhite = findViewById(R.id.gottaGetWhite);

        spPlayTxt = findViewById(R.id.spPlayTxt);

        trainBtn = findViewById(R.id.trainBtn);

        settingsBtn = findViewById(R.id.settingsBtn);

        singlePlayerMenu = findViewById(R.id.singlePlayerMenu);

        spX = findViewById(R.id.x);

        highScoreBox = findViewById(R.id.highScoreBox);

        mpMenuTxt = findViewById(R.id.mpMenuTxt);

        connectBtn = findViewById(R.id.connectBtn);

        connectBtnTxt = findViewById(R.id.connectBtnTxt);

        chooseNameTxt = findViewById(R.id.chooseNameTxt);

        nameBox = findViewById(R.id.editTextName);

        doneBtn = findViewById(R.id.doneBtn);

        doneBtnTxt = findViewById(R.id.doneBtnTxt);

        mpX = findViewById(R.id.mpX);

        if (highScore == -1) {

            highScoreBox.setVisibility(View.INVISIBLE);

            highScoreTxt.setVisibility(View.INVISIBLE);

        } else {

            highScoreTxt.setText("High Score: " + highScore);

        }

        aboutToPlayMultiplayer = false;

    }

    public void openSinglePlayerMenu (View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        singlePlayerMenu.setVisibility(v);

        spX.setVisibility(v);

        spTrainTxt.setVisibility(v);


        trainBtn.setVisibility(v);

        singlePlayerPlayBtn.setVisibility(v);

        spMenuTxt.setVisibility(v);

        spPlayTxt.setVisibility(v);

    }

    public void closeSinglePlayerMenu (View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        singlePlayerMenu.setVisibility(i);

        spX.setVisibility(i);

        spTrainTxt.setVisibility(i);

        trainBtn.setVisibility(i);

        singlePlayerPlayBtn.setVisibility(i);

        spMenuTxt.setVisibility(i);

        spPlayTxt.setVisibility(i);

    }

    public void openMultiplayerMenu (View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        singlePlayerMenu.setVisibility(View.VISIBLE);

        twoPlBtn.setVisibility(View.VISIBLE);

        threePlBtn.setVisibility(View.VISIBLE);

        fourPlBtn.setVisibility(View.VISIBLE);

        mpMenuTxt.setVisibility(View.VISIBLE);

        connectBtn.setVisibility(View.VISIBLE);

        connectBtnTxt.setVisibility(View.VISIBLE);

        mpX.setVisibility(View.VISIBLE);



    }

    public void closeMultiplayerMenu(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        numPlayers = -1;

        singlePlayerMenu.setVisibility(View.INVISIBLE);

        twoPlBtn.setVisibility(View.INVISIBLE);

        threePlBtn.setVisibility(View.INVISIBLE);

        fourPlBtn.setVisibility(View.INVISIBLE);

        mpMenuTxt.setVisibility(View.INVISIBLE);

        connectBtn.setVisibility(View.INVISIBLE);

        connectBtnTxt.setVisibility(View.INVISIBLE);

        mpX.setVisibility(View.INVISIBLE);

        twoPlBtn.setScaleX(1f);

        twoPlBtn.setScaleY(1f);

        threePlBtn.setScaleX(1f);

        threePlBtn.setScaleY(1f);

        fourPlBtn.setScaleX(1f);

        fourPlBtn.setScaleY(1f);

    }

    public void openTrainingMode (View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        if (view.getTag().toString().equals("train")) {

            gameMode = TRAIN;

        } else if (view.getTag().toString().equals("play")) {

            Log.i("!@#$%This","is happening");

            gameMode = PLAY;

        } else {

            Log.i("!@#$%This","is not happening");

        }

        musicPlaying = true;

        Intent intent = new Intent(this,  TrainMode.class);

        startActivity(intent);

    }

    public void chooseName () {

        if (!sfxMuted) {

            tapSfx.start();

        }

        mpX.setVisibility(View.INVISIBLE);

        mpMenuTxt.setVisibility(View.INVISIBLE);

        twoPlBtn.setVisibility(View.INVISIBLE);

        threePlBtn.setVisibility(View.INVISIBLE);

        fourPlBtn.setVisibility(View.INVISIBLE);

        chooseNameTxt.setVisibility(View.VISIBLE);

        nameBox.setVisibility(View.VISIBLE);

        doneBtn.setVisibility(View.VISIBLE);

        doneBtnTxt.setVisibility(View.VISIBLE);

    }

    public void closeNamePanel(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        username = nameBox.getText().toString();

        if (username.length() < 3) {

            Toast.makeText(this, "Sorry, username must be longer than 2 characters", Toast.LENGTH_SHORT).show();

            return;

        }

        if (username.length() > 12) {

            Toast.makeText(this, "Sorry, username cannot be longer than 12 characters", Toast.LENGTH_SHORT).show();

            return;

        }


        prefs.edit().putString("username", username).apply();

        if (!username.equals("") && !aboutToPlayMultiplayer) {

            chooseNameTxt.setVisibility(View.INVISIBLE);

            nameBox.setVisibility(View.INVISIBLE);

            doneBtn.setVisibility(View.INVISIBLE);

            doneBtnTxt.setVisibility(View.INVISIBLE);

        }

        if (aboutToPlayMultiplayer && numPlayers != -1) {

            aboutToPlayMultiplayer = false;

            musicPlaying = true;

            Intent intent = new Intent(this,  BluetoothConnect.class);

            startActivity(intent);

        }

    }

    public void openConnectPanel(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        aboutToPlayMultiplayer = true;

        if (username.equals("") && numPlayers != -1) {

            chooseName();

        } else if (numPlayers != -1) {

            Intent intent = new Intent(this,  BluetoothConnect.class);

            musicPlaying = true;

            startActivity(intent);

        }

        if (numPlayers == -1) {

            Toast.makeText(this, "Please select the number of players", Toast.LENGTH_SHORT).show();

        }

    }

    public void resizeMultiplayerBtns(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        twoPlBtn.setScaleX(.8f);

        twoPlBtn.setScaleY(.8f);

        threePlBtn.setScaleX(.8f);

        threePlBtn.setScaleY(.8f);

        fourPlBtn.setScaleX(.8f);

        fourPlBtn.setScaleY(.8f);

        ((ImageView)view).setScaleX(1.2f);

        ((ImageView)view).setScaleY(1.2f);

        numPlayers = Integer.parseInt(view.getTag().toString());

    }

    public void createNotification() {

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        String msg1 = "Come enhance your brain with a few rounds of Get 24!";

        String msg2 = "We miss you! Come back!";

        String msg3 = "Did you know that playing Get 24 can make you smarter?";

        String msg4 = "Come compete in Get 24 with your friends!";

        String birthdayMsg = "Hippity Happity Birthday! Enjoy your day!";

        String specialDay = "What a perfect day to come play some get 24!";

        String title1 = "Come Play!";

        String title2 = "Come Back!";

        String title3 = "Hey there, Genius!";

        String title4 = "Come Get 24!";

        String title5 = "Time to Get 24!";

        String specialDayTitle = "Today's the 24th!";

        String birthdayMsgTitle = "HAPPY BIRTHDAY!!!";

        String msg;

        String title;

        double random1 = Math.random();

        double random2 = Math.random();

        if (random1 < .25) {

            msg = msg1;

        } else if (random1 < .5) {

            msg = msg2;

        } else if (random1 < .75) {

            msg = msg3;

        } else {

            msg = msg4;

        }

        if (random2 < .2) {

            title = title1;

        } else if (random2 < .4) {

            title = title2;

        } else if (random2 < .6) {

            title = title3;

        }  else if (random2 < .8) {

            title = title4;

        } else {

            title = title5;

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.notification_icon);

        builder.setContentTitle(title);

        builder.setContentText(msg);

        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);


        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

        NotificationManager manager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "Your_channel_id";

            NotificationChannel channel = new NotificationChannel(channelId,"Channel human readable title", NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(channel);

            builder.setChannelId(channelId);

        }

        manager.notify(notifCount,builder.build());

        notifCount++;

    }

    public void openQuestionGeneration(View view) {

        Intent intent = new Intent(this, QuestionGeneration.class);

        musicPlaying = true;

        startActivity(intent);

    }

    public void openSettings(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }


        if (!sfxMuted) {

            sfxBtn.setImageResource(R.drawable.white_box);

        } else {

            sfxBtn.setImageResource(R.drawable.gold_box);

        }

        if (!musicMuted) {

            musicBtn.setImageResource(R.drawable.white_box);

        } else {

            musicBtn.setImageResource(R.drawable.gold_box);

        }

        if (!hardBeatOn) {

            hardBeatBtn.setImageResource(R.drawable.white_box);

        } else {

            hardBeatBtn.setImageResource(R.drawable.gold_box);

        }

        black.setVisibility(View.VISIBLE);

        settingsNameBox.setText(username + "");

        settingsGridLayout.setVisibility(View.VISIBLE);

        settingsTxt.setVisibility(View.VISIBLE);

        saveSettingsBtn.setVisibility(View.VISIBLE);

        saveSettingsBtnTxt.setVisibility(View.VISIBLE);

    }

    public void saveSettings(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        username = settingsNameBox.getText().toString();

        if (username.length() < 3) {

            Toast.makeText(this, "Sorry, username must be longer than 2 characters", Toast.LENGTH_SHORT).show();

            return;

        }

        if (username.length() > 12) {

            Toast.makeText(this, "Sorry, username cannot be longer than 12 characters", Toast.LENGTH_SHORT).show();

            return;

        }

        prefs.edit().putString("username", username).apply();

        black.setVisibility(View.INVISIBLE);

        settingsGridLayout.setVisibility(View.INVISIBLE);

        settingsTxt.setVisibility(View.INVISIBLE);

        saveSettingsBtn.setVisibility(View.INVISIBLE);

        saveSettingsBtnTxt.setVisibility(View.INVISIBLE);

    }


    public void changeMusicState(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        if (musicMuted) {

            musicMuted = false;

            musicCurrentlyPlaying = true;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

            stopService(new Intent(this, MyService.class));

            startService(new Intent(this, MyService.class));

            musicBtn.setImageResource(R.drawable.white_box);

        } else {

            musicMuted = true;

            musicCurrentlyPlaying = false;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

            stopService(new Intent(this, MyService.class));

            Log.i("!@#$%Is this shit","even running");

            musicBtn.setImageResource(R.drawable.gold_box);

        }


        Log.i("!@#$%Is this shit","even running2");

        prefs.edit().putBoolean("musicMuted", musicMuted).apply();

    }


    public void changeSfxState(View view) {

        if (sfxMuted) {

            sfxMuted = false;

            sfxBtn.setImageResource(R.drawable.white_box);

            if (!sfxMuted) {

                tapSfx.start();

            }

        } else {

            sfxMuted = true;

            sfxBtn.setImageResource(R.drawable.gold_box);

        }

        prefs.edit().putBoolean("sfxMuted", sfxMuted).apply();

    }

    public void changeHardBeatState(View view) {

        if (!sfxMuted) {

            tapSfx.start();

        }

        if (hardBeatOn) {

            hardBeatOn = false;

            hardBeatBtn.setImageResource(R.drawable.white_box);

            stopService(new Intent(this, MyService.class));

            startService(new Intent(this, MyService.class));


        } else {

            hardBeatOn = true;

            hardBeatBtn.setImageResource(R.drawable.gold_box);

            stopService(new Intent(this, MyService.class));

            startService(new Intent(this, MyService.class));

        }

        prefs.edit().putBoolean("hardBeat", hardBeatOn).apply();

    }

    public void multiplayerResults(View view) {

        if (true) {

            return;

        }

        Intent intent = new Intent(this,  MultiplayerResults.class);

        startActivity(intent);

    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

        Log.i("App status","you just left");

        if (musicCurrentlyPlaying) {

            stopService(new Intent(this, MyService.class));

            musicCurrentlyPlaying = false;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

        }

    }
//
//    @Override
//
//    protected void onPause() {
//
//        super.onPause();
//
//        Log.i("!@#$%LEAVING MAIN", "");
//
//        Log.i("!@#$%hardBeat.isPlaying","" + hardBeat.isPlaying());
//
////        musicPlaying = hardBeat.isPlaying() || gameMusic.isPlaying();
//
//        Log.i("!@#$%App status aaaaa","just paused");
//
//        Log.i("!@#$%this.isFinishing", this.isFinishing() + "");
//
//        if (this.isFinishing()) {
//
//            if (hardBeat.isPlaying()) {
//
//                Log.i("!@#$%App status","About to switch to game mode, music is stopping");
//
//                hardBeat.pause();
//
//                pos = hardBeat.getCurrentPosition();
//
//                Log.i("!@#$%music status aaaaa","just stopped");
//
//                musicPlaying = false;
//
//            } else if (gameMusic.isPlaying()) {
//
//                gameMusic.pause();
//
//                pos = hardBeat.getCurrentPosition();
//
//                musicPlaying = false;
//
//            }
//
////            Log.i("!@#$%YOU PRESSED BACK FROM aaaaa","YOUR 'HOME/MAIN' ACTIVITY");
//
//            Log.i("!@#$%You are"," leaving app");
//
//        }
//
//        Context context = getApplicationContext();
//
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//
//        if (!taskInfo.isEmpty()) {
//
//            ComponentName topActivity = taskInfo.get(0).topActivity;
//
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//
//                if (hardBeat.isPlaying()) {
//
//                    Log.i("!@#$%App status","About to switch to game mode, music is stopping");
//
//                    hardBeat.pause();
//
//                    musicPlaying = false;
//
//                }
//
//                if (gameMusic.isPlaying()) {
//
//                    gameMusic.pause();
//
//                    musicPlaying = false;
//
//                    Log.i("!@#$%App status","About to switch to game mode, music is stopping");
//
//                }
//
//                Log.i("!@#$%YOU LEFT The APP aaaaa","");
//            }
//            else {
//
//                Log.i("!@#$%YOU SWITCHED ACTIVITIES aaaaa", "WITHIN YOUR APP");
//
////                if (hardBeat.isPlaying() || gameMusic.isPlaying()) {
////
////                    musicPlaying = true;
////
////                } else {
////
////                    musicPlaying = false;
////
////                }
//
//            }
//
//        }
//
//    }
//
//
//    @Override
//
//    protected void onResume() {
//
////        musicPlaying = hardBeat.isPlaying() || gameMusic.isPlaying();
//
//        super.onResume();
//
//        if (hardBeatOn && !musicMuted && !hardBeat.isPlaying() && !musicPlaying) {
//
//            hardBeat.stop();
//
//            hardBeat = null;
//
//            hardBeat = MediaPlayer.create(this,R.raw.trap_loop);
//
//            hardBeat.seekTo((int)pos);
//
//            hardBeat.setLooping(true);
//
//            hardBeat.start();
//
//            musicPlaying = true;
//
//        } else if (!hardBeatOn && !musicMuted && !gameMusic.isPlaying() && !musicPlaying) {
//
//            Log.i("!@#$%App status","Just switched to main, the music is not muted and music " +
//                    "is not playing, so music will begin playing now");
//
//            gameMusic.stop();
//
//            gameMusic = null;
//
//            gameMusic = MediaPlayer.create(this,R.raw.trap_loop);
//
//            gameMusic.seekTo((int)pos);
//
//            gameMusic.setLooping(true);
//
//            gameMusic.start();
//
//            musicPlaying = true;
//
//        }
//
//        Log.i("!@#$%Main Activity aaaaa","resumed");
//
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)

    public void onAppBackgrounded() {

        //App in background

        stopService(new Intent(this, MyService.class));

        musicCurrentlyPlaying = false;

        prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)

    public void onAppForegrounded() {

        // App in foreground

        musicCurrentlyPlaying = prefs.getBoolean("musicCurrentlyPlaying", false);

        Log.i("musicCurrentlyPlaying","" + musicCurrentlyPlaying);

        if (!musicCurrentlyPlaying && !musicMuted) {

            startService(new Intent(this, MyService.class));

            musicCurrentlyPlaying = true;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

            Log.i("music","wasn't playing, just turned on");

        }


    }

    @Override

    public void onBackPressed () {

    }

}