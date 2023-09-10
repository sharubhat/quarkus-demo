package com.sh.experiment

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.inject.Inject
import jakarta.validation.Valid
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
import org.jboss.logging.Logger
import java.net.URI

@Path("/user")
class UserResource {

    private val log: Logger = Logger.getLogger(UserResource::class.java)

    @Inject
    lateinit var userRepository: UserRepository

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "howdy!"

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun save(@Valid user: User): Response =
        try {
            userRepository.saveUser(user).awaitSuspending()
            Response.created(URI("/user/${user.id}")).entity(user).build()
        } catch (e: Exception) {
            log.error("Failed.", e)
            Response.status(Response.Status.CONFLICT).build()
        }

    @DELETE
    @Path("/{id}")
    suspend fun delete(@PathParam("id") id: Int): Response? =
        when (userRepository.deleteUser(id).awaitSuspending()) {
            true -> Response.ok().status(Response.Status.NO_CONTENT).build()
            false -> Response.status(Response.Status.NOT_FOUND).build()
        }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findById(@PathParam("id") id: Int): Response =
        userRepository.findUserById(id)?.awaitSuspending()?.let {
            Response.ok(it).build()
        } ?: Response.status(Response.Status.NOT_FOUND).build()

    @GET
    @Path("/by-email/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByEmail(@QueryParam("email") email: String): Response =
        userRepository.findUserByEmail(email).awaitSuspending()?.let {
            Response.ok(it).build()
        } ?: Response.status(Response.Status.NOT_FOUND).build()

    @GET
    @Path("/by-status/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByStatus(@QueryParam("status") status: String): Response =
        Response.ok(userRepository.findUsersByStatus(Status.valueOf(status)).awaitSuspending()).build()
}
