package com.example.baselibrary.view.signature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.example.baselibrary.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SignatureView extends View {
    private static final String TAG = "SignatureView";
    public static final int PEN_WIDTH = 10;
    public static final int PEN_COLOR = -16777216;
    public static final int BACK_COLOR = -1;
    private float mPenX;
    private float mPenY;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap cacheBitmap;
    private int mPentWidth;
    private int mPenColor;
    private int mBackColor;
    private boolean isTouched;
    private String mSavePath;
    private Set<Float> listX = new HashSet<>();
    private Set<Float> listY = new HashSet<>();
    private int Interfacewidth;
    private int Interfacehight;

    public SignatureView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SignatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint = new Paint();
        this.mPath = new Path();
        this.mPentWidth = 10;
        this.mPenColor = -16777216;
        this.mBackColor = -1;
        this.isTouched = false;
        this.mSavePath = null;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SignatureView);
        this.mPenColor = typedArray.getColor(R.styleable.SignatureView_penColor, -16777216);
        this.mBackColor = typedArray.getColor(R.styleable.SignatureView_backColor, -1);
        this.mPentWidth = typedArray.getInt(R.styleable.SignatureView_penWidth, 10);
        typedArray.recycle();
        this.init();
    }

    private void init() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mPentWidth);
        this.mPaint.setColor(this.mPenColor);
    }

    public boolean getTouched() {
        return this.isTouched;
    }

    public void setPentWidth(int pentWidth) {
        this.mPentWidth = pentWidth;
    }

    public void setPenColor(int penColor) {
        this.mPenColor = penColor;
    }

    public void setBackColor(int backColor) {
        this.mBackColor = backColor;
    }

    public void clear() {
        if (this.mCanvas != null) {
            listX.clear();
            listY.clear();
            this.isTouched = false;
            this.mPaint.setColor(this.mPenColor);
            this.mCanvas.drawColor(this.mBackColor, Mode.CLEAR);
            this.mPaint.setColor(this.mPenColor);
            this.invalidate();
        }

    }

    //保存
    @SuppressLint("WrongThread")
    public boolean save(String path, boolean clearBlank, int blank) throws IOException {
        if (!TextUtils.isEmpty(path)) {
            this.mSavePath = path;
            Bitmap bitmap = this.cacheBitmap;
            if (listX.size()==0||listY.size()==0){
                return false;
            }
            //取出绘制图的四个点
            int maxX = new BigDecimal(Collections.max(listX)).intValue();
            int minX = new BigDecimal(Collections.min(listX)).intValue();
            int maxY = new BigDecimal(Collections.max(listY)).intValue();
            int minY = new BigDecimal(Collections.min(listY)).intValue();
            int with = maxX - minX;
            if (with < 0) {
                with = 0;
            }
            int hight = maxY - minY;
            if (hight < 0) {
                hight = 0;
            }
            int maxrtX = minX;
            if (maxrtX < 0) {
                maxrtX = 0;
            }
            int maxrtY = minY;
            if (maxrtY < 0) {
                maxrtY = 0;
            }
            if ((maxrtX + with) > Interfacewidth) {
                with = Interfacewidth;
                maxrtX = 0;
            }
            if ((maxrtY + hight) > Interfacehight) {
                with = Interfacehight;
                maxrtY = 0;
            }
            bitmap = Bitmap.createBitmap(bitmap, maxrtX, maxrtY, with, hight);
            if (clearBlank) {
                bitmap = this.clearBlank(bitmap, blank);
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, bos);

            byte[] buffer = bos.toByteArray();
            if (buffer != null) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (file.exists()) {
                    file.delete();
                }
                OutputStream os = new FileOutputStream(file);
                os.write(buffer);
                os.close();
                bos.close();
            }
            return true;
        } else {
            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //    获取bitmap
    public Bitmap getBitmap() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bitmap = this.getDrawingCache();
        this.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public String getSavePath() {
        return this.mSavePath;
    }

    //清除界面数据
    private Bitmap clearBlank(Bitmap bmp, int blank) {
        int height = bmp.getHeight();
        int width = bmp.getWidth();
        int top = 0;
        int left = 0;
        int right = 0;
        int bottom = 0;
        int[] pixs = new int[width];
        boolean isStop;
        int x;
        int[] var12;
        int var13;
        int var14;
        int pix;
        for (x = 0; x < height; ++x) {
            bmp.getPixels(pixs, 0, width, 0, x, width, 1);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;
            for (var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    top = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }

        for (x = height - 1; x >= 0; --x) {
            bmp.getPixels(pixs, 0, width, 0, x, width, 1);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;
            for (var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    bottom = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        pixs = new int[height];
        for (x = 0; x < width; ++x) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;
            for (var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    left = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        for (x = width - 1; x > 0; --x) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;

            for (var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    right = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        if (blank < 0) {
            blank = 0;
        }
        left = left - blank > 0 ? left - blank : 0;
        top = top - blank > 0 ? top - blank : 0;
        right = right + blank > width - 1 ? width - 1 : right + blank;
        bottom = bottom + blank > height - 1 ? height - 1 : bottom + blank;
        return Bitmap.createBitmap(bmp, left, top, right - left, bottom - top);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.cacheBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);
        //获取界面的绘制宽高，以保证在保存的绘制点不能比这连个大
        Interfacewidth = this.getWidth();
        Interfacehight = this.getHeight();
        this.mCanvas = new Canvas(this.cacheBitmap);
        this.mCanvas.drawColor(this.mBackColor);
        this.isTouched = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.cacheBitmap, 0.0F, 0.0F, this.mPaint);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mPenX = event.getX();
                this.mPenY = event.getY();
                this.mPath.moveTo(this.mPenX, this.mPenY);
                return true;
            case 1:
                this.mCanvas.drawPath(this.mPath, this.mPaint);
                this.mPath.reset();
                break;
            case 2:
                this.isTouched = true;
                float x = event.getX();
                float y = event.getY();
                float preX = this.mPenX;
                float preY = this.mPenY;
                int X = new BigDecimal(preX).intValue();
                int Y = new BigDecimal(preY).intValue();
                //不能比0小，不能比界面宽度大
                if (X >= 0) {
                    if (X < Interfacewidth) {
                        listX.add(preX);
                    }
                }
                //绘制点不能比0小，比界面高度大
                if (Y >= 0) {
                    if (Y < Interfacehight) {
                        listY.add(preY);
                    }
                }
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= 3.0F || dy >= 3.0F) {
                    float cx = (x + preX) / 2.0F;
                    float cy = (y + preY) / 2.0F;
                    this.mPath.quadTo(preX, preY, cx, cy);
                    this.mPenX = x;
                    this.mPenY = y;
                }
                this.invalidate();
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
