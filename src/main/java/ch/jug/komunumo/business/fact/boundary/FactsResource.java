/*
 * Komunumo – Open Source Community Manager
 * Copyright (C) 2017 Java User Group Switzerland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.jug.komunumo.business.fact.boundary;

import ch.jug.komunumo.business.fact.control.FactService;
import ch.jug.komunumo.business.fact.entity.Fact;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.util.Optional;

@Path("facts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FactsResource {

    private final FactService service;

    @Inject
    public FactsResource(final FactService service) {
        this.service = service;
    }

    @POST
    public Response createEvent(@Valid final Fact fact, @Context final UriInfo info) {
        final String id = this.service.create(fact).getId();
        final URI uri = info.getAbsolutePathBuilder().path(File.separator + id).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("random")
    public Fact readRandomFact() {
        final Optional<Fact> fact = this.service.readRandom();
        return fact.orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("{id}")
    public Response deleteEvent(@PathParam("id") final String id) {
        this.service.delete(id);
        return Response.noContent().build();
    }

}
