package com.game2048.zhang.a2048game;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private int num = 0;
    private TextView label;

    public Card(@NonNull Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(45);
        label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        label.setGravity(Gravity.CENTER);
//        label.setBackgroundColor(0xff660000);
        label.setBackgroundResource(R.drawable.text_view_border);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0,0);
        addView(label, lp);

        setNum(0);
    }

    public void setBackgroundColor(){
        switch(this.num){
            case 2:
                label.setBackgroundResource(R.drawable.text_view_border2);
                break;
            case 4:
                label.setBackgroundResource(R.drawable.text_view_border4);
                break;
            case 8:
                label.setBackgroundResource(R.drawable.text_view_border8);
                break;
            case 16:
                label.setBackgroundResource(R.drawable.text_view_border16);
                break;
            case 32:
                label.setBackgroundResource(R.drawable.text_view_border32);
                break;
            case 64:
                label.setBackgroundResource(R.drawable.text_view_border64);
                break;
            case 128:
                label.setBackgroundResource(R.drawable.text_view_border128);
                break;
            case 256:
                label.setBackgroundResource(R.drawable.text_view_border256);
                break;
            case 512:
                label.setBackgroundResource(R.drawable.text_view_border512);
                break;
            case 1024:
                label.setBackgroundResource(R.drawable.text_view_border1024);
                break;
            case 2048:
                label.setBackgroundResource(R.drawable.text_view_border2048);
                break;
            default:
                label.setBackgroundResource(R.drawable.text_view_border);
                break;
        }
    }

    public boolean equals(Card o){
        return getNum() == o.getNum();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if(num <= 0){
            label.setText("");
        }else {
            label.setText(Integer.toString(num));
        }
    }
}
