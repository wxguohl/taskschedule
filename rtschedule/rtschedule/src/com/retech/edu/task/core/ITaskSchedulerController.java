package com.retech.edu.task.core;

/**
 * Created by jackwang on 15/3/2.
 */
public interface ITaskSchedulerController {
    void Start();
    void Stop();
    void Pause(int id);
    void Resume(int id);
    void Remove(int id);
}
