package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Worker;
import org.rent.circle.vendor.api.persistence.repository.WorkerRepository;
import org.rent.circle.vendor.api.service.mapper.WorkerMapper;

@AllArgsConstructor
@ApplicationScoped
@Slf4j
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;

    @Transactional
    public void updateWorkerInfo(Long workerId, Long vendorId, UpdateWorkerDto updateWorkerInfo) {
        Worker worker = workerRepository.findWorker(workerId, vendorId);
        if (worker == null) {
            return;
        }

        workerMapper.updateWorker(updateWorkerInfo, worker);
        workerRepository.persist(worker);
    }
}
