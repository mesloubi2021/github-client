package com.jraska.github.client.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.jraska.github.client.R;
import com.jraska.github.client.rx.ResultDelegate;
import com.jraska.github.client.rx.ResultDelegateProvider;
import com.jraska.github.client.rx.IOPoolTransformer;
import com.jraska.github.client.rx.ObservableLoader;
import com.jraska.github.client.rx.SimpleDataResultDelegate;
import com.jraska.github.client.users.User;
import com.jraska.github.client.users.UsersRepository;

import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends BaseActivity implements UsersAdapter.UserListener {

    static ResultDelegateProvider<UsersActivity, List<User>> USERS_DELEGATE = UsersActivity::createUsersDelegate;

    @Inject
    UsersRepository _usersRepository;
    @Inject
    ObservableLoader _observableLoader;
    @Inject
    GitHubIconClickHandler _iconClickHandler;

    private UsersFragment _usersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getComponent().inject(this);

        _usersFragment = (UsersFragment) findFragmentById(R.id.fragment_users);
        _usersFragment.setUsersListener(this);

        startLoading();
    }

    void onUsersLoaded(List<User> users) {
        _usersFragment.setUsers(users);
        onLoadingFinished();
    }

    void onUsersError(Throwable error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

        onLoadingFinished();
    }

    void startLoading() {
        _usersFragment.showProgressIndicator();
        _observableLoader.load(_usersRepository.getUsers(0).compose(IOPoolTransformer.get()), USERS_DELEGATE);
    }

    void onLoadingFinished() {
        _usersFragment.hideProgressIndicator();
    }

    @Override
    public void onUserClicked(User user) {
        UserDetailActivity.start(this, user);
    }

    @Override
    public void onUserGitHubIconClicked(User user) {
        _iconClickHandler.userGitHubClicked(user);
    }

    void refresh() {
        // TODO: 16/04/16 Clear network cache
        startLoading();
    }

    private ResultDelegate<List<User>> createUsersDelegate() {
        return new SimpleDataResultDelegate<>(this::onUsersLoaded, this::onUsersError);
    }
}
