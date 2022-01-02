package daniel.ajayi.gottaget24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionGeneration extends AppCompatActivity {

    ImageView backArrow;

    ImageView forwardsArrow;

    TextView questionCountG;

    TextView questionCountW;

    ImageView homeBtnTrain;

    ImageView homeBtnPlay;

    ImageView dash1;

    ImageView dash2;

    ImageView dash3;

    ImageView dash4;

    TextView txtDash1;

    TextView txtDash2;

    TextView txtDash3;

    TextView txtDash4;

    ImageView mathSymbol1;

    ImageView mathSymbol2;

    ImageView mathSymbol3;

    int secsLeft = 60;

    ArrayList<Integer> numsUserUsed;

    ArrayList<Integer> opersUserUsed;

    ArrayList<View> spacesForEquation;

    int[][] equationsUserCreated;

    int currentQuestionNum = 1;

    int[] takenElemsInQuestion;

    int totalQuestionCount = 5 * (MainActivity.numPlayers - 1);

    int[][] numsOfQuestionsGenerated;

    int[][] symbolsOfQuestionsGenerated;

    ImageView redDashBg;

    ImageView greenDashBg;

    TextView oppReadyTxt;

    TextView oppReadyTxt2;

    TextView timer;

    ImageView timerBox;

    Vibrator v;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question_generation);

        totalQuestionCount = 5;

        takenElemsInQuestion = new int[totalQuestionCount];

        timer = findViewById(R.id.timer);

        timerBox = findViewById(R.id.timerBox);

        oppReadyTxt = findViewById(R.id.oppReadyTextView);

        oppReadyTxt2 = findViewById(R.id.oppReadyTextView2);

        oppReadyTxt.setText("0/" + MainActivity.numPlayers + " Opponents are ready");

        oppReadyTxt2.setText("0/" + MainActivity.numPlayers + " Opponents are ready");

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        redDashBg = findViewById(R.id.redDashBg);

        greenDashBg = findViewById(R.id.greenDashBg);

        questionCountG = findViewById(R.id.qCountG);

        questionCountW = findViewById(R.id.qCountW);

        backArrow = findViewById(R.id.backQuestionArrow);

        forwardsArrow = findViewById(R.id.forwardsQuestionArrow);

        spacesForEquation = new ArrayList<>();

        numsOfQuestionsGenerated = new int[totalQuestionCount][4];

        symbolsOfQuestionsGenerated = new int[totalQuestionCount][3];

        equationsUserCreated = new int[totalQuestionCount][7];

        for (int i = 0; i < totalQuestionCount; i++) {

            for (int j = 0; j < 7; j++) {

                equationsUserCreated[i][j] = -1;

            }

        }

        txtDash1 = findViewById(R.id.guessedNum1);

        txtDash2 = findViewById(R.id.guessedNum2);

        txtDash3 = findViewById(R.id.guessedNum3);

        txtDash4 = findViewById(R.id.guessedNum4);

        txtDash1.setText(" ");

        txtDash2.setText(" ");

        txtDash3.setText(" ");

        txtDash4.setText(" ");

        mathSymbol1 = findViewById(R.id.mathSymbol1);

        mathSymbol2 = findViewById(R.id.mathSymbol2);

        mathSymbol3 = findViewById(R.id.mathSymbol3);

        mathSymbol1.setImageResource(android.R.color.transparent);

        mathSymbol2.setImageResource(android.R.color.transparent);

        mathSymbol3.setImageResource(android.R.color.transparent);

        spacesForEquation = new ArrayList<>();

        spacesForEquation.add(txtDash1);

        spacesForEquation.add(mathSymbol1);

        spacesForEquation.add(txtDash2);

        spacesForEquation.add(mathSymbol2);

        spacesForEquation.add(txtDash3);

        spacesForEquation.add(mathSymbol3);

        spacesForEquation.add(txtDash4);

        numsUserUsed = new ArrayList<>();

        opersUserUsed = new ArrayList<>();

        timerToMakeQuestions();

    }

    public void updateQuestionCount(View view) {

        if (view == null) {

            if (currentQuestionNum == totalQuestionCount) {

                return;

            }

            currentQuestionNum++;

        } else if (view.getTag().equals("next")) {

            if (currentQuestionNum == totalQuestionCount) { //don't advance to the next question if user is already on the last question

                return;

            }

            int numCount = 0;

            int operCount = 0;

            for (int i = 0; i < 7; i++) { //save the incomplete equation that the user currently has to the 2d array of equations

                if (i % 2 == 0) {

                    if (numCount < numsUserUsed.size()) {

                        equationsUserCreated[currentQuestionNum - 1][i] = numsUserUsed.get(numCount);

                        numCount++;

                    }

                } else {

                    if (numCount < numsUserUsed.size()) {

                        equationsUserCreated[currentQuestionNum - 1][i] = opersUserUsed.get(operCount);

                        operCount++;

                    }

                }

            }

            numsUserUsed.clear();

            opersUserUsed.clear();

            for (int i = 0; i < 7; i++) {

                if (i % 2 == 0 && equationsUserCreated[currentQuestionNum][i] != -1) {

                    numsUserUsed.add(equationsUserCreated[currentQuestionNum][i]);

                } else if (equationsUserCreated[currentQuestionNum][i] != -1) {

                    opersUserUsed.add(equationsUserCreated[currentQuestionNum][i]);

                }

            }

            currentQuestionNum++;

        } else {

            if (currentQuestionNum <= 1) {

                return;

            }

            int numCount = 0;

            int operCount = 0;

            for (int i = 0; i < 7; i++) { //save the incomplete equation that the user currently has to the 2d array of equations

                if (i % 2 == 0) {

                    if (numCount < numsUserUsed.size()) {

                        equationsUserCreated[currentQuestionNum - 1][i] = numsUserUsed.get(numCount);

                        numCount++;

                    }

                } else {

                    if (numCount < numsUserUsed.size()) {

                        equationsUserCreated[currentQuestionNum - 1][i] = opersUserUsed.get(operCount);

                        operCount++;

                    }

                }

            }

            numsUserUsed.clear();

            opersUserUsed.clear();

            for (int i = 0; i < 7; i++) {

                if (i % 2 == 0 && equationsUserCreated[currentQuestionNum - 2][i] != -1) {

                    numsUserUsed.add(equationsUserCreated[currentQuestionNum - 2][i]);

                } else if (equationsUserCreated[currentQuestionNum - 2][i] != -1) {

                    opersUserUsed.add(equationsUserCreated[currentQuestionNum - 2][i]);

                }

            }

            currentQuestionNum--;

        }

            for (int i = 0; i < 7; i++) { //the number of spaces on screen

                //this means it's a number, because the format is number, symbol, number, etc.

                if (i % 2 == 0) { //For example, 1 + 2 x 3 / 4

                    if (equationsUserCreated[currentQuestionNum - 1][i] != -1) {

                        ((TextView)spacesForEquation.get(i)).setText(equationsUserCreated[currentQuestionNum - 1][i] + "");

                    } else {

                        ((TextView)spacesForEquation.get(i)).setText("");

                    }

                } else {

                    if (equationsUserCreated[currentQuestionNum - 1][i] != -1) {

                        if (equationsUserCreated[currentQuestionNum - 1][i] == 0) {

                            ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.add);

                        } else if (equationsUserCreated[currentQuestionNum - 1][i] == 1) {

                            ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.subtract);

                        } else if (equationsUserCreated[currentQuestionNum - 1][i] == 2) {

                            ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.multiply);

                        } else {

                            ((ImageView)spacesForEquation.get(i)).setImageResource(R.drawable.divide);
                        }

                    } else {

                        ((ImageView)spacesForEquation.get(i)).setImageResource(android.R.color.transparent);

                    }


                }


        }

        questionCountW.setText(currentQuestionNum + "/" + totalQuestionCount);

        questionCountG.setText(currentQuestionNum + "/" + totalQuestionCount);

    }

    public void updateEquationOnScreen(View view) {

        if (takenElemsInQuestion[currentQuestionNum - 1] == 7) {

            return;

        }

        if(!spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1]).getTag().toString().equals("number")) {

            if (view.getTag().toString().equals("plus")) {

                ((ImageView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1])).setImageResource(R.drawable.add);

                takenElemsInQuestion[currentQuestionNum - 1]++;

                opersUserUsed.add(0);

            } else if (view.getTag().toString().equals("minus")) {

                ((ImageView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1])).setImageResource(R.drawable.subtract);

                takenElemsInQuestion[currentQuestionNum - 1]++;

                opersUserUsed.add(1);

            } else if (view.getTag().toString().equals("multiply")) {

                ((ImageView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1])).setImageResource(R.drawable.multiply);

                takenElemsInQuestion[currentQuestionNum - 1]++;

                opersUserUsed.add(2);

            } else if (view.getTag().toString().equals("divide")) {

                ((ImageView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1])).setImageResource(R.drawable.divide);

                takenElemsInQuestion[currentQuestionNum - 1]++;

                opersUserUsed.add(3);

            }

        } else {

            //Check to see that the button clicked on is a number and not a math symbol before setting the equation equal to the tag

            if (!view.getTag().toString().equals("plus") && !view.getTag().toString().equals("minus")
                    && !view.getTag().toString().equals("multiply") && !view.getTag().toString().equals("divide")) {

                ((TextView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1])).setText(view.getTag().toString());

                takenElemsInQuestion[currentQuestionNum - 1]++;

                numsUserUsed.add(Integer.parseInt(view.getTag().toString()));

            }

        }

        if (takenElemsInQuestion[currentQuestionNum - 1] == 7) {

            checkIfUserHas24();

        }



    }

    public void delete(View view) {

        if (takenElemsInQuestion[currentQuestionNum - 1] == 0) {

            return;

        } else if (takenElemsInQuestion[currentQuestionNum - 1] == 7) {

            redDashBg.setVisibility(View.INVISIBLE);

        }

        if(spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1] - 1).getTag().toString().equals("number")) {

            ((TextView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1] - 1)).setText("");

            numsUserUsed.remove(numsUserUsed.size() - 1);

        } else {

            ((ImageView)spacesForEquation.get(takenElemsInQuestion[currentQuestionNum - 1] - 1)).setImageResource(android.R.color.transparent);

            opersUserUsed.remove(numsUserUsed.size() - 1);

        }

        takenElemsInQuestion[currentQuestionNum - 1]--;

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

            solution2 = numsUserUsed2.get(opersUserUsed.indexOf(2));

            solution2 = performOperation(solution2, numsUserUsed2.get(opersUserUsed2.indexOf(2) + 1), opersUserUsed2.get(opersUserUsed2.indexOf(2)));

            numsUserUsed2.remove(opersUserUsed.indexOf(2));

            numsUserUsed2.remove(opersUserUsed.indexOf(2));

            numsUserUsed2.add(opersUserUsed.indexOf(2), solution2);

            opersUserUsed2.remove(opersUserUsed.indexOf(2));

        }

        if (opersUserUsed2.contains(3)) {

            solution2 = numsUserUsed2.get(opersUserUsed2.indexOf(3));

            solution2 = performOperation(solution2, numsUserUsed2.get(opersUserUsed.indexOf(3) + 1), opersUserUsed2.get(opersUserUsed2.indexOf(3)));

            numsUserUsed2.remove(opersUserUsed.indexOf(3));

            numsUserUsed2.remove(opersUserUsed.indexOf(3));

            numsUserUsed2.add(opersUserUsed.indexOf(3), solution2);

            opersUserUsed2.remove(opersUserUsed.indexOf(3));

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


        if (solution == 24 || solution2 == 24 || solution3 == 24 || solution4 == 24 || solution5 == 24 || solution6 == 24) {

            greenDashBg.setVisibility(View.VISIBLE);

            //This countdown timer is if you get the answer right

            new CountDownTimer(1200 + 100,1000){

                @Override

                public void onTick(long millisUntilFinished) {

                }

                @Override

                public void onFinish() {

                    greenDashBg.setVisibility(View.INVISIBLE);

                    int numCount = 0;

                    int operCount = 0;

                    for (int i = 0; i < 7; i++) {

                        if (i % 2 == 0) {

                            equationsUserCreated[currentQuestionNum - 1][i] = numsUserUsed.get(numCount);

                            numCount++;

                        } else {

                            equationsUserCreated[currentQuestionNum - 1][i] = opersUserUsed.get(operCount);

                            operCount++;

                        }

                    }

                    if (currentQuestionNum != totalQuestionCount) { //change the array of the

                        // numbers of the questions on screen to be numbers on the question it's about to go to

                        numsUserUsed.clear();

                        opersUserUsed.clear();

                        for (int i = 0; i < 7; i++) {

                            if (i % 2 == 0 && equationsUserCreated[currentQuestionNum][i] != -1) {

                                numsUserUsed.add(equationsUserCreated[currentQuestionNum][i]);

                            } else if (equationsUserCreated[currentQuestionNum][i] != -1) {

                                opersUserUsed.add(equationsUserCreated[currentQuestionNum][i]);

                            }

                        }

                    }




                    updateQuestionCount(null);

                }

            }.start();


        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.EFFECT_TICK));

            }

            redDashBg.setVisibility(View.VISIBLE);

        }

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

    public void timerToMakeQuestions() {

        new CountDownTimer(60 * 1000 + 100,1000){

            @Override

            public void onTick(long millisUntilFinished) {

                secsLeft--;

                if (secsLeft <= 30) {

                    timer.setText(secsLeft + "");

                    timer.setVisibility(View.VISIBLE);

                    timerBox.setVisibility(View.VISIBLE);

                }

            }

            @Override

            public void onFinish() {



            }

        }.start();

    }


}