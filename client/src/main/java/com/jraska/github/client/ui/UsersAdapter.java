package com.jraska.github.client.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

  @Inject
  public UsersAdapter(LayoutInflater inflater, Picasso picasso) {
    _inflater = inflater;
    _picasso = picasso;
  }

  @Override public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflated = _inflater.inflate(R.layout.item_row_user, parent, false);
    return new UserHolder(inflated);
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

  public void addUsers(Collection<User> users) {
    _users.addAll(users);
  }

  public static class UserHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.user_login) TextView _loginTextView;
    @Bind(R.id.user_avatar) ImageView _avatarView;
    @Bind(R.id.user_admin_image) View _adminView;

    public UserHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.user_container) void onItemClick() {
      Toast.makeText(_loginTextView.getContext(), "Item " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }
  }
}
