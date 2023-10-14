package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
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
    public void updateVendorInfo(Long vendorId, UpdateVendorDto updateVendorInfo) {
        Vendor vendor = vendorRepository.findById(vendorId);
        if (vendor == null) {
            return;
        }

        vendorMapper.updateVendor(updateVendorInfo, vendor);
        vendorRepository.persist(vendor);
    }

    public VendorDto getVendor(Long vendorId, Long ownerId) {
        Vendor vendor = vendorRepository.findVendor(vendorId, ownerId);
        if (vendor == null) {
            log.info("Could Not Find Vendor With Given Ids: VendorId {} OwnerId {}", vendorId, ownerId);
            return null;
        }

        return vendorMapper.toDto(vendor);
    }

    public List<VendorDto> getVendors(Long ownerId, boolean filterActiveWorkers, int page, int pageSize) {
        List<Vendor> vendors = vendorRepository.findVendors(ownerId, filterActiveWorkers, page, pageSize);
        return vendorMapper.toDtoList(vendors);
    }
}
