package com.songmin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.songmin.myapplication.manager.TranslateManager;

public class TransActivity extends AppCompatActivity {

    EditText keywordView;
    TextView addView;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        keywordView = (EditText) findViewById(R.id.editText2);
        addView = (TextView) findViewById(R.id.textView_add);
        String add = addView.getText().toString();
        Toast.makeText(TransActivity.this, "" + add, Toast.LENGTH_SHORT).show();

//        String keyword = keywordView.getText().toString();
//        if (!TextUtils.isEmpty(keyword)){
//            TranslateManager.getInstance().translateKoreantoEng(keyword);
//        }else {
//            Toast.makeText(TransActivity.this, "없당!!!!!!!", Toast.LENGTH_SHORT).show();
//        }

        TranslateManager.getInstance().translateKoreantoEng(add);
//        Intent intent = new Intent(getApplicationContext(), TranslateManager.class);
//        intent.putExtra("add", add);
//        startActivity(intent);
        mContext=this;
    }

    public void setTranslate(String trans){
        addView.setText(trans);
    }
}
