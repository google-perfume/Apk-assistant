package com.perfume.UI;

import android.view.*;
import android.widget.*;

import android.content.Context;
import android.util.AttributeSet;
import com.perfume.R;

public class LoadListView extends ListView implements AbsListView.OnScrollListener
 {
      View footer;
      int totalItemCount;
      int lastVisibleItem;
      boolean isLoading;
      ILoadListener iLoadListener;
      public LoadListView(Context context) {
            super(context);
            initView(context);
         }
      public LoadListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
         }
      public LoadListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initView(context);
         }
      private void initView(Context context) {
            LayoutInflater inflater = LayoutInflater.from(context);
            footer = inflater.inflate(R.layout.loading, null);
            footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
            this.addFooterView(footer);
            this.setOnScrollListener(this);
         }
      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.lastVisibleItem = firstVisibleItem + visibleItemCount;
            this.totalItemCount = totalItemCount;
         }
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (totalItemCount == lastVisibleItem
                && scrollState == SCROLL_STATE_IDLE) {
                  if (!isLoading) {
                        isLoading = true;
                        footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                        iLoadListener.onLoad();
                     }
               }
         }
      public void loadComplete(){
            isLoading = false;
            footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
         }
      public void setInterface(ILoadListener iLoadListener){
            this.iLoadListener = iLoadListener;
         }
      public interface ILoadListener{
            public void onLoad();
         }
   }


