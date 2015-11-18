package de.h_da.fbi.game1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.h_da.fbi.game1.R;
import de.h_da.fbi.game1.model.Task;


public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> aufgaben) {
        super(context, 0, aufgaben);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Task user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_task_for_history, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Username);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.Score);
        TextView tvSkipped = (TextView) convertView.findViewById(R.id.SkippedExercises);
        TextView tvWrong = (TextView) convertView.findViewById(R.id.WrongExercises);
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.imageView);
        TextView adLoesung= (TextView) convertView.findViewById(R.id.TVLoesung);


        // Populate the data into the template view using the data object
        tvName.setText(user.Name);

        if (user.Score.equals(1)) {
            tvImage.setImageResource(R.drawable.checkmark);
        } else {
            tvImage.setImageResource(R.drawable.cross);
        }

        tvSkipped.setText(user.SkippedEx.toString());
        tvWrong.setText(user.WrongEx.toString());
        adLoesung.setText(user.solution.toString());


        // Return the completed view to render on screen

        return convertView;
    }
}
