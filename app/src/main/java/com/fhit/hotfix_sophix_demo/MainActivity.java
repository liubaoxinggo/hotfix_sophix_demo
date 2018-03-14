package com.fhit.hotfix_sophix_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fhit.hotfix_sophix_demo.util.LogUtils;

public class MainActivity extends Activity {

    TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = (TextView)findViewById(R.id.tv_info);
        tvInfo.setText("\"热更新技术\",\"没有bug的版本\"");
        LogUtils.d("热更新技术","没有bug的版本");
        findViewById(R.id.btn_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResult();
            }
        });
    }
    private float getResult(){
        tvInfo.setText(tvInfo.getText().toString()+"\n???1123456");
        Toast.makeText(this,"我被修复了",Toast.LENGTH_LONG).show();
        return 1 / 1;
    }
}
