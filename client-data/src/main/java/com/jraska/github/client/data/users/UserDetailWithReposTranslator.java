package com.jraska.github.client.data.users;

import com.jraska.github.client.common.Pair;
import com.jraska.github.client.users.Repo;
import com.jraska.github.client.users.UserDetail;
import com.jraska.github.client.users.UserStats;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

final class UserDetailWithReposTranslator
    implements SingleTransformer<Pair<GitHubUserDetail, List<GitHubRepo>>, UserDetail> {
  static final Comparator<GitHubRepo> BY_STARS_REPO_COMPARATOR = (lhs, rhs) -> rhs.stargazersCount.compareTo(lhs.stargazersCount);
  static final int MAX_REPOS_TO_DISPLAY = 5;

  static final UserDetailWithReposTranslator INSTANCE = new UserDetailWithReposTranslator();

  @Override
  public Single<UserDetail> apply(Single<Pair<GitHubUserDetail, List<GitHubRepo>>> single) {
    return single.map(result -> translateUserDetail(result.first, result.second));
  }

  UserDetail translateUserDetail(GitHubUserDetail gitHubUserDetail, List<GitHubRepo> gitHubRepos) {
    LocalDateTime joined = LocalDateTime.parse(gitHubUserDetail.createdAt, DateTimeFormatter.ISO_DATE_TIME);

    UserStats stats = new UserStats(gitHubUserDetail.followers, gitHubUserDetail.following,
        gitHubUserDetail.publicRepos, joined);

    Collections.sort(gitHubRepos, BY_STARS_REPO_COMPARATOR);

    List<Repo> usersRepos = new ArrayList<>();
    List<Repo> contributedRepos = new ArrayList<>();

    for (GitHubRepo gitHubRepo : gitHubRepos) {
      if (usersRepos.size() < MAX_REPOS_TO_DISPLAY && gitHubUserDetail.login.equals(gitHubRepo.owner.login)) {
        Repo repo = translateRepo(gitHubRepo);
        usersRepos.add(repo);
      } else if (contributedRepos.size() < MAX_REPOS_TO_DISPLAY) {
        Repo repo = translateRepo(gitHubRepo);
        contributedRepos.add(repo);
      }
    }

    return new UserDetail(stats, usersRepos, contributedRepos);
  }

  Repo translateRepo(GitHubRepo gitHubRepo) {
    return new Repo(gitHubRepo.name, gitHubRepo.description, gitHubRepo.watchersCount,
        gitHubRepo.stargazersCount, gitHubRepo.forks, gitHubRepo.size);
  }
}
