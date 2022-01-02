package daniel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import daniel.ajayi.gottaget24.DonutProgress;
import daniel.ajayi.gottaget24.DonutProgress;
import daniel.ajayi.gottaget24.MainActivity;
import daniel.ajayi.gottaget24.MyService;
import daniel.ajayi.gottaget24.R;

import static daniel.ajayi.gottaget24.MainActivity.gameMusic;
import static daniel.ajayi.gottaget24.MainActivity.hardBeat;
import static daniel.ajayi.gottaget24.MainActivity.hardBeatOn;
import static daniel.ajayi.gottaget24.MainActivity.musicCurrentlyPlaying;
import static daniel.ajayi.gottaget24.MainActivity.musicMuted;
import static daniel.ajayi.gottaget24.MainActivity.musicPlaying;
import static daniel.ajayi.gottaget24.MainActivity.pos;
import static daniel.ajayi.gottaget24.MainActivity.prefs;


public class TrainMode extends AppCompatActivity {

    Random rand = new Random();

    int tries = 0;

    ArrayList<Integer> randOperation = new ArrayList();

    ArrayList<Integer> selectedNumbers = new ArrayList();

    ArrayList<Integer> selectedNumbersBackup = new ArrayList();

    ArrayList<Integer> numsUsed = new ArrayList();

    ArrayList<Integer> opersUsed = new ArrayList();

    ArrayList<String> correctAnswer = new ArrayList();

    private DonutProgress countDownProgress;

    int operationsLeft;

    double answer;

    int attempts = 0;

    final int numAttemptsBeforeTryingNewNumbers = 3000;

    int numOne;

    int numTwo;

    int numThree;

    int numFour;

    TextView num1;

    TextView num2;

    TextView num3;

    TextView num4;

    TextView txtDash1;

    TextView txtDash2;

    TextView txtDash3;

    TextView txtDash4;

    ImageView mathSymbol1;

    ImageView mathSymbol2;

    ImageView mathSymbol3;

    ImageView btnNum1;

    ImageView btnNum2;

    ImageView btnNum3;

    ImageView btnNum4;

    ImageView scoreBg;

    TextView scoreW;

    TextView scoreG;

    TextView countdownTop;

    TextView countdownBot;

    TextView youScoredTxt;

    TextView youScoredTxt2;

    TextView bigScore;

    TextView bigScore2;

    TextView okBtnTxt;

    ImageView okBtn;

    int score;

    ArrayList<View> spacesForEquation;

    ArrayList<View> numbers;

    int takenElemsOnScreen = 0;

    ImageView redDashBg;

    ImageView greenDashBg;

    ArrayList<Integer> numsUserUsed;

    ArrayList<Integer> opersUserUsed;

    Vibrator v;

    ArrayList<Boolean> numsClickedOn;

    int countdown = 3;

    ImageView keyboardBg;

    GridLayout keyboard;

    TextView get24TxtBlue;

    TextView get24TxtLightBlue;

    ImageView dashBg;

    ImageView homeBtnTrain;

    ImageView homeBtnPlay;

    ImageView dash1;

    ImageView dash2;

    ImageView dash3;

    ImageView dash4;

    ImageView blackOverlay;

    TextView hintBtnTxt;

    boolean userAskingForHint;

    int skips = 3;

    TextView skipsLeftTxt;

    boolean gameOver;

    MediaPlayer correctSfx;

    MediaPlayer incorrectSfx;

    //The following code is for changing the equation symbols used in the middle of the screen

    //imageButton.setImageResource(R.drawable.eng2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        setContentView(R.layout.activity_train_mode);

        skipsLeftTxt = findViewById(R.id.skipsLeft);

        skipsLeftTxt.setVisibility(View.INVISIBLE);

        correctSfx = MediaPlayer.create(this, R.raw.correct);

        incorrectSfx = MediaPlayer.create(this, R.raw.wrong);

        correctSfx.setVolume(115,115);

        incorrectSfx.setVolume(135,135);

        gameOver = false;

        num1 = findViewById(R.id.num1);

        num2 = findViewById(R.id.num2);

        num3 = findViewById(R.id.num3);

        num4 = findViewById(R.id.num4);

        userAskingForHint = true;

        hintBtnTxt = findViewById(R.id.hint);

        txtDash1 = findViewById(R.id.guessedNum1);

        txtDash2 = findViewById(R.id.guessedNum2);

        txtDash3 = findViewById(R.id.guessedNum3);

        txtDash4 = findViewById(R.id.guessedNum4);

        txtDash1.setText("");

        score = 0;

        txtDash2.setText("");

        txtDash3.setText("");

        txtDash4.setText("");

        mathSymbol1 = findViewById(R.id.mathSymbol1);

        mathSymbol2 = findViewById(R.id.mathSymbol2);

        mathSymbol3 = findViewById(R.id.mathSymbol3);

        mathSymbol1.setImageResource(android.R.color.transparent);

        mathSymbol2.setImageResource(android.R.color.transparent);

        mathSymbol3.setImageResource(android.R.color.transparent);

        countDownProgress = findViewById (R.id.countdown_progress);

        countDownProgress.setProgress (30);

        blackOverlay = findViewById(R.id.black);

        spacesForEquation = new ArrayList<>();

        spacesForEquation.add(txtDash1);

        spacesForEquation.add(mathSymbol1);

        spacesForEquation.add(txtDash2);

        spacesForEquation.add(mathSymbol2);

        spacesForEquation.add(txtDash3);

        spacesForEquation.add(mathSymbol3);

        spacesForEquation.add(txtDash4);

        okBtn = findViewById(R.id.okBtn);

        okBtnTxt = findViewById(R.id.okTxt);

        bigScore = findViewById(R.id.bigScore);

        bigScore2 = findViewById(R.id.bigScore2);

        youScoredTxt = findViewById(R.id.youScoredTxt);

        youScoredTxt2 = findViewById(R.id.youScoredTxt2);

        btnNum1 = findViewById(R.id.buttonNum1);

        btnNum2 = findViewById(R.id.buttonNum2);

        btnNum3 = findViewById(R.id.buttonNum3);

        btnNum4 = findViewById(R.id.buttonNum4);

        numbers = new ArrayList<>();

        numbers.add(btnNum1);

        numbers.add(num1);

        numbers.add(btnNum2);

        numbers.add(num2);

        numbers.add(btnNum3);

        numbers.add(num3);

        numbers.add(btnNum4);

        numbers.add(num4);

        redDashBg = findViewById(R.id.redDashBg);

        greenDashBg = findViewById(R.id.greenDashBg);

        keyboardBg = findViewById(R.id.keyboardBg);

        keyboard = findViewById(R.id.keyboard);

        countdownTop = findViewById(R.id.countdownTop);

        countdownBot = findViewById(R.id.countdownBot);

        get24TxtBlue = findViewById(R.id.get24Bot);

        get24TxtLightBlue = findViewById(R.id.get24Top);

        dashBg = findViewById(R.id.dashBg);

        homeBtnPlay = findViewById(R.id.playHome);

        homeBtnTrain = findViewById(R.id.trainHome);

        dash1 = findViewById(R.id.dash);

        dash2 = findViewById(R.id.dash2);

        dash3 = findViewById(R.id.dash3);

        dash4 = findViewById(R.id.dash4);

        correctAnswer = new ArrayList<>();

        numsUserUsed = new ArrayList<>();

        opersUserUsed = new ArrayList<>();

        randOperation.add(0);

        randOperation.add(1);

        randOperation.add(2);

        randOperation.add(3);

        //Operations left to be used to make 24

        operationsLeft = 3;

        selectedNumbersBackup = chooseNums();

        //Generate four random numbers

        for (int i : selectedNumbersBackup) {

            selectedNumbers.add(i);

        }

        solveQuestion();

        scoreBg = findViewById(R.id.scoreBg);

        scoreW = findViewById(R.id.scoreW);

        scoreG = findViewById(R.id.scoreG);

        if (MainActivity.gameMode == MainActivity.PLAY) {

            scoreBg.setVisibility(View.VISIBLE);

            scoreW.setVisibility(View.VISIBLE);

            scoreG.setVisibility(View.VISIBLE);

            homeBtnTrain.setVisibility(View.INVISIBLE);

            countdownTop.setVisibility(View.VISIBLE);

            countdownBot.setVisibility(View.VISIBLE);

            Log.i("!@#$%This","is also happening");

            //check if activity is showing on screen

            startCountdownTimer();

        } else {

            countDownProgress.setVisibility(View.INVISIBLE);

            homeBtnPlay.setVisibility(View.INVISIBLE);

            homeBtnTrain.setVisibility(View.VISIBLE);

        }

    }

    public ArrayList<Integer> chooseNums() {

        //Generate and return four random numbers

        numOne = 0;

        numTwo = 0;

        numThree = 0;

        numFour = 0;

        int zeros = 0;

        boolean runAgain = zeros >= 2 && !selectedNumbersBackup.contains(8) && !selectedNumbersBackup.contains(3);

        boolean runAgain2 = zeros >= 2 && !selectedNumbersBackup.contains(6) && !selectedNumbersBackup.contains(4);

        while (numOne + numTwo + numThree + numFour < 9 || runAgain || runAgain2) {

            selectedNumbersBackup.clear();

            numOne = rand.nextInt(10);

            numTwo = rand.nextInt(10);

            numThree = rand.nextInt(10);

            numFour = rand.nextInt(10);

            if (numOne == 0) {

                zeros++;

            }

            if (numTwo == 0) {

                zeros++;

            }

            if (numThree == 0) {

                zeros++;

            }

            if (numFour == 0) {

                zeros++;

            }

            selectedNumbersBackup.add(numOne);

            selectedNumbersBackup.add(numTwo);

            selectedNumbersBackup.add(numThree);

            selectedNumbersBackup.add(numFour);

            runAgain = zeros >= 2 && !selectedNumbersBackup.contains(8) && !selectedNumbersBackup.contains(3);

            runAgain2 = zeros >= 2 && !selectedNumbersBackup.contains(6) && !selectedNumbersBackup.contains(4);

        }

        return selectedNumbersBackup;

    }


    public void solveQuestion() {

        //choose one number randomly from the ArrayList of four randomly selected numbers

        operationsLeft++;

        int firstNum;

        firstNum = chooseNumRand(selectedNumbers);

        answer = firstNum;

        numsUsed.add(firstNum);

        //While it hasn't used all four numbers yet, perform another operation involving another number
        
        while (operationsLeft > 0 && attempts < numAttemptsBeforeTryingNewNumbers) {


            int tryThisNum = chooseNumRand(selectedNumbers);

            int tryThisOper = randOperation.get(rand.nextInt(4));
            
            answer = performOperation(answer, tryThisNum, tryThisOper);

            opersUsed.add(tryThisOper);

            numsUsed.add(tryThisNum);

            attempts++;

        }

        if (answer == 24) {

            //If the answer is equal to 24, it got it right

            String equation = "";

            for (int i = 0; i < numsUsed.size()- 1; i++) {

                equation += numsUsed.get(i) + " ";

                    equation += corrMathSymbol(opersUsed.get(i)) + " ";

                correctAnswer.add(numsUsed.get(i) + "");

                correctAnswer.add(corrMathSymbol(opersUsed.get(i)));

            }

            equation += numsUsed.get(numsUsed.size()- 1);

            correctAnswer.add(numsUsed.get(numsUsed.size()- 1) + "");

            Log.i("!@#$%24", equation);

            setButtonsToNumbers();

        } else if (attempts >= numAttemptsBeforeTryingNewNumbers) {

            Log.i("!@#$%challenging numbers", numOne + ", " + numTwo + ", " + numThree + ", " + numFour + ", ");

            resetAndGenerateNewQuestion();

            solveQuestion();

        } else {

            // If the answer is not equals to 24, try again

            System.gc();

            tries++;

            String equation = "";

            for (int i = 0; i < numsUsed.size()- 1; i++) {

                equation += numsUsed.get(i) + " ";

                equation += corrMathSymbol(opersUsed.get(i)) + " ";

            }

            equation += numsUsed.get(numsUsed.size()- 1);

            Log.i("!@#$%Equation I tried","" + equation);

            Log.i("!@#$%tries",tries+"");

            tryAgain();

        }

    }

    public void resetAndGenerateNewQuestion() {

        operationsLeft = 3;

        selectedNumbersBackup = chooseNums();

        numsUsed.clear();

        opersUsed.clear();

        selectedNumbers.clear();

        //Generate four random numbers

        for (int i : selectedNumbersBackup) {

            selectedNumbers.add(i);

        }

        Log.i("!@#$%Resetting","attempts hit zero");

        attempts = 0;

    }

    public void tryAgain() {

        operationsLeft = 3;

        selectedNumbers.clear();

        for (int i : selectedNumbersBackup) {

            selectedNumbers.add(i);

        }

        numsUsed.clear();

        opersUsed.clear();

        System.gc();

        solveQuestion();

    }


    public double performOperation (double a, double b, int oper) {

        if (oper == 0) {

            return a + b;

        } else if (oper == 1) {

            return a - b;

        } else if (oper == 2) {

            return a * b;

        } else {

            return a / b;

        }

    }



    public int chooseNumRand (ArrayList<Integer> numbers) {

        int num;

        //choose a random number from the arraylist of numbers that were chosen

        int indexOfNumUsed = rand.nextInt(operationsLeft);

        num = selectedNumbers.get(indexOfNumUsed);

        //remove that number from the arraylist

        selectedNumbers.remove(indexOfNumUsed);

        //remove from the number of operations left to make

        operationsLeft--;

        // Return randomly selected number from arraylist

        return num;

    }

    public String corrMathSymbol (int i) {

        if (i == 0) {

            return "+";

        } else if (i == 1) {

            return "-";

        } else if (i == 2) {

            return "x";

        } else {

            return "/";

        }

    }

    public void setButtonsToNumbers() {

        num1.setText(numOne + "");

        num2.setText(numTwo + "");

        num3.setText(numThree + "");

        num4.setText(numFour + "");

        btnNum1.setTag(numOne);

        btnNum2.setTag(numTwo);

        btnNum3.setTag(numThree);

        btnNum4.setTag(numFour);

    }

    public void updateEquationOnScreen(View view) {

        if (takenElemsOnScreen == 7 || view.getAlpha() != 1 || gameOver) {

            return;

        }

        //set the next empty spot in the equation equal to the text of the TextView just clicked

        //If the next element of the equation is a math symbol and not a number

        if(!spacesForEquation.get(takenElemsOnScreen).getTag().toString().equals("number")) {

            if (view.getTag().toString().equals("plus")) {

                ((ImageView)spacesForEquation.get(takenElemsOnScreen)).setImageResource(R.drawable.add);

                Log.i("!@#$%takenElemsOnScreen","" + takenElemsOnScreen);

                Log.i("!@#$%spacesForEquation","" + spacesForEquation.get(takenElemsOnScreen));

                takenElemsOnScreen++;
                
                opersUserUsed.add(0);

            } else if (view.getTag().toString().equals("minus")) {

                ((ImageView)spacesForEquation.get(takenElemsOnScreen)).setImageResource(R.drawable.subtract);

                Log.i("!@#$%takenElemsOnScreen","" + takenElemsOnScreen);

                Log.i("!@#$%spacesForEquation","" + spacesForEquation.get(takenElemsOnScreen));

                takenElemsOnScreen++;

                opersUserUsed.add(1);

            } else if (view.getTag().toString().equals("multiply")) {

                ((ImageView)spacesForEquation.get(takenElemsOnScreen)).setImageResource(R.drawable.multiply);

                Log.i("!@#$%takenElemsOnScreen","" + takenElemsOnScreen);

                Log.i("!@#$%spacesForEquation","" + spacesForEquation.get(takenElemsOnScreen));

                takenElemsOnScreen++;

                opersUserUsed.add(2);

            } else if (view.getTag().toString().equals("divide")) {

                ((ImageView)spacesForEquation.get(takenElemsOnScreen)).setImageResource(R.drawable.divide);

                Log.i("!@#$%takenElemsOnScreen","" + takenElemsOnScreen);

                Log.i("!@#$%spacesForEquation","" + spacesForEquation.get(takenElemsOnScreen));

                takenElemsOnScreen++;

                opersUserUsed.add(3);

            }

        } else {

            //Check to see that the button clicked on is a number and not a math symbol before setting the equation equal to the tag

            if (!view.getTag().toString().equals("plus") && !view.getTag().toString().equals("minus")
                    && !view.getTag().toString().equals("multiply") && !view.getTag().toString().equals("divide")) {

                ((TextView)spacesForEquation.get(takenElemsOnScreen)).setText(view.getTag().toString());

                int i = numbers.indexOf(view);

                ((TextView)spacesForEquation.get(takenElemsOnScreen)).setTag("number" + numbers.get(i + 1).getTag().toString());

                numbers.get(i).setAlpha(.5f);

                numbers.get(i + 1).setAlpha(.5f);

                takenElemsOnScreen++;

                numsUserUsed.add(Integer.parseInt(view.getTag().toString()));

            }

        }

        if (takenElemsOnScreen == 7) {

            checkIfUserHas24();

        }

    }

    public void delete(View view) {

        if (takenElemsOnScreen == 0 || gameOver) {

            return;

        } else if (takenElemsOnScreen == 7) {

            redDashBg.setVisibility(View.INVISIBLE);

        }

        if(spacesForEquation.get(takenElemsOnScreen - 1).getTag().toString().contains("number")) {

            String corrBtnNum = "";

            if (spacesForEquation.get(takenElemsOnScreen - 1).getTag().toString().contains("zero")) {

                corrBtnNum = "zero";

            } else if (spacesForEquation.get(takenElemsOnScreen - 1).getTag().toString().contains("one")) {

                corrBtnNum = "one";

            } else if (spacesForEquation.get(takenElemsOnScreen - 1).getTag().toString().contains("two")) {

                corrBtnNum = "two";

            } else if (spacesForEquation.get(takenElemsOnScreen - 1).getTag().toString().contains("three")) {

                corrBtnNum = "three";

            }

            for (int i = 0; i < numbers.size(); i++) {

                if (numbers.get(i).getTag().toString().contains(corrBtnNum + "")) {

                    numbers.get(i).setAlpha(1f);

                    numbers.get(i - 1).setAlpha(1f);

                    i = numbers.size();

                }

            }

            spacesForEquation.get(takenElemsOnScreen - 1).setTag("number");

            ((TextView)spacesForEquation.get(takenElemsOnScreen - 1)).setText("");

            numsUserUsed.remove(numsUserUsed.size() - 1);

        } else {

            ((ImageView)spacesForEquation.get(takenElemsOnScreen - 1)).setImageResource(android.R.color.transparent);

            opersUserUsed.remove(numsUserUsed.size() - 1);

        }

        takenElemsOnScreen--;

    }


    public void checkIfUserHas24() {

        double solution = numsUserUsed.get(0);

        double solution2 = -1;

        double solution3 = -1;

        double solution4 = -1;

        double solution5 = -1;

        double solution6 = -1;

        ArrayList<Double> numsUserUsed2 = new ArrayList<>();

        for (int i : numsUserUsed) {

            numsUserUsed2.add((double) i);

        }



        ArrayList<Integer> opersUserUsed2 = new ArrayList<>();

        for (int i : opersUserUsed) {

            opersUserUsed2.add(i);

        }

        if (opersUserUsed.contains(2)) {

            solution2 = numsUserUsed.get(opersUserUsed.indexOf(2));

            solution2 = performOperation(solution2, numsUserUsed.get(opersUserUsed.indexOf(2) + 1), opersUserUsed.get(opersUserUsed.indexOf(2)));

            numsUserUsed2.remove(opersUserUsed.indexOf(2));

            numsUserUsed2.remove(opersUserUsed.indexOf(2));

            numsUserUsed2.add(opersUserUsed.indexOf(2), solution2);

            opersUserUsed2.remove(opersUserUsed.indexOf(2));

        }

        if (opersUserUsed2.contains(3)) {

            solution2 = numsUserUsed2.get(opersUserUsed2.indexOf(3));

            solution2 = performOperation(solution2, numsUserUsed2.get(opersUserUsed2.indexOf(3) + 1), opersUserUsed2.get(opersUserUsed2.indexOf(3)));

            numsUserUsed2.remove(opersUserUsed2.indexOf(3));

            numsUserUsed2.remove(opersUserUsed2.indexOf(3));

            numsUserUsed2.add(opersUserUsed2.indexOf(3), solution2);

            opersUserUsed2.remove(opersUserUsed2.indexOf(3));

        }

        for (int i = 0; i < opersUserUsed2.size(); i++) {

            solution2 = performOperation(numsUserUsed2.get(i),numsUserUsed2.get(i+1), opersUserUsed2.get(i));

        }

        for (int i = 0; i < 3; i++) {

            solution = performOperation(solution, numsUserUsed.get(i + 1), opersUserUsed.get(i));

        }

        // computing a + (b + c) + d

        numsUserUsed2.clear();

        opersUserUsed2.clear();

        for (int i : opersUserUsed) {

            opersUserUsed2.add(i);

        }

        for (int i : numsUserUsed) {

            numsUserUsed2.add((double) i);

        }

        solution3 = numsUserUsed2.get(1);

        solution3 = performOperation(solution3, numsUserUsed2.get(2), opersUserUsed2.get(1));

        numsUserUsed2.remove(1);

        numsUserUsed2.remove(1);

        numsUserUsed2.add(1,solution3);

        opersUserUsed2.remove(1);

        solution3 = numsUserUsed2.get(0);

        for (int i = 0; i < 2; i++) {

            solution3 = performOperation(solution3, numsUserUsed2.get(i + 1), opersUserUsed2.get(i));

        }

        // computing a + b + (c + d) SOLUTION 4

        numsUserUsed2.clear();

        opersUserUsed2.clear();

        for (int i : opersUserUsed) {

            opersUserUsed2.add(i);

        }

        for (int i : numsUserUsed) {

            numsUserUsed2.add((double) i);

        }

        solution4 = numsUserUsed2.get(2);

        solution4 = performOperation(solution4, numsUserUsed2.get(3), opersUserUsed2.get(2));

        numsUserUsed2.remove(2);

        numsUserUsed2.remove(2);

        numsUserUsed2.add(2,solution4);

        opersUserUsed2.remove(2);

        solution4 = numsUserUsed2.get(0);

        for (int i = 0; i < 2; i++) {

            solution4 = performOperation(solution4, numsUserUsed2.get(i + 1), opersUserUsed2.get(i));

        }

        // computing a + ((b + c) + d) SOLUTION 5

        numsUserUsed2.clear();

        opersUserUsed2.clear();

        for (int i : opersUserUsed) {

            opersUserUsed2.add(i);

        }

        for (int i : numsUserUsed) {

            numsUserUsed2.add((double) i);

        }

        solution4 = numsUserUsed2.get(1);

        solution4 = performOperation(solution4, numsUserUsed2.get(2), opersUserUsed2.get(1));

        solution4 = performOperation(solution4, numsUserUsed2.get(3), opersUserUsed2.get(2));

        numsUserUsed2.remove(1);

        numsUserUsed2.remove(1);

        numsUserUsed2.remove(1);

        numsUserUsed2.add(solution4);

        opersUserUsed2.remove(1);

        opersUserUsed2.remove(1);

        solution4 = performOperation(numsUserUsed2.get(0), solution4, opersUserUsed2.get(0));

        // computing a + (b + (c + d)) SOLUTION 5

        numsUserUsed2.clear();

        opersUserUsed2.clear();

        for (int i : opersUserUsed) {

            opersUserUsed2.add(i);

        }

        for (int i : numsUserUsed) {

            numsUserUsed2.add((double) i);

        }

        solution5 = numsUserUsed2.get(2);

        solution5 = performOperation(solution5, numsUserUsed2.get(3),opersUserUsed2.get(2));

        solution5 = performOperation(numsUserUsed2.get(1), solution5, opersUserUsed2.get(1));

        solution5 = performOperation(numsUserUsed2.get(0), solution5, opersUserUsed2.get(0));


        // computing a + (b + (c + d)) SOLUTION 6

        solution6 = numsUserUsed2.get(0);

        solution6 = performOperation(solution6, numsUserUsed2.get(1),opersUserUsed2.get(0));

        double temp = 0;

        temp = numsUserUsed2.get(2);

        temp = performOperation(temp, numsUserUsed2.get(3), opersUserUsed2.get(2));

        solution6 = performOperation(solution6, temp, opersUserUsed2.get(1));



        Log.i("!@#$%solution 1",""+solution);

        Log.i("!@#$%solution 2",""+solution2);

        Log.i("!@#$%solution 3",""+solution3);

        Log.i("!@#$%solution 4",""+solution4);

        Log.i("!@#$%solution 5",""+solution5);

        Log.i("!@#$%solution 6",""+solution6);


        if (solution == 24 || solution2 == 24 || solution3 == 24 || solution4 == 24 || solution5 == 24 || solution6 == 24) {

            correctSfx.start();

            score++;

            if (scoreW.getVisibility() == View.VISIBLE) {

                if (score >= 10) {

                    scoreW.setTextSize(40f);

                    scoreG.setTextSize(40f);

                }

                scoreW.setText(score + "");

                scoreG.setText(score + "");

            }


            greenDashBg.setVisibility(View.VISIBLE);

            //This countdown timer is if you get the answer right

            new CountDownTimer(1200 + 100,1000){

                @Override

                public void onTick(long millisUntilFinished) {

                }

                @Override

                public void onFinish() {

                    greenDashBg.setVisibility(View.INVISIBLE);

                    correctAnswer.clear();

                    resetUserResponse();

                    resetTags();

                    resetAndGenerateNewQuestion();

                    solveQuestion();

                    for (View v : numbers) {

                        v.setAlpha(1f);

                    }

                }

            }.start();


        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.EFFECT_TICK));

            }

            redDashBg.setVisibility(View.VISIBLE);

            incorrectSfx.start();

        }

    }

    public void resetTags() {

        for (int i = 0; i < spacesForEquation.size(); i++) {

            if (i % 2 == 0) {

                spacesForEquation.get(i).setTag("number");

            }

        }

    }

    public void resetUserResponse() {

        txtDash1.setText("");

        txtDash2.setText("");

        txtDash3.setText("");

        txtDash4.setText("");

        mathSymbol1.setImageResource(android.R.color.transparent);

        mathSymbol2.setImageResource(android.R.color.transparent);

        mathSymbol3.setImageResource(android.R.color.transparent);

        numsUserUsed.clear();

        opersUserUsed.clear();

        takenElemsOnScreen = 0;

    }

    public void goHome(View view) {

        musicPlaying = true;

        if (view.getTag().equals("homeBtn") && gameOver) {

            return;

        }

        Intent intent = new Intent(this,  MainActivity.class);

        startActivity(intent);

    }

    public void revealAnswer(View view) {

        if (gameOver) {

            return;

        }

        if (MainActivity.gameMode == MainActivity.TRAIN && userAskingForHint) {

            for (int i = 0; i < 7; i++) {

                if (i % 2 == 0) {

                    ((TextView)spacesForEquation.get(i)).setText(correctAnswer.get(i));

                } else {

                    if (correctAnswer.get(i).equals("+")) {

                        ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.add);

                    } else if (correctAnswer.get(i).equals("-")) {

                        ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.subtract);

                    } else if (correctAnswer.get(i).equals("x")) {

                        ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.multiply);

                    } else if (correctAnswer.get(i).equals("/")) {

                        ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.divide);

                    }

                }

            }

            hintBtnTxt.setText("OK");

            hintBtnTxt.setTextSize(40f);

            userAskingForHint = false;

        } else if (MainActivity.gameMode == MainActivity.TRAIN && !userAskingForHint) {

            correctAnswer.clear();

            for (View v : numbers) {

                v.setAlpha(1f);

            }

            hintBtnTxt.setTextSize(50f);

            hintBtnTxt.setText("?");

            userAskingForHint = true;

            resetUserResponse();

            resetAndGenerateNewQuestion();

            solveQuestion();

        } else if (MainActivity.gameMode == MainActivity.PLAY) {

            if (skips > 0) {

                resetUserResponse();

                resetTags();

                resetAndGenerateNewQuestion();

                solveQuestion();

                redDashBg.setVisibility(View.INVISIBLE);

                for (View v : numbers) {

                    v.setAlpha(1f);

                }

            }

            if (skips >= 0) {

                skips--;

            }

            updateSkipsLeft();

        }

    }

    public void updateSkipsLeft() {

        if (skips < 0) {

            skipsLeftTxt.setText("Out of Skips");

        } else {

            skipsLeftTxt.setText(skips + " Skips Left");

        }

        skipsLeftTxt.setAlpha(1f);

        skipsLeftTxt.setVisibility(View.VISIBLE);

        skipsLeftTxt.animate().alpha(0.0f).setDuration(1200).withEndAction(new Runnable() {

            @Override

            public void run() {

                skipsLeftTxt.setVisibility(View.INVISIBLE);

            }

        });

    }

    public void startCountdownTimer() {

        scoreBg.setVisibility(View.INVISIBLE);

        scoreG.setVisibility(View.INVISIBLE);

        scoreW.setVisibility(View.INVISIBLE);

        homeBtnPlay.setVisibility(View.INVISIBLE);

        keyboard.setVisibility(View.INVISIBLE);

        keyboardBg.setVisibility(View.INVISIBLE);

        txtDash1.setVisibility(View.INVISIBLE);

        txtDash2.setVisibility(View.INVISIBLE);

        txtDash3.setVisibility(View.INVISIBLE);

        txtDash4.setVisibility(View.INVISIBLE);

        dash1.setVisibility(View.INVISIBLE);

        dash2.setVisibility(View.INVISIBLE);

        dash3.setVisibility(View.INVISIBLE);

        dash4.setVisibility(View.INVISIBLE);

        dashBg.setVisibility(View.INVISIBLE);

        countDownProgress.setVisibility(View.INVISIBLE);

        get24TxtBlue.setVisibility(View.INVISIBLE);

        get24TxtLightBlue.setVisibility(View.INVISIBLE);

        //this countdown timer is the initial 3, 2, 1 timer

        new CountDownTimer(4500,1000){

            @Override

            public void onTick(long millisUntilFinished) {

                if (countdown > 0) {

                    countdownTop.setText(countdown + "");

                    countdownBot.setText(countdown + "");

                    countdown--;

                } else {

                    countdownTop.setText("Go!");

                    countdownBot.setText("Go!");

                }

            }

            @Override

            public void onFinish() {

                countdownTop.setVisibility(View.INVISIBLE);

                countdownBot.setVisibility(View.INVISIBLE);

                scoreBg.setVisibility(View.VISIBLE);

                scoreG.setVisibility(View.VISIBLE);

                scoreW.setVisibility(View.VISIBLE);

                homeBtnPlay.setVisibility(View.VISIBLE);

                keyboard.setVisibility(View.VISIBLE);

                keyboardBg.setVisibility(View.VISIBLE);

                txtDash1.setVisibility(View.VISIBLE);

                txtDash2.setVisibility(View.VISIBLE);

                txtDash3.setVisibility(View.VISIBLE);

                txtDash4.setVisibility(View.VISIBLE);

                dashBg.setVisibility(View.VISIBLE);

                get24TxtBlue.setVisibility(View.VISIBLE);

                get24TxtLightBlue.setVisibility(View.VISIBLE);

                dash1.setVisibility(View.VISIBLE);

                dash2.setVisibility(View.VISIBLE);

                dash3.setVisibility(View.VISIBLE);

                dash4.setVisibility(View.VISIBLE);

                dashBg.setVisibility(View.VISIBLE);

                countDownProgress.setVisibility(View.VISIBLE);

                startTimer();

            }

        }.start();

    }

    public void startTimer() {

        //This countdown timer is the game timer

        new CountDownTimer(90 * 1000 + 100, 1000) {

            @Override

            public void onTick(long millisUntilFinished) {

                countDownProgress.setProgress((int) millisUntilFinished / 1000);

            }

            @Override

            public void onFinish() {

                blackOverlay.setAlpha(0f);

                blackOverlay.setVisibility(View.VISIBLE);

                blackOverlay.animate().alpha(0.5f).setDuration(700).withEndAction(new Runnable() {

                    @Override

                    public void run() {

                        youScoredTxt.setVisibility(View.VISIBLE);

                        bigScore.setText(score + "");

                        bigScore2.setText(score + "");

                        bigScore.setVisibility(View.VISIBLE);

                        bigScore2.setVisibility(View.VISIBLE);

                        okBtn.setVisibility(View.VISIBLE);

                        okBtnTxt.setVisibility(View.VISIBLE);

                        gameOver = true;

                    }

                });

                if (score > MainActivity.highScore) {

                    prefs.edit().putInt("highScore", score).apply();

                }

            }

        }.start();

    }
//
//        @Override
//
//        protected void onPause() {
//
//            super.onPause();
//
//            Log.i("!@#$%LEAVING GAME MODE", "");
//
//            Log.i("!@#$%hardBeat.isPlaying","" + hardBeat.isPlaying());
//
////            musicPlaying = hardBeat.isPlaying() || gameMusic.isPlaying();
//
//            Log.i("!@#$%Activity","leaving play mode");
//
//            if (this.isFinishing()) {
//
//                if (hardBeat.isPlaying()) {
//
//                    hardBeat.pause();
//
//                    pos =  hardBeat.getCurrentPosition();
//
//                    Log.i("!@#$%music status","music stopped");
//
//                } else if ( gameMusic.isPlaying()) {
//
//                    gameMusic.pause();
//
//                    pos =  hardBeat.getCurrentPosition();
//
//                }
//
//                Log.i("!@#$%music status","stopped");
//
//            }
//
//            Context context = getApplicationContext();
//
//            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//
//            if (!taskInfo.isEmpty()) {
//
//                ComponentName topActivity = taskInfo.get(0).topActivity;
//
//                if (!topActivity.getPackageName().equals(context.getPackageName())) {
//
//                    if (hardBeat.isPlaying()) {
//
//                        Log.i("!@#$%App status","Leaving game mode, music was playing and is turning off");
//
//                        hardBeat.pause();
//
//                        musicPlaying = false;
//
//                    }
//
//                    if (gameMusic.isPlaying()) {
//
//                        Log.i("!@#$%App status","Leaving game mode, music was playing and is turning off");
//
//                        gameMusic.pause();
//
//                        musicPlaying = false;
//
//                    }
//
//                    Log.i("!@#$%YOU LEFT The APP","");
//                }
//                else {
//
//                    Log.i("!@#$%YOU SWITCHED ACTIVITIES", "WITHIN YOUR APP");
//
//                }
//
//        }
//
//    }
//
//    @Override
//
//    protected void onResume() {
//
//        super.onResume();
//
//        Log.i("!@#$%COMING TO PLAY MODE", "");
//
//        Log.i("!@#$%hardBeat.isPlaying","" + hardBeat.isPlaying());
//
////        musicPlaying = hardBeat.isPlaying() || gameMusic.isPlaying();
//
//        Log.i("!@#$%Activity aaaaa","just changed to play mode");
//
//        Log.i("!@#$%hardBeatOn", hardBeatOn + "");
//
//        Log.i("!@#$%!musicMuted", !musicMuted + "");
//
//        Log.i("!@#$%!hardBeat.isPlaying()", !hardBeat.isPlaying() + "");
//
//        Log.i("!@#$%!musicPlaying", !musicPlaying + "");
//
//        if (hardBeatOn && !musicMuted && !hardBeat.isPlaying() && !musicPlaying) {
//
//            Log.i("!@#$%music status aaaaa","resumed");
//
//            Log.i("!@#$%App status","Just entered game mode, music was not playing and is turning on");
//
//            Log.i("!@#$%musicPlaying aaaaa",musicPlaying + "");
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
//            Log.i("!@#$%music status aaaaa","resumed");
//
//            Log.i("!@#$%App status","Just entered game mode, music was not playing and is turning on");
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

        if (!musicCurrentlyPlaying && !musicMuted) {

            startService(new Intent(this, MyService.class));

            musicCurrentlyPlaying = true;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

        }


    }

    @Override

    public void onBackPressed () {

    }

}