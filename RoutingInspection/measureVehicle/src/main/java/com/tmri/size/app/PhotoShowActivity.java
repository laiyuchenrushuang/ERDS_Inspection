package com.tmri.size.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoShowActivity extends Activity {
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        String photoPath = getIntent().getStringExtra("path");
        PhotoView photo = (PhotoView) findViewById(R.id.photo);
        GlideLoader imageLoader=new GlideLoader();
        imageLoader.displayImage(this,photoPath,photo);
        mAttacher=new PhotoViewAttacher(photo);
        mAttacher.update();
    }

    public void btnBack(View view) {
        onBackPressed();
    }
}
