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
import com.jraska.github.client.users.Repo;
import com.jraska.github.client.widget.RepeaterLayout;

import java.util.List;

public class ReposFragment extends Fragment {
  @BindView(R.id.repos_repeater) RepeaterLayout _reposRepeater;
  @BindView(R.id.repos_container) ViewGroup _reposContainer;
  @BindView(R.id.repos_title) TextView _reposTitle;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_repos, container, false);
    ButterKnife.bind(this, view);

    setContainerVisibility(false);

    return view;
  }

  void setTitle(CharSequence title) {
    _reposTitle.setText(title);
  }

  void setRepos(List<Repo> repos) {
    if (_reposRepeater == null) {
      throw new IllegalStateException("View was not created yet");
    }

    setContainerVisibility(repos.size() > 0);

    ReposAdapter reposAdapter = new ReposAdapter(LayoutInflater.from(getActivity()));
    reposAdapter.setRepos(repos);
    _reposRepeater.setAdapter(reposAdapter);
  }

  private void setContainerVisibility(boolean visible) {
    if (visible) {
      _reposContainer.setVisibility(View.VISIBLE);
    } else {
      _reposContainer.setVisibility(View.GONE);
    }
  }
}
