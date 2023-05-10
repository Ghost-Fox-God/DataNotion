package com.datanotion.backend.controllers;

import com.datanotion.backend.models.TagStatistics;
import com.datanotion.backend.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datanotion.backend.models.TextAnotationProjectStatistics;

@RestController
@RequestMapping("/projects/{id}")
public class StatisticsController {
    @Autowired
    private StatisticsRepository statisticsRepository;

    @GetMapping("/statistics")
    public TextAnotationProjectStatistics getStatisticsByProjectId(@PathVariable("id") int projectId) {

        return new TextAnotationProjectStatistics(
                TagStatistics.getEntityStatisticsByProjectId(statisticsRepository, projectId),
                TagStatistics.getClassificationTagStatisticsByProjectId(statisticsRepository,
                        projectId),
                TextAnotationProjectStatistics.getCountOfTotalDataTasks(statisticsRepository, projectId),
                TextAnotationProjectStatistics.getCountOfAnnotatedDataTask(statisticsRepository, projectId));
    }
}
