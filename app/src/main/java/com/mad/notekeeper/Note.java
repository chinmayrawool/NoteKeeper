package com.mad.notekeeper;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Chinmay Rawool on 2/27/2017.
 */

public class Note {
    private long id;
    private String text,priority,status,update_time;
    private int p;


    public Note(String text, String priority, String update_time, String status) {
        this.text = text;
        this.priority = priority;
        this.update_time = update_time;
        this.status = status;
    }

    public Note(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setP() {
        if(getPriority().equals("High Priority")){
            this.p = 2;
        }else if(getPriority().equals("Medium Priority")){
            this.p = 1;
        } else  if(getPriority().equals("Low Priority")){
            this.p = 0;
        }
    }

    public int getP() {
        return p;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", priority='" + priority + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    public static Comparator<Note> priorityComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            o1.setP();
            o2.setP();
            Double o1P = Double.valueOf(o1.getP());
            Double o2P = Double.valueOf(o2.getP());
            return o2P.compareTo(o1P);
        }
    };

    public static Comparator<Note> dateComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {


            Date d1 = new Date(Long.parseLong(o1.update_time));
            Date d2 = new Date(Long.parseLong(o2.update_time));
            return d2.compareTo(d1);

        }
    };

}
