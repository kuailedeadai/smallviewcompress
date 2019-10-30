package com.duoyi.smallvideolib;

import java.nio.ByteBuffer;

public class YuvHelper {

    static {
        System.loadLibrary("video");
    }

    public native static int convertVideoFrame(ByteBuffer src, ByteBuffer dest, int destFormat, int width, int height, int padding, int swap);
}
