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
package ch.jug.coma.business.sponsor.boundary;

import ch.jug.coma.business.sponsor.control.SponsorService;
import ch.jug.coma.business.sponsor.entity.Level;
import ch.jug.coma.business.sponsor.entity.Sponsor;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import java.util.List;

@Path("sponsors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SponsorsResource {

    private final SponsorService service;

    @Inject
    public SponsorsResource(final SponsorService service) {
        this.service = service;
    }

    @POST
    public Response createSponsor(@Valid final Sponsor sponsor, @Context final UriInfo info) {
        final String id = this.service.createSponsor(sponsor);
        final URI uri = info.getAbsolutePathBuilder().path(File.separator + id).build();
        return Response.created(uri).build();
    }

    @GET
    public List<Sponsor> readAllSponsors() {
        return this.service.readAllSponsors();
    }

    @GET
    @Path("{level}")
    public List<Sponsor> readSponsorsWithLevel(@PathParam("level") final String level) {
        return this.service.readSponsorsWithLevel(Level.fromString(level));
    }

}
