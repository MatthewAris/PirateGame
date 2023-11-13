package uk.ac.tees.mgd.b1032293.weekoneapp;

import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    //private SurfaceHolder surfaceHolder;
    private volatile boolean playing = true;
    private long timeThisFrame;
    private long fps;


    GestureDetector gestureDetector;
    ImageView boatShape, targetShape;
    int movementInteger = 50;
    TextView posXView, posYView, winScreen;
    MainActivity mainActivity;

    //gestureDetector = new GestureDetector(this, this);

    //posXView = findViewById(R.id.textX);
    //posYView = findViewById(R.id.textY);
    //winScreen = findViewById(R.id.winText);

    //boatShape = findViewById(R.id.boatShape);
    //targetShape = findViewById(R.id.goalShape);

    public GameView(MainActivity2 context) {
        super(context);

    }

    public void run() {
        boatShape = findViewById(R.id.boatShape);
        targetShape = findViewById(R.id.goalShape);

        while (playing)
        {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1)
            {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {

    }

    public void draw() {

    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView", "Interrupted");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


//    public boolean dispatchTouchEvent(MotionEvents event) {
//        //TouchEvent Dispatcher
//        if (gestureDetector != null) {
//            if (gestureDetector.onTouchEvent(event)) {
//                return true;
//            }
//        }
//
//        return super.dispatchTouchEvent(event);
//    }

    public boolean onTouchEvent(MotionEvent event) {
        posXView.setText("X: " + targetShape.getX() + "Y: " + targetShape.getY());
        posYView.setText("X: " + boatShape.getX() + "Y: " + boatShape.getY());

       /* switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                boatShape.setY(boatShape.getY() - movementInteger);
                if (checkOverlap(boatShape)){ winScreen.setVisibility(View.VISIBLE); }
                break;
        } */


        return gestureDetector.onTouchEvent(event);
    }

    public boolean checkOverlap(ImageView checking) {
        if (checking.getX() >= targetShape.getX() - 5 && checking.getX() <= targetShape.getX() + 5){
            if (checking.getY() >= targetShape.getY() - 5 && checking.getY() <= targetShape.getY() + 5){
                return true;
            }
        }
        return false;
    }

//    private static final int SWIPE_THRESHOLD = 10;
//    private static final int SWIPE_VELOCITY_THRESHOLD = 10;
//
//    @Override
//    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
//
//        posXView.setText("X: " + targetShape.getX() + "Y: " + targetShape.getY());
//        posYView.setText("X: " + boatShape.getX() + "Y: " + boatShape.getY());
//
//        float diffY = e2.getY() - e1.getY();
//        float diffX = e2.getX() - e1.getX();
//
//        if (Math.abs(diffX) > Math.abs(diffY)) {
//            // Horizontal Swipe
//            if ((Math.abs(diffX) > SWIPE_THRESHOLD) && (Math.abs(velocityX)) > SWIPE_VELOCITY_THRESHOLD) {
//                if (diffX > 0) {
//                    onSwipeRight();
//                } else {
//                    onSwipeLeft();
//                }
//            }
//        } else {
//            // Vertical Swipe
//            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                if (diffY > 0) {
//                    onSwipeDown();
//                } else {
//                    onSwipeUp();
//                }
//            }
//        }
//        return true;
//    }
//
//    private void onSwipeUp() {
//        boatShape.setY(boatShape.getY() - movementInteger);
//        if (checkOverlap(boatShape)) {
//            winScreen.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void onSwipeDown() {
//        boatShape.setY(boatShape.getY() + movementInteger);
//        if (checkOverlap(boatShape)) {
//            winScreen.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void onSwipeLeft() {
//        boatShape.setX(boatShape.getX() - movementInteger);
//        if (checkOverlap(boatShape)) {
//            winScreen.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void onSwipeRight() {
//        boatShape.setX(boatShape.getX() + movementInteger);
//        if (checkOverlap(boatShape)) {
//            winScreen.setVisibility(View.VISIBLE);
//        }
//    }
}
