package com.example.michael.aysms.patients;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.aysms.R;
import com.example.michael.aysms.model.PatientQuestionnaire;
import com.example.michael.aysms.view.QuestionnaireListActivity;
import java.util.List;

/**
 * Created by Michael Connolly on 018/09/2018.
 *
 *  Recycler Adapter to handle list of questionnaires
 */

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.MyViewHolder> {

    private int year, month, day;
    private List<PatientQuestionnaire> questionnaireList;
    private Activity mContext;
    private int mPosition;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView mPatientName;
        ;
        public MyViewHolder(View view) {
            super(view);
            mPatientName = (TextView) view.findViewById(R.id.patientID);

            // Now set up the button listeners and pass the button tag
            mPatientName.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                mPosition = position;
                ((QuestionnaireListActivity) mContext).chooseQuestionnaire(position);
                notifyItemChanged(position);
            });
        }
    }

    public QuestionnaireAdapter(Activity context, List<PatientQuestionnaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
        this.mContext = context;
        Log.d("QuestionnaireAdapter", Integer.toString(this.questionnaireList.size()));
    }

    @Override
    public void onBindViewHolder(QuestionnaireAdapter.MyViewHolder holder, int position) {
        Log.d("onBindViewHolder", Integer.toString(position));
        PatientQuestionnaire questionnaire = questionnaireList.get(position);
        String dateTime = questionnaire.getDateTime();

        holder.mPatientName.setText(dateTime.toString());
    }

    @Override
    public int getItemCount() {
        return questionnaireList.size();
    }

    @Override
    public QuestionnaireAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_layout_row, parent, false);
        return new QuestionnaireAdapter.MyViewHolder(v);
    }

}


