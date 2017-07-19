package kore.botssdk.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kore.botssdk.R;
import kore.botssdk.adapter.BotCarouselAdapter;
import kore.botssdk.application.AppControl;
import kore.botssdk.models.BotCarouselModel;
import kore.botssdk.view.viewUtils.LayoutUtils;
import kore.botssdk.view.viewUtils.MeasureUtils;

/**
 * Created by Pradeep Mahato on 13-July-17.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */

public class BotCarouselView extends ViewGroup {

    ViewPager carouselViewpager;
    int dp1;
    private int layoutWidth, layoutHeight;
    FragmentManager fragmentManager;
    Activity activityContext;

    public void setActivityContext(Activity activityContext) {
        this.activityContext = activityContext;
    }

    public BotCarouselView(Context context) {
        super(context);
        init();
    }

    public BotCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BotCarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dp1 = (int) AppControl.getInstance().getDimensionUtil().dp1;
        View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.bot_carousel_view, this, true);
        carouselViewpager = (ViewPager) inflatedView.findViewById(R.id.carouselViewpager);
        carouselViewpager.setPageMargin(10 * dp1);
        carouselViewpager.setClipChildren(false);
        carouselViewpager.setClipToPadding(false);
        carouselViewpager.setPadding(50 * dp1, 0, 100 * dp1, 0);
        carouselViewpager.setOffscreenPageLimit(6);
//        setBackgroundColor(0xff00ff00);
    }

    public void setLayoutWidth(int layoutWidth) {
        this.layoutWidth = layoutWidth;
    }

    public void setLayoutHeight(int layoutHeight) {
        this.layoutHeight = layoutHeight;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    BotCarouselAdapter botCarouselAdapter;
    public void populateCarouselView(ArrayList<BotCarouselModel> botCarouselModelArrayList) {
        if (fragmentManager != null && activityContext != null) {
            if (carouselViewpager.getAdapter() == null) {
                botCarouselAdapter = new BotCarouselAdapter(fragmentManager, activityContext);
                botCarouselAdapter.setBotCarouselModels(botCarouselModelArrayList);
                botCarouselAdapter.notifyDataSetChanged();
                carouselViewpager.setAdapter(botCarouselAdapter);
            } else {
                botCarouselAdapter = (BotCarouselAdapter) carouselViewpager.getAdapter();
                botCarouselAdapter.setBotCarouselModels(botCarouselModelArrayList);
                botCarouselAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxAllowedWidth = parentWidth;
        int wrapSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        int totalHeight = getPaddingTop();
        int totalWidth = getPaddingLeft();

        int childWidthSpec;
        int childHeightSpec;
        int contentWidth = 0;

        /*
         * For Carousel ViewPager Layout
         */
        childWidthSpec = MeasureSpec.makeMeasureSpec(maxAllowedWidth, MeasureSpec.AT_MOST);
        childHeightSpec = MeasureSpec.makeMeasureSpec(200 * dp1, MeasureSpec.AT_MOST);
        MeasureUtils.measure(carouselViewpager, childWidthSpec, childHeightSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int count = getChildCount();
        int parentWidth = getMeasuredWidth();

        //get the available size of child view
        int childLeft = 0;
        int childTop = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutUtils.layoutChild(child, childLeft, childTop);
                childTop += child.getMeasuredHeight();
            }
        }
    }
}
