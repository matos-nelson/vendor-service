package org.rent.circle.vendor.api.resource;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.service.VendorService;

@AllArgsConstructor
@Path("/vendor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class VendorResource {

    private final VendorService vendorService;

    @POST
    public Long saveVendor(@Valid SaveVendorDto saveVendorDto) {
        return vendorService.saveVendor(saveVendorDto);
    }

    @PATCH
    @Path("/{id}")
    public void updateVendor(@NotNull @PathParam("id") long vendorId, @NotNull @Valid UpdateVendorDto updateVendorInfo) {
        vendorService.updateVendorInfo(vendorId, updateVendorInfo);
    }
}
