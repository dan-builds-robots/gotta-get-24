package daniel.ajayi.gottaget24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiplayerResults extends AppCompatActivity {

    ImageView moreResultsBlack;

    ImageView moreResultsPanel;

    TextView resultsPanelTxt;

    TextView resultsPanelTxt2;

    GridLayout resultsGridLayout;

    ImageView XresultsPanel;

    TextView resultsTxt;

    TextView resultsTxt2;

    ImageView ground;

    ImageView backlight;

    TextView firstPName1;

    TextView firstPName2;

    TextView secondPName1;

    TextView secondPName2;

    TextView thirdPName1;

    TextView thirdPName2;

    ImageView podium1;

    ImageView podiumTop1;

    ImageView podium2;

    ImageView podiumTop2;

    ImageView podium3;

    ImageView podiumTop3;

    TextView num1;

    TextView num2;

    TextView num3;

    ImageView trophy1;

    ImageView trophy2;

    ImageView trophy3;

    ImageView exitBtn;

    ImageView playAgainBtn;

    ImageView seeMoreBtn;

    TextView exitBtnTxt;

    TextView playAgainBtnTxt;

    TextView seeMoreBtnTxt;

    TextView questionScore1;

    TextView questionScore2;

    TextView questionScore3;

    public int marker;

    ScaleAnimation scaleAnim;

    ScaleAnimation scaleAnim2;

    ArrayList<ImageView> podiums;

    boolean[] podiumAlreadyAnimated;

    MediaPlayer fanfare;

    MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiplayer_results);

        MainActivity.hardBeat.stop();

        MainActivity.gameMusic.stop();

        MainActivity.musicPlaying = false;

        fanfare = MediaPlayer.create(this,R.raw.fanfare);

        fanfare.start();

        music = MediaPlayer.create(this,R.raw.achievement_song);

        music.setLooping(true);

        fanfare.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override

            public void onCompletion(MediaPlayer mediaPlayer) {
                music.start();
            }

        });

//        podiumAlreadyAnimated = new ArrayList<>();

        podiumAlreadyAnimated = new boolean[3];

        podiumAlreadyAnimated[0] = false;

        podiumAlreadyAnimated[1] = false;

        podiumAlreadyAnimated[2] = false;

        marker = 0;

        moreResultsBlack = findViewById(R.id.moreResultsBlack);

        moreResultsPanel = findViewById(R.id.moreInfoPanel);

        resultsPanelTxt = findViewById(R.id.resultsPanelTxt);

        resultsPanelTxt2 = findViewById(R.id.resultsPanelTxt2);

        resultsGridLayout = findViewById(R.id.moreInfo);

        XresultsPanel = findViewById(R.id.resultsPanelX);

        resultsTxt = findViewById(R.id.resultsTxt);

        resultsTxt2 = findViewById(R.id.resultsTxt2);

        ground = findViewById(R.id.ground);

        backlight = findViewById(R.id.shadow);

        exitBtn = findViewById(R.id.exitBtnBg);

        exitBtnTxt = findViewById(R.id.exitBtn);

        playAgainBtn = findViewById(R.id.playAgainBtnBg);

        playAgainBtnTxt = findViewById(R.id.playAgainBtn);

        seeMoreBtnTxt = findViewById(R.id.seeMoreBtn);

        seeMoreBtn = findViewById(R.id.seeMoreBtnBg);

        firstPName1 = findViewById(R.id.firstPlaceName);

        firstPName2 = findViewById(R.id.firstPlaceName2);

        secondPName1 = findViewById(R.id.secondPlaceName);

        secondPName2 = findViewById(R.id.secondPlaceName2);

        thirdPName1 = findViewById(R.id.thirdPlaceName);

        thirdPName2 = findViewById(R.id.thirdPlaceName2);

        podium1 = findViewById(R.id.firstPlacePodium);

        podiumTop1 = findViewById(R.id.firstPlaceTop);

        podium2 = findViewById(R.id.secondPlacePodium);

        podiumTop2 = findViewById(R.id.secondPlaceTop);

        podium3 = findViewById(R.id.thirdPlacePodium);

        podiumTop3 = findViewById(R.id.thirdPlaceTop);

        num1 = findViewById(R.id.oneTxt);

        num2 = findViewById(R.id.twoTxt);

        num3 = findViewById(R.id.threeTxt);

        trophy1 = findViewById(R.id.firstPlaceTrophy);

        trophy2 = findViewById(R.id.secondPlaceTrophy);

        trophy3 = findViewById(R.id.thirdPlaceTrophy);

        questionScore1 = findViewById(R.id.oneQuestionResult);

        questionScore2 = findViewById(R.id.twoQuestionResult);

        questionScore3 = findViewById(R.id.threeQuestionResult);


//
//        resultsTxt.setVisibility(View.INVISIBLE);
//
//        resultsTxt2.setVisibility(View.INVISIBLE);

        backlight.setVisibility(View.INVISIBLE);

        ground.setVisibility(View.INVISIBLE);

        podium1.setVisibility(View.INVISIBLE);

        podiumTop1.setVisibility(View.INVISIBLE);

        podium2.setVisibility(View.INVISIBLE);

        podiumTop2.setVisibility(View.INVISIBLE);

        podium3.setVisibility(View.INVISIBLE);

        podiumTop3.setVisibility(View.INVISIBLE);

        num1.setVisibility(View.INVISIBLE);

        num2.setVisibility(View.INVISIBLE);

        num3.setVisibility(View.INVISIBLE);

        trophy1.setVisibility(View.INVISIBLE);

        trophy2.setVisibility(View.INVISIBLE);

        trophy3.setVisibility(View.INVISIBLE);

        firstPName1.setVisibility(View.INVISIBLE);

        firstPName2.setVisibility(View.INVISIBLE);

        secondPName1.setVisibility(View.INVISIBLE);

        secondPName2.setVisibility(View.INVISIBLE);

        thirdPName1.setVisibility(View.INVISIBLE);

        thirdPName2.setVisibility(View.INVISIBLE);

        questionScore1.setVisibility(View.INVISIBLE);

        questionScore2.setVisibility(View.INVISIBLE);

        questionScore3.setVisibility(View.INVISIBLE);

        exitBtn.setVisibility(View.INVISIBLE);

        exitBtnTxt.setVisibility(View.INVISIBLE);

        playAgainBtn.setVisibility(View.INVISIBLE);

        playAgainBtnTxt.setVisibility(View.INVISIBLE);

        seeMoreBtnTxt.setVisibility(View.INVISIBLE);

        seeMoreBtn.setVisibility(View.INVISIBLE);

        startAnimation();

    }

    public void openMoreResults(View view) {

        moreResultsBlack.setVisibility(View.VISIBLE);

        moreResultsPanel.setVisibility(View.VISIBLE);

        resultsPanelTxt.setVisibility(View.VISIBLE);

        resultsPanelTxt2.setVisibility(View.VISIBLE);

        resultsGridLayout.setVisibility(View.VISIBLE);

        XresultsPanel.setVisibility(View.VISIBLE);

    }




    public void closeMoreResults(View view) {

        moreResultsBlack.setVisibility(View.INVISIBLE);

        moreResultsPanel.setVisibility(View.INVISIBLE);

        resultsPanelTxt.setVisibility(View.INVISIBLE);

        resultsPanelTxt2.setVisibility(View.INVISIBLE);

        resultsGridLayout.setVisibility(View.INVISIBLE);

        XresultsPanel.setVisibility(View.INVISIBLE);

    }

    public void goHome(View view) {

        if (fanfare.isPlaying()) {

            fanfare.stop();

        }

        if (music.isPlaying()) {

            music.stop();

        }

        Intent intent = new Intent(this,  MainActivity.class);

        startActivity(intent);

    }

    public void playAgain(View view) {



    }

    public void startAnimation() {

        new CountDownTimer(1000 + 100,1000){

            @Override

            public void onTick(long millisUntilFinished) {

            }

            @Override

            public void onFinish() {


                ground.setScaleX(0);

                ground.setVisibility(View.VISIBLE);

                ground.animate().scaleXBy(1f).setDuration(1000).withEndAction(new Runnable() {

                    @Override

                    public void run() {

                        animateBacklight();

                    }

                });


            }

        }.start();

    }

    public void animateBacklight() {

        backlight.setAlpha(0f);

        backlight.setVisibility(View.VISIBLE);

        backlight.animate().alpha(1f).setDuration(600).withEndAction(new Runnable() {

            @Override

            public void run() {

                animatePodium();

            }

        }).start();


    }

    public void animatePodium() {

        podiums = new ArrayList();

        podiums.add(podium3);

        podiums.add(podium2);

        podiums.add(podium1);

        scaleAnim = new ScaleAnimation(

                1f, 1f,

                0f, 1f,

                Animation.ABSOLUTE, 0,

                Animation.RELATIVE_TO_SELF , 1);

        scaleAnim.setDuration(1200);

        scaleAnim.setRepeatCount(0);

        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnim.setFillAfter(true);

        scaleAnim.setFillBefore(true);

        scaleAnim.setFillEnabled(true);

        podiums.get(marker).setVisibility(View.VISIBLE);

        podiums.get(marker).startAnimation(scaleAnim);

        podiumAlreadyAnimated[0] = true;

        scaleAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override

            public void onAnimationStart(Animation arg0) {

            }

            @Override

            public void onAnimationRepeat(Animation arg0) {

            }

            @Override

            public void onAnimationEnd(Animation arg0) {

                podiums.get(marker).setAnimation(null);

                Log.i("This is getting run", "this isn't getting run");

                Log.i("marker", "" + marker);

                marker++;

                Log.i("marker2", "" + marker);

                if (marker == 3) {

                    scaleAnim = null;
//
//                    num1.setAlpha(0f);
//
//                    num2.setAlpha(0f);
//
//                    num3.setAlpha(0f);

                    num1.setVisibility(View.VISIBLE);

                    num2.setVisibility(View.VISIBLE);

                    num3.setVisibility(View.VISIBLE);

                    Animation fadeIn = AnimationUtils.loadAnimation(MultiplayerResults.this, R.anim.fade_in);
//
//                    num1.animate().alpha(1f).setDuration(600).start();
//
//                    num2.animate().alpha(1f).setDuration(600).start();
//
//                    num3.animate().alpha(1f).setDuration(600).start();

                    num1.startAnimation(fadeIn);

                    num2.startAnimation(fadeIn);

                    num3.startAnimation(fadeIn);

                    animatePodiumTops();

                    return;

                }

                if (podiumAlreadyAnimated[marker] == false) {

                    podiums.get(marker).setVisibility(View.VISIBLE);

                    podiums.get(marker).startAnimation(scaleAnim);

                    Log.i("podiums.get(marker)","" + podiums.get(marker).getId());

                    podiumAlreadyAnimated[marker] = true;

                }

            }

        });

    }

    public void animatePodiumTops() {

        scaleAnim = new ScaleAnimation(

                0f, 1f,

                1f, 1f,

                Animation.RELATIVE_TO_SELF, 1f,

                Animation.RELATIVE_TO_SELF , 0);

        scaleAnim.setDuration(850);

        scaleAnim.setRepeatCount(0);

        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnim.setFillAfter(true);

        scaleAnim.setFillBefore(true);

        scaleAnim.setFillEnabled(true);

        scaleAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override

            public void onAnimationStart(Animation arg0) {

            }

            @Override

            public void onAnimationRepeat(Animation arg0) {

            }

            @Override

            public void onAnimationEnd(Animation arg0) {

                animateTrophies();

            }

        });

        scaleAnim2 = new ScaleAnimation(

                0f, 1f,

                1f, 1f,

                Animation.ABSOLUTE, 1,

                Animation.RELATIVE_TO_SELF , 0);

        scaleAnim2.setDuration(850);

        scaleAnim2.setRepeatCount(0);

        scaleAnim2.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnim2.setFillAfter(true);

        scaleAnim2.setFillBefore(true);

        scaleAnim2.setFillEnabled(true);

        scaleAnim2.setAnimationListener(new Animation.AnimationListener() {

            @Override

            public void onAnimationStart(Animation arg0) {

            }

            @Override

            public void onAnimationRepeat(Animation arg0) {

            }

            @Override

            public void onAnimationEnd(Animation arg0) {


            }

        });

        podiumTop1.setScaleX(0);

        podiumTop1.setVisibility(View.VISIBLE);

        podiumTop1.animate().scaleXBy(1f).setDuration(1000).withEndAction(new Runnable() {

            @Override

            public void run() {

                podiumTop2.setVisibility(View.VISIBLE);

                podiumTop2.startAnimation(scaleAnim);

                podiumTop3.setVisibility(View.VISIBLE);

                podiumTop3.startAnimation(scaleAnim2);

            }

        });

    }

    public void animateTrophies() {

        trophy1.setVisibility(View.VISIBLE);

        trophy2.setVisibility(View.VISIBLE);

        trophy3.setVisibility(View.VISIBLE);

        Animation moveUp = AnimationUtils.loadAnimation(MultiplayerResults.this, R.anim.move_up);

        trophy1.startAnimation(moveUp);

        trophy2.startAnimation(moveUp);

        trophy3.startAnimation(moveUp);

        moveUp.setAnimationListener(new Animation.AnimationListener(){

            @Override

            public void onAnimationStart(Animation animation) {

            }

            @Override

            public void onAnimationEnd(Animation animation) {

                showText();

            }

            @Override

            public void onAnimationRepeat(Animation animation) {

            }

        });

//        trophy1.setAlpha(0f);
//
//        trophy2.setAlpha(0f);
//
//        trophy3.setAlpha(0f);
//
//        trophy1.setVisibility(View.VISIBLE);
//
//        trophy2.setVisibility(View.VISIBLE);
//
//        trophy3.setVisibility(View.VISIBLE);

//        trophy1.animate().alpha(1f).setDuration(600).withEndAction(new Runnable() {
//
//            @Override
//
//            public void run() {
//
//                showText();
//
//            }
//
//        }).start();
//
//        trophy2.animate().alpha(1f).setDuration(600).start();
//
//        trophy3.animate().alpha(1f).setDuration(600).start();
//
    }

    public void showText() {

        new CountDownTimer(500 + 100,1000){

            @Override

            public void onTick(long millisUntilFinished) {

            }

            @Override

            public void onFinish() {

                fade(questionScore1, 1f);

                fade(questionScore2, 1f);

                fade(questionScore3, 1f);

                fade(firstPName1, 1f);

                fade(firstPName2, 1f);

                fade(secondPName1, 1f);

                fade(secondPName2, 1f);

                fade(thirdPName1, 1f);

                fade(thirdPName2, 1f);

                fade(exitBtn, .25f);

                fade(exitBtnTxt, 1f);

                fade(playAgainBtn, .25f);

                fade(playAgainBtnTxt, 1f);

                fade(seeMoreBtn, .25f);

                fade(seeMoreBtnTxt, 1f);

            }

        }.start();

    }

    public void fade(View view,float f) {

        view.setAlpha(0f);

        view.setVisibility(View.VISIBLE);

        view.animate().alpha(f).setDuration(600);

    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

        Log.i("this ", "app is closing");

        if (music.isPlaying()) {

            music.stop();

        }

        if (fanfare.isPlaying()) {

            fanfare.stop();

        }

    }

}