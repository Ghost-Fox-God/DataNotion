package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.EntityDbMock;
import com.datanotion.backend.responses.GetMappingRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class EntityTest {
    @Autowired
    EntityDbMock entityDbMock;

    List<Entity> entityList = new ArrayList<>(List.of(new Entity(10,20,5,1)));

    @Test
    public void fetchAllEntityByTaskSuccessTest(){
        List<GetMappingRequestResponse> responseList = Entity.fetchAllEntityByTask(entityDbMock, 1, 1);
        Assertions.assertEquals(1,responseList.size());
    }
    @Test
    public void fetchAllEntityByTaskFailureTest(){
        List<GetMappingRequestResponse> responseList = Entity.fetchAllEntityByTask(entityDbMock, 1, 5);
        Assertions.assertNotEquals(1,responseList.size());
    }

    @Test
    public void createEntityByTaskSuccessTest(){
        int response = Entity.createEntityByTask(entityDbMock, 1, 1, entityList);
        Assertions.assertEquals(1,response);
    }
    @Test
    public void createEntityByTaskFailureTest(){
        int response = Entity.createEntityByTask(entityDbMock, 1, 5, entityList);
        Assertions.assertNotEquals(1,response);
    }
    @Test
    public void removeEntityByTaskSuccessTest(){
        int response = Entity.removeEntityByTask(entityDbMock, 1, 2);
        Assertions.assertEquals(1,response);
    }
    @Test
    public void removeEntityByTaskFailureTest(){
        int response = Entity.removeEntityByTask(entityDbMock, 1, 2);
        Assertions.assertEquals(1,response);
    }
}
