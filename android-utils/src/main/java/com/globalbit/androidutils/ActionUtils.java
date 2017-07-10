package com.globalbit.androidutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.Locale;

/**
 * Created by Alex on 12/12/2016.
 */

public class ActionUtils {

    public static void openPhone(String phoneNumber, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void openSms(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+phone));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     *
     * @param activity
     * @param photoFile
     * @param takePhotoRequest request code for permission results (write/camera)
     */
    public static void openCamera(Activity activity, File photoFile, int takePhotoRequest) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    takePhotoRequest);

        }
        else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    activity.startActivityForResult(takePictureIntent, takePhotoRequest);
                }
            }
        }
    }

    /**
     *
     * @param fragment
     * @param photoFile
     * @param takePhotoRequest request code for permission results (write/camera)
     */
    public static void openCamera(Fragment fragment, File photoFile, int takePhotoRequest) {
        if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    takePhotoRequest);

        }
        else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    fragment.startActivityForResult(takePictureIntent, takePhotoRequest);
                }
            }
        }
    }

    /***
     *
     * @param fragment
     * @param takePhotoRequest request code for permission results (read)
     */
    public static void openGallery(Fragment fragment, int takePhotoRequest) {

        if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    takePhotoRequest);

        }
        else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            if (photoPickerIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
                photoPickerIntent.setType("image/*");
                fragment.startActivityForResult(photoPickerIntent, takePhotoRequest);
            }
        }

    }

    /**
     *
     * @param context
     * @param latitude
     * @param longitude
     * @param address
     */
    public static void openNavigation(Context context, float latitude, float longitude, String address) {
        Intent intent=new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format(Locale.getDefault(), "geo:%f,%f?q=%s", latitude, longitude,address)));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     *
     * @param context
     * @param email
     */
    public static void openEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+ email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_email_using)));
        }
    }

}
