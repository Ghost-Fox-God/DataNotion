package com.datanotion.backend.repositories;

import com.datanotion.backend.models.Import;
import com.datanotion.backend.models.TextAnnotationImport;

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

@Repository
public class ImportRepository implements IImportRepository {

    @Autowired
    private DBconnect dbConnect;

    @Autowired
    DatanotionTextAnnotationFactory dataAnnotationTextAnnotationFactory;

    @Override
    public List<TextAnnotationImport> getImportsByProjectId(int projectId) {
        try {
            List<TextAnnotationImport> imports = new ArrayList<TextAnnotationImport>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Import WHERE project_id=?");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                TextAnnotationImport fetchedImport = (TextAnnotationImport) dataAnnotationTextAnnotationFactory
                        .createImport(res.getDate("import_date"),
                                res.getString("file_name"), res.getInt("record_count"), res.getString("status"));
                fetchedImport.setId(res.getInt("id"));
                fetchedImport.setProjectId(res.getInt("project_id"));
                imports.add(fetchedImport);
            }
            return imports;
        } catch (SQLException e) {
            throw new RuntimeException("Imports not found with projectId " + projectId);
        }
    }

    @Override
    public TextAnnotationImport createImport(Import newImports) {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Import (project_id, import_date, file_name, record_count, status) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ((TextAnnotationImport) newImports).getProjectId());
            statement.setDate(2, newImports.getImportDate());
            statement.setString(3, newImports.getFileName());
            statement.setInt(4, newImports.getRecordCount());
            statement.setString(5, newImports.getStatus());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            res.next();
            ((TextAnnotationImport) newImports).setId(res.getInt(1));
            return (TextAnnotationImport) newImports;
        } catch (SQLException e) {
            throw new RuntimeException("Import could not be created");
        }
    }

    @Override
    public TextAnnotationImport updateImport(Import importToBeUpdated) {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Import SET project_id=?, import_date=?, file_name=?, record_count=?, status=? WHERE id=?");
            statement.setInt(1, ((TextAnnotationImport) importToBeUpdated).getProjectId());
            statement.setDate(2, importToBeUpdated.getImportDate());
            statement.setString(3, importToBeUpdated.getFileName());
            statement.setInt(4, importToBeUpdated.getRecordCount());
            statement.setString(5, importToBeUpdated.getStatus());
            statement.setInt(6, ((TextAnnotationImport) importToBeUpdated).getId());
            statement.executeUpdate();
            return (TextAnnotationImport) importToBeUpdated;
        } catch (SQLException e) {
            throw new RuntimeException("Import could not be updated");
        }
    }

    @Override
    public void assignAnnotatorToImport(Import importToBeUpdated, int annotatorId) {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Import_has_User (import_project_id, import_id, user_id) VALUES (?, ?, ?)");
            statement.setInt(1, ((TextAnnotationImport) importToBeUpdated).getProjectId());
            statement.setInt(2, ((TextAnnotationImport) importToBeUpdated).getId());
            statement.setInt(3, annotatorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Annotator could not be assigned to import");
        }
    }

}
