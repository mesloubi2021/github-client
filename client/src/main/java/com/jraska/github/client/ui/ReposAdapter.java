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
  private final List<Repo> repositories = new ArrayList<>();

  private final LayoutInflater inflater;

  @Inject
  public ReposAdapter(LayoutInflater inflater) {
    this.inflater = inflater;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowView = inflater.inflate(R.layout.item_row_user_detail_repo, parent, false);
    return new Holder(rowView);
  }

  @Override public void onBindViewHolder(Holder holder, int position) {
    Repo repo = repositories.get(position);

    holder.titleTextView.setText(repo.name);
    holder.descriptionTextView.setText(repo.description);
    holder.watchersTextView.setText(String.valueOf(repo.watchers));
    holder.starsTextView.setText(String.valueOf(repo.stars));
    holder.forksTextView.setText(String.valueOf(repo.forks));
  }

  @Override public int getItemCount() {
    return repositories.size();
  }

  void setRepos(Collection<Repo> repos) {
    repositories.clear();
    repositories.addAll(repos);
    notifyDataSetChanged();
  }

  static class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.repo_item_title) TextView titleTextView;
    @BindView(R.id.repo_item_description) TextView descriptionTextView;
    @BindView(R.id.repo_item_watchers) TextView watchersTextView;
    @BindView(R.id.repo_item_stars) TextView starsTextView;
    @BindView(R.id.repo_item_forks) TextView forksTextView;

    public Holder(View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }
}
