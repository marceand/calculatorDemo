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

    private static final int OFFSET = 4;
    private static final int DISPLAY_HEIGHT = 360;
    private static final int OPERATOR_WIDTH = 260;

    private UIKey[][] mUINumericKeys;
    private UIKey[] mUIOperatorKeys;


    private Paint recDisplayPaint;
    private Paint rectNumberPaint;
    private Paint rectOperatorsPaint;
    private Paint keyTextPaint;

    private int widthSize;
    private int heightSize;
    private int numericKeyHalfWidth;
    private int numericKeyHalfHeight;
    private int operatorKeyHalfWidth;
    private int operatorKeyHalfHeight;

    private boolean showCircle;
    private float animCircleX;
    private float animCircleY;
    private Paint circlePaint;
    private String userOperations;
    private float displayTextHeight;
    private Paint operationPaint;
    private String operationResult;
    private Paint resultPaint;
    private float displayTextXAxis;

    public CalculatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initNumericKeys();
        initOperatorKeys();
    }

    private void init() {
        initDisplayPaint();
        initNumbersPaint();
        initOperatorsPaint();
        initKeyTextPaint();
        initAnimCirclePaint();
        initOperationPaint();
        initResultOperation();
        initShowCircle();
        initUserOperation();
        initOperationResult();
    }

    private void initOperationPaint() {
        operationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        operationPaint.setTextSize(56);
        operationPaint.setColor(Color.DKGRAY);
        operationPaint.setTextAlign(Paint.Align.RIGHT);
    }

    private void initResultOperation() {
        resultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resultPaint.setTextSize(36);
        resultPaint.setColor(Color.DKGRAY);
        resultPaint.setTextAlign(Paint.Align.RIGHT);
    }

    private void initAnimCirclePaint() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAlpha(60);
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

    private void initKeyTextPaint() {
        keyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        keyTextPaint.setTextSize(56);
        keyTextPaint.setColor(Color.WHITE);
        keyTextPaint.setTextAlign(Paint.Align.CENTER);
    }


    private void initShowCircle() {
        showCircle = false;
    }

    private void initUserOperation() {
        userOperations = "";
    }

    private void initOperationResult() {
        operationResult = "";
    }

    private void initNumericKeys() {
        mUINumericKeys = new UIKey[4][3];
        mUINumericKeys[0][0] = new UIKey("7");
        mUINumericKeys[0][1] = new UIKey("8");
        mUINumericKeys[0][2] = new UIKey("9");
        mUINumericKeys[1][0] = new UIKey("4");
        mUINumericKeys[1][1] = new UIKey("5");
        mUINumericKeys[1][2] = new UIKey("6");
        mUINumericKeys[2][0] = new UIKey("1");
        mUINumericKeys[2][1] = new UIKey("2");
        mUINumericKeys[2][2] = new UIKey("3");
        mUINumericKeys[3][0] = new UIKey(".");
        mUINumericKeys[3][1] = new UIKey("0");
        mUINumericKeys[3][2] = new UIKey("=");
    }

    private void initOperatorKeys() {
        mUIOperatorKeys = new UIKey[5];
        mUIOperatorKeys[0] = new UIKey("DEL");
        mUIOperatorKeys[1] = new UIKey("\u00F7");
        mUIOperatorKeys[2] = new UIKey("\u00D7");
        mUIOperatorKeys[3] = new UIKey("\u2212");
        mUIOperatorKeys[4] = new UIKey("\u002B");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthSize = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        heightSize = View.resolveSize(getDesiredHeight(), heightMeasureSpec);

        numericKeyHalfWidth = ((widthSize - OPERATOR_WIDTH) / 3) / 2;
        numericKeyHalfHeight = ((heightSize - DISPLAY_HEIGHT) / 4) / 2;

        operatorKeyHalfWidth = (widthSize - OPERATOR_WIDTH) + (OPERATOR_WIDTH/2);
        operatorKeyHalfHeight = ((heightSize - DISPLAY_HEIGHT) / 5) / 2;


        displayTextHeight = (float) DISPLAY_HEIGHT / 3;
        displayTextXAxis =  (float) widthSize - 20;

        setNumericKeysPosition();
        setOperatorKeysPosition();

        setMeasuredDimension(widthSize, heightSize);
    }


    private void setNumericKeysPosition() {

        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 3; column++) {

                int keyXCenter = numericKeyHalfWidth + 2 *column* numericKeyHalfWidth;
                int keyYCenter = numericKeyHalfHeight + DISPLAY_HEIGHT + 2 *row* numericKeyHalfHeight;

                setTextKeyAxis(row, column, keyXCenter, keyYCenter);
                setKeyRect(row, column, keyXCenter, keyYCenter);
            }
        }
    }

    private void setTextKeyAxis(int row, int column, int keyXCenter, int keyYCenter) {
        mUINumericKeys[row][column].setKeyCenterAxis(keyXCenter, keyYCenter);
    }

    private void setKeyRect(int row, int column, int keyXCenter, int keyYCenter) {
        int left = keyXCenter - numericKeyHalfWidth - OFFSET;
        int top = keyYCenter - numericKeyHalfHeight - OFFSET;
        int right = keyXCenter + numericKeyHalfWidth - OFFSET;
        int bottom = keyYCenter + numericKeyHalfHeight - OFFSET;

        mUINumericKeys[row][column].setButtonRect(left, top, right, bottom);
    }
    private void setOperatorKeysPosition() {
        for(int row = 0; row < mUIOperatorKeys.length; row++){
            int keyXCenter = operatorKeyHalfWidth;
            int keyYCenter = 2*row* operatorKeyHalfHeight + operatorKeyHalfHeight +DISPLAY_HEIGHT;

            int left = keyXCenter - (OPERATOR_WIDTH/2) - OFFSET;
            int top = keyYCenter - operatorKeyHalfHeight - OFFSET;
            int right = keyXCenter + (OPERATOR_WIDTH/2) - OFFSET;
            int bottom = keyYCenter + operatorKeyHalfHeight - OFFSET;

            mUIOperatorKeys[row].setKeyCenterAxis(keyXCenter, keyYCenter);
            mUIOperatorKeys[row].setButtonRect(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawCircleAnimation(canvas);
        drawTextNumbers(canvas);
        drawTextOperators(canvas);
        drawUserOperations(canvas);
        drawOperationResult(canvas);
    }

    private void drawUserOperations(Canvas canvas) {
        canvas.drawText(userOperations, displayTextXAxis, displayTextHeight, operationPaint);
    }

    private void drawOperationResult(Canvas canvas) {
        canvas.drawText(operationResult, displayTextXAxis, 2* displayTextHeight, resultPaint);
    }

    private void drawCircleAnimation(Canvas canvas) {
        if(showCircle) {
            canvas.drawCircle(animCircleX, animCircleY, numericKeyHalfWidth,circlePaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            setTouchDownOnNumberKey((int)event.getX(), (int)event.getY());
            setTouchDownOnOperatorKey((int)event.getX(), (int)event.getY());
            showTouchDownCircle(true);
            invalidate();
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            showUserOperations((int)event.getX(), (int)event.getY());
            showOperationResult((int)event.getX(), (int)event.getY());
            cancelNumericKeyTouchDown();
            cancelOperatorKeyTouchDown();
            showTouchDownCircle(false);
            invalidate();
        }


        return true;
    }

    private void cancelOperatorKeyTouchDown() {
        for(int row = 0; row < mUIOperatorKeys.length; row++){
            mUIOperatorKeys[row].cancel();
        }
    }

    private void cancelNumericKeyTouchDown() {
        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 3; column++) {
                mUINumericKeys[row][column].cancel();
            }
        }
    }

    private void showOperationResult(int x, int y) {
        for(int row = 0; row < mUIOperatorKeys.length; row++){
            if(mUIOperatorKeys[row].isPressed(x, y)){
                if(mUIOperatorKeys[row].getKeyText().equals("DEL")) {
                    if(userOperations.length() > 0) {
                        userOperations = userOperations.substring(0,userOperations.length()-1);
                    }
                }else {
                    userOperations = userOperations + mUIOperatorKeys[row].getKeyText();
                    operationResult = "Result";
                }
            }
        }
    }

    private void showUserOperations(int x, int y) {
        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 3; column++) {
                if(mUINumericKeys[row][column].isPressed(x, y)){
                    userOperations = userOperations + mUINumericKeys[row][column].getKeyText();
                }
            }
        }
    }

    private void showTouchDownCircle(boolean show) {
        showCircle = show;
    }

    private void setTouchDownOnOperatorKey(int x, int y) {
        for(int row = 0; row < mUIOperatorKeys.length; row++){
            if(mUIOperatorKeys[row].isContained(x, y)){
                animCircleX = mUIOperatorKeys[row].getTextKeyXAxis();
                animCircleY = mUIOperatorKeys[row].getTextKeyYAxis();
            }
        }
    }

    private void setTouchDownOnNumberKey(int x, int y) {
        for(int row = 0; row < 4; row++){
            for(int column = 0; column < 3; column++) {
                if(mUINumericKeys[row][column].isContained(x, y)){
                    animCircleX = mUINumericKeys[row][column].getTextKeyXAxis();
                    animCircleY = mUINumericKeys[row][column].getTextKeyYAxis();
                }
            }
        }
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
                canvas.drawText(mUINumericKeys[row][column].getKeyText(), mUINumericKeys[row][column].getTextKeyXAxis(),
                        mUINumericKeys[row][column].getTextKeyYAxis(), keyTextPaint);
            }
        }
    }

    private void drawTextOperators(Canvas canvas) {
        for(int row = 0; row < mUIOperatorKeys.length; row++){
            canvas.drawText(mUIOperatorKeys[row].getKeyText(), mUIOperatorKeys[row].getTextKeyXAxis(), mUIOperatorKeys[row].getTextKeyYAxis(), keyTextPaint);
        }
    }


    public int getDesiredWidth() {
        return getPaddingLeft() + getPaddingRight();
    }

    private int getDesiredHeight() {
        return getPaddingTop() + getPaddingBottom();
    }
}
