package com.stayzilla.postbooking.util;


import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.stayzilla.postbooking.R;

public class OverflowLayoutDrawer {

    private Activity activity;
    public int noOfLinesAdded = 0;
    static int NO_OF_LINES = 3;
    public int noOfInterestsDisplayed = 0;
    public boolean isSecondLayoutUsed = false;
    int margin;
    int padding;

    public OverflowLayoutDrawer(Activity activity) {
        this.activity = activity;
        margin = activity.getResources().getDimensionPixelSize(R.dimen.ofl_margin);
        padding = activity.getResources().getDimensionPixelSize(R.dimen.ofl_padding);
    }

    public void populateText(LinearLayout parentLinearLayout, View[] views) {
        int maxWidth = getScreenWidth();

        parentLinearLayout.removeAllViews();
        LinearLayout tempLinearLayout = createNewLayoutForNewLine();

        int widthSoFar = 0;

        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            view.measure(0, 0);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            widthSoFar += measuredWidth + margin + padding;
            if (widthSoFar >= maxWidth) {
                parentLinearLayout.addView(tempLinearLayout);
                tempLinearLayout = createNewLayoutForNewLine();
                tempLinearLayout.addView(view, new LinearLayout.LayoutParams(measuredWidth, measuredHeight));
                widthSoFar = measuredWidth;
            } else {
                tempLinearLayout.addView(view);
            }
        }
        parentLinearLayout.addView(tempLinearLayout);
    }

    public void populateText(LinearLayout parentLinearLayout1, LinearLayout parentLinearLayout2, View[] views) {
        noOfLinesAdded = 0;
        int maxWidth = getScreenWidth();

        parentLinearLayout1.removeAllViews();
        parentLinearLayout2.removeAllViews();

        LinearLayout tempLinearLayout = createNewLayoutForNewLine();

        int widthSoFar = 0;

        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            view.measure(0, 0);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            widthSoFar += measuredWidth + margin + padding;
            if (widthSoFar >= maxWidth) {
                addTempLayoutToParentLayoutAsNewLine(parentLinearLayout1, parentLinearLayout2, tempLinearLayout);

                tempLinearLayout = createNewLayoutForNewLine();
                tempLinearLayout.addView(view, new LinearLayout.LayoutParams(measuredWidth, measuredHeight));
                widthSoFar = measuredWidth;
            } else {
                tempLinearLayout.addView(view);
                if (!isSecondLayoutUsed) {
                    noOfInterestsDisplayed += 1;
                }
            }
        }
        addTempLayoutToParentLayoutAsNewLine(parentLinearLayout1, parentLinearLayout2, tempLinearLayout);
    }

    private int getScreenWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    private LinearLayout createNewLayoutForNewLine() {
        LinearLayout tempLinearLayout = new LinearLayout(activity);
        tempLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tempLinearLayout.setGravity(Gravity.LEFT);
        tempLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return tempLinearLayout;
    }

    private void addTempLayoutToParentLayoutAsNewLine(LinearLayout parentLinearLayout1, LinearLayout parentLinearLayout2, LinearLayout tempLinearLayout) {
        noOfLinesAdded++;
        if (noOfLinesAdded <= NO_OF_LINES) {
            parentLinearLayout1.addView(tempLinearLayout);
        } else {
            isSecondLayoutUsed = true;
            parentLinearLayout2.addView(tempLinearLayout);
        }
    }
}
