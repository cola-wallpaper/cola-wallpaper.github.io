package cn.nevercode.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.nevercode.cola.R;


public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private TextView mHintTextView;
	private ImageView mRound;
	private ImageView mRight;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	private Animation mRotateAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	public final static int STATE_DONE = 3;

	public XListViewHeader(Context context) {
		super(context);
		if(isInEditMode()) {
			return;
		}
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) {
			return;
		}
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = findViewById(R.id.xlistview_header_arrow);
		mHintTextView = findViewById(R.id.xlistview_header_hint_textview);
		mRound = findViewById(R.id.xlistview_header_round);
		mRight = findViewById(R.id.xlistview_header_right);


		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);

		mRotateAnim = AnimationUtils.loadAnimation(context, R.anim.refresh_rotate);
	}

	public void setState(int state) {
		if (state == mState) return ;
		
		if (state == STATE_REFRESHING)
        {	// 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			mRound.setVisibility(View.VISIBLE);
			mRound.startAnimation(mRotateAnim);
			mRight.setVisibility(View.GONE);
		} else if(state == STATE_DONE) {
			mRound.clearAnimation();
			mRound.setVisibility(View.GONE);
			mRight.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			mRound.clearAnimation();
			mArrowImageView.setVisibility(View.VISIBLE);
			mRound.setVisibility(View.GONE);
			mRight.setVisibility(View.GONE);
		}

		switch (state) {
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					mArrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					mArrowImageView.clearAnimation();
				}
				mHintTextView.setText(R.string.xlistview_header_hint_normal);
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateUpAnim);
					mHintTextView.setText(R.string.xlistview_header_hint_ready);
				}
				break;
			case STATE_REFRESHING:
				mHintTextView.setText(R.string.xlistview_header_hint_loading);
				break;
			case STATE_DONE:
				mHintTextView.setText(R.string.xlistview_header_hint_done);
				break;
			default:
		}
		
		mState = state;
	}
	
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
//		return mContainer.getHeight();
		return mContainer.getLayoutParams().height;
	}

}
