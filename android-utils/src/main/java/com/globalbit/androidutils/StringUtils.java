package com.globalbit.androidutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Alex on 12/12/2016.
 */

public class StringUtils {

    //Checking if string is null or empty (including white spaces
    public static boolean isEmpty(String str) {
        if(TextUtils.isEmpty(str)||TextUtils.isEmpty(str.trim())) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String formatNumber(double d)
    {
        return new DecimalFormat("#.##").format(d);
    }

    //Validating if string is a full name
    public static boolean isFullNameValid(String fullName) {
        boolean isFullName=true;
        if(StringUtils.isEmpty(fullName)) {
            isFullName=false;
        }
        else {
            String regex="^[\\p{L} ,.'-]{2,}$";
            if(!Pattern.matches(regex, fullName)) {
                isFullName=false;
            } else {
                fullName=fullName.trim();
                String[] str=fullName.split(" ");
                if(str.length<2) {
                    isFullName=false;
                }
                for(String name:str) {
                    if (name.trim().length() < 2) {
                        isFullName = false;
                        break;
                    }
                }
            }
        }
        return isFullName;
    }

    //Email validation
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    /**
     *
     * @param activity
     * @param requestCode request code for permission results (write/read)
     * @return
     */
    public static File createImageFile(Activity activity, int requestCode) {

        File image = null;

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestCode);

        }else{

            try {
                // Create an image file name
                String timeStamp = Long.toString(Calendar.getInstance().getTimeInMillis());
                String imageFileName = "JPEG_" + timeStamp + "_";

                File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                //File outputDir = activity.getFilesDir(); // activity being the Activity pointer

                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        outputDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return image;
    }

}
