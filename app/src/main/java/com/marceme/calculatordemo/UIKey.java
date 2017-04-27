package com.marceme.calculatordemo;


import android.graphics.Rect;

/**
 * @author Marcelino Yax-marce7j@gmail.com-Android Developer
 *         Created on 4/24/2017.
 */

public class UIKey {

    private Rect buttonRect;
    private boolean touchDown;
    private String keyText;
    private float xKeyCenter;
    private float yKeyCenter;

    public UIKey(String keyText) {
        this.keyText = keyText;
        buttonRect = new Rect();

    }

    public boolean isContained(int touchX, int touchY){
        touchDown = buttonRect.contains(touchX, touchY);
        return touchDown;
    }

    public boolean isPressed(int touchX, int touchY){
        return touchDown && buttonRect.contains(touchX, touchY);
    }

    public void setButtonRect(int left, int top, int right, int bottom){
        buttonRect.set(left, top, right, bottom);
    }

    public void setKeyCenterAxis(float xKeyCenter, float yKeyCenter){
        this.xKeyCenter = xKeyCenter;
        this.yKeyCenter = yKeyCenter;
    }

    public float getTextKeyXAxis() {
        return xKeyCenter;
    }

    public float getTextKeyYAxis() {
        return yKeyCenter;
    }

    public String getKeyText() {
        return keyText;
    }

    public void cancel() {
        touchDown = false;
    }
}
