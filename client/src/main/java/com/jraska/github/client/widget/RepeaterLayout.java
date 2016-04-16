package com.jraska.github.client.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RepeaterLayout extends LinearLayout {
  public RepeaterLayout(Context context) {
    super(context);
  }

  public RepeaterLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RepeaterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public RepeaterLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @SuppressWarnings("unchecked")
  public void setAdapter(RecyclerView.Adapter adapter) {
    removeAllViews();

    int itemCount = adapter.getItemCount();
    for (int position = 0; position < itemCount; position++) {
      int itemViewType = adapter.getItemViewType(position);
      RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(this, itemViewType);

      addView(viewHolder.itemView);

      adapter.bindViewHolder(viewHolder, position);
    }
  }
}
