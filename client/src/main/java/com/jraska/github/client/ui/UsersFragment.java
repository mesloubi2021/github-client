package com.jraska.github.client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.R;

public class UsersFragment extends Fragment {
  @Bind(R.id.users_recycler) RecyclerView _usersRecyclerView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_users, container, false);
    ButterKnife.bind(this, view);

    _usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    return view;
  }

  void setUsersAdapter(RecyclerView.Adapter adapter) {
    if(_usersRecyclerView == null){
      throw new IllegalStateException("View was not created yet");
    }

    _usersRecyclerView.setAdapter(adapter);
  }
}
