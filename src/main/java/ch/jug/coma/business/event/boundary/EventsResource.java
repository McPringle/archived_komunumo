/*
 * CoMa – Open Source Community Manager
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
package ch.jug.coma.business.event.boundary;

import ch.jug.coma.business.event.control.EventService;
import ch.jug.coma.business.event.entity.Event;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.util.List;

@Path("events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    private final EventService service;

    @Inject
    public EventsResource(final EventService service) {
        this.service = service;
    }

    @POST
    public Response createEvent(@Valid final Event event, @Context final UriInfo info) {
        final String id = this.service.createEvent(event);
        final URI uri = info.getAbsolutePathBuilder().path(File.separator + id).build();
        return Response.created(uri).build();
    }

    @GET
    public List<Event> readAllEvents(@QueryParam("city") final String city) {
        return city != null && !city.isEmpty()
                ? this.service.readEventsForCity(city)
                : this.service.readAllEvents();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEvent(@PathParam("id") final String id) {
        this.service.deleteEvent(id);
        return Response.noContent().build();
    }

}
