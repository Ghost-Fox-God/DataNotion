package com.datanotion.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.factories.DatanotionTextAnnotationFactory;
import com.datanotion.backend.models.Export;
import com.datanotion.backend.models.ExportRow;
import com.datanotion.backend.models.TextAnnotationExport;

@Repository
public class ExportRepository implements IExportRepository {

    @Autowired
    private DBconnect dbConnect;

    @Autowired
    DatanotionTextAnnotationFactory dataAnnotationTextAnnotationFactory;

    @Override
    public List<TextAnnotationExport> getExportsByProjectId(int projectId) {
        try {
            List<TextAnnotationExport> exports = new ArrayList<TextAnnotationExport>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Export WHERE project_id=?");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                TextAnnotationExport fetchedExport = (TextAnnotationExport) dataAnnotationTextAnnotationFactory
                        .createExport(res.getDate("export_date"),
                                res.getString("status"), res.getInt("record_count"));
                fetchedExport.setId(res.getInt("id"));
                fetchedExport.setProjectId(res.getInt("project_id"));
                exports.add(((TextAnnotationExport) fetchedExport));
            }
            return exports;
        } catch (SQLException e) {
            throw new RuntimeException("Exports not found with projectId " + projectId);
        }
    }

    @Override
    public TextAnnotationExport createExport(Export newExport) {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Export (project_id, export_date, status, record_count) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ((TextAnnotationExport) newExport).getProjectId());
            statement.setDate(2, newExport.getExportDate());
            statement.setString(3, newExport.getStatus());
            statement.setInt(4, newExport.getRecordCount());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            res.next();
            ((TextAnnotationExport) newExport).setId(res.getInt(1));
            return (TextAnnotationExport) newExport;
        } catch (SQLException e) {
            throw new RuntimeException("Export not created");
        }
    }

    @Override
    public TextAnnotationExport updateExport(Export exportToBeUpdated) {

        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Export SET project_id=?, export_date=?, status=?, record_count=? WHERE id=?");
            statement.setInt(1, ((TextAnnotationExport) exportToBeUpdated).getProjectId());
            statement.setDate(2, exportToBeUpdated.getExportDate());
            statement.setString(3, exportToBeUpdated.getStatus());
            statement.setInt(4, exportToBeUpdated.getRecordCount());
            statement.setInt(5, ((TextAnnotationExport) exportToBeUpdated).getId());
            statement.executeUpdate();
            return (TextAnnotationExport) exportToBeUpdated;
        } catch (SQLException e) {
            throw new RuntimeException("Export not updated");
        }
    }

    @Override
    public List<ExportRow> exportDataAnnotationsByProjectId(int projectId) {
        try {
            List<ExportRow> exportRows = new ArrayList<ExportRow>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select dt.content, ct.tag_name as 'classification', GROUP_CONCAT(et.tag_name) as 'entity', GROUP_CONCAT(em.start) as start, GROUP_CONCAT(em.end) as end from DataTask dt left outer join EntityMapping em on dt.id = em.datatask_id left outer join ClassificationTag ct on dt.classification_id=ct.id left outer join EntityTag et on em.entity_id=et.id where (classification_id IS null and entity_id IS null)=false and dt.project_id = ? group by dt.id;");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                exportRows.add(new ExportRow(res.getString("content"), res.getString("classification"),
                        res.getString("entity"),
                        res.getString("start"), res.getString("end")));
            }
            return exportRows;
        } catch (SQLException e) {
            throw new RuntimeException("Export rows not found with projectId " + projectId);
        }
    }

}
