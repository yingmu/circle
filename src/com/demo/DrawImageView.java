package com.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DrawImageView extends ImageView {

	private final Paint paintInner;
	private final Paint paintOut;
	private final Context context;
	public DrawImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.paintInner = new Paint();
		paintInner.setColor(0x3eb84b);
		this.paintInner.setAntiAlias(true); // 消除锯齿
		this.paintOut = new Paint();
		paintInner.setStyle(Style.STROKE);
		paintOut.setStyle(Style.STROKE);
		this.paintOut.setAntiAlias(true); // 消除锯齿
	}

	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int center = getWidth() / 2;
		int innerCircle = dip2px(context, 83); // 内圆半径
		int ringWidth = dip2px(context, 20); // 圆环宽度
		int outerCircle = dip2px(context, innerCircle+ringWidth);
		int outerWidth = dip2px(context, 10);
		// 第一种方法绘制圆环
		// 绘制内圆
		this.paintInner.setStrokeWidth(22);
		canvas.drawCircle(center, center, innerCircle, this.paintInner);
		this.paintInner.setStrokeWidth(ringWidth);
		canvas.drawCircle(center, center, innerCircle + 1 + ringWidth / 2,
				this.paintInner);
//		paintInner.setColor(0x3eb84b);
		this.paintInner.setARGB(255, 138, 43, 226);
		this.paintInner.setStrokeWidth(2);
		canvas.drawCircle(center, center, innerCircle + ringWidth, this.paintInner);
	    //绘制外圆
//		this.paintOut.setStrokeWidth(10);
//		canvas.drawCircle(center, center, outerCircle, this.paintOut);
		super.onDraw(canvas);

	}

	/* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
