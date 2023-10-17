package com.sh.zettai.resource

import com.sh.zettai.domain.ListName
import com.sh.zettai.domain.User
import com.sh.zettai.service.ZettaiService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info

@OpenAPIDefinition(info = Info(title = "ToDo API", version = "1.0"))
@Path("/todo")
class ZettaiResource(
    private val zettaiService: ZettaiService
) {
    @GET
    @Path("/{user}/{list}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun showList(@PathParam("user") user: String, @PathParam("list") list: String) =
        Response
            .ok()
            .entity(zettaiService.getToDoList(User(user) to ListName(list)))
            .build()
}
