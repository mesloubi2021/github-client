package com.jraska.github.client.users;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public final class User implements Serializable {
  @NonNull public final String _login;
  @NonNull public final String _avatarUrl;
  @NonNull public final String _gitHubUrl;

  public final boolean _isAdmin;

  public User(@NonNull String login, @NonNull String avatarUrl,
              boolean isAdmin, @NonNull String gitHubUrl) {
    _login = login;
    _avatarUrl = avatarUrl;
    _isAdmin = isAdmin;
    _gitHubUrl = gitHubUrl;
  }

  // TODO: 17/08/16 make it parcelable, not serializable

//  @Override public int describeContents() {
//    return 0;
//  }
//
//  @Override public void writeToParcel(Parcel dest, int flags) {
//    dest.writeString(this._login);
//    dest.writeString(this._avatarUrl);
//    dest.writeString(this._gitHubUrl);
//    dest.writeByte(_isAdmin ? (byte) 1 : (byte) 0);
//  }
//
//  protected User(Parcel in) {
//    this._login = in.readString();
//    this._avatarUrl = in.readString();
//    this._gitHubUrl = in.readString();
//    this._isAdmin = in.readByte() != 0;
//  }
//
//  public static final Creator<User> CREATOR = new Creator<User>() {
//    public User createFromParcel(Parcel source) {
//      return new User(source);
//    }
//
//    public User[] newArray(int size) {
//      return new User[size];
//    }
//  };
}
