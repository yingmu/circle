package com.demo;

import com.example.circledemo.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

public class CircleView extends View {
	private static final int STROKE_WIDTH = 20;
	private int mInLineColor = 0x3eb84b;
	private int mOutLineColor = 0xffffff;
	private Context context;
	private String mTitle = "";
	private String mSubTitle = "";
	private int canvasBackground;
	private int mStrokeWidth = STROKE_WIDTH;
	private final Paint mInnerPaint = new Paint();
	private final Paint mOutPaint = new Paint();
	private final Paint mTitlePaint = new Paint();
	private final Paint mSubTitlePaint = new Paint();
	private final RectF mCircleBounds = new RectF();
	private int mTitleColor = 0xffffff;
	private int mSubTitleColor = 0xffffff;
	private int mTitleSize;
	private int mSubTitleSize;
	private boolean isTitleCenter;
	float densityMultiplier;

	public CircleView(Context context) {
		super(context);
		this.context = context;
		initView(null);
	}

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView(attrs);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView(attrs);
	}

	private void initView(AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.hollow_circlr);
		mInLineColor = a.getColor(R.styleable.hollow_circlr_innerColor,
				0x3eb84b);
		mOutLineColor = a.getColor(R.styleable.hollow_circlr_outerColor,
				0xffffff);
		mStrokeWidth = a.getInt(R.styleable.hollow_circlr_innerStrokewidth,
				STROKE_WIDTH);
		isTitleCenter = a.getBoolean(R.styleable.hollow_circlr_isTitleCenter,
				false);
		String t = a.getString(R.styleable.hollow_circlr_title);
		if (t != null) {
			mTitle = t;
		}
		String sub = a.getString(R.styleable.hollow_circlr_subTitle);
		if (sub != null) {
			mSubTitle = sub;
		}
		mTitleColor = a
				.getColor(R.styleable.hollow_circlr_titleColor, 0xffffff);
		mSubTitleColor = a.getColor(R.styleable.hollow_circlr_subTtitleColor,
				R.color.innercolor);
		mTitleSize = a.getInt(R.styleable.hollow_circlr_titleSize, 40);
		mSubTitleSize = a.getInt(R.styleable.hollow_circlr_subTitleSize, 18);
		a.recycle();
		Resources res = context.getResources();
		densityMultiplier = getContext().getResources().getDisplayMetrics().density;
		initInnerPaint(res);
		initOutPaint(res);
		initTitlePaint(res);
		initSubTitlePaint(res);
	}

	/**
	 * 初始化外圆画笔
	 * 
	 * @param res
	 */
	private void initOutPaint(Resources res) {
		mOutLineColor = res.getColor(R.color.outcolor2);
		mOutPaint.setAntiAlias(true);
		mOutPaint.setColor(mOutLineColor);
		mOutPaint.setStyle(Style.STROKE);
	}

	/**
	 * 初始化标题画笔
	 * 
	 * @param res
	 */
	private void initTitlePaint(Resources res) {
		mTitlePaint.setAntiAlias(true);
		mTitlePaint.setTypeface(Typeface.create("Roboto-Thin", Typeface.BOLD));
		mTitlePaint.setColor(Color.WHITE);
		mTitlePaint.setTextSize(mTitleSize * densityMultiplier);
		mTitlePaint.setStyle(Style.FILL);
	}

	/**
	 * 初始化副标题画笔
	 * 
	 * @param res
	 */
	private void initSubTitlePaint(Resources res) {
		mSubTitlePaint.setAntiAlias(true);
		mSubTitlePaint.setColor(Color.WHITE);
		mSubTitlePaint.setStyle(Style.FILL);
		mSubTitlePaint.setTextSize(mSubTitleSize * densityMultiplier);
		mSubTitlePaint.setTypeface(Typeface.create("Roboto-Thin",
				Typeface.NORMAL));
	}

	/**
	 * 初始化内圆画笔
	 * 
	 * @param res
	 */
	private void initInnerPaint(Resources res) {
		canvasBackground = res.getColor(android.R.color.transparent);
		mInLineColor = res.getColor(R.color.innercolor2);
		mInnerPaint.setAntiAlias(true);
		mInnerPaint.setColor(mInLineColor);
		mInnerPaint.setStyle(Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawArc(mCircleBounds, 0, 360 , false, mBackgroundColorPaint);
		// 获取圆心坐标
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		int radus = getWidth() / 2;
		mInnerPaint.setAntiAlias(true); // 设置画笔为无锯齿
		mInnerPaint.setStrokeWidth(mStrokeWidth); // 线宽
		mOutPaint.setStrokeWidth((mStrokeWidth / 2));
		canvas.drawCircle(centerX, centerY, radus - mStrokeWidth / 2
				- (mStrokeWidth / 4), mInnerPaint); // 绘制圆形
		canvas.drawCircle(centerX, centerY, radus - (mStrokeWidth / 4),
				mOutPaint); // 绘制圆形\
		if (!TextUtils.isEmpty(mTitle)) {
			// get the width of the text
			int ts = (int) mTitlePaint.measureText(mTitle);
			// 得到的是控件的宽度,不是坐标
			int xWidth = getMeasuredWidth();
			// the text start draw cordinate x;
			// width/2-textWidth/2
			int xPos = (int) (getMeasuredWidth() / 2 - mTitlePaint
					.measureText(mTitle) / 2);
			// the text start draw cordinate y;
			// height/2-textHeight/2
			int yPos = (int) (getMeasuredHeight() / 2);
			int xHeight = getMeasuredHeight();
			float titleHeight = Math.abs(mTitlePaint.descent()
					+ mTitlePaint.ascent());
			// if (TextUtils.isEmpty(mSubTitle)) {
			// yPos += titleHeight / 2;
			// canvas.drawText(mTitle, xPos, yPos, mTitlePaint);
			// }
			float subTitleHeight = Math.abs(mSubTitlePaint.descent()
					+ mSubTitlePaint.ascent());
			if (isTitleCenter) {
				yPos += titleHeight / 2;
				canvas.drawText(mTitle, xPos, yPos, mTitlePaint);
				int ddd = getMeasuredHeight() / 2;
				yPos = (int) ((Math.abs(yPos
						- (getMeasuredHeight() - titleHeight * 2) / 4)) + subTitleHeight / 2);
				xPos = (int) (getMeasuredWidth() / 2 - mSubTitlePaint
						.measureText(mSubTitle) / 2);
				canvas.drawText(mSubTitle, xPos, yPos, mSubTitlePaint);
			} else {
				canvas.drawText(mTitle, xPos, yPos, mTitlePaint);
				yPos += titleHeight;
				xPos = (int) (getMeasuredWidth() / 2 - mSubTitlePaint
						.measureText(mSubTitle) / 2);

				canvas.drawText(mSubTitle, xPos, yPos, mSubTitlePaint);
			}

		}
	}

	public void setSubTitle(String subTitle) {
		mSubTitle = subTitle;
		invalidate();
	}

	public void setTitle(String title) {
		mTitle = title;
		invalidate();
	}

	public void setStorkeWidth(int width) {
		mStrokeWidth = width;
		invalidate();
	}
}
