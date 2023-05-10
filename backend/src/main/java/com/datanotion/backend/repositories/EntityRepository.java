package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.Entity;
import com.datanotion.backend.responses.GetMappingRequestResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepository implements IEntityRepository {

    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<GetMappingRequestResponse> getAllEntityByTask(int projectId, int taskId) {
        try {
            List<GetMappingRequestResponse> entities = new ArrayList<GetMappingRequestResponse>();
            PreparedStatement statement = dbConnect.getDBConnection().prepareStatement(
                    "SELECT EntityMapping.start, EntityMapping.end, EntityMapping.entity_id, EntityMapping.datatask_id,  EntityTag.tag_name From EntityMapping Inner Join EntityTag ON EntityMapping.entity_id = EntityTag.id AND EntityMapping.datatask_id = ?;");
            statement.setInt(1, taskId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                entities.add(new GetMappingRequestResponse(res.getInt("start"), res.getInt("end"),
                        res.getInt("entity_id"), res.getString("tag_name")));
            }
            return entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addEntityByTask(int projectId, int taskId, List<Entity> newEntity) {
        int count = 0;
        deleteEntityByTask(projectId, taskId);
        for (Entity entity : newEntity) {
            try (PreparedStatement statement = dbConnect.getDBConnection().prepareStatement(
                    "INSERT INTO EntityMapping (start, end, entity_id, datatask_id) VALUES (?, ?, ?, ?);")) {
                statement.setInt(1, entity.getStart());
                statement.setInt(2, entity.getEnd());
                statement.setInt(3, entity.getTagId());
                statement.setInt(4, taskId);
                statement.executeUpdate();
                count++;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    @Override
    public int deleteEntityByTask(int projectId, int taskId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM EntityMapping WHERE datatask_id = ?")) {
            statement.setInt(1, taskId);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
