package org.rent.circle.vendor.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
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
    public Long saveVendor(SaveVendorDto saveVendor, @NotBlank String managerId) {
        Vendor vendor = vendorMapper.toModel(saveVendor);
        vendor.setManagerId(managerId);

        vendorRepository.persist(vendor);
        return vendor.getId();
    }

    @Transactional
    public void updateVendorInfo(Long vendorId, String managerId, UpdateVendorDto updateVendorInfo) {
        Vendor vendor = vendorRepository.findVendor(vendorId, managerId);
        if (vendor == null) {
            log.info("Could Not Find Vendor With Given Ids: VendorId {} ManagerId {}", vendorId, managerId);
            return;
        }

        vendorMapper.updateVendor(updateVendorInfo, vendor);
        vendorRepository.persist(vendor);
    }

    public VendorDto getVendor(Long vendorId, String managerId) {
        Vendor vendor = vendorRepository.findVendor(vendorId, managerId);
        if (vendor == null) {
            log.info("Could Not Find Vendor With Given Ids: VendorId {} ManagerId {}", vendorId, managerId);
            return null;
        }

        return vendorMapper.toDto(vendor);
    }

    public List<VendorDto> getVendors(String managerId, boolean filterActiveWorkers, int page, int pageSize) {
        List<Vendor> vendors = vendorRepository.findVendors(managerId, filterActiveWorkers, page, pageSize);
        return vendorMapper.toDtoList(vendors);
    }
}
