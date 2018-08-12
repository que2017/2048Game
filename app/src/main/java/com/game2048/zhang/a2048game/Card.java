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
        label.setBackgroundResource(R.drawable.text_view_border);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0,0);
        addView(label, lp);

        setNum(0);
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
