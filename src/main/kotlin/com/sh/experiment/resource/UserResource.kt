package com.sh.experiment.resource

import arrow.core.Either
import com.sh.experiment.entity.User
import com.sh.experiment.service.UserService
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.jboss.logging.Logger
import java.net.URI

const val GREETINGS = "howdy!"

@OpenAPIDefinition(info = Info(title = "User API", version = "1.0"))
@Path("/user")
class UserResource(
    private val userService: UserService
) {

    private val log: Logger = Logger.getLogger(UserResource::class.java)

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = GREETINGS

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun save(user: User): Response =
       when (val x = userService.saveUser(user)) {
            is Either.Right -> Response.created(URI("/user/${user.id}")).entity(user).build()
            is Either.Left -> {
                log.error("Error saving the user : $User", x.value)
                Response.status(Response.Status.CONFLICT).build()
            }
        }

    @DELETE
    @Path("/{id}")
    suspend fun delete(@PathParam("id") id: ObjectId): Response? =
        when (userService.deleteUser(id)) {
            true -> Response.ok().status(Response.Status.NO_CONTENT).build()
            false -> Response.status(Response.Status.NOT_FOUND).build()
        }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findById(@PathParam("id") id: ObjectId): Response =
        userService.findUserById(id)?.let {
            Response.ok(it).build()
        } ?: Response.status(Response.Status.NOT_FOUND).build()

    @GET
    @Path("/by-email/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByEmail(@QueryParam("email") email: String): Response =
        userService.findUserByEmail(email)?.let {
            Response.ok(it).build()
        } ?: Response.status(Response.Status.NOT_FOUND).build()

    @GET
    @Path("/by-status/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByStatus(@QueryParam("status") status: String): Response =
        Response.ok(userService.findUsersByStatus(status)).build()
}
