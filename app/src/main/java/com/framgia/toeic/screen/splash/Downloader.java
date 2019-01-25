package com.framgia.toeic.screen.splash;

import android.content.Context;
import android.content.ContextWrapper;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Downloader {
    private static final int BYTE = 1024;
    private static final String WRITE_SUCCESS = "Download success!";
    private Context mContext;
    private List<String> mLink;

    public Downloader(Context context, List<String> link) {
        mContext = context;
        mLink = link;
    }

    public void download(OnWriteData callback, String folder) {
        for (String link : mLink) {
            int count;
            Exception exception = null;
            FileOutputStream fos = null;
            try {
                ContextWrapper cw = new ContextWrapper(mContext);
                File directory = cw.getDir(folder, Context.MODE_PRIVATE);
                URL url = new URL(link);
                String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(url));
                String name = URLUtil.guessFileName(String.valueOf(url), null, fileExtenstion);
                File mypath = new File(directory, name);
                if (!mypath.exists()) {
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    InputStream input = new BufferedInputStream(url.openStream());
                    fos = new FileOutputStream(mypath);
                    byte data[] = new byte[BYTE];
                    while ((count = input.read(data)) != -1) {
                        fos.write(data, 0, count);
                    }
                    fos.flush();
                    fos.close();
                    input.close();
                }
            } catch (FileNotFoundException e) {
                exception = e;
            } catch (MalformedURLException e) {
                exception = e;
            } catch (IOException e) {
                exception = e;
            }
            if (exception != null) {
                callback.writeFileError(exception);
            } else {
                callback.writeFileSuccess(link);
            }
        }
    }
}
