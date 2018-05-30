package com.test.heart.heartme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.heart.heartme.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private MainPresenter mPresenter;
    private EditText mTestName;
    private EditText mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter();
        mPresenter.init(this);
        mTestName = findViewById(R.id.test_name);
        mResult = findViewById(R.id.result);
        findViewById(R.id.submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String testName = mTestName.getText().toString();
        String result = mResult.getText().toString();
        int testResult = 3;
        if (testName.equals("") || result.equals(""))
        {
            testResult = 2;
        }
        else
        {
            testResult = mPresenter.Check(mTestName.getText().toString(), Long.parseLong(mResult.getText().toString()));
        }

        TextView userfinal = findViewById(R.id.user_final);
        if (testResult == 1)
        {
            userfinal.setText("very Ok");
        } else if (testResult == 2)
        {
            userfinal.setText(getString(R.string.unknown));
        } else if (testResult == 3)
        {
            userfinal.setText("Bad");
        }
    }
}

