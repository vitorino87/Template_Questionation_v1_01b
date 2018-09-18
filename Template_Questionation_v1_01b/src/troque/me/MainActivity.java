package troque.me;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends QuestaoConector implements OnGestureListener {
	GestureDetector gestureDetector= new GestureDetector(this,this);
	//public MainActivity(Questao a){
		
	    //gestureDetector = 
	//}
    

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        if (motionEvent1.getY() - motionEvent2.getY() > 50) {
            //Toast.makeText(this, "You Swiped up!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > 50) {
            //Toast.makeText(MainActivity.this, "You Swiped Down!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (motionEvent1.getX() - motionEvent2.getX() > 50) {
            //Toast.makeText(MainActivity.this, "You Swiped Left!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (motionEvent2.getX() - motionEvent1.getX() > 50) {
            //Toast.makeText(MainActivity.this, "You Swiped Right!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
