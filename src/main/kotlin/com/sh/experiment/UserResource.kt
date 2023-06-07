package com.sh.experiment

import java.net.URI
import jakarta.transaction.Transactional
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

@Path("/user")
class UserResource {

    private val log: Logger = Logger.getLogger(UserResource::class.java)

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(@Valid user: User): Response =
            user.let {
                it.persist()
                log.info("In ${Thread.currentThread().name}")
                Response.created(URI("/user/${user.id}")).entity(user).build()
            }

    @DELETE
    @Transactional
    @Path("/{id}")
    fun delete(@PathParam("id") id: Long): Response? =
        User.findById(id)?.let {
            it.delete()
            Response.ok().status(Response.Status.NO_CONTENT).build()
        } ?: Response.ok().status(Response.Status.NOT_FOUND).build()

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findById(@PathParam("id") id: String): Response =
            Response.ok(User.findById(id.toLong())).build()

    @GET
    @Path("/by-email/")
    @Produces(MediaType.APPLICATION_JSON)
    fun findByEmail(@QueryParam("email") email: String): Response =
            Response.ok(User.findByEmail(email)).build()

    @GET
    @Path("/by-status/")
    @Produces(MediaType.APPLICATION_JSON)
    fun findByStatus(@QueryParam("status") status: String): Response =
            Response.ok(User.findByStatus(Status.valueOf(status))).build()

}
