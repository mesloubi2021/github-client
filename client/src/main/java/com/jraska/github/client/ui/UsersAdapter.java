package com.jraska.github.client.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jraska.github.client.R;
import com.jraska.github.client.users.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
  private final List<User> _users = new ArrayList<>();
  private final LayoutInflater _layoutInflater;

  @Inject
  public UsersAdapter(LayoutInflater layoutInflater) {
    _layoutInflater = layoutInflater;
  }

  @Override public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflated = _layoutInflater.inflate(R.layout.item_row_user, parent, false);
    return new UserHolder(inflated);
  }

  @Override public void onBindViewHolder(UserHolder holder, int position) {
    User user = _users.get(position);
    holder._loginTextView.setText(user._login);
  }

  @Override public int getItemCount() {
    return _users.size();
  }

  public void addUsers(Collection<User> users) {
    _users.addAll(users);
  }

  public static class UserHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.user_login) TextView _loginTextView;

    public UserHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
