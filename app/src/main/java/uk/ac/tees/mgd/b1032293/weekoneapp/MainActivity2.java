package uk.ac.tees.mgd.b1032293.weekoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    public void onTouchBack(View v){
        Log.d("MainActivity2", "Button Pressed");
        finish();
    }
}