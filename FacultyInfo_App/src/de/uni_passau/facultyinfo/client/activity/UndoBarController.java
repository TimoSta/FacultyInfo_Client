package de.uni_passau.facultyinfo.client.activity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;

public class UndoBarController {
    private View mBarView;
    private TextView mMessageView;
    private ViewPropertyAnimator mBarAnimator;
    private Handler mHideHandler = new Handler();

    private UndoListener mUndoListener;

    // State objects
    private Parcelable mUndoToken;
    private CharSequence mUndoMessage;

    public interface UndoListener {
        void onUndo(Parcelable token);
    }

    public UndoBarController(View undoBarView, UndoListener undoListener) {
        mBarView = undoBarView;
        mBarAnimator = mBarView.animate();
        mUndoListener = undoListener;

        mMessageView = (TextView) mBarView.findViewById(R.id.undobar_message);
        mBarView.findViewById(R.id.undobar_button).setClickable(true);
        mBarView.findViewById(R.id.undobar_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	System.out.println("onClick");
                        hideUndoBar(false);
                        mUndoListener.onUndo(mUndoToken);
                    }
                });

//        hideUndoBar(true);
    }

    public void showUndoBar(boolean immediate, CharSequence message, Parcelable undoToken) {
    	System.out.println("UndoBarController->showUndoBar");
        mUndoToken = undoToken;
        mUndoMessage = message;
        mMessageView.setText(mUndoMessage);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable,
                mBarView.getResources().getInteger(R.integer.undobar_hide_delay));

        mBarView.setVisibility(View.VISIBLE);
//        mBarView.setAlpha(1);
        if (immediate) {
        	System.out.println("showUndoBar->immediate");
            mBarView.setAlpha(1);
        } else {
        	System.out.println("showUndoBar->else");
        	mBarView.setAlpha(1);
            mBarAnimator.cancel();
            mBarView.animate().setDuration(1000000); 
            mBarAnimator
                    .setDuration(
                            mBarView.getResources()
                                    .getInteger(android.R.integer.config_longAnimTime))
                    .setListener(null);
        }
    }

    public void hideUndoBar(boolean immediate) {
    	System.out.println("hideUndoBar");
        mHideHandler.removeCallbacks(mHideRunnable);
        if (immediate) {
        	System.out.println("hideUndoBar->immediate"); 
            mBarView.setVisibility(View.GONE);
            mBarView.setAlpha(0);
            mUndoMessage = null;
            mUndoToken = null;
        } else {
        	System.out.println("hideUndoBar->else"); 
        	mBarView.setVisibility(View.VISIBLE);
//            mBarAnimator.cancel();
            mBarAnimator
                    .alpha(0)
                    .setDuration(mBarView.getResources()
                    		.getInteger(android.R.integer.config_longAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBarView.setVisibility(View.GONE);
                            mUndoMessage = null;
                            mUndoToken = null;
                        }
                    });
            mBarView.setPressed(true);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
    	System.out.println("UndoBarController->onSaveInstanceState");
        outState.putCharSequence("undo_message", mUndoMessage);
        outState.putParcelable("undo_token", mUndoToken);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	System.out.println("UndoBarController->onRestoreInstanceState");
        if (savedInstanceState != null) {
            mUndoMessage = savedInstanceState.getCharSequence("undo_message");
            mUndoToken = savedInstanceState.getParcelable("undo_token");

            if (mUndoToken != null || !TextUtils.isEmpty(mUndoMessage)) {
                showUndoBar(true, mUndoMessage, mUndoToken);
            }
        }
    }

    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
        	System.out.println("Runnable->run");
            hideUndoBar(false);
        }
    };
}
