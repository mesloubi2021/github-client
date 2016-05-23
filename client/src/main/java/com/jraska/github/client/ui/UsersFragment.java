package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jraska.github.client.GitHubClientApp;
import com.jraska.github.client.R;
import com.jraska.github.client.rx.ResultDelegate;
import com.jraska.github.client.users.User;

import javax.inject.Inject;
import java.util.List;

public class UsersFragment extends Fragment {
  @BindView(R.id.users_refresh_swipe_layout) SwipeRefreshLayout _swipeRefreshLayout;
  @BindView(R.id.users_recycler) RecyclerView _usersRecyclerView;

  @Inject UsersAdapter _usersAdapter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    GitHubClientApp application = (GitHubClientApp) getActivity().getApplication();
    application.getComponent().inject(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_users, container, false);
    ButterKnife.bind(this, view);

    _usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    _usersRecyclerView.setAdapter(_usersAdapter);

    _swipeRefreshLayout.setOnRefreshListener(() -> ((UsersActivity) getActivity()).refresh());

    return view;
  }

  void setUsers(List<User> users) {
    if (_usersRecyclerView == null) {
      throw new IllegalStateException("View was not created yet");
    }

    _usersAdapter.setUsers(users);
    _usersAdapter.notifyDataSetChanged();
  }

  void setUsersListener(UsersAdapter.UserListener listener) {
    _usersAdapter.setUserListener(listener);
  }

  void showProgressIndicator() {
    ensureProgressIndicatorVisible();

    _swipeRefreshLayout.setRefreshing(true);
  }

  void hideProgressIndicator() {
    _swipeRefreshLayout.setRefreshing(false);
  }

  private void ensureProgressIndicatorVisible() {
    // Workaround for start progress called before onMeasure
    // Issue: https://code.google.com/p/android/issues/detail?id=77712
    if (_swipeRefreshLayout.getMeasuredHeight() == 0) {
      int circleSize = getResources().getDimensionPixelSize(R.dimen.swipe_progress_circle_diameter);
      _swipeRefreshLayout.setProgressViewOffset(false, 0, circleSize);
    }
  }

  static class UsersFragmentAwareLoadingDelegate<R> implements ResultDelegate<R> {
    private final ResultDelegate<R> _resultDelegate;
    private final UsersFragment _usersFragment;

    public UsersFragmentAwareLoadingDelegate(ResultDelegate<R> resultDelegate, UsersFragment usersFragment) {
      _resultDelegate = resultDelegate;
      _usersFragment = usersFragment;
    }

    @Override public void onStart() {
      _resultDelegate.onStart();
      _usersFragment.showProgressIndicator();
    }

    @Override public void onNext(R result) {
      _resultDelegate.onNext(result);
      _usersFragment.hideProgressIndicator();
    }

    @Override public void onError(Throwable error) {
      _resultDelegate.onError(error);
      _usersFragment.hideProgressIndicator();
    }

    @Override public void onCompleted() {
      _resultDelegate.onCompleted();
      _usersFragment.hideProgressIndicator();
    }
  }
}
