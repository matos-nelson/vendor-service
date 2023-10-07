package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
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
    public Long saveVendor(SaveVendorDto saveVendor) {
        Vendor vendor = vendorMapper.toModel(saveVendor);

        vendorRepository.persist(vendor);
        return vendor.getId();
    }

    @Transactional
    public void updateVendorInfo(Long vendorId, Long ownerId, UpdateVendorDto updateVendorInfo) {
        Vendor vendor = vendorRepository.findVendor(vendorId, ownerId);
        if (vendor == null) {
            return;
        }

        vendorMapper.updateVendor(updateVendorInfo, vendor);
        vendorRepository.persist(vendor);
    }
}
