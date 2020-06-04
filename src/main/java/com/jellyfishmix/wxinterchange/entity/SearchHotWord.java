package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 搜索热词统计表(SearchHotWord)实体类
 *
 * @author makejava
 * @since 2020-06-04 12:08:10
 */
@Data
public class SearchHotWord {
    /**
    * 热词wordId
    */
    private String wordId;
    /**
    * 热词内容
    */
    private String word;
    /**
    * 搜索次数
    */
    private Integer frequency;
    /**
    * 热词等级，1为天数据，2为周数据
    */
    private Integer grade;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;

}