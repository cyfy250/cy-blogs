package com.cyfy.cyblogsbackend.common.request;

import lombok.Data;

/**
 * 分页请求包装类
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 每页显示条数
     */
    private int pageSize = 10;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式，默认降序
     */
    private String sortOrder = "descend";
}