package com.mt.boostgamesuper.utils;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;

public class Utils {

    public static int[] colormain = new int[]{
            0xFF051937,
            0xFF004d7a,
            0xFF008793,
            0xFF00bf72};

    public static int[] colorRed = new int[]{
            0xFFdc86cd,
            0xFFde6ea8,
            0xFFd85981,
            0xFFcb495a,
            0xFFb73f33};


    public static int[] colorCenter = new int[]{
            0xFF7b72b5,
            0xFF9973aa,
            0xFFac769f,
            0xFFb77d95};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundMain(RelativeLayout frameLayout, final int[] colormain) {
        try {
            ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
                @Override
                public Shader resize(int width, int height) {
//             background-image: linear-gradient(to right top, #ee0730, #e84947, #e0695e, #d58278, #c69992);
                    LinearGradient linearGradient = new LinearGradient(0, 0, width, height,

                            colormain,
                            new float[]{
                                    0, 0.40f, 0.60f, 1},
                            Shader.TileMode.REPEAT);
                    return linearGradient;
                }
            };
            PaintDrawable paint = new PaintDrawable();
            paint.setShape(new RectShape());
            paint.setShaderFactory(shaderFactory);

            frameLayout.setBackground(paint);
        } catch (Exception e) {

        }
    }

}
