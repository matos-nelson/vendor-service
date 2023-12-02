package org.rent.circle.vendor.api.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.rent.circle.vendor.api.persistence.model.Worker;

@ApplicationScoped
public class WorkerRepository implements PanacheRepository<Worker> {

    public Worker findWorker(Long id, Long vendorId, String managerId) {
        Parameters queryParams = Parameters.with("id", id)
            .and("vendorId", vendorId)
            .and("managerId", managerId);
        return find("From Worker w "
            + "left join fetch w.vendor v "
            + "where v.managerId = :managerId and v.id = :vendorId and w.id =:id", queryParams).firstResult();
    }
}
