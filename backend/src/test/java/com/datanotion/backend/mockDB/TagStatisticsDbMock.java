package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.TagStatistics;
import com.datanotion.backend.repositories.IStatisticsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TagStatisticsDbMock implements IStatisticsRepository {
    List<TagStatistics> tagStatistics = new ArrayList<>(
            List.of(new TagStatistics("temp", 3), new TagStatistics("test", 4)));

    @Override
    public List<TagStatistics> getEntityTagStatisticsByProjectId(int projectId) {
        if (projectId == 1) {
            return tagStatistics;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TagStatistics> getClassificationTagStatisticsByProjectId(int projectId) {
        if (projectId == 1) {
            return tagStatistics;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public int getCountOfTotalDataTasks(int projectId) {
        if (projectId == 1) {
            return 5;
        } else {
            return 0;
        }
    }

    @Override
    public int getCountOfAnnotatedDataTask(int projectId) {
        if (projectId == 1) {
            return 2;
        } else {
            return 0;
        }
    }
}
