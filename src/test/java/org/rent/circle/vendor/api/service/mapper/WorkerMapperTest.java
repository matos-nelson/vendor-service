package org.rent.circle.vendor.api.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Worker;

@QuarkusTest
public class WorkerMapperTest {

    @Inject
    WorkerMapper workerMapper;

    @Test
    public void updateWorker_WhenGivenNullUpdateWorkerDto_ShouldReturnNull() {
        // Arrange
        Worker worker = new Worker();
        worker.setName("Simple Test");
        worker.setEmail("simpletest@email.com");
        worker.setPhone("1234567890");

        // Act
        workerMapper.updateWorker(null, worker);

        // Assert
        assertNotNull(worker);
    }

    @Test
    public void updateWorker_WhenGivenAnUpdateWorkerDto_ShouldMap() {
        // Arrange
        Worker worker = new Worker();
        worker.setName("Simple Test");
        worker.setEmail("simpletest@email.com");
        worker.setPhone("1234567890");

        UpdateWorkerDto updateWorker = UpdateWorkerDto.builder()
            .name("Updated Name")
            .email("updated@email.com")
            .phone("9876543210")
            .active(true)
            .build();

        // Act
        workerMapper.updateWorker(updateWorker, worker);

        // Assert
        assertNotNull(worker);
        assertEquals(updateWorker.getName(), worker.getName());
        assertEquals(updateWorker.getEmail(), worker.getEmail());
        assertEquals(updateWorker.getPhone(), worker.getPhone());
    }
}
