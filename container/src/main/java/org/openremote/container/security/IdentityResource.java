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
package org.openremote.container.security;

import org.openremote.container.web.WebResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("identity")
public class IdentityResource extends WebResource {

    final protected IdentityService identityService;

    public IdentityResource(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GET
    @Path("install/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientInstall getClientInstall(@PathParam("clientId") String clientId) {
        ClientRealm clientRealm =
            identityService.getClientRealm(getRealm(), clientId);
        if (clientRealm == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return clientRealm.clientInstall;
    }
}
