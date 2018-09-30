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
 *  Activity to handle End of Treatment Advice page
 */

public class EndOfTreatmentActivity extends AppCompatActivity {
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_treatment);

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);
    }

    public void giveAdvice(View view) {
        String str = "";
        final Dialog myAdviceDialog = new Dialog(this, R.style.myDialogTheme);
        myAdviceDialog.setContentView(R.layout.selfcare_advice_dialog_layout);
        TextView advice = (TextView) myAdviceDialog.findViewById(R.id.advice);
        Button myOKButton = (Button) myAdviceDialog.findViewById(R.id.okButton);

        myOKButton.setOnClickListener((View view1) -> {
            myAdviceDialog.dismiss();
        });

        switch (view.getId()) {
            case R.id.once_treatment_is_over:
                myAdviceDialog.setTitle(R.string.title_once_treatment_is_over);
                str = getResources().getString(R.string.once_treatment_is_over_advice);
                break;
            case R.id.worries_about_reoccurrence:
                myAdviceDialog.setTitle(R.string.title_worries_about_reoccurrence);
                str = getResources().getString(R.string.worries_about_reoccurrence_advice);
                break;
            case R.id.follow_up_care:
                myAdviceDialog.setTitle(R.string.title_follow_up_care);
                str = getResources().getString(R.string.follow_up_care_advice);
                break;
            case R.id.emotions_and_feelings:
                myAdviceDialog.setTitle(R.string.title_emotions_and_feelings);
                str = getResources().getString(R.string.emotions_and_feelings_advice);
                break;
            case R.id.disturbed_sleep:
                myAdviceDialog.setTitle(R.string.title_disturbed_sleep);
                str = getResources().getString(R.string.disturbed_sleep_advice);
                break;
            case R.id.fatigue:
                myAdviceDialog.setTitle(R.string.title_fatigue);
                str = getResources().getString(R.string.fatigue_advice);
                break;
            case R.id.lifestyle_choices:
                myAdviceDialog.setTitle(R.string.title_lifestyle_choices);
                str = getResources().getString(R.string.lifestyle_choices_advice);
                break;
            case R.id.environmental_factors:
                myAdviceDialog.setTitle(R.string.title_environmental_factors);
                str = getResources().getString(R.string.environmental_factors_advice);
                break;
        }
        advice.setText(Html.fromHtml(str, null, new UITagHandler()));
        myAdviceDialog.show();
    }

    public void previousPage(View view) {
        Intent i = new Intent(EndOfTreatmentActivity.this, SelfCareAdviceActivity.class);
        startActivity(i);
    }
}
