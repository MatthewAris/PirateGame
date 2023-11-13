package uk.ac.tees.mgd.b1032293.weekoneapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private final GestureDetector gestureDetector;
    public OnSwipeTouchListener (Context ctx) {
        gestureDetector = new GestureDetector(ctx,
                new GestureListener());
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try{
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)){
                // TODO: ADD SWIPE VARIABLES AND FUNCTIONS
                if ((Math.abs(diffX) > SWIPE_THRESHOLD) && (Math.abs(velocityX)) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0){
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
            }
            result = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;

}

    private void onSwipeTop() {
    }

    private void onSwipeBottom() {
    }

    private void onSwipeLeft() {
    }

    private void onSwipeRight() {
    }

    private class GestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(@NonNull MotionEvent motionEvent) {
            return false;
        }
        @Override
        public void onShowPress(@NonNull MotionEvent motionEvent) {
        }
        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
            return false;
        }
        @Override
        public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
        @Override
        public void onLongPress(@NonNull MotionEvent motionEvent) {
        }
        @Override
        public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }
}
