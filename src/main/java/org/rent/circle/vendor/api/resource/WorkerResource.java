package org.rent.circle.vendor.api.resource;

import io.quarkus.security.Authenticated;
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
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.service.WorkerService;

@AllArgsConstructor
@Authenticated
@Path("/worker")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class WorkerResource {

    private final WorkerService workerService;
    private final JsonWebToken jwt;

    @POST
    @Path("/vendor/{vendorId}")
    public Long saveWorker(@NotNull @PathParam("vendorId") long vendorId, @Valid SaveWorkerDto saveWorkerDto) {
        return workerService.saveWorker(vendorId, jwt.getName(), saveWorkerDto);
    }

    @PATCH
    @Path("/{id}/vendor/{vendorId}")
    public void updateWorker(@NotNull @PathParam("id") Long workerId, @NotNull @PathParam("vendorId") Long vendorId,
        @Valid UpdateWorkerDto updateWorkerInfo) {
        workerService.updateWorkerInfo(workerId, vendorId, jwt.getName(), updateWorkerInfo);
    }
}
