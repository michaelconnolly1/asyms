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
 *  Activity to handle Self Care Advice page
 */

public class SelfCareAdviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care_advice);
    }

    public void giveEndOfTreatmentAdvice(View view) {
        Intent i = new Intent(SelfCareAdviceActivity.this, EndOfTreatmentActivity.class);
        startActivity(i);
    }

    public void giveChemotherapyAdvice(View view) {
        Intent i = new Intent(SelfCareAdviceActivity.this, ChemotherapyAdviceActivity.class);
        startActivity(i);
    }

    public void giveCancerAdvice(View view) {
        Intent i = new Intent(SelfCareAdviceActivity.this, CancerAdviceActivity.class);
        startActivity(i);
    }

    public void giveEatingWellAdvice(View view) {
        Intent i = new Intent(SelfCareAdviceActivity.this, EatingWellAdviceActivity.class);
        startActivity(i);
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
            case R.id.end_of_treatment:
                myAdviceDialog.setTitle(R.string.title_end_of_treatment);
                str = getResources().getString(R.string.end_of_treatment);
                break;
        }
        advice.setText(Html.fromHtml(str, null, new UITagHandler()));
        myAdviceDialog.show();
    }
}
