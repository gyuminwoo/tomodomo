package com.VO;

public class Page {
    private int currentPage; // 현재 페이지
    private int totalPost; // 총 게시글 수
    private int pageSize; // 한 페이지에 보여줄 게시글 수
    private int pagingSize = 5; // 보여줄 페이지 네비게이션 수
    private int startIndex; // 시작 인덱스
    private int totalPage; // 총 페이지 수

    public Page(int currentPage, int pageSize, int totalPost) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPost = totalPost;
        this.totalPage = (int) Math.ceil((double) totalPost / pageSize);
        this.startIndex = (currentPage - 1) * pageSize;
    }

    // Getter Methods
    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getPagingSize() {
        return pagingSize;
    }

    // 페이지 네비게이션을 위한 메서드
    public int getStartPage() {
        return ((currentPage - 1) / pagingSize) * pagingSize + 1;
    }

    public int getEndPage() {
        int endPage = getStartPage() + pagingSize - 1;
        return Math.min(endPage, totalPage);
    }

    public boolean hasPrevPage() {
        return getStartPage() > 1;
    }

    public boolean hasNextPage() {
        return getEndPage() < totalPage;
    }

    public int getPrevPage() {
        return getStartPage() - 1;
    }

    public int getNextPage() {
        return getEndPage() + 1;
    }
}
