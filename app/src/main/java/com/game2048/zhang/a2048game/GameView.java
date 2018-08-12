package com.game2048.zhang.a2048game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<Point>();

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGameView();
    }

    /*@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10) / 4;
        // System.out.println("cardWidth="+cardWidth);
        addCards(cardWidth, cardWidth);
    }*/

    private void addCards(int cardWidth, int cardHeight) {
        Card c;

        for(int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                c = new Card(getContext());
                c.setNum(0);
                // System.out.println(c.getNum());
                addView(c, cardWidth, cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardWidth(), getCardWidth());
        
        startGame();

        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;

                        if(Math.abs(offsetX) > Math.abs(offsetY)){
                            if(offsetX < -5){
                                // System.out.println("left");
                                swipeLeft();
                            }else if (offsetX > 5){
                                // System.out.println("right");
                                swipeRight();
                            }
                        }else {
                            if (offsetY < -5){
                                // System.out.println("up");
                                swipeUp();
                            }else if (offsetY > 5) {
                                // System.out.println("down");
                                swipeDown();
                            }
                        }
                        break;
                }
                // return true 表示touch down事件触发成功，后续会有touch move 和 touch up 事件
                // return false 表示touch down事件没有触发成功，就不会有touch move 和 touch up 事件
                return true;
            }
        });
    }

    private void startGame() {
        // MainActivity.getMainActivity().clearScore();

        for(int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    private int getCardWidth() {
        // 屏幕信息对象
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取屏幕信息
        int width = dm.widthPixels;

        return (width - 10) / 4;
    }

    private void addRandomNum(){
        emptyPoint.clear();

        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if(cardsMap[x][y].getNum() <= 0){
                    emptyPoint.add(new Point(x,y));
                }
            }
        }

        Point p = emptyPoint.remove((int)(Math.random() * emptyPoint.size()));
        cardsMap[p.x][p.y].setNum(Math.random() <= 0.1 ? 4 : 2);
    }

    private boolean checkWin(){
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(cardsMap[x][y].getNum() == 2048) return true;
            }
        }
        return false;
    }

    private void checkComplete(){
        boolean complete = true;
        ALL:
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                if(cardsMap[x][y].getNum() == 0 ||
                    (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                    (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                    (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                    (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {
                    complete = false;
                    break ALL;
                }
            }
        }

        if(complete){
            new AlertDialog.Builder(getContext())
                    .setTitle("提示:")
                    .setMessage("游戏结束!")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).show();
        }

        if(checkWin()){
            new AlertDialog.Builder(getContext())
                    .setTitle("提示:")
                    .setMessage("恭喜你赢了!\n点击 确定 再来一局").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).show();
        }
    }

    private void swipeLeft(){
        // 是否可以添加
        boolean merge = false;

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for(int y1 = y+1; y1 < 4; y1++){
                    if(cardsMap[x][y1].getNum() > 0){
                        if(cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight(){
        boolean merge = false;

        for (int x = 0; x < 4; x++){
            for (int y = 3; y >= 0; y--){
                for(int y1 = y-1; y1 >= 0; y1--){
                    if(cardsMap[x][y1].getNum() > 0){
                        if(cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp(){
        boolean merge = false;

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                for(int x1 = x+1; x1 < 4; x1++){
                    if(cardsMap[x1][y].getNum() > 0){
                        if(cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown(){
        boolean merge = false;

        for (int y = 0; y < 4; y++){
            for (int x = 3; x >= 0; x--){
                for(int x1 = x-1; x1 >= 0; x1--){
                    if(cardsMap[x1][y].getNum() > 0){
                        if(cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
}
