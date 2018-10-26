package com.wanji.customview.base64;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * by  :   syz
 * Time: 2018/10/19 14:54
 * Description:
 */
public class Base64Convert {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encode(String string) {
        try {
            return Base64.getEncoder().encodeToString(string.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e("syz", "encode error:" + e.getMessage());
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] decode(String string) {
        byte[] bytes = null;
        try {
            String replace = string.replace("\n", "");
            String rr = replace.replace(" ", "");
            bytes = Base64.getDecoder().decode(rr);
//            Log.e("syz", new String(bytes, "utf-8"));
        } catch (Exception e) {
            Log.e("syz", "decode error:" + e.getMessage());
        }
        String[] s = new String[]{"asd","asd"};
        return bytes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] get(Context context,String fileName){
        byte[] bytes = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);
            is.close();
            String result = new String(buffer, "utf-8");
            String replace = result.replace("\r\n", "");
            bytes = Base64.getDecoder().decode(replace);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
