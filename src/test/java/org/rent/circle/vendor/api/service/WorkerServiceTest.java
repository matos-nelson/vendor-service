package org.rent.circle.vendor.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.model.Worker;
import org.rent.circle.vendor.api.persistence.repository.VendorRepository;
import org.rent.circle.vendor.api.persistence.repository.WorkerRepository;
import org.rent.circle.vendor.api.service.mapper.WorkerMapper;

@QuarkusTest
public class WorkerServiceTest {

    @InjectMock
    WorkerRepository workerRepository;

    @InjectMock
    VendorRepository vendorRepository;

    @InjectMock
    WorkerMapper workerMapper;

    @Inject
    WorkerService workerService;

    @Test
    public void saveWorker_WhenVendorCantBeFound_ShouldReturnNull() {
        // Arrange
        long vendorId = 1L;
        String managerId = "2";
        SaveWorkerDto saveWorkerDto = SaveWorkerDto.builder().build();
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(null);

        // Act
        Long result = workerService.saveWorker(vendorId, managerId, saveWorkerDto);

        // Assert
        assertNull(result);
        verify(workerMapper, never()).toModel(saveWorkerDto);
        verify(workerRepository, never()).persist(Mockito.any(Worker.class));
    }

    @Test
    public void saveWorker_WhenCalled_ShouldSaveWorker() {
        // Arrange
        long vendorId = 123L;
        String managerId = "2";

        Vendor vendor = new Vendor();
        vendor.setId(vendorId);
        vendor.setWorkers(new ArrayList<>());

        Worker worker = new Worker();
        worker.setId(100L);

        SaveWorkerDto saveWorkerDto = SaveWorkerDto.builder().build();
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(vendor);
        when(workerMapper.toModel(saveWorkerDto)).thenReturn(worker);

        // Act
        Long result = workerService.saveWorker(vendorId, managerId, saveWorkerDto);

        // Assert
        assertNotNull(result);
        verify(workerRepository, times(1)).persist(worker);
    }

    @Test
    public void updateWorkerInfo_WhenWorkerIsNotFound_ShouldReturnNotUpdate() {
        // Arrange
        long workerId = 1L;
        long vendorId = 2L;
        String managerId = "3";
        UpdateWorkerDto updateWorkerDto = UpdateWorkerDto.builder().build();
        when(workerRepository.findWorker(workerId, vendorId, managerId)).thenReturn(null);

        // Act
        workerService.updateWorkerInfo(workerId, vendorId, managerId, updateWorkerDto);

        // Assert
        verify(workerMapper, never()).updateWorker(updateWorkerDto, null);
        verify(workerRepository, never()).persist(Mockito.any(Worker.class));
    }

    @Test
    public void updateWorkerInfo_WhenCalled_ShouldUpdate() {
        // Arrange
        Long workerId = 1L;
        Long vendorId = 2L;
        String managerId = "3";

        Worker worker = new Worker();
        worker.setId(workerId);

        UpdateWorkerDto updateWorkerInfo = UpdateWorkerDto.builder()
            .name("Updated Name")
            .phone("9876543210")
            .email("updated@email.com")
            .active(true)
            .build();
        when(workerRepository.findWorker(workerId, vendorId, managerId)).thenReturn(worker);

        // Act
        workerService.updateWorkerInfo(workerId, vendorId, managerId, updateWorkerInfo);

        // Assert
        verify(workerMapper, times(1)).updateWorker(updateWorkerInfo, worker);
        verify(workerRepository, times(1)).persist(worker);
    }
}
