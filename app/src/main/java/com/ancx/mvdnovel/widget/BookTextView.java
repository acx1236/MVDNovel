package com.ancx.mvdnovel.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.ancx.mvdnovel.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ancx on 16/3/14.
 */
public class BookTextView extends View {

    private boolean isNight;

    private float paddingTop = DisplayUtil.dip2px(5);
    private float paddingBottom = DisplayUtil.dip2px(5);
    private float paddingLeft = DisplayUtil.dip2px(10);
    private float paddingRight = DisplayUtil.dip2px(10);

    public void setNight(boolean night) {
        isNight = night;
    }

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();

    private float hintTextSize = DisplayUtil.sp2px(12);
    private int hintTextColor = Color.parseColor("#91846C");

    private float textSize = DisplayUtil.sp2px(20);
    // 正常时的颜色
    private int normalTextColor = Color.parseColor("#3B3220");
    private int normalBackGroundColor = Color.parseColor("#F0E2C0");
    // 夜间时的颜色
    private int nightTextColor = Color.parseColor("#404040");
    private int nightBackGroundColor = Color.parseColor("#000000");

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public BookTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
    }

    /**
     * 总宽，总高
     */
    private int mWidth, mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mHeight == 0)
            return;
        drawBackGround(canvas);
        drawHintText(canvas);
        drawContent(canvas);
    }

    private void drawBackGround(Canvas canvas) {
        if (isNight)
            mPaint.setColor(nightBackGroundColor);
        else
            mPaint.setColor(normalBackGroundColor);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }

    private String title, currentChapter, chaptersCount;

    public void setHintText(String title, String currentChapter, String chaptersCount) {
        this.title = title;
        this.currentChapter = currentChapter;
        this.chaptersCount = chaptersCount;
    }

    /**
     * 绘制top和bottom区域的文字（章节名称，页数，章节数等）
     *
     * @param canvas
     */
    private void drawHintText(Canvas canvas) {
        if (title == null)
            return;
        mPaint.setTextSize(hintTextSize);
        mPaint.setColor(hintTextColor);
        mPaint.getTextBounds(title, 0, title.length(), mRect);
        int titleHeight = mRect.height();
        canvas.drawText(title, paddingLeft, paddingTop + titleHeight, mPaint);
        StringBuilder chapterText = new StringBuilder(currentChapter);
        chapterText.append("/");
        chapterText.append(chaptersCount);
        chapterText.append("章");
        mPaint.getTextBounds(chapterText.toString(), 0, chapterText.toString().length(), mRect);
        canvas.drawText(chapterText.toString(), (mWidth - mRect.width()) / 2f, mHeight - paddingBottom, mPaint);
        StringBuilder pageText = new StringBuilder("第" + currentPage);
        pageText.append("/");
        pageText.append("" + totalPage);
        pageText.append("页");
        mPaint.getTextBounds(pageText.toString(), 0, pageText.toString().length(), mRect);
        canvas.drawText(pageText.toString(), mWidth - paddingRight - mRect.width(), mHeight - paddingBottom, mPaint);
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        canvas.drawText(time, paddingLeft, mHeight - paddingBottom, mPaint);
    }

    private float contentTop, contentBottom;

    private void drawContent(Canvas canvas) {

    }

    private int currentPage, totalPage;
}
