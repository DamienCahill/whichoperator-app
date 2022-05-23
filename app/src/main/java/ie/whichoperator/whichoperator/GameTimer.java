package ie.whichoperator.whichoperator;
/*
    This class is a modified version of android.os.CountDownTimer
    https://developer.android.com/reference/android/os/CountDownTimer
    https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/os/CountDownTimer.java

    The modifications were made so the class could instantiated and allow the timer remaining to be
    updated after the countdown had started

    Abstract methods from the original methods have also been implemented in this version of the class.
 */
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

public  class GameTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;
    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;
    private long mStopTimeInFuture;
    private TextView timerText;
    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;
    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public GameTimer(long millisInFuture, long countDownInterval, TextView timeText) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        this.timerText = timeText;
    }
    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }
    /**
     * Start the countdown.
     */
    public synchronized final GameTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }
    /**
     * Update the TextView with remaining seconds.
     */
    public void onTick(long millisUntilFinished) {
        if (millisUntilFinished >0)
            timerText.setText("seconds remaining: " + millisUntilFinished / 1000);
        else
            onFinish();
    }
    /**
     * Update the TextView to indicate time has run out.
     */
    public void onFinish() {
        timerText.setText("Times Up");
    }

    /**
     * Change the remaining time.
     */
    public synchronized void addTime(long millis) {
        mStopTimeInFuture += millis;
        if (((mStopTimeInFuture - SystemClock.elapsedRealtime())/1000) > 0) {
            timerText.setText("seconds remaining " + ((mStopTimeInFuture - SystemClock.elapsedRealtime())/1000));
        } else {
            onFinish();
        }
    }
    private static final int MSG = 1;
    // handles counting down
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (GameTimer.this) {
                if (mCancelled) {
                    return;
                }
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);
                    // take into account user's onTick taking time to execute
                    long lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart;
                    long delay;
                    if (millisLeft < mCountdownInterval) {
                        // just delay until done
                        delay = millisLeft - lastTickDuration;
                        // special case: user's onTick took more than interval to
                        // complete, trigger onFinish without delay
                        if (delay < 0) delay = 0;
                    } else {
                        delay = mCountdownInterval - lastTickDuration;
                        // special case: user's onTick took more than interval to
                        // complete, skip to next interval
                        while (delay < 0) delay += mCountdownInterval;
                    }
                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}