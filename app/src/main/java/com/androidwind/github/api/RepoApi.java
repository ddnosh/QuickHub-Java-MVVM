package com.androidwind.github.api;

import com.androidwind.github.bean.GithubEvent;
import com.androidwind.github.bean.GithubSearch;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface RepoApi {

    @NonNull
    @GET("users/{user}/received_events")
    Observable<List<GithubEvent>> getTrends(
            @Header("forceNetWork") boolean forceNetWork,
            @Path("user") String user,
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @GET("/search/repositories")
    Observable<GithubSearch> query(@Query("q") String keyword,
                                   @Query("sort") String sort,
                                   @Query("order") String order,
                                   @Query("page") int page,
                                   @Query("per_page") int perPage);
}
