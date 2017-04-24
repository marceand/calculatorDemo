package com.marceme.calculatordemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Marcelino Yax-marce7j@gmail.com-Android Developer
 *         Created on 4/18/2017.
 */

public class CalculatorView extends View {

    private static final String[][] NUMBERS = {{"7","8","9"},{"4","5","6"},{"1","2","3"},{".","0","="}};
    private static final String[] OPERATORS = {"DEL","\u00F7","\u00D7",	"\u2212","\u002B"};


    private static final int DISPLAY_HEIGHT = 360;
    private static final int OPERATOR_WIDTH = 260;


    private Paint recDisplayPaint;
    private Paint rectNumberPaint;
    private Paint rectOperatorsPaint;

    private int widthSize;
    private int heightSize;
    private Paint textPaint;
    private int xTextPosition;
    private int yTextPosition;
    private int xOperatorPosition;
    private int yOperatorPosition;


    public CalculatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initDisplayPaint();
        initNumbersPaint();
        initOperatorsPaint();
        initTextPaint();
    }

    private void initDisplayPaint() {
        recDisplayPaint = new Paint();
        recDisplayPaint.setColor(Color.WHITE);
    }

    private void initNumbersPaint() {
        rectNumberPaint = new Paint();
        rectNumberPaint.setColor(Color.BLACK);
    }

    private void initOperatorsPaint() {
        rectOperatorsPaint = new Paint();
        rectOperatorsPaint.setColor(Color.GRAY);
    }

    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(56);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthSize = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        heightSize = View.resolveSize(getDesiredHeight(), heightMeasureSpec);

        xTextPosition = ((widthSize - OPERATOR_WIDTH)/3)/2;
        yTextPosition = ((heightSize - DISPLAY_HEIGHT)/4)/2;

        xOperatorPosition = (widthSize - OPERATOR_WIDTH)+(OPERATOR_WIDTH/2);
        yOperatorPosition = ((heightSize - DISPLAY_HEIGHT)/5)/2;

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawTextNumbers(canvas);
        drawTextOperators(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xPos = event.getX();
        float yPos = event.getY();
        return super.onTouchEvent(event);
    }

    private void drawBackground(Canvas canvas) {
        drawDisplayBg(canvas);
        drawNumbersBg(canvas);
        drawOperatorsBg(canvas);
    }

    private void drawDisplayBg(Canvas canvas) {
        canvas.drawRect(0, 0, widthSize, DISPLAY_HEIGHT, recDisplayPaint);
    }

    private void drawNumbersBg(Canvas canvas) {
        canvas.drawRect(0, DISPLAY_HEIGHT, widthSize - OPERATOR_WIDTH, heightSize, rectNumberPaint);
    }


    private void drawOperatorsBg(Canvas canvas) {
        canvas.drawRect(widthSize - OPERATOR_WIDTH, DISPLAY_HEIGHT, widthSize, heightSize, rectOperatorsPaint);
    }


    private void drawTextNumbers(Canvas canvas) {
        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 3; column++) {
                canvas.drawText(NUMBERS[row][column], xTextPosition +
                        2 *column*xTextPosition, 2 *row*yTextPosition+yTextPosition+DISPLAY_HEIGHT, textPaint);
            }
        }
    }

    private void drawTextOperators(Canvas canvas) {
        for(int row = 0; row < OPERATORS.length; row++){
            canvas.drawText(OPERATORS[row], xOperatorPosition, 2*row*yOperatorPosition+yOperatorPosition+DISPLAY_HEIGHT, textPaint);
        }
    }


    public int getDesiredWidth() {
        return getPaddingLeft() + getPaddingRight();
    }

    private int getDesiredHeight() {
        return getPaddingTop() + getPaddingBottom();
    }
}
