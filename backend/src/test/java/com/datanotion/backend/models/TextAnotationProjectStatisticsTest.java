package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.TagStatisticsDbMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TextAnotationProjectStatisticsTest {

    @Autowired
    private TagStatisticsDbMock tagStatisticsDbMock;

    @Test
    void textAnotationProjectStatisticsObjectTest() {
        List<TagStatistics> entity = new ArrayList<>();
        List<TagStatistics> classification = new ArrayList<>();

        TextAnotationProjectStatistics textAnotationProjectStatistics = new TextAnotationProjectStatistics(entity,
                                                                                                           classification,
                                                                                                           5, 6
        );

        assertEquals(0, textAnotationProjectStatistics.getEntityStatistics().size());
        assertEquals(0, textAnotationProjectStatistics.getClassificationStatistics().size());
        assertEquals(5, textAnotationProjectStatistics.getTotalDataTasks());
        assertEquals(6, textAnotationProjectStatistics.getAnnotatedDataTasks());
    }

    @Test
    void textAnotationProjectStatisticsGetterSetterTest() {
        List<TagStatistics> entity = new ArrayList<>();
        List<TagStatistics> classification = new ArrayList<>();

        TextAnotationProjectStatistics textAnotationProjectStatistics = new TextAnotationProjectStatistics(entity,
                                                                                                           classification,
                                                                                                           5, 6
        );

        entity.add(new TagStatistics("test", 4));
        classification.add(new TagStatistics("test", 4));

        textAnotationProjectStatistics.setClassificationStatistics(classification);
        textAnotationProjectStatistics.setEntityStatistics(entity);
        textAnotationProjectStatistics.setTotalDataTasks(1);
        textAnotationProjectStatistics.setAnnotatedDataTasks(1);

        assertEquals(1, textAnotationProjectStatistics.getEntityStatistics().size());
        assertEquals(1, textAnotationProjectStatistics.getClassificationStatistics().size());
        assertEquals(1, textAnotationProjectStatistics.getTotalDataTasks());
        assertEquals(1, textAnotationProjectStatistics.getAnnotatedDataTasks());
    }

    @Test
    void getCountOfTotalDataTasksSuccessTest() {
        int outPutResult = TextAnotationProjectStatistics.getCountOfTotalDataTasks(tagStatisticsDbMock, 1);

        assertEquals(5, outPutResult);
    }

    @Test
    void getCountOfTotalDataTasksFailureTest() {
        int outPutResult = TextAnotationProjectStatistics.getCountOfTotalDataTasks(tagStatisticsDbMock, 2);
        assertEquals(0, outPutResult);

    }

    @Test
    void getCountOfAnnotatedDataTaskSuccessTest() {
        int outPutResult = TextAnotationProjectStatistics.getCountOfAnnotatedDataTask(tagStatisticsDbMock, 1);
        assertEquals(2, outPutResult);
    }

    @Test
    void getCountOfAnnotatedDataTaskFailureTest() {
        int outPutResult = TextAnotationProjectStatistics.getCountOfAnnotatedDataTask(tagStatisticsDbMock, 2);
        assertEquals(0, outPutResult);

    }



}
