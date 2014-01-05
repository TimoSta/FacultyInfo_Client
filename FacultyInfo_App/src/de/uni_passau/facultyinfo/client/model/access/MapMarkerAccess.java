package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.MapMarkerCategory;

public class MapMarkerAccess {
	private static final String RESSOURCE = "/mapmarker";

	private RestConnection<MapMarkerCategory> restConnection = null;

	private RestConnection<MapMarkerCategory> getRestConnection() {
		if (restConnection == null) {
			restConnection = new RestConnection<MapMarkerCategory>(
					MapMarkerCategory.class);
		}
		return restConnection;
	}

	protected MapMarkerAccess() {
		super();
	}

	/**
	 * Gives a list of available map marker categories containing sub categories
	 * and map markers.
	 * 
	 * @return List<MapMarkerCategory>
	 * @author Timo Staudinger
	 */
	public List<MapMarkerCategory> getMapMarkers() {
		List<MapMarkerCategory> mapMarkerCategories = null;

		mapMarkerCategories = getRestConnection().getRessourceAsList(RESSOURCE);

		if (mapMarkerCategories == null) {
			return null;
		}

		for (MapMarkerCategory mapMarkerCategory : mapMarkerCategories) {
			setSuperCategoryInTree(mapMarkerCategory);
		}

		// TODO: Database operations

		return Collections.unmodifiableList(mapMarkerCategories);
	}

	/**
	 * Gives a list of FAQs that are currently cached locally.
	 * 
	 * @return List<MapMarkerCategory>
	 * @author Timo Staudinger
	 */
	public List<MapMarkerCategory> getMapMarkersFromCache() {
		return Collections.unmodifiableList(new ArrayList<MapMarkerCategory>());
	}

	private void setSuperCategoryInTree(MapMarkerCategory superCategory) {
		if (superCategory != null && superCategory.getMapMarkerCategories() != null) {
			for (MapMarkerCategory mapMarkerCategory : superCategory
					.getMapMarkerCategories()) {
				mapMarkerCategory.setSuperCategory(superCategory);
				setSuperCategoryInTree(mapMarkerCategory);
			}
		}
	}

}
