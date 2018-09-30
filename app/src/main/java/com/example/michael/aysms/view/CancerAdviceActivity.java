package com.example.michael.aysms.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.UITagHandler;

/**
 * Created by Michael Connolly on 15/08/2018.
 *
 *  Activity to handle Cancer Advice page
 */

public class CancerAdviceActivity extends AppCompatActivity {
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancer);
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);
    }

    public void giveAdvice(View view) {
        String str = "";
        final Dialog myAdviceDialog = new Dialog(this, R.style.myDialogTheme);
        myAdviceDialog.setContentView(R.layout.selfcare_advice_dialog_layout);
        TextView advice = (TextView)myAdviceDialog.findViewById(R.id.advice);
        Button myOKButton = (Button)myAdviceDialog.findViewById(R.id.okButton);

        myOKButton.setOnClickListener((View view1) -> {
            myAdviceDialog .dismiss();
        });

        switch (view.getId()) {
            case R.id.what_is_cancer:
                myAdviceDialog.setTitle(R.string.title_what_is_cancer);
                str = getResources().getString(R.string.what_is_cancer_advice);
                break;
            case R.id.what_is_colorectal_cancer:
                myAdviceDialog.setTitle(R.string.title_what_is_colorectal_cancer);
                str = getResources().getString(R.string.what_is_colorectal_cancer_advice);
                break;
            case R.id.what_is_treatment_options:
                myAdviceDialog.setTitle(R.string.title_what_is_treatment_options);
                str = getResources().getString(R.string.what_is_treatment_options_advice);
                break;
        }
        advice.setText(Html.fromHtml(str, null, new UITagHandler()));
        myAdviceDialog.show();
    }

    public void previousPage(View view) {
        Intent i = new Intent(CancerAdviceActivity.this, SelfCareAdviceActivity.class);
        startActivity(i);
    }
}
