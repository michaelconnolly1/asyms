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
 *  Activity to handle Eating Well Advice page
 */

public class EatingWellAdviceActivity extends AppCompatActivity {
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_well_advice);
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
            case R.id.making_changes:
                myAdviceDialog.setTitle(R.string.title_making_changes);
                str = getResources().getString(R.string.making_changes_advice);
                break;
            case R.id.before_after_surgery:
                myAdviceDialog.setTitle(R.string.title_before_after_surgery);
                str = getResources().getString(R.string.before_after_surgery_advice);
                break;
            case R.id.after_surgery:
                myAdviceDialog.setTitle(R.string.title_after_surgery);
                str = getResources().getString(R.string.after_surgery_advice);
                break;
            case R.id.low_fibre_diets:
                myAdviceDialog.setTitle(R.string.title_low_fibre_diets);
                str = getResources().getString(R.string.low_fibre_diets_advice);
                break;
            case R.id.foods_you_can_eat:
                myAdviceDialog.setTitle(R.string.title_foods_you_can_eat);
                str = getResources().getString(R.string.foods_you_can_eat_advice);
                break;

        }
        advice.setText(Html.fromHtml(str, null, new UITagHandler()));
        myAdviceDialog.show();
    }

    public void previousPage(View view) {
        Intent i = new Intent(EatingWellAdviceActivity.this, SelfCareAdviceActivity.class);
        startActivity(i);
    }
}
