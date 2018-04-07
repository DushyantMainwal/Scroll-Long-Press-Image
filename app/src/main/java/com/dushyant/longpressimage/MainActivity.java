package com.dushyant.longpressimage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thumb1);
        int bitmapWidth = bitmap.getWidth() / 3;
        int bitmapHeight = bitmap.getHeight() / 3;

        System.out.println("Bitmap Width: " + bitmapWidth);
        System.out.println("Bitmap Height: " + bitmapHeight);

        // set maximum scroll amount (based on center of image)
        int maxX = (int) ((bitmapWidth / 2) - (1080 / 2));
        int maxY = (int) ((bitmapHeight / 2) - (1920 / 2));

        // set scroll limits
        final int maxRightLimit = (maxX * -1);
        final int maxLeftLimit = maxX;
        final int maxTopLimit = maxY;
        final int maxBottomLimit = (maxY * -1);

        final int maxRight = 1080 + (maxX * -1);
        final int maxLeft = maxX;
        final int maxTop = maxY;
        final int maxBottom = 1920 + (maxY * -1);

        System.out.println("MaxLeft: " + maxLeft);
        System.out.println("MaxRight: " + maxRight);
        System.out.println("MaxTop: " + maxTop);
        System.out.println("MaxBottom: " + maxBottom);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            float downX, downY;
            int scrollByX, scrollByY;

            public boolean onTouch(View view, MotionEvent event) {
                float currentX, currentY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        System.out.println("Down X: " + downX + " and Y: " + downY);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        currentX = event.getX();
                        currentY = event.getY();
                        scrollByX = (int) (downX - currentX);
                        scrollByY = (int) (downY - currentY);

                        System.out.println("Down X: " + downX + " Y: " + downY);
                        System.out.println("Current X: " + currentX + " Y: " + currentY);
                        System.out.println("Scroll X: " + scrollByX + " Y: " + scrollByY);

                        Rect rectf = new Rect();
                        //For coordinates location relative to the parent
                        imageView.getLocalVisibleRect(rectf);
                        System.out.println("left   :" + String.valueOf(rectf.left));
                        System.out.println("right  :" + String.valueOf(rectf.right));
                        System.out.println("top    :" + String.valueOf(rectf.top));
                        System.out.println("bottom :" + String.valueOf(rectf.bottom));

                        if (currentX > downX) {//Left
                            System.out.println("Left Side");
                            if (rectf.left != maxLeft) {
                                if (rectf.left <= maxLeft) {
                                    imageView.scrollTo(maxLeftLimit, 0);
                                    System.out.println("Last Scroll Left: " + rectf.left);
                                } else
                                    imageView.scrollBy(scrollByX, 0);
                            }
                        }

                        if (currentX < downX) {//Right
                            System.out.println("Right Side");
                            if (rectf.right != maxRight) {
                                if (rectf.right >= maxRight) {
                                    imageView.scrollTo(maxRightLimit, 0);
                                    System.out.println("Last Scroll Right: " + rectf.right);
                                } else
                                    imageView.scrollBy(scrollByX, 0);
                            }
                        }

//                        if (currentY > downY) {//Top
//                            System.out.println("Top Side");
//                            if (rectf.top != maxTop) {
//                                if (rectf.top <= maxTop) {
//                                    imageView.scrollTo(0, maxTopLimit);
//                                    System.out.println("Last Scroll Top: " + rectf.top);
//                                } else
//                                    imageView.scrollBy(0, scrollByY);
//                            }
//                        }

//                        if (currentY < downY) {//Bottom
//                            System.out.println("Bottom Side");
//                            if (rectf.bottom != maxBottom) {
//                                if (rectf.bottom <= maxBottom) {
//                                    imageView.scrollTo(0, maxBottomLimit);
//                                    System.out.println("Last Scroll Top: " + rectf.bottom);
//                                } else
//                                    imageView.scrollBy(0, scrollByY);
//                            }
//                        }

                        downX = currentX;
                        downY = currentY;
                        break;

                }
                return true;
            }
        });

    }
}
