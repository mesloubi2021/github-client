package com.jraska.github.client.data.users;

import com.jraska.github.client.common.Pair;
import com.jraska.github.client.users.Repo;
import com.jraska.github.client.users.UserDetail;
import com.jraska.github.client.users.UserStats;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Locale.ENGLISH;

final class UserDetailWithReposTranslator
    implements SingleTransformer<Pair<GitHubUserDetail, List<GitHubRepo>>, UserDetail> {
  static final DateFormat GIT_HUB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", ENGLISH);
  static final Comparator<GitHubRepo> BY_STARS_REPO_COMPARATOR = (lhs, rhs) -> rhs.stargazersCount.compareTo(lhs.stargazersCount);
  static final int MAX_REPOS_TO_DISPLAY = 5;

  static final UserDetailWithReposTranslator INSTANCE = new UserDetailWithReposTranslator();

  @Override
  public Single<UserDetail> apply(Single<Pair<GitHubUserDetail, List<GitHubRepo>>> single) {
    return single.map(result -> translateUserDetail(result.first, result.second));
  }

  UserDetail translateUserDetail(GitHubUserDetail gitHubUserDetail, List<GitHubRepo> gitHubRepos) {
    Date joined = parseDate(gitHubUserDetail.createdAt);

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

  private static Date parseDate(String text) {
    synchronized (GIT_HUB_DATE_FORMAT) {
      try {
        return GIT_HUB_DATE_FORMAT.parse(text);
      } catch (ParseException e) {
        throw new RuntimeException(e); // being lazy now
      }
    }
  }
}
