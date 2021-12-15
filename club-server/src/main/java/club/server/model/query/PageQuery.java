package club.server.model.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Objects;
import java.util.Optional;
/*
 *@Description
 *@Author Chen
 *@Date 2021/10/16 23:01
 */
public class PageQuery<T> {
    /**
     * which page
     */
    private Integer page = 1;

    /**
     * Page size
     */
    private Integer size = 10;

    /**
     * Sort
     */
    private String sort;

    public PageQuery() {

    }
    public Page<T> getIPage() {
        //Paging parameters
        int curPage = Optional.ofNullable(this.getPage()).orElse(1);
        int limit = Optional.ofNullable(this.getSize()).orElse(10);
        //Pagination object
        return new Page<>(curPage, limit);
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getSort() {
        return this.sort;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageQuery<?> pageQuery = (PageQuery<?>) o;
        return Objects.equals(page, pageQuery.page) &&
                Objects.equals(size, pageQuery.size) &&
                Objects.equals(sort, pageQuery.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size, sort);
    }
    @Override
    public String toString() {
        return "PageQuery(page=" + this.getPage() + ", size=" + this.getSize() + ", sort=" + this.getSort() + ")";
    }
}