package com.jraska.github.client.ui;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.jraska.github.client.R;
import com.jraska.github.client.users.Repo;

public class RepoModel extends EpoxyModelWithHolder<RepoModel.RepoHolder> {
  private final Repo repo;

  public RepoModel(Repo repo) {
    this.repo = repo;
  }

  @Override protected RepoHolder createNewHolder() {
    return new RepoHolder();
  }

  @Override protected int getDefaultLayout() {
    return R.layout.item_row_user_detail_repo;
  }

  @Override public void bind(RepoHolder holder) {
    holder.titleTextView.setText(repo.name);
    holder.descriptionTextView.setText(repo.description);
    holder.watchersTextView.setText(String.valueOf(repo.watchers));
    holder.starsTextView.setText(String.valueOf(repo.stars));
    holder.forksTextView.setText(String.valueOf(repo.forks));
  }

  static class RepoHolder extends EpoxyHolder {
    @BindView(R.id.repo_item_title) TextView titleTextView;
    @BindView(R.id.repo_item_description) TextView descriptionTextView;
    @BindView(R.id.repo_item_watchers) TextView watchersTextView;
    @BindView(R.id.repo_item_stars) TextView starsTextView;
    @BindView(R.id.repo_item_forks) TextView forksTextView;

    @Override protected void bindView(View itemView) {
      ButterKnife.bind(this, itemView);
    }
  }
}
