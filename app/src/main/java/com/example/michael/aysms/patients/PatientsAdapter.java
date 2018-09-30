package com.example.michael.aysms.patients;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.aysms.R;
import com.example.michael.aysms.model.Patient;
import com.example.michael.aysms.view.ClinicianMainActivity;

import java.util.List;

/**
 * Created by Michael Connolly on 08/09/2018.
 *
 *  Recycler Adapter to handle list of patients
 */

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.MyViewHolder> {

    private int year, month, day;
    private List<Patient> patientList;
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
                ((ClinicianMainActivity) mContext).choosePatient(position);
                notifyItemChanged(position);
            });
        }
    }

    public PatientsAdapter(Activity context, List<Patient> patientList) {
        this.patientList = patientList;
        this.mContext = context;
        Log.d("PatientsAdapter", Integer.toString(this.patientList.size()));
    }

    @Override
    public void onBindViewHolder(PatientsAdapter.MyViewHolder holder, int position) {
        Log.d("onBindViewHolder", Integer.toString(position));
        Patient patient = patientList.get(position);
        String fullName = patient.getPatientName();
        String CHINo = patient.getCHINo();

        holder.mPatientName.setText(fullName + " (CHI Number: " + CHINo + ")");
        if ( (position & 1) == 0)
            holder.mPatientName.setBackground(ContextCompat.getDrawable(mContext, R.drawable.buttonalternate));
        else
            holder.mPatientName.setBackground(ContextCompat.getDrawable(mContext, R.drawable.button));
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    @Override
    public PatientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_layout_row, parent, false);
        return new PatientsAdapter.MyViewHolder(v);
    }

}

