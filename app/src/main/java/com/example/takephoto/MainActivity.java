package com.example.takephoto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestTakePhoto testTakePhoto = new TestTakePhoto();
                testTakePhoto.doTakePhoto(MainActivity.this);
            }
        });
//        {
//            // 打不开
//            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
//        }
        {   // 打开
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
//            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
//        {   // 打不开
//            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
//            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
//        }
    }
}
