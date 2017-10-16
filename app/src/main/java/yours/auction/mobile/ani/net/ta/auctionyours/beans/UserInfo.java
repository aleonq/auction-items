package yours.auction.mobile.ani.net.ta.auctionyours.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taru on 5/13/2017.
 */

public class UserInfo {
    @SerializedName("_id")
    public String userId;

    @SerializedName("user_name")
    public String userName;

    @SerializedName("user_key")
    public String userKey;

    public UserInfo(String userId, String userName, String userKey) {
        this.userId = userId;
        this.userName = userName;
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userKey='" + userKey + '\'' +
                '}';
    }
}
