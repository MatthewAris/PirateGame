package uk.ac.tees.mgd.b1032293.weekoneapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;

public class GameView extends SurfaceView implements Runnable {

    protected GestureDetector gestureDetector;
    TextView posXView, posYView, winScreen;
    MainActivity mainActivity;

    //gestureDetector = new GestureDetector(this, this);

    //posXView = findViewById(R.id.textX);
    //posYView = findViewById(R.id.textY);
    //winScreen = findViewById(R.id.winText);

    private Thread gameThread;
    private final SurfaceHolder surfaceHolder;
    private volatile boolean playing = true;
    private Canvas canvas;

    private long timeThisFrame;
    private long fps;

    private long lastFrameChangeTime = 0;
    private final int frameLengthInMS = 100;


    Bitmap boatBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boat);
    private float boatX = 10;
    private float boatY = 10;

    private final float boataspectRatio = boatBitmap.getWidth() / (float) boatBitmap.getHeight();
    private final int boatWidth = 240;
    private final int boatHeight = Math.round(boatWidth / boataspectRatio);

    private final int boatFrameW = boatWidth;
    private final int boatFrameH = boatHeight;
    private final Rect boatFrameToDraw = new Rect(0, 0, boatFrameW, boatFrameH);
    private final RectF boatWhereToDraw = new RectF(boatX, boatY, boatX + boatFrameW, boatFrameH);

    Bitmap chestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_chest);
    private final float chestX = 50;
    private final float chestY = 50;

    private final float chestAspectRatio = chestBitmap.getWidth() / (float) chestBitmap.getHeight();
    private final int chestWidth = 560;
    private final int chestHeight = Math.round(chestWidth / chestAspectRatio);

    private final int chestFrameW = chestWidth / 4;
    private final int chestFrameH = chestHeight;
    private final Rect chestFrameToDraw = new Rect(0, 0, chestFrameW, chestFrameH);
    private final RectF chestWhereToDraw = new RectF(chestX, chestY, chestX + chestFrameW, chestFrameH);

    private int currentFrame = 0;
    private final int frameCount = 4;

    public GameView(MainActivity2 context) {
        super(context);
        surfaceHolder = getHolder();
        boatBitmap = Bitmap.createScaledBitmap(boatBitmap, boatWidth, boatHeight, false);
        chestBitmap = Bitmap.createScaledBitmap(chestBitmap, chestWidth, chestHeight, false);

        gestureDetector = new GestureDetector(this.getContext(), gestureListener);
    }

    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private boolean isMoving;

    private void update() {
        float velocity = 250;
        if (isMoving) {
            boatX = boatX + velocity / fps;
            if (boatX > getWidth()) {
                boatY += 20;
                boatX = 10;
            }
            if (boatY + boatFrameH > getHeight()) {
                boatY = 10;
            }
            if (checkOverlap(boatX, boatY)) {
                isMoving = false;
                //winScreen.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean checkOverlap(float posX, float posY) {
        if (posX >= chestX - 5 && posX <= chestX + 5) {
            return posY >= chestY - 5 && posY <= chestY + 5;
        }
        return false;
    }

    public final void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (time > lastFrameChangeTime + frameLengthInMS) {
            lastFrameChangeTime = time;
            //currentFrame++;

            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }

        chestFrameToDraw.left = currentFrame * chestFrameW;
        chestFrameToDraw.right = chestFrameToDraw.left + chestFrameW;
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(33, 150, 243));

            canvas.drawBitmap(chestBitmap, chestFrameToDraw, chestWhereToDraw, null);
            chestWhereToDraw.set(chestX, chestY, chestX + chestFrameW, chestY + chestFrameH);

            canvas.drawBitmap(boatBitmap, boatFrameToDraw, boatWhereToDraw, null);
            boatWhereToDraw.set(boatX, boatY, boatX + boatFrameW, boatY + boatFrameH);

            manageCurrentFrame();

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public final void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("GameView", "Interrupted");
        }
    }

    public final void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        //TouchEvent Dispatcher
        // if (gestureDetector != null) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        // posXView.setText("X: " + targetShape.getX() + "Y: " + targetShape.getY());
        //posYView.setText("X: " + boatShape.getX() + "Y: " + boatShape.getY());

       // if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            // isMoving = !isMoving;
       // }
        return gestureDetector.onTouchEvent(event);
    }

    private final SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {

        public boolean onDown(MotionEvent e) {
            return true;
        }

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Handle swipe gesture here
            isMoving = !isMoving;

            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Horizontal swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                // Vertical swipe
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                }
            }
            return true;
        }
    };

    private void onSwipeUp() {
        boatY -= 10;
        if (checkOverlap(boatX, boatY)) {
            winScreen.setVisibility(View.VISIBLE);
        }
    }

    private void onSwipeDown() {
        boatY += 10;
        if (checkOverlap(boatX, boatY)) {
            winScreen.setVisibility(View.VISIBLE);
        }
    }

    private void onSwipeLeft() {
        boatX -= 10;
        if (checkOverlap(boatX, boatY)) {
            winScreen.setVisibility(View.VISIBLE);
        }
    }

    private void onSwipeRight() {
        boatX += 10;
        if (checkOverlap(boatX, boatY)) {
            winScreen.setVisibility(View.VISIBLE);
        }
    }
}
