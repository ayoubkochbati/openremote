/*
 * Copyright 2016, OpenRemote Inc.
 *
 * See the CONTRIBUTORS.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.manager.shared.assets;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import jsinterop.annotations.JsType;
import org.jboss.resteasy.annotations.Form;
import org.openremote.manager.shared.http.PATCH;
import org.openremote.manager.shared.http.RequestParams;
import org.openremote.manager.shared.http.SuccessStatusCode;
import org.openremote.manager.shared.ngsi.Entity;
import org.openremote.manager.shared.ngsi.params.EntityListParams;
import org.openremote.manager.shared.ngsi.params.EntityParams;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * http://telefonicaid.github.io/fiware-orion/api/v2/
 */
@Path("assets")
@JsType(isNative = true)
public interface AssetsResource {

    @GET
    @Path("entities")
    @Produces(APPLICATION_JSON)
    @SuccessStatusCode(200)
    @RolesAllowed({"read:assets"})
    JsonArray getEntities(@Form RequestParams requestParams, @Form EntityListParams entityListParams);

    @POST
    @Path("entities")
    @Consumes(APPLICATION_JSON)
    @SuccessStatusCode(201)
    @RolesAllowed({"write:assets"})
    void postEntity(@Form RequestParams requestParams, Entity entity);

    @GET
    @Path("entities/{entityId}")
    @Produces(APPLICATION_JSON)
    @SuccessStatusCode(200)
    @RolesAllowed({"read:assets"})
    JsonObject getEntity(@Form RequestParams requestParams, @PathParam("entityId") String entityId, @Form EntityParams entityParams);

    @DELETE
    @Path("entities/{entityId}")
    @SuccessStatusCode(204)
    @RolesAllowed({"write:assets"})
    void deleteEntity(@Form RequestParams requestParams, @PathParam("entityId") String entityId);

    @PUT
    @Path("entities/{entityId}")
    @Consumes(APPLICATION_JSON)
    @SuccessStatusCode(204)
    @RolesAllowed({"write:assets"})
    void putEntity(@Form RequestParams requestParams, @PathParam("entityId") String entityId, Entity entity);

    @PATCH
    @Path("entities/{entityId}")
    @Consumes(APPLICATION_JSON)
    @SuccessStatusCode(204)
    @RolesAllowed({"write:assets"})
    void patchEntity(@Form RequestParams requestParams, @PathParam("entityId") String entityId, Entity entity);

}
