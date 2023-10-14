package org.rent.circle.vendor.api.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {

    public Vendor findVendor(Long id, Long ownerId) {
        Parameters queryParams = Parameters.with("id", id).and("ownerId", ownerId);
        return find("id = :id and ownerId = :ownerId", queryParams).firstResult();
    }

    public List<Vendor> findVendors(Long ownerId, int page, int pageSize) {
        return find("ownerId", ownerId)
            .page(Page.of(page, pageSize))
            .list();
    }
}
