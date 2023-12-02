package org.rent.circle.vendor.api.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.persistence.model.Worker;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class WorkerRepositoryTest {

    @Inject
    WorkerRepository workerRepository;

    @Test
    @TestTransaction
    public void findWorker_WhenWorkerDoesNotExist_ShouldReturnNull() {
        // Arrange

        // Act
        Worker result = workerRepository.findWorker(123L, 456L, "invalid");

        // Assert
        assertNull(result);
    }

    @Test
    @TestTransaction
    public void findWorker_WhenWorkerDoesExist_ShouldReturnWorker() {
        // Arrange

        // Act
        Worker result = workerRepository.findWorker(700L, 400L, "auth_user");

        // Assert
        assertNotNull(result);
    }
}
