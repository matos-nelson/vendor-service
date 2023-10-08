package org.rent.circle.vendor.api.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.rent.circle.vendor.api.persistence.model.Worker;

@ApplicationScoped
public class WorkerRepository implements PanacheRepository<Worker> {

    public Worker findWorker(Long id, Long vendorId) {
        Parameters queryParams = Parameters.with("id", id).and("vendorId", vendorId);
        return find("id = :id and vendorId = :vendorId", queryParams).firstResult();
    }
}
