package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.ClassificationTag;

import com.datanotion.backend.requests.TagRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassificationTagRepository implements IClassificationTagRepository {

    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<ClassificationTag> getAllClassificationTagsByProjectId(int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("SELECT * FROM ClassificationTag WHERE project_id = ?;")) {
            statement.setInt(1, projectId);
            List<ClassificationTag> classificationTags = new ArrayList<>();
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
    public ClassificationTag getClassificationTagById(int classificationTagId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("SELECT * FROM ClassificationTag WHERE id = ?;")) {
            statement.setInt(1, classificationTagId);
            ResultSet res = statement.executeQuery();
            res.next();
            return new ClassificationTag(res.getInt("id"), res.getString("tag_name"), res.getInt("project_id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createClassificationTag(TagRequest tagRequest) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("INSERT INTO ClassificationTag (tag_name, project_id) VALUES (?, ?);")) {
            statement.setString(1, tagRequest.getTagName());
            statement.setInt(2, tagRequest.getProjectId());
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteClassificationTag(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM ClassificationTag WHERE id = ?")) {
            statement.setInt(1, id);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
