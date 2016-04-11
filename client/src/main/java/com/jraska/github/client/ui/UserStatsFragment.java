package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.R;
import com.jraska.github.client.users.UserStats;

import java.text.DateFormat;
import java.util.Date;

public class UserStatsFragment extends Fragment {
  static final DateFormat JOINED_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

  @Bind(R.id.user_detail_repos_count) TextView _reposCountTextView;
  @Bind(R.id.user_detail_following_count) TextView _followingTextView;
  @Bind(R.id.user_detail_followers_count) TextView _followersTextView;
  @Bind(R.id.user_detail_joined) TextView _joinedTextView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  void setUserStats(UserStats basicStats) {

    _followersTextView.setText(String.valueOf(basicStats._followers));
    _followingTextView.setText(String.valueOf(basicStats._following));
    _reposCountTextView.setText(String.valueOf(basicStats._publicRepos));

    String joinedDateText = formatJoinedDate(basicStats._joined);
    String joinedText = getString(R.string.user_detail_joined_template, joinedDateText);
    _joinedTextView.setText(joinedText);
  }

  static String formatJoinedDate(Date joinedDate) {
    synchronized (JOINED_FORMAT) {
      return JOINED_FORMAT.format(joinedDate);
    }
  }
}
