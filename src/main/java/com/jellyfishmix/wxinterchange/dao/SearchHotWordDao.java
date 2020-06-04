package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.SearchHotWord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 搜索热词统计表(SearchHotWord)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-04 12:08:10
 */
public interface SearchHotWordDao {

    /**
     * 新增数据
     *
     * @param searchHotWord 实例对象
     * @return 影响行数
     */
    int insert(SearchHotWord searchHotWord);

}