package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.CreateVendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.repository.VendorRepository;
import org.rent.circle.vendor.api.service.mapper.VendorMapper;

@AllArgsConstructor
@ApplicationScoped
@Slf4j
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Transactional
    public Long saveVendor(CreateVendorDto createVendor) {
        Vendor vendor = vendorMapper.toModel(createVendor);

        vendorRepository.persist(vendor);
        return vendor.getId();
    }
}
