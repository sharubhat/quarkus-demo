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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun save(@Valid user: User): Response {
        log.info("In ${Thread.currentThread().name}")
        return user.let {
            try {
                saveUser(user).awaitSuspending()
            } catch (e: Exception) {
                log.error("Failed.", e)
            }
            Response.created(URI("/user/${user.id}")).entity(user).build()
        }
    }

    @WithTransaction
    fun saveUser(user: User): Uni<User> {
        log.info("In ${Thread.currentThread().name}")
        return userRepository.persist(user)
    }

    @DELETE
    @Path("/{id}")
    suspend fun delete(@PathParam("id") id: Int): Response? =
        when(deleteUser(id).awaitSuspending()) {
            true -> Response.ok().status(Response.Status.NO_CONTENT).build()
            false -> Response.ok().status(Response.Status.NOT_FOUND).build()
        }

    @WithTransaction
    fun deleteUser(id: Int) = userRepository.deleteById(id)

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findById(@PathParam("id") id: Int): Response =
            Response.ok(findUserById(id).awaitSuspending()).build()

    @WithTransaction
    fun findUserById(id: Int) = userRepository.findById(id)

    @GET
    @Path("/by-email/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByEmail(@QueryParam("email") email: String): Response =
            Response.ok(findUserByEmail(email).awaitSuspending()).build()

    @GET
    @Path("/by-status/")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findByStatus(@QueryParam("status") status: String): Response =
            Response.ok(findUserByStatus(Status.valueOf(status)).awaitSuspending()).build()

    @WithTransaction
    fun findUserByEmail(email: String) = userRepository.findByEmail(email)

    @WithTransaction
    fun findUserByStatus(status: Status) = userRepository.findByStatus(status)

}
