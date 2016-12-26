package com.lyun;

import java.io.File;

public class AppFileDirs {

    private static AppFileDirs mInstance;

    private static String IMAGE = "image";
    private static String VIDEO = "video";
    private static String AUDIO = "audio";
    private static String CACHE = "cache";
    private static String LOG = "log";

    private File image;
    private File video;
    private File audio;
    private File cache;
    private File log;

    private AppFileDirs() {
    }

    public static AppFileDirs instance() {
        if (mInstance == null) {
            synchronized (AppFileDirs.class) {
                mInstance = new AppFileDirs();
            }
        }
        return mInstance;
    }

    public File image() {
        return image;
    }

    public File video() {
        return video;
    }

    public File audio() {
        return audio;
    }

    public File cache() {
        return cache;
    }

    public File log() {
        return log;
    }

    public AppFileDirs create(String parent) {
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        image = getOrCreateDir(parent, IMAGE);
        video = getOrCreateDir(parent, VIDEO);
        audio = getOrCreateDir(parent, AUDIO);
        cache = getOrCreateDir(parent, CACHE);
        log = getOrCreateDir(parent, LOG);
        return mInstance;
    }

    private File getOrCreateDir(String parent, String name) {
        File file = new File(parent + "/" + name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
