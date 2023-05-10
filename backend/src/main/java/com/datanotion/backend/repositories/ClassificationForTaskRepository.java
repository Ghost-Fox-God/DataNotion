package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.requests.ClassificationAdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClassificationForTaskRepository implements IClassificationForTaskRepository {

    @Autowired
    private DBconnect dbConnect;

    public List<ClassificationTag> getClassificationOfTask(int taskId) {
        try {
            List<ClassificationTag> classificationTags = new ArrayList<ClassificationTag>();
            PreparedStatement statement = dbConnect.getDBConnection().prepareStatement(
                    "SELECT * FROM ClassificationTag WHERE id = (SELECT Classification_id from DataTask WHERE id = ?)");
            statement.setInt(1, taskId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                classificationTags.add(
                        new ClassificationTag(res.getInt("id"), res.getString("tag_name"), res.getInt("project_id")));
            }
            return classificationTags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addClassificationForTask(int taskId, ClassificationAdd classificationTagID) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("UPDATE DataTask SET classification_id = ? WHERE id = ?;")) {
            statement.setInt(1, classificationTagID.getClassificationId());
            statement.setInt(2, taskId);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteClassificationOfTask(int taskId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("Update DataTask SET Classification_id = null WHERE id = ?")) {
            statement.setInt(1, taskId);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
