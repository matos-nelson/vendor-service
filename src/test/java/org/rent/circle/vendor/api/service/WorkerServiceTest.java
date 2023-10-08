package org.rent.circle.vendor.api.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Worker;
import org.rent.circle.vendor.api.persistence.repository.WorkerRepository;
import org.rent.circle.vendor.api.service.mapper.WorkerMapper;

@QuarkusTest
public class WorkerServiceTest {

    @InjectMock
    WorkerRepository workerRepository;

    @InjectMock
    WorkerMapper workerMapper;

    @Inject
    WorkerService workerService;

    @Test
    public void updateWorkerInfo_WhenWorkerIsNotFound_ShouldReturnNotUpdate() {
        // Arrange
        long workerId = 1L;
        long vendorId = 2L;
        UpdateWorkerDto updateWorkerDto = UpdateWorkerDto.builder().build();
        when(workerRepository.findWorker(workerId, vendorId)).thenReturn(null);

        // Act
        workerService.updateWorkerInfo(workerId, vendorId, updateWorkerDto);

        // Assert
        verify(workerMapper, never()).updateWorker(updateWorkerDto, null);
        verify(workerRepository, never()).persist(Mockito.any(Worker.class));
    }

    @Test
    public void updateWorkerInfo_WhenCalled_ShouldUpdate() {
        // Arrange
        Long workerId = 1L;
        Long vendorId = 2L;

        Worker worker = new Worker();
        worker.setId(workerId);

        UpdateWorkerDto updateWorkerInfo = UpdateWorkerDto.builder()
            .name("Updated Name")
            .phone("9876543210")
            .email("updated@email.com")
            .build();
        when(workerRepository.findWorker(workerId, vendorId)).thenReturn(worker);

        // Act
        workerService.updateWorkerInfo(workerId, vendorId, updateWorkerInfo);

        // Assert
        verify(workerMapper, times(1)).updateWorker(updateWorkerInfo, worker);
        verify(workerRepository, times(1)).persist(worker);
    }
}
