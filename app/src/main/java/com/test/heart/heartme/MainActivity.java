package com.test.heart.heartme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.heart.heartme.presenter.ActivityListener;
import com.test.heart.heartme.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActivityListener
{

    private MainPresenter mPresenter;
    private EditText mTestName;
    private EditText mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        loadData();
        mTestName = findViewById(R.id.test_name);
        mResult = findViewById(R.id.result);
        findViewById(R.id.submit).setOnClickListener(this);
    }

    private void loadData()
    {
        mPresenter.loadJSONData(this);
    }

    @Override
    public void onClick(View v)
    {
        String testName = mTestName.getText().toString();
        String result = mResult.getText().toString();
        int testResult = 2;
        if (testName.equals("") || result.equals(""))
        {
            testResult = 2;
        }
        else
        {
            testResult = mPresenter.Check(testName, Long.parseLong(result));
        }

        TextView userFinal = findViewById(R.id.user_final);
        if (testResult == 1)
        {
            userFinal.setText("Good");
        } else if (testResult == 2)
        {
            userFinal.setText(getString(R.string.unknown));
        } else if (testResult == 3)
        {
            userFinal.setText("Bad");
        }
    }

    @Override
    public void onError()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("there was an error in the network request, try again ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadData();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //exit the app if the user clicks 'no' for data
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}

