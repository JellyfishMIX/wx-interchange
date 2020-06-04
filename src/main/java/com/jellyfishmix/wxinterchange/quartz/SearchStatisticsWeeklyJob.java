package com.jellyfishmix.wxinterchange.quartz;

import com.jellyfishmix.wxinterchange.dao.SearchHotWordDao;
import com.jellyfishmix.wxinterchange.entity.SearchHotWord;
import com.jellyfishmix.wxinterchange.enums.RedisEnum;
import com.jellyfishmix.wxinterchange.service.RedisService;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 * @author JellyfishMIX
 * @date 2020/6/4 6:11 下午
 */
public class SearchStatisticsWeeklyJob implements Job, Serializable {
    private static final long serialVersionUID = 3252817805236884065L;
    @Resource
    private SearchHotWordDao searchHotWordDao;
    @Autowired
    private RedisService redisService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        this.countWeeklyHotWord();
    }

    /**
     * 统计每周搜索热词
     */
    private void countWeeklyHotWord() {
        // 查询五个搜索hotWord
        Set<ZSetOperations.TypedTuple<String>> resultSet = redisService.queryTopSearchHotKey(RedisEnum.WEEKLY_SEARCH_HOT_WORD_ZSET.getKey(), 0, 4);
        for (ZSetOperations.TypedTuple<String> tuple : resultSet) {
            SearchHotWord searchHotWord = new SearchHotWord();
            searchHotWord.setWordId(UniqueKeyUtil.getUniqueKey());
            searchHotWord.setWord(tuple.getValue());
            searchHotWord.setFrequency(tuple.getScore().intValue());
            // 热词等级，1为天数据，2为周数据
            searchHotWord.setGrade(2);
            searchHotWordDao.insert(searchHotWord);
        }
        // 清空对应的redis Sorted Set
        redisService.deleteKey(RedisEnum.WEEKLY_SEARCH_HOT_WORD_ZSET.getKey());
    }
}
