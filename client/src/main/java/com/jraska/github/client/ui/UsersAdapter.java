package com.jraska.github.client.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
  private final List<User> usersList = new ArrayList<>();
  private final LayoutInflater inflater;
  private final Picasso picasso;

  private UserListener userListener;

  @Inject
  public UsersAdapter(LayoutInflater inflater, Picasso picasso) {
    this.inflater = inflater;
    this.picasso = picasso;
  }

  public void setUserListener(UserListener userClickListener) {
    userListener = userClickListener;
  }

  @Override public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflated = inflater.inflate(R.layout.item_row_user, parent, false);
    return new UserHolder(this, inflated);
  }

  @Override public void onBindViewHolder(UserHolder holder, int position) {
    User user = usersList.get(position);
    holder.loginTextView.setText(user.login);
    holder.avatarView.setImageDrawable(null);
    picasso.load(user.avatarUrl).into(holder.avatarView);

    if (user.isAdmin) {
      holder.adminView.setVisibility(View.VISIBLE);
    } else {
      holder.adminView.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return usersList.size();
  }

  public void setUsersList(Collection<User> users) {
    usersList.clear();
    usersList.addAll(users);
  }

  void onItemClicked(int position) {
    if (userListener != null) {
      User user = usersList.get(position);
      userListener.onUserClicked(user);
    }
  }

  void onGitHubIconClicked(int adapterPosition) {
    if (userListener != null) {
      User user = usersList.get(adapterPosition);
      userListener.onUserGitHubIconClicked(user);
    }
  }

  interface UserListener {
    void onUserClicked(User user);

    void onUserGitHubIconClicked(User user);
  }

  public static class UserHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.user_login) TextView loginTextView;
    @BindView(R.id.user_avatar) ImageView avatarView;
    @BindView(R.id.user_admin_image) View adminView;

    private final UsersAdapter adapter;

    public UserHolder(UsersAdapter adapter, View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.adapter = adapter;
    }

    @OnClick(R.id.user_container) void onItemClick() {
      adapter.onItemClicked(getAdapterPosition());
    }

    @OnClick(R.id.user_item_gitHub_icon) void onGitHubIconClicked() {
      adapter.onGitHubIconClicked(getAdapterPosition());
    }
  }
}
