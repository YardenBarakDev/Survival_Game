package com.ybdev.survivalgame;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Game extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_STATE = "EXTRA_STATE";
    int currentLevel = 0;
    int[] steps = {1, 1, 1, 2, 2, 2, 3, 3, 3};
    private ImageButton[] arrows;
    private boolean goodToGo = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        shuffleSteps();
        findViews();
        initViews();
    }

    private void shuffleSteps(){
        String id = getIntent().getStringExtra(EXTRA_ID);
        for (int i = 0; i < steps.length; i++) {
            steps[i] = Integer.parseInt(String.valueOf(id.charAt(i))) % 4;
        }
    }
    private void arrowClicked(int direction) {
        if (goodToGo && direction != steps[currentLevel]) {
            goodToGo = false;
        }
        currentLevel += 1;
        if (currentLevel >= steps.length) {
            finishGame();
        }
    }

    private void finishGame() {
        String state = getIntent().getStringExtra(EXTRA_STATE);
        if (goodToGo) {
            Toast.makeText(this, "Yarden Barak Survived in " + state, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You Failed ", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void initViews() {
        for (int i = 0; i < arrows.length; i++) {
            int finalI = i;
            arrows[i].setOnClickListener(v -> arrowClicked(finalI));
        }
    }

    private void findViews() {
        arrows = new ImageButton[]{
                findViewById(R.id.game_BTN_left),
                findViewById(R.id.game_BTN_right),
                findViewById(R.id.game_BTN_up),
                findViewById(R.id.game_BTN_down)};
    }
}
