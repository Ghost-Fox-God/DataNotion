package com.datanotion.backend.models;

import com.datanotion.backend.repositories.IStatisticsRepository;
import java.util.List;

public class TagStatistics {
    private String tagName;
    private int tagCount;

    public TagStatistics(String tagName, int tagCount) {
        this.tagName = tagName;
        this.tagCount = tagCount;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public static List<TagStatistics> getEntityStatisticsByProjectId(IStatisticsRepository statisticsRepository,
            int id) {
        return statisticsRepository.getEntityTagStatisticsByProjectId(id);
    }

    public static List<TagStatistics> getClassificationTagStatisticsByProjectId(
            IStatisticsRepository statisticsRepository, int id) {
        return statisticsRepository.getClassificationTagStatisticsByProjectId(id);
    }
}
