package com.njupt.school.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YYT on 2015/12/7.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static  void finishAll()
    {
        for (Activity activity :activities){
            if (!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
