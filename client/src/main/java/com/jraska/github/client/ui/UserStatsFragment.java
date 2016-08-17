package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jraska.github.client.R;
import com.jraska.github.client.users.UserStats;

import java.text.DateFormat;
import java.util.Date;

public class UserStatsFragment extends Fragment {
  static final DateFormat JOINED_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

  @BindView(R.id.user_detail_repos_count) TextView reposCountTextView;
  @BindView(R.id.user_detail_following_count) TextView followingTextView;
  @BindView(R.id.user_detail_followers_count) TextView followersTextView;
  @BindView(R.id.user_detail_joined) TextView joinedTextView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  void setUserStats(UserStats basicStats) {

    followersTextView.setText(String.valueOf(basicStats.followers));
    followingTextView.setText(String.valueOf(basicStats.following));
    reposCountTextView.setText(String.valueOf(basicStats.publicRepos));

    String joinedDateText = formatJoinedDate(basicStats.joined);
    String joinedText = getString(R.string.user_detail_joined_template, joinedDateText);
    joinedTextView.setText(joinedText);
  }

  static String formatJoinedDate(Date joinedDate) {
    synchronized (JOINED_FORMAT) {
      return JOINED_FORMAT.format(joinedDate);
    }
  }
}
