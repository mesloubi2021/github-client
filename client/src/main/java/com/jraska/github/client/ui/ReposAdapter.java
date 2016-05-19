package com.jraska.github.client.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jraska.github.client.R;
import com.jraska.github.client.users.Repo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.Holder> {
  private final List<Repo> _repos = new ArrayList<>();

  private final LayoutInflater _inflater;

  @Inject
  public ReposAdapter(LayoutInflater inflater) {
    _inflater = inflater;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowView = _inflater.inflate(R.layout.item_row_user_detail_repo, parent, false);
    return new Holder(rowView);
  }

  @Override public void onBindViewHolder(Holder holder, int position) {
    Repo repo = _repos.get(position);

    holder._titleTextView.setText(repo._name);
    holder._descriptionTextView.setText(repo._description);
    holder._watchersTextView.setText(String.valueOf(repo._watchers));
    holder._starsTextView.setText(String.valueOf(repo._stars));
    holder._forksTextView.setText(String.valueOf(repo._forks));
  }

  @Override public int getItemCount() {
    return _repos.size();
  }

  void setRepos(Collection<Repo> repos) {
    _repos.clear();
    _repos.addAll(repos);
    notifyDataSetChanged();
  }

  static class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.repo_item_title) TextView _titleTextView;
    @BindView(R.id.repo_item_description) TextView _descriptionTextView;
    @BindView(R.id.repo_item_watchers) TextView _watchersTextView;
    @BindView(R.id.repo_item_stars) TextView _starsTextView;
    @BindView(R.id.repo_item_forks) TextView _forksTextView;

    public Holder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }
}
