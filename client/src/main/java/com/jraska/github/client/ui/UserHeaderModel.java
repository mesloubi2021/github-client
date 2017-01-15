package com.jraska.github.client.ui;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.jraska.github.client.R;
import com.jraska.github.client.users.UserStats;

import java.text.DateFormat;
import java.util.Date;

public class UserHeaderModel extends EpoxyModelWithHolder<UserHeaderModel.HeaderHolder> {
  static final DateFormat JOINED_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

  private final UserStats basicStats;

  public UserHeaderModel(UserStats basicStats) {
    this.basicStats = basicStats;
  }

  @Override protected HeaderHolder createNewHolder() {
    return new HeaderHolder();
  }

  @Override protected int getDefaultLayout() {
    return R.layout.item_user_stats;
  }

  @Override public void bind(HeaderHolder holder) {
    holder.followersTextView.setText(String.valueOf(basicStats.followers));
    holder.followingTextView.setText(String.valueOf(basicStats.following));
    holder.reposCountTextView.setText(String.valueOf(basicStats.publicRepos));

    String joinedDateText = formatJoinedDate(basicStats.joined);
    String joinedText = holder.joinedTextView.getContext()
        .getString(R.string.user_detail_joined_template, joinedDateText);
    holder.joinedTextView.setText(joinedText);
  }

  static String formatJoinedDate(Date joinedDate) {
    synchronized (JOINED_FORMAT) {
      return JOINED_FORMAT.format(joinedDate);
    }
  }

  static class HeaderHolder extends EpoxyHolder {
    @BindView(R.id.user_detail_repos_count) TextView reposCountTextView;
    @BindView(R.id.user_detail_following_count) TextView followingTextView;
    @BindView(R.id.user_detail_followers_count) TextView followersTextView;
    @BindView(R.id.user_detail_joined) TextView joinedTextView;

    @Override protected void bindView(View itemView) {
      ButterKnife.bind(this, itemView);
    }
  }
}
