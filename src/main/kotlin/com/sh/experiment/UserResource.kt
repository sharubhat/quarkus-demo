package com.sh.experiment

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.coroutines.awaitSuspending
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
import java.net.URI

@Path("/user")
class UserResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun save(@Valid user: User): Response =
            user.let {
                saveUser(user).awaitSuspending()
                Response.created(URI("/user/${user.id}")).entity(user).build()
            }

    @WithTransaction
    fun saveUser(user: User) = user.persist<User>()

    @DELETE
    @Path("/{id}")
    suspend fun delete(@PathParam("id") id: Int): Response? =
        when(deleteUser(id).awaitSuspending()) {
            true -> Response.ok().status(Response.Status.NO_CONTENT).build()
            false -> Response.ok().status(Response.Status.NOT_FOUND).build()
        }

    @WithTransaction
    fun deleteUser(id: Int) = User.deleteById(id)

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findById(@PathParam("id") id: Int): Response =
            Response.ok(findUserById(id).awaitSuspending()).build()

    @WithTransaction
    fun findUserById(id: Int) = User.findById(id.toLong())

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
    fun findUserByEmail(email: String) = User.findByEmail(email)

    @WithTransaction
    fun findUserByStatus(status: Status) = User.findByStatus(status)

}
