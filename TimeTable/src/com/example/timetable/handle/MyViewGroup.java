/*
 * 这个类是找来别人的直接用的，转到第几个view
 */
package com.example.timetable.handle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyViewGroup extends ViewGroup {

	private static final String TAG = "scroller";

	private Scroller scroller;

	private int currentScreenIndex;

	private GestureDetector gestureDetector;

	// 璁剧疆涓€涓爣蹇椾綅锛岄槻姝㈠簳灞傜殑onTouch浜嬩欢閲嶅澶勭悊UP浜嬩欢
	private boolean fling;

	public void setCurrentScreenIndex(int currentScreenIndex) {
		this.currentScreenIndex = Math.max(0,
				Math.min(currentScreenIndex, getChildCount()));
		scrollTo(this.currentScreenIndex * getWidth(), 0);
		invalidate();
	}

	public Scroller getScroller() {
		return scroller;
	}

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MyViewGroup(Context context) {
		super(context);
		initView(context);
	}

	private void initView(final Context context) {
		this.scroller = new Scroller(context);

		this.gestureDetector = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				if ((distanceX > 0 && currentScreenIndex < getChildCount() - 1)// 闃叉绉诲姩杩囨渶鍚庝竴椤�
						|| (distanceX < 0 && getScrollX() > 0)) {// 闃叉鍚戠涓€椤典箣鍓嶇Щ鍔�
					scrollBy((int) distanceX, 0);
				}
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				Log.d(TAG, "min velocity >>>"
						+ ViewConfiguration.get(context)
								.getScaledMinimumFlingVelocity()
						+ " current velocity>>" + velocityX);
				if (Math.abs(velocityX) > ViewConfiguration.get(context)
						.getScaledMinimumFlingVelocity()) {// 鍒ゆ柇鏄惁杈惧埌鏈€灏忚交鏉鹃€熷害锛屽彇缁濆鍊肩殑
					if (velocityX > 0 && currentScreenIndex > 0) {
						Log.d(TAG, ">>>>fling to left");
						fling = true;
						scrollToScreen(currentScreenIndex - 1);
					} else if (velocityX < 0
							&& currentScreenIndex < getChildCount() - 1) {
						Log.d(TAG, ">>>>fling to right");
						fling = true;
						scrollToScreen(currentScreenIndex + 1);
					}
				}

				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, ">>left: " + left + " top: " + top + " right: " + right
				+ " bottom:" + bottom);

		/**
		 * 璁剧疆甯冨眬锛屽皢瀛愯鍥鹃『搴忔í灞忔帓鍒�
		 */
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.setVisibility(View.VISIBLE);
			child.measure(right - left, bottom - top);
			child.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
		}
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (!fling) {
				snapToDestination();
			}
			fling = false;
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 鍒囨崲鍒版寚瀹氬睆
	 * 
	 * @param whichScreen
	 */
	public void scrollToScreen(int whichScreen) {
		if (getFocusedChild() != null && whichScreen != currentScreenIndex
				&& getFocusedChild() == getChildAt(currentScreenIndex)) {
			getFocusedChild().clearFocus();
		}

		final int delta = whichScreen * getWidth() - getScrollX();
		scroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
		invalidate();

		currentScreenIndex = whichScreen;
	}

	/**
	 * 鏍规嵁褰撳墠x鍧愭爣浣嶇疆纭畾鍒囨崲鍒扮鍑犲睆
	 */
	private void snapToDestination() {
		scrollToScreen((getScrollX() + (getWidth() / 2)) / getWidth());
	}

}