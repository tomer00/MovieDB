package com.android.moviedb.modals;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieItem> movieItems;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieItem> getMovieItems() {
        return movieItems;
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @NonNull
    @Override
    public String toString() {
        return "NetworkResponse{" +
                "page=" + page +
                ", movieItems=" + movieItems +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }
}
