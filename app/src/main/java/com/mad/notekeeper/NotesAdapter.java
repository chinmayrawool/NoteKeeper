package com.mad.notekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by neha5 on 23-02-2017.
 */

public class NotesAdapter extends ArrayAdapter<Note> {
    Context mContext;
    List<Note> mNotes;
    int mResource;
    Note note;
    DatabaseDataManager dm;
    CheckBox checkBox;

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mNotes = objects;
        dm = MainActivity.dm;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        note = mNotes.get(position);
        //ImageView iv = (ImageView) convertView.findViewById(R.id.iv_logo);


        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvTitle.setText(note.getText());

        checkBox = (CheckBox) convertView.findViewById(R.id.check_note);
        checkBox.setChecked(false);
        if(note.getStatus().equals("COMPLETED")){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tv_priority);
        tvPriority.setText(note.getPriority());


        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);

            String updated_time = new PrettyTime(new Locale("")).format(new Date(Long.parseLong(note.getUpdate_time())));
        tvTime.setText(updated_time);

        //tvTime.setText(note.getUpdate_time());


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("demo","checked="+isChecked);
                final CheckBox cb = (CheckBox)buttonView;
                if(isChecked){
                    //checkBox.setChecked(true);
                    Note note = mNotes.get(position);

                    long id = note.getId();
                    note.setStatus("COMPLETED");
                    dm.updateNote(note);
                    changeCheckBoxStatus(note,cb);
                }
                if(!isChecked){
                    //checkBox.setChecked(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Do you really want to mark it as Pending?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Note note = mNotes.get(position);
                                    long id = note.getId();
                                    note.setStatus("PENDING");
                                    dm.updateNote(note);
                                    changeCheckBoxStatus(note,cb);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        });


        checkBox.setFocusable(false);
        convertView.setClickable(true);
        convertView.setLongClickable(true);
        return convertView;
    }
    public void changeCheckBoxStatus(Note note,CheckBox cb){
        if(note.getStatus().equals("COMPLETED")){
            cb.setChecked(true);
        }else if(note.getStatus().equals("PENDING")){
            cb.setChecked(false);
        }
    }
}

