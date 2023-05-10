package com.datanotion.backend.jobs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.datanotion.backend.models.TextAnnotationImport;
import com.datanotion.backend.models.Task;
import com.datanotion.backend.repositories.ImportRepository;
import com.datanotion.backend.repositories.TaskListRepository;

@Component
public class ImportJob {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private ImportRepository importRepository;

    @Async("executor")
    public void loadAndAssignToAnnotators(String pathToFile, int[] assignees, TextAnnotationImport newImport) {

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String line = reader.readLine();
            int count = 0;
            int currentAssignee = 0;
            while (line != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                Task task = new Task(0, line, 0, newImport.getProjectId(), assignees[currentAssignee]);
                taskListRepository.createTask(task);
                currentAssignee = (currentAssignee + 1) % assignees.length;
                line = reader.readLine();
                count++;
            }
            newImport.setRecordCount(count);
            newImport.setStatus("Done");
            importRepository.updateImport(newImport);
        } catch (IOException e) {
            newImport.setStatus("Failed");
            importRepository.updateImport(newImport);
            e.printStackTrace();
        }
    }
}
