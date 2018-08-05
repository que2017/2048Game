package com.game2048.zhang.a2048game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int score = 0;
    private TextView tvScore;
    private static MainActivity mainActivity = null;

    public MainActivity(){
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = (TextView)findViewById(R.id.tvScore);
        showScore();
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void showScore(){
        tvScore.setText(Integer.toString(score));
    }

    public void addScore(int s){
        score += s;
        showScore();
    }

    public int getScore(){
        return score;
    }
}
