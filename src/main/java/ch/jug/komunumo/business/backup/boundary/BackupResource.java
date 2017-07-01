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
package ch.jug.komunumo.business.backup.boundary;

import ch.jug.komunumo.business.backup.control.BackupService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("backup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BackupResource {

    private BackupService service;

    @Inject
    public BackupResource(final BackupService service) {
        this.service = service;
    }

    @GET
    public Response createBackup() {
        return Response.ok(this.service.createBackup()).build();
    }

    @POST
    public Response restoreBackup(final Map<String, JsonArray> jsonMap) throws ClassNotFoundException {
        final Map<String, List<Serializable>> data = new HashMap<>();
        final Gson gson = new Gson();
        for (final String key : jsonMap.keySet()) {
            final List<Serializable> objects = new ArrayList<>();
            final Class clazz = getClass(key);
            final JsonArray jsonArray = jsonMap.get(key);
            for (int i = 0; i < jsonArray.size(); i++) {
                final JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                final Object object = gson.fromJson(jsonObject.toString(), clazz);
                objects.add((Serializable) object);
            }
            data.put(key, objects);
        }
        this.service.restoreBackup(data);
        return Response.ok().build();
    }

    private Class getClass(final String key) throws ClassNotFoundException {
        final String className = String.format("ch.jug.komunumo.business.%s.entity.%s",
                key.toLowerCase(), key);
        return Class.forName(className);
    }

}
