package com.jraska.github.client.users;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import rx.Observable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Locale.ENGLISH;

final class GitHubApiUsersRepository implements UsersRepository {
  static final DateFormat GIT_HUB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", ENGLISH);
  static final Comparator<GitHubRepo> BY_STARS_REPO_COMPARATOR = (lhs, rhs) -> rhs.stargazersCount.compareTo(lhs.stargazersCount);
  static final int MAX_REPOS_TO_DISPLAY = 10;

  private final GitHubUsersApi _gitHubUsersApi;
  private final GitHubUserDetailApi _gitHubUserDetailApi;

  public GitHubApiUsersRepository(@NonNull GitHubUsersApi gitHubUsersApi,
                                  @NonNull GitHubUserDetailApi gitHubUserDetailApi) {
    _gitHubUsersApi = gitHubUsersApi;
    _gitHubUserDetailApi = gitHubUserDetailApi;
  }

  @Override public Observable<List<User>> getUsers(int since) {
    return _gitHubUsersApi.getUsers(since).map(this::translateUsers);
  }

  @Override public Observable<UserDetail> getUserDetail(String login) {
    return _gitHubUserDetailApi.getUserDetail(login)
        .zipWith(_gitHubUserDetailApi.getRepos(login), Pair::new)
        .map(result -> translateUserDetail(result.first, result.second));
  }

  List<User> translateUsers(List<GitHubUser> gitHubUsers) {
    ArrayList<User> users = new ArrayList<>();
    for (GitHubUser gitHubUser : gitHubUsers) {
      users.add(translateUser(gitHubUser));
    }

    return users;
  }

  private User translateUser(GitHubUser gitHubUser) {
    boolean isAdmin = gitHubUser.siteAdmin == null ? false : gitHubUser.siteAdmin;
    return new User(gitHubUser.login, gitHubUser.avatarUrl, isAdmin, gitHubUser.htmlUrl);
  }

  UserDetail translateUserDetail(GitHubUserDetail gitHubUserDetail, List<GitHubRepo> gitHubRepos) {
    Date joined = parseDate(gitHubUserDetail.createdAt);

    UserStats stats = new UserStats(gitHubUserDetail.followers, gitHubUserDetail.following,
        gitHubUserDetail.publicRepos, joined);

    Collections.sort(gitHubRepos, BY_STARS_REPO_COMPARATOR);

    List<Repo> repos = new ArrayList<>(MAX_REPOS_TO_DISPLAY);
//    for (int i = 0, size = gitHubRepos.size(); i < size && i < MAX_REPOS_TO_DISPLAY; i++) {
//      repos.add(translateRepo(gitHubRepos.get(i)));
//    }

    for (int i = 0, size = gitHubRepos.size(); i < size; i++) {
      repos.add(translateRepo(gitHubRepos.get(i)));
    }

    return new UserDetail(stats, repos);
  }

  Repo translateRepo(GitHubRepo gitHubRepo) {
    return new Repo(gitHubRepo.name, gitHubRepo.description, gitHubRepo.watchersCount,
        gitHubRepo.stargazersCount, gitHubRepo.forks, gitHubRepo.size);
  }

  private static Date parseDate(String text) {
    synchronized (GIT_HUB_DATE_FORMAT) {
      try {
        return GIT_HUB_DATE_FORMAT.parse(text);
      }
      catch (ParseException e) {
        throw new RuntimeException(e); // being lazy now
      }
    }
  }
}
