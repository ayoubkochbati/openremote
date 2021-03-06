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
package org.openremote.app.client.map;

import com.google.gwt.user.client.ui.IsWidget;
import org.openremote.app.client.AppSecurity;
import org.openremote.model.geo.GeoJSON;
import org.openremote.model.geo.GeoJSONPoint;
import org.openremote.model.value.ObjectValue;

import java.util.List;

public interface MapView extends IsWidget {

    interface Presenter {
        void onMapReady();

        AppSecurity getSecurity();
    }

    void setPresenter(Presenter presenter);

    void setAssetViewHistoryToken(String token);

    void initialiseMap(ObjectValue mapOptions);

    boolean isMapInitialised();

    void showDroppedPin(GeoJSON geoFeature);

    void flyTo(GeoJSONPoint point);

    void showInfoItems(List<MapInfoItem> infoItems);
}
