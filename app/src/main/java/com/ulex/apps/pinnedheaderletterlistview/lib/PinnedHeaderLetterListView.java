package com.ulex.apps.pinnedheaderletterlistview.lib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;

import com.ulex.apps.pinnedheaderletterlistview.R;
import com.ulex.apps.pinnedheaderletterlistview.lib.entity.PinLetterBaseEntity;
import com.ulex.apps.pinnedheaderletterlistview.lib.pinnedheaderlistview.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ulex on 2016/6/15.
 */
public class PinnedHeaderLetterListView extends PinnedHeaderListView {
    private final static String TAG = PinnedHeaderLetterListView.class.getSimpleName();

    private final static int ALPHA_PADDING = 5;// pixels
    private PinLetterBaseAdapter mAdapter;

    private TextPaint mAlphaTextPaint;
    private Paint mOverlayPaint;
    private boolean isShowOverlay = false;
    private boolean isAlphaSelecting = false;
    private float mAlphaInternal = 0f;
    private int mCurrentAlphaIndex = -1;

    private int mAlphaTextColor;
    private int mAlphaTextSize;
    private int mAlphaBackgroudColor;
    private int mAlphaBackgroudNormalColor;
    private int mAlphaBackgroudPressedColor;
    private int mAlphaPaddingLeft;
    private int mAlphaPaddingRight;
    private int mAlphaPaddingTop;
    private int mAlphaPaddingBottom;
    private List<String> mAlphaList;

    private int mOverlayColor;
    private int mOverlaySize;
    private int mOverlayTextColor;
    private int mOverlayTextSize;
    private int mOverlayRadius;

    public PinnedHeaderLetterListView(Context context) {
        super(context);
        init();
    }

    public PinnedHeaderLetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public PinnedHeaderLetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        initAttrs(attrs);
    }

    private void init() {
        this.setVerticalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.pin_letter_list_view);
        mAlphaTextSize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaTextSize, 16);
        mAlphaTextColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaTextColor, Color.BLACK);
        mAlphaBackgroudNormalColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaBackgroundNormalColor, Color.TRANSPARENT);
        mAlphaBackgroudPressedColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaBackgroundPressedColor, Color.GRAY);
        mAlphaBackgroudColor = mAlphaBackgroudNormalColor;
        mAlphaPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingLeft, 0);
        mAlphaPaddingRight = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingRight, 0);
        mAlphaPaddingTop = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingTop, 5);
        mAlphaPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingBottom, 5);
        String alphaStr = typedArray.getString(R.styleable.pin_letter_list_view_alphaArrays);
        initAlphaList(alphaStr);

        mOverlayTextColor = typedArray.getColor(R.styleable.pin_letter_list_view_overlayTextColor, Color.WHITE);
        mOverlayTextSize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlayTextSize, 40);
        mOverlaySize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlaySize, 80);
        mOverlayColor = typedArray.getColor(R.styleable.pin_letter_list_view_overlayColor, Color.BLACK);
        mOverlayRadius = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlayRadius, 10);

        typedArray.recycle();

    }

    private void initAlphaList(String alphaStr) {
        if (alphaStr == null) {
            return;
        }
        alphaStr = alphaStr.trim();
        if (alphaStr.length() <= 0) {
            return;
        }
        mAlphaList = new ArrayList<>(alphaStr.length());
        char[] alphaArray = alphaStr.toCharArray();
        for (char c : alphaArray) {
            String s = String.valueOf(c);
            if (!mAlphaList.contains(s)) {
                mAlphaList.add(s);
            }
        }
    }

    private void initPaint() {
        if (mAlphaTextPaint == null) {
            mAlphaTextPaint = new TextPaint();
            mAlphaTextPaint.setColor(Color.BLACK);
            mAlphaTextPaint.setTextAlign(Paint.Align.CENTER);
            mAlphaTextPaint.setAntiAlias(true);
        }

        if (mOverlayPaint == null) {
            mOverlayPaint = new Paint();
            mOverlayPaint.setTextAlign(Paint.Align.CENTER);
            mOverlayPaint.setAntiAlias(true);
            mOverlayPaint.setStyle(Paint.Style.FILL);
        }

    }

    /**
     * this method has abandon, throw UnsupportedOperationException, please use
     * {@link #setAdapter(PinLetterBaseAdapter adapter)}
     *
     * @throws UnsupportedOperationException
     */
    @Deprecated
    public void setAdapter(ListAdapter adapter) {
        throw new UnsupportedOperationException("please use ContactListAdapter");
    }

    public void setAdapter(PinLetterBaseAdapter adapter) {
        mAdapter = adapter;
        super.setAdapter(adapter);
        initAlphaListFromAdapterDataIfNeed();
    }

    private void initAlphaListFromAdapterDataIfNeed() {
        if (mAlphaList != null && mAlphaList.size() > 0) {
            return;
        }
        List<PinLetterBaseEntity> tempList = (List<PinLetterBaseEntity>) mAdapter.list;
        for (PinLetterBaseEntity entity : tempList) {
            if (entity != null && entity.getLetter() != null && !mAlphaList.contains(entity.getLetter())) {
                mAlphaList.add(entity.getLetter());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawAlpha(canvas);
        drawOverlay(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isAlphaListEmpty()) {
            mAlphaInternal = 0;
        } else {
            mAlphaInternal = getAlphaHeight() / mAlphaList.size();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ev.getAction() == MotionEvent.ACTION_DOWN
                && isInAlpha(getWidth() - ev.getX())
                || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isAlphaSelecting && ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            isAlphaSelecting = false;
            onAlphaEnd();
            return true;
        }

        float distanceToRight = getWidth() - ev.getX();
        float distanctToTop = ev.getY();
        if (distanctToTop >= 0 && distanctToTop <= getHeight()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN
                    && isInAlpha(distanceToRight)) {
                isAlphaSelecting = true;
                onAlphaStart(getTouchAlphaIndex(distanctToTop));
                return true;
            } else if (isAlphaSelecting
                    && ev.getAction() == MotionEvent.ACTION_MOVE) {
                onAlphaSelected(getTouchAlphaIndex(distanctToTop));
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void onAlphaStart(int index) {
        mCurrentAlphaIndex = index;
        isShowOverlay = true;
        mAlphaBackgroudColor = mAlphaBackgroudPressedColor;
        scrollToPosition(index);
        invalidate();
    }

    private void onAlphaSelected(int index) {
        if (mCurrentAlphaIndex >= 0 && mCurrentAlphaIndex == index) {
            //on same select
            return;
        }
        mCurrentAlphaIndex = index;
        isShowOverlay = true;
        mAlphaBackgroudColor = mAlphaBackgroudPressedColor;
        scrollToPosition(index);
        invalidate();
    }

    private void onAlphaEnd() {
        mAlphaBackgroudColor = mAlphaBackgroudNormalColor;
        invalidate();
        hideOverlay();
    }

    private void scrollToPosition(int index) {
        int headerViewCount = getHeaderViewsCount();
        String letter = mAlphaList.get(index);
        int wholePosition = mAdapter.getWholePosition(letter);
        setSelection(headerViewCount + wholePosition);
    }

    private boolean isInAlpha(float distanceToRight) {
        return mAlphaInternal > 0 && distanceToRight < (mAlphaInternal + mAlphaPaddingLeft + mAlphaPaddingRight);
    }

    private int getTouchAlphaIndex(float y) {
        if (isAlphaListEmpty()) {
            return 0;
        }
        y = y - ALPHA_PADDING - mAlphaPaddingTop;
        int index = ((int) (y / mAlphaInternal));
        return index >= mAlphaList.size() ? mAlphaList.size() - 1 : (index <= 0 ? 0 : index);
    }

    private float getAlphaHeight() {
        return getHeight() - 2 * ALPHA_PADDING - mAlphaPaddingTop - mAlphaPaddingBottom;
    }

    private boolean isAlphaListEmpty() {
        if (mAlphaList == null || mAlphaList.size() <= 0) {
            return true;
        }
        return false;
    }

    private void drawAlpha(Canvas canvas) {
        if (isAlphaListEmpty()) {
            return;
        }
        drawAlphaBackgroud(canvas);

        float textSizePixel = translateTextSize(mAlphaTextSize);
        mAlphaTextPaint.setTextSize(mAlphaInternal > textSizePixel ? textSizePixel : mAlphaInternal);
        mAlphaTextPaint.setColor(mAlphaTextColor);

        canvas.save();
        float yStart = mAlphaInternal + ALPHA_PADDING + mAlphaPaddingTop;
        float xStart = getMeasuredWidth() - (mAlphaInternal / 2) - mAlphaPaddingRight;
        for (int i = 0; i < mAlphaList.size(); i++) {
            String alpha = mAlphaList.get(i);
            canvas.drawText(alpha, xStart, yStart, mAlphaTextPaint);
            yStart = yStart + mAlphaInternal;
        }
        canvas.restore();
    }

    private void drawOverlay(Canvas canvas) {
        if (!isShowOverlay || mCurrentAlphaIndex < 0) {
            return;
        }
        String alpha = mAlphaList.get(mCurrentAlphaIndex);
        if (TextUtils.isEmpty(alpha)) {
            return;
        }

        mOverlayPaint.setColor(mOverlayColor);

        float size = dp2px(getContext(), mOverlaySize);
        canvas.save();
        float left = (getWidth() - size) / 2;
        float top = (getHeight() - size) / 2;
        float radius = dp2px(getContext(), mOverlayRadius);
        canvas.drawRoundRect(new RectF(left, top, left + size, top + size), radius, radius, mOverlayPaint);

        mOverlayPaint.setColor(mOverlayTextColor);

        float textSize = translateTextSize(mOverlayTextSize);
        mOverlayPaint.setTextSize(textSize);

        canvas.drawText(alpha, getWidth() / 2, getHeight() / 2 + textSize / 2 - 10, mOverlayPaint);

        canvas.restore();

    }

    private void drawAlphaBackgroud(Canvas canvas) {
        mAlphaTextPaint.setColor(mAlphaBackgroudColor);
        canvas.save();
        canvas.drawRect(getWidth() - mAlphaInternal - mAlphaPaddingLeft
                - mAlphaPaddingRight, 0, getWidth(), getHeight(), mAlphaTextPaint);
        canvas.restore();
    }

    /**
     * @param textSize
     * @return translate from sp to pixel
     */
    private float translateTextSize(int textSize) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, textSize, r.getDisplayMetrics());
    }

    private void hideOverlay() {
        postDelayed(delayHideOverlayRunnable, 500);
    }

    private Runnable delayHideOverlayRunnable = new Runnable() {
        @Override
        public void run() {
            if (isShowOverlay) {
                isShowOverlay = false;
                mCurrentAlphaIndex = -1;
                invalidate();
            }
        }
    };

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
