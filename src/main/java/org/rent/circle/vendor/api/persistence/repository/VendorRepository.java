package org.rent.circle.vendor.api.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {

    public Vendor findVendor(Long id, String managerId) {
        Parameters queryParams = Parameters.with("id", id).and("managerId", managerId);
        return find("id = :id and managerId = :managerId", queryParams).firstResult();
    }

    public List<Vendor> findVendors(String managerId, boolean filterActiveWorkers, int page, int pageSize) {

        if (!filterActiveWorkers) {
            return find("managerId", managerId)
                .page(Page.of(page, pageSize))
                .list();
        }

        Parameters queryParams = Parameters.with("managerId", managerId);
        return find("from Vendor v "
            + "left join fetch v.workers w "
            + "where v.managerId = :managerId and w.active = true", queryParams)
            .list();
    }
}
