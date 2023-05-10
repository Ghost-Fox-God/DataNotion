package com.datanotion.backend.repositories;

import com.datanotion.backend.models.TagStatistics;

import java.util.List;

public interface IStatisticsRepository {
    List<TagStatistics> getEntityTagStatisticsByProjectId(int projectId);

    List<TagStatistics> getClassificationTagStatisticsByProjectId(int projectId);

    int getCountOfTotalDataTasks(int projectId);

    int getCountOfAnnotatedDataTask(int projectId);
}
