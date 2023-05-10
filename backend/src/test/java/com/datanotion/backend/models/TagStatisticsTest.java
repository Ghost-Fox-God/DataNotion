package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.TagStatisticsDbMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TagStatisticsTest {
    @Autowired
    private TagStatisticsDbMock statisticsRepository;

    @Test
    void tagStatisticsObjectTest() {
        TagStatistics tagStatistics = new TagStatistics("temp", 3);

        assertEquals("temp", tagStatistics.getTagName());
        assertEquals(3, tagStatistics.getTagCount());
    }

    @Test
    void tagStatisticsGetterSetterTest() {
        TagStatistics tagStatistics = new TagStatistics("temp", 3);
        tagStatistics.setTagCount(5);
        tagStatistics.setTagName("new tag");

        assertEquals("new tag", tagStatistics.getTagName());
        assertEquals(5, tagStatistics.getTagCount());
    }

    @Test
    void getEntityStatisticsByProjectIdSuccessTest() {
       List<TagStatistics> tagStatistics = TagStatistics.getEntityStatisticsByProjectId(statisticsRepository, 1);

       assertEquals(2, tagStatistics.size());
    }
    @Test
    void getEntityStatisticsByProjectIdFailureTest() {
        List<TagStatistics> tagStatistics = TagStatistics.getEntityStatisticsByProjectId(statisticsRepository, 2);

        assertEquals(0, tagStatistics.size());
    }

    @Test
    void getClassificationTagStatisticsByProjectIdSuccessTest(){
        List<TagStatistics> tagStatistics = TagStatistics.getClassificationTagStatisticsByProjectId(statisticsRepository, 1);

        assertEquals(2, tagStatistics.size());
    }
    @Test
    void getClassificationTagStatisticsByProjectIdFailureTest(){
        List<TagStatistics> tagStatistics = TagStatistics.getClassificationTagStatisticsByProjectId(statisticsRepository, 2);

        assertEquals(0, tagStatistics.size());
    }
}