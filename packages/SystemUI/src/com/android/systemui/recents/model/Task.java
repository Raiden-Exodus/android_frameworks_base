/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.recents.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.android.systemui.recents.Constants;


/**
 * A task represents the top most task in the system's task stack.
 */
public class Task {
    public final int id;
    public final Intent intent;
    public String title;
    public Drawable icon;
    public Bitmap thumbnail;

    TaskCallbacks mCb;

    public Task(int id, Intent intent, String activityTitle, Drawable icon, Bitmap thumbnail) {
        this.id = id;
        this.intent = intent;
        this.title = activityTitle;
        this.icon = icon;
        this.thumbnail = thumbnail;
    }

    /** Set the callbacks */
    public void setCallbacks(TaskCallbacks cb) {
        mCb = cb;
    }

    /** Notifies the callback listeners that this task's data has changed */
    public void notifyTaskDataChanged() {
        if (mCb != null) {
            mCb.onTaskDataChanged(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        // If we have multiple task entries for the same task, then we do the simple object
        // equality check
        if (Constants.Values.RecentsTaskLoader.TaskEntryMultiplier > 1) {
            return super.equals(o);
        }

        // Otherwise, check that the id and intent match (the other fields can be asynchronously
        // loaded and is unsuitable to testing the identity of this Task)
        Task t = (Task) o;
        return (id == t.id) &&
                (intent.equals(t.intent));
    }

    @Override
    public String toString() {
        return "Task: " + intent.getComponent().getPackageName() + " [" + super.toString() + "]";
    }
}