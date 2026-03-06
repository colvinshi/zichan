package com.company.asset.common.core.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Paged result
 */
@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Current page number
     */
    private long current;
    
    /**
     * Page size
     */
    private long size;
    
    /**
     * Total records
     */
    private long total;
    
    /**
     * Total pages
     */
    private long pages;
    
    /**
     * Data list
     */
    private List<T> records;
    
    /**
     * Has previous page
     */
    private boolean hasPrevious;
    
    /**
     * Has next page
     */
    private boolean hasNext;

    public PageResult(List<T> records, long total, long current, long size) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = (total + size - 1) / size;
        this.hasPrevious = current > 1;
        this.hasNext = current < pages;
    }

    /**
     * Create from MyBatis Plus page
     */
    public static <T> PageResult<T> of(com.baomidou.mybatisplus.core.metadata.IPage<T> page) {
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
