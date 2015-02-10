package com.retech.edu.task.core;

import java.util.Date;

/**
 * Created by jackwang on 15-2-10.
 */
public class TaskSchedulerInfo {
    private int id;
    private String name;
    private String description;
    private Date startdate;
    private Date enddate;

    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }

    public Date getStartdate(){
        return startdate;
    }
    public void setStartdate(Date startdate){
        this.startdate=startdate;
    }

    public Date getEnddate(){
        return enddate;
    }

    public void setEnddate(Date enddate){
        this.enddate=enddate;
    }


}
