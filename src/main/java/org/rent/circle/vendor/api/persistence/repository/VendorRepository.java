package org.rent.circle.vendor.api.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {

}
