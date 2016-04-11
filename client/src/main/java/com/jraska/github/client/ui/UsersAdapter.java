package com.jraska.github.client.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
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
  private final List<User> _users = new ArrayList<>();
  private final LayoutInflater _inflater;
  private final Picasso _picasso;

  private UserListener _userListener;

  @Inject
  public UsersAdapter(LayoutInflater inflater, Picasso picasso) {
    _inflater = inflater;
    _picasso = picasso;
  }

  public void setUserListener(UserListener userClickListener) {
    _userListener = userClickListener;
  }

  @Override public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflated = _inflater.inflate(R.layout.item_row_user, parent, false);
    return new UserHolder(this, inflated);
  }

  @Override public void onBindViewHolder(UserHolder holder, int position) {
    User user = _users.get(position);
    holder._loginTextView.setText(user._login);
    holder._avatarView.setImageDrawable(null);
    _picasso.load(user._avatarUrl).into(holder._avatarView);

    if (user._isAdmin) {
      holder._adminView.setVisibility(View.VISIBLE);
    } else {
      holder._adminView.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return _users.size();
  }

  public void setUsers(Collection<User> users) {
    _users.clear();
    _users.addAll(users);
  }

  void onItemClicked(int position) {
    if (_userListener != null) {
      User user = _users.get(position);
      _userListener.onUserClicked(user);
    }
  }

  void onGitHubIconClicked(int adapterPosition) {
    if (_userListener != null) {
      User user = _users.get(adapterPosition);
      _userListener.onUserGitHubIconClicked(user);
    }
  }

  interface UserListener {
    void onUserClicked(User user);

    void onUserGitHubIconClicked(User user);
  }

  public static class UserHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.user_login) TextView _loginTextView;
    @Bind(R.id.user_avatar) ImageView _avatarView;
    @Bind(R.id.user_admin_image) View _adminView;

    private final UsersAdapter _adapter;

    public UserHolder(UsersAdapter adapter, View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      _adapter = adapter;
    }

    @OnClick(R.id.user_container) void onItemClick() {
      _adapter.onItemClicked(getAdapterPosition());
    }

    @OnClick(R.id.user_item_gitHub_icon) void onGitHubIconClicked() {
      _adapter.onGitHubIconClicked(getAdapterPosition());
    }
  }
}
