package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.EntityTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.datanotion.backend.requests.TagRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntityTagRepository implements IEntityTagRepository {

    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<EntityTag> getEntityTagsByProjectId(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("SELECT * FROM EntityTag WHERE project_id = ?;")) {
            List<EntityTag> tags = new ArrayList<>();
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                tags.add(new EntityTag(res.getInt("id"), res.getString("tag_name"), res.getInt("project_id")));
            }
            return tags;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityTag getEntityTagById(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("SELECT * FROM EntityTag WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();
            return new EntityTag(res.getInt("id"), res.getString("tag_name"), res.getInt("project_id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createEntityTag(TagRequest tagRequest) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("INSERT INTO EntityTag (tag_name, project_id) VALUES (?, ?);")) {
            statement.setString(1, tagRequest.getTagName());
            statement.setInt(2, tagRequest.getProjectId());
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteEntityTag(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM EntityTag WHERE id = ?;")) {
            statement.setInt(1, id);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
