package cn.daycode.fatalism.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageVO<T> implements Iterable<T>, Serializable{

    private List<T> content = new ArrayList<T>();
    private long total;
    private int pageNo;
    private int pageSize;

    public PageVO() {
    }

    public PageVO(List<T> content, long total, int pageNo, int pageSize) {
        this.content = content;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public boolean hasPrevious() {
        return getPageNo() > 0;
    }

    public boolean hasNext() {
        return getPageNo() + 1 < getTotalPage();
    }

    public boolean isFirst() {
        return !hasPrevious();
    }

    boolean isLast() {
        return !hasNext();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPage() {
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean hasContent() {
        return getContentSize() > 0;
    }

    public int getContentSize() {
        return content.size();
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Iterator<T> iterator() {
        return getContent().iterator();
    }
}
