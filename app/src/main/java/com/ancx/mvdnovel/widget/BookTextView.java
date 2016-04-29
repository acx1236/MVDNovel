package com.ancx.mvdnovel.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ancx.mvdnovel.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ancx on 16/3/14.
 */
public class BookTextView extends View {

    private boolean isNight;

    public void setNight(boolean night) {
        isNight = night;
        invalidate();
    }

    public boolean isNight() {
        return isNight;
    }

    private String title, currentChapter, chaptersCount;

    public void setHintText(String title, int currentChapter, String chaptersCount) {
        this.title = title;
        this.currentChapter = "" + (currentChapter + 1);
        this.chaptersCount = chaptersCount;
    }

    private int currentPage;

    public void setCurrentPage(int currentPage) {
        if (currentPage == 0)
            this.currentPage = 0;
        else
            this.currentPage = currentPage - 1;
        isEndPage = false;
    }

    private String content;

    public void setContent(String content) {
        this.content = content;
        needMeasure = true;
        invalidate();
    }

    private final float dp10 = DisplayUtil.dip2px(10);
    private float paddingTop = DisplayUtil.dip2px(5);
    private float paddingBottom = DisplayUtil.dip2px(5);
    private float paddingLeft = dp10;
    private float paddingRight = dp10;
    private float lineSpacing = dp10;

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();

    private float hintTextSize = DisplayUtil.sp2px(12);
    private int hintTextColor = Color.parseColor("#91846C");

    private float textSize = DisplayUtil.sp2px(20);

    public void setTextSize(float textSize) {
        this.textSize = DisplayUtil.sp2px(textSize);
    }

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

    private boolean needMeasure = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mHeight == 0)
            return;
        drawBackGround(canvas);
        drawHintText(canvas);
        if (content == null)
            return;
        if (needMeasure)
            measurePage();
        drawContent(canvas);
    }

    private void drawBackGround(Canvas canvas) {
        if (isNight)
            mPaint.setColor(nightBackGroundColor);
        else
            mPaint.setColor(normalBackGroundColor);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }

    private void setHintPaint() {
        mPaint.setTextSize(hintTextSize);
        mPaint.setColor(hintTextColor);
    }

    private void getTitleHeight() {
        if (titleHeight == 0) {
            mPaint.getTextBounds(title, 0, title.length(), mRect);
            titleHeight = mRect.height();
        }
    }

    private void getBottomHeight(StringBuilder chapterText) {
        if (bottomHeight == 0) {
            mPaint.getTextBounds(chapterText.toString(), 0, chapterText.toString().length(), mRect);
            bottomHeight = mRect.height();
        }
    }

    private int titleHeight, bottomHeight;

    /**
     * 绘制top和bottom区域的文字（章节名称，页数，章节数等）
     *
     * @param canvas
     */
    private void drawHintText(Canvas canvas) {
        if (title == null)
            return;
        setHintPaint();
        getTitleHeight();
        canvas.drawText(title, paddingLeft, paddingTop + titleHeight, mPaint);
        StringBuilder chapterText = new StringBuilder(currentChapter);
        chapterText.append("/");
        chapterText.append(chaptersCount);
        chapterText.append("章");
        getBottomHeight(chapterText);
        canvas.drawText(chapterText.toString(), (mWidth - mRect.width()) / 2f, mHeight - paddingBottom, mPaint);
    }

    private void setTextPaint() {
        if (isNight)
            mPaint.setColor(nightTextColor);
        else
            mPaint.setColor(normalTextColor);
        mPaint.setTextSize(textSize);
    }

    private float contentTop, contentBottom, contentHeight, contentWidth, fontHeight, fontWidth;
    private int lineMaxCount, pageMaxLine, totalLines, totalPage;

    private void measurePage() {
        contentTop = paddingTop + titleHeight + dp10;
        contentBottom = mHeight - paddingBottom - bottomHeight - dp10;
        contentHeight = contentBottom - contentTop;
        contentWidth = mWidth - paddingLeft - paddingRight;
        setTextPaint();
        mPaint.getTextBounds("鑫", 0, 1, mRect);
        fontHeight = mRect.height();
        fontWidth = mPaint.measureText("鑫");
        lineMaxCount = (int) (contentWidth / fontWidth);
        textSplit = content.split("\n");
        pageMaxLine = (int) ((contentHeight + lineSpacing) / (fontHeight + lineSpacing));
        totalLines = 0;
        for (int i = 0; i < textSplit.length; i++) {
            totalLines += (textSplit[i].length() / lineMaxCount) + 1;
        }
        totalPage = totalLines / pageMaxLine + 1;
        if (isEndPage) {
            currentPage = totalPage - 1;
            isEndPage = false;
        }
        textList = new String[totalLines];
        int i = 0; // 管理总行数的
        int j = 0; // 管理段落的
        while (i < totalLines) {
            for (int k = 0; k < (textSplit[j].length() / lineMaxCount + 1); k++) {
                textList[i] = textSplit[j].substring(lineMaxCount * k, (lineMaxCount * (k + 1) > textSplit[j].length() ? textSplit[j].length() : lineMaxCount * (k + 1)));
                i++;
            }
            j++;
        }
        textSplit = null;
        needMeasure = false;
    }

    private void drawContent(Canvas canvas) {
        drawPage(canvas);
        drawText(canvas);
    }

    private void drawPage(Canvas canvas) {
        setHintPaint();
        StringBuilder pageText = new StringBuilder("第" + (currentPage + 1));
        pageText.append("/");
        pageText.append("" + totalPage);
        pageText.append("页");
        mPaint.getTextBounds(pageText.toString(), 0, pageText.toString().length(), mRect);
        canvas.drawText(pageText.toString(), mWidth - paddingRight - mRect.width(), mHeight - paddingBottom, mPaint);
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        canvas.drawText(time, paddingLeft, mHeight - paddingBottom, mPaint);
    }

    private String[] textSplit, textList;

    private void drawText(Canvas canvas) {
        setTextPaint();
        for (int line = 0; line < (pageMaxLine > (totalLines - currentPage * pageMaxLine) ? (totalLines - currentPage * pageMaxLine) : pageMaxLine); line++) {
            canvas.drawText(textList[line + currentPage * pageMaxLine], paddingLeft, contentTop + ((line + 1) * fontHeight) + (line * lineSpacing), mPaint);
        }
    }

    public void addTextSize() {
        textSize += DisplayUtil.sp2px(3);
        needMeasure = true;
        invalidate();
    }

    public void minusTextSize() {
        textSize -= DisplayUtil.sp2px(3);
        needMeasure = true;
        invalidate();
    }

    public void nextPage() {
        if (currentPage == totalPage - 1) {
            if (onChapterChangeListener != null) {
                isEndPage = false;
                currentPage = 0;
                onChapterChangeListener.onNextChapter();
            }
            return;
        }
        currentPage++;
        if (onChapterChangeListener != null)
            onChapterChangeListener.updateRecord(currentPage + 1);
        invalidate();
    }

    public void prePage() {
        if (currentPage == 0) {
            if (onChapterChangeListener != null) {
                isEndPage = true;
                onChapterChangeListener.onPreChapter();
            }
            return;
        }
        currentPage--;
        if (onChapterChangeListener != null)
            onChapterChangeListener.updateRecord(currentPage + 1);
        invalidate();
    }

    public interface OnChapterChangeListener {

        void onPreChapter();

        void onNextChapter();

        void onMenu();

        void updateRecord(int currentPage);
    }

    private OnChapterChangeListener onChapterChangeListener;

    public void setOnChapterChangeListener(OnChapterChangeListener onChapterChangeListener) {
        this.onChapterChangeListener = onChapterChangeListener;
    }

    private float onTouchDownX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDownX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (onTouchDownX - event.getX() > 20) {
                    nextPage();
                } else if (event.getX() - onTouchDownX > 20) {
                    prePage();
                } else {
                    if (onTouchDownX < mWidth / 3) {
                        prePage();
                    } else if (onTouchDownX > (mWidth / 3 * 2)) {
                        nextPage();
                    } else {
                        if (onChapterChangeListener != null) {
                            onChapterChangeListener.onMenu();
                        }
                    }
                }
                onTouchDownX = 0;
                break;
        }
        return true;
    }

    private boolean isEndPage;

    public void readComplete() {
        currentPage = totalPage - 1;
        isEndPage = true;
    }
}
