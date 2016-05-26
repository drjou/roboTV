package org.xvdr.extractor;

import android.util.Log;

import com.google.android.exoplayer.MediaFormat;

import java.nio.ByteBuffer;

public class Allocation {

    private ByteBuffer mData;
    private boolean mAllocated = false;
    private MediaFormat mFormat = null;

    public long timeUs = 0;
    public int flags = 0;

    public Allocation(int size) {
        resize(size);
    }

    public Allocation(MediaFormat format) {
        mFormat = format;
    }

    public boolean isFormat() {
        return (mFormat != null);
    }

    public boolean isSample() {
        return (mFormat == null);
    }

    public void resize(int size) {
        if(mData != null && mData.capacity() >= size) {
            return;
        }

        size = ((size / 8192) + 1) * 8192;

        mData = ByteBuffer.allocateDirect(size);
    }

    MediaFormat getFormat() {
        return mFormat;
    }

    public ByteBuffer data() {
        return mData;
    }

    public void setLength(int length) {
        mData.limit(length);
    }

    public int length() {
        return mData.limit();
    }

    public int size() {
        return mData.capacity();
    }

    synchronized public boolean allocate() {
        if(mAllocated) {
            return false;
        }

        mAllocated = true;
        mData.clear();

        return true;
    }

    synchronized boolean release() {
        if(!mAllocated) {
            return false;
        }

        mAllocated = false;
        mFormat = null;

        return true;
    }

}
