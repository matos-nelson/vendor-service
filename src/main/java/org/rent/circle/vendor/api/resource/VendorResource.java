package org.rent.circle.vendor.api.resource;

import io.quarkus.security.Authenticated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
import org.rent.circle.vendor.api.service.VendorService;

@AllArgsConstructor
@Authenticated
@Path("/vendor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class VendorResource {

    private final VendorService vendorService;
    private final JsonWebToken jwt;

    @POST
    public Long saveVendor(@Valid SaveVendorDto saveVendorDto) {
        return vendorService.saveVendor(saveVendorDto, jwt.getName());
    }

    @PATCH
    @Path("/{id}")
    public void updateVendor(@NotNull @PathParam("id") long vendorId, @Valid UpdateVendorDto updateVendorInfo) {
        vendorService.updateVendorInfo(vendorId, jwt.getName(), updateVendorInfo);
    }

    @GET
    @Path("/{id}")
    public VendorDto getVendor(@NotNull @PathParam("id") Long vendorId) {
        return vendorService.getVendor(vendorId, jwt.getName());
    }

    @GET
    public List<VendorDto> getVendors(@NotNull @QueryParam("filterActiveWorkers") boolean filterActiveWorkers,
        @NotNull @QueryParam("page") @Min(0) Integer page,
        @NotNull @QueryParam("pageSize") @Min(1) Integer pageSize) {

        return vendorService.getVendors(jwt.getName(), filterActiveWorkers, page, pageSize);
    }
}
