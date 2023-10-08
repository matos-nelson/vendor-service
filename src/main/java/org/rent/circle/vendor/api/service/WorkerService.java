package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.model.Worker;
import org.rent.circle.vendor.api.persistence.repository.VendorRepository;
import org.rent.circle.vendor.api.persistence.repository.WorkerRepository;
import org.rent.circle.vendor.api.service.mapper.WorkerMapper;

@AllArgsConstructor
@ApplicationScoped
@Slf4j
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final VendorRepository vendorRepository;
    private final WorkerMapper workerMapper;

    @Transactional
    public Long saveWorker(Long vendorId, SaveWorkerDto saveWorker) {

        Vendor vendor = vendorRepository.findById(vendorId);
        if (vendor == null) {
            log.info("Vendor Could Not Be Found With Id: {}", vendorId);
            return null;
        }

        Worker worker = workerMapper.toModel(saveWorker);
        vendor.getWorkers().add(worker);

        workerRepository.persist(worker);
        return worker.getId();
    }

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
