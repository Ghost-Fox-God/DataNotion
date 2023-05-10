package com.datanotion.backend.models;

import java.util.List;

import com.datanotion.backend.repositories.IStatisticsRepository;

public class TextAnotationProjectStatistics extends ProjectStatistics {
    private List<TagStatistics> entityStatistics;
    private List<TagStatistics> classificationStatistics;

    public TextAnotationProjectStatistics(List<TagStatistics> entityStatistics,
            List<TagStatistics> classificationStatistics,
            int totalDataTasks, int annotatedDataTasks) {
        super(totalDataTasks, annotatedDataTasks);
        this.entityStatistics = entityStatistics;
        this.classificationStatistics = classificationStatistics;
    }

    public List<TagStatistics> getEntityStatistics() {
        return entityStatistics;
    }

    public void setEntityStatistics(List<TagStatistics> entityStatistics) {
        this.entityStatistics = entityStatistics;
    }

    public List<TagStatistics> getClassificationStatistics() {
        return classificationStatistics;
    }

    public void setClassificationStatistics(List<TagStatistics> classificationStatistics) {
        this.classificationStatistics = classificationStatistics;
    }

    public static int getCountOfTotalDataTasks(IStatisticsRepository statisticsRepository, int projectId) {
        return statisticsRepository.getCountOfTotalDataTasks(projectId);
    }

    public static int getCountOfAnnotatedDataTask(IStatisticsRepository statisticsRepository, int projectId) {
        return statisticsRepository.getCountOfAnnotatedDataTask(projectId);
    }
}
