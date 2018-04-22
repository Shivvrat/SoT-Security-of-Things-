package com.example.prashant_admin.raspnotifier;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prashant-admin on 20/12/17.
 */

public class TemperatureDataViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = TemperatureDataViewHolder.class.getSimpleName();;
    private List<TemperatureData> temperatureDataObject;
    TextView messageTextView;
    TextView temperatureTextView;
    TextView dateTextView;
    public TemperatureDataViewHolder(final View itemView, final List<TemperatureData> temperatureDataObject) {
        super(itemView);
        this.temperatureDataObject = temperatureDataObject;
        temperatureTextView = (TextView) itemView.findViewById(R.id.temperatureTextView);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
    }
}
