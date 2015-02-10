package com.retech.edu.task.exception;

/**
 * Created by jackwang on 15-2-2.
 */
public class ScheduleException extends RuntimeException {
    public ScheduleException(){
        super();
    }

    public ScheduleException(String s){
        super(s);
    }

    public ScheduleException(String s,Throwable e){
        super(s,e);
    }

    public ScheduleException(Throwable e){
        super(e);
    }
}
