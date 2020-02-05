package com.androidwind.github.api;

import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.bean.GithubUser;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface UserApi {
    /**
     * 获取用户Starred Repo(需登录后使用)
     *
     * @param page
     * @param perPage
     * @return
     */
    @GET("user/starred") Observable<List<GithubRepository>> getGithubStarred(@Query("page") int page, @Query("per_page") int perPage);

    /**
     * 获取用户资料(需登录后使用)
     *
     * @return
     */
    @NonNull
    @GET("user")
    Observable<GithubUser> getUserInfo();
}
