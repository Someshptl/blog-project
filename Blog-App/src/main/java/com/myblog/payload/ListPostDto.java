package com.myblog.payload;

import java.util.List;

public class ListPostDto {

    private List<PostDto> postDtoList;
    private int totalPages;
    private int totalElements;
    private boolean firstPage;
    private boolean lastPage;
    private int pageNumber;

    public List<PostDto> getPostDtoList() {
        return postDtoList;
    }

    public void setPostDtoList(List<PostDto> postDtoList) {
        this.postDtoList = postDtoList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
