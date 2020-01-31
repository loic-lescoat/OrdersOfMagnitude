package com.example.ordersofmagnitude;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Disc extends View {

    float radius;
    int color;
    Paint paintbrush = new Paint();



    public Disc(Context context, AttributeSet attributeSet){
        super(context, attributeSet);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.Disc,
                0, 0);

        try {
            int col = a.getColor(R.styleable.Disc_color, Color.LTGRAY);
            float rad = a.getFloat(R.styleable.Disc_radius, 10f);
            setDiscAttributes(col, rad);
            setPaintBrushAttributes();

        } finally {
            a.recycle();
        }
    }

    public Disc(Context context, float rad, int col){
        super(context);

        setDiscAttributes(col, rad);

    }

    public void setDiscAttributes(int col, float rad){
        setColor(col);
        setRadius(rad);
    }


    private void setPaintBrushAttributes() {


        paintbrush.setDither(true);
        paintbrush.setAntiAlias(true);
        paintbrush.setColor(color);
        paintbrush.setStyle(Paint.Style.FILL);
        paintbrush.setStrokeJoin(Paint.Join.ROUND);
        paintbrush.setStrokeWidth(10f);
    }

    public int getColor(){
        return color;
    }

    public float getRadius(){
        return radius;
    }



    public void setColor(int col){
        color = col; // color of Disc is redundant TODO remove it
        paintbrush.setColor(color);
        invalidate();
//        requestLayout(); // used only when change size or shape
    }

    public void setRadius(float rad){
        radius = rad;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paintbrush);
    }

    public void resize(float to, float duration){
        float from = getRadius();
        ValueAnimator animation = ValueAnimator.ofFloat(from, to);
        animation.setDuration((long) duration);

//        animation.setRepeatMode(ValueAnimator.REVERSE);
//        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                setRadius((float) updatedAnimation.getAnimatedValue());
            }
        });

        animation.start();


    }

    public void resizeByFactor(float factor, float duration){
        resize(getRadius() * factor, 1000);
    }

    public ValueAnimator loopBetweenSizes(float from, float to, float duration){
        ValueAnimator animation = ValueAnimator.ofFloat(from, to);
        animation.setDuration((long) duration);

        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                setRadius((float) updatedAnimation.getAnimatedValue());
            }
        });

        animation.start();
        return animation;
    }

}
