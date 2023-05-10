package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.TagStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepository implements IStatisticsRepository {

    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<TagStatistics> getEntityTagStatisticsByProjectId(int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement(
                        "SELECT COUNT(em.entity_id) as tag_count, et.tag_name FROM EntityMapping em RIGHT OUTER JOIN EntityTag et ON em.entity_id = et.id WHERE et.project_id= ? GROUP BY tag_name;")) {

            List<TagStatistics> statistics = new ArrayList<>();
            statement.setInt(1, projectId);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                statistics.add(new TagStatistics(res.getString("tag_name"), res.getInt("tag_count")));
            }
            return statistics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TagStatistics> getClassificationTagStatisticsByProjectId(int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement(
                        "SELECT COUNT(em.classification_id) as tag_count, et.tag_name FROM DataTask em RIGHT OUTER JOIN ClassificationTag et ON em.classification_id = et.id WHERE et.project_id = ? GROUP BY tag_name;")) {

            List<TagStatistics> statistics = new ArrayList<>();
            statement.setInt(1, projectId);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                statistics.add(new TagStatistics(res.getString("tag_name"), res.getInt("tag_count")));
            }
            return statistics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCountOfAnnotatedDataTask(int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement(
                        "select count(distinct (dt.id)) as annotated_task_count from DataTask dt left outer join EntityMapping em on dt.id = em.datatask_id left outer join ClassificationTag ct on dt.classification_id=ct.id left outer join EntityTag et on em.entity_id=et.id where (classification_id IS null and entity_id IS null)=false and dt.project_id = ? ;")) {
            statement.setInt(1, projectId);

            ResultSet res = statement.executeQuery();
            res.next();
            return res.getInt("annotated_task_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCountOfTotalDataTasks(int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement(
                        "select count(id) as total_task_count from DataTask where project_id = ?;")) {
            statement.setInt(1, projectId);

            ResultSet res = statement.executeQuery();
            res.next();
            return res.getInt("total_task_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
