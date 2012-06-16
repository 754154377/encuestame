/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.GeoLocationSupport;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.geo.ItemGeoLocationBean;
import org.springframework.stereotype.Service;

/**
 * Location Service.
 * @author Picado, Juan juanATencuestame.org
 * @since May 15, 2010 8:17:15 PM
 */
@Service
public class GeoLocationService extends AbstractBaseService implements GeoLocationSupport{

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link UnitLocationTypeBean}
     * @return locatTypeBean type bean
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createGeoPointType(
            final UnitLocationTypeBean locatTypeBean) throws EnMeExpcetion {
        if (locatTypeBean != null) {
            try {
                final GeoPointType locationTypeDomain = new GeoPointType();
                locationTypeDomain.setLocationTypeDescription(locatTypeBean
                        .getLocTypeDesc());
                locationTypeDomain.setLocationTypeLevel(locatTypeBean
                        .getLevel());
                locationTypeDomain.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
                getGeoPointTypeDao().saveOrUpdate(locationTypeDomain);
                locatTypeBean.setIdLocType(locationTypeDomain
                        .getLocationTypeId());
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return locatTypeBean;
        } else {
            throw new EnMeExpcetion("Cat Location Type is null");
        }
    }

    /**
     * Update Cat Location.
     * @param locationBean locationBean
     * @param username username
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateGeoPoint(final UnitLocationBean locationBean) throws EnMeNoResultsFoundException, EnMeExpcetion{
       final GeoPoint geoPoint =  getLocation(locationBean.getId(), getUserPrincipalUsername());
        if (geoPoint!=null){
            geoPoint.setLocationStatus(Status.valueOf(locationBean.getStatus()));
            geoPoint.setLocationDescription(locationBean.getName());
            geoPoint.setLocationLatitude(locationBean.getLat());
            geoPoint.setLocationLongitude(locationBean.getLng());
            getGeoPointDao().saveOrUpdate(geoPoint);
        }else{
            throw new EnMeExpcetion("geoPoint not found");
        }
   }


    /**
     * Update Cat Location Type.
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    public void updateGeoPointType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion, EnMeNoResultsFoundException{
        final GeoPointType geoPointType = getGeoPointTypeDao().getLocationById(locationTypeBean.getIdLocType());
        if (geoPointType!=null){
            geoPointType.setLocationTypeDescription(locationTypeBean.getLocTypeDesc());
            geoPointType.setLocationTypeLevel(locationTypeBean.getLevel());
            getGeoPointTypeDao().saveOrUpdate(geoPointType);
        }
        else{
            throw new EnMeNoResultsFoundException("location type not found");
        }
    }

    /**
     * Create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public UnitLocationBean createGeoPoint(final UnitLocationBean location) throws EnMeExpcetion{
        if (location != null){
            try{
                final GeoPoint geoPointDomain = new GeoPoint();
                geoPointDomain.setLocationDescription(location.getName());
                geoPointDomain.setLocationStatus(Status.ACTIVE);
                geoPointDomain.setLocationLatitude(location.getLat());
                geoPointDomain.setAccount(getUserAccount(getUserPrincipalUsername()).getAccount());
                geoPointDomain.setLocationLongitude(location.getLng());
                if(location.getTidtype() != null){
                    geoPointDomain.setTidtype(getGeoPointTypeDao().getLocationById(location.getTidtype()));
                }
                getGeoPointDao().saveOrUpdate(geoPointDomain);
                location.setId(geoPointDomain.getLocateId());
                createNotification(NotificationEnum.LOCATION_NEW, location.getName() +" is created.", geoPointDomain.getAccount());
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return location;
        } else {
            throw new EnMeExpcetion("location info not found");
        }
    }

    /**
     * Create Location Folder.
     * @param locationFolder {@link UnitLocationFolder}
     * @return {@link UnitLocationFolder}.
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationFolder createGeoPointFolder(final UnitLocationFolder locationFolder) throws EnMeNoResultsFoundException{
        final GeoPointFolder geoPointFolder = new GeoPointFolder();
        geoPointFolder.setFolderType(GeoPointFolderType.valueOf(locationFolder.getType()));
        geoPointFolder.setFolderName(locationFolder.getName());
        geoPointFolder.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
        getGeoPointDao().saveOrUpdate(geoPointFolder);
        locationFolder.setId(geoPointFolder.getLocationFolderId());
        createNotification(NotificationEnum.LOCATION_FOLDER_NEW, "New Folder "+locationFolder.getName() +" is created.", geoPointFolder.getUsers());
        return locationFolder;
    }

    /**
     * Assign Location to Location Folder.
     * @param location
     */
    public void assignLocationToLocationFolder(final GeoPoint location, final GeoPointFolder geoPointFolder){
            location.setGeoPointFolder(geoPointFolder);
            getGeoPointDao().saveOrUpdate(location);
    }

    /**
     * Retrieve Location Folders by User.
     * @param currentName
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFolders(getUserAccountId(currentUserName)));
    }

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFoldersByLocationFolderId(locationFolderId, getUserAccountId(currentUserName)));
    }

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByFolder(locationFolderId, getUserAccountId(username)));
    }

    /**
     * Retrieve Locations Items by Username
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationBean> retrieveLocationItemsByUsername(final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByUser(getUserAccountId(username)));
    }

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationBean getLocationItem(final Long locationId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertLocationToBean(getLocation(locationId, username));
    }

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertGeoPointFolderDomainToBean(getGeoPointDao()
                                .getLocationFolderByIdAndUserId(folderLocationId, getUserAccountId(username)));
    }

    /**
     * Update Location Map.
     * @param Latitude
     * @param Longitude
     * @param locationId
     * @param username
     * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationMap(final UnitLocationBean locationBean, final Long locationId, final String username) throws EnMeExpcetion, EnMeNoResultsFoundException{
        final GeoPoint location = getLocation(locationId, username);
        log.info("location map location "+location);
        if(location == null){
            throw new EnMeExpcetion("location not found");
        }
        else{
            location.setLocationAccuracy(locationBean.getAccuracy());
            location.setLocationLatitude(locationBean.getLat());
            location.setLocationAddress(locationBean.getAddress());
            location.setLocationCountryCode(locationBean.getCountryCode());
            location.setLocationCountryName(locationBean.getCountryName());
            location.setLocationLongitude(locationBean.getLng());
            getGeoPointDao().saveOrUpdate(location);
            createNotification(NotificationEnum.LOCATION_GMAP_UPDATED, "Updated to "+ locationBean.getAddress(), location.getAccount());
            log.info("location map updated");
        }
    }

    /**
     * Get {@link GeoPoint}.
     * @param locationId location Id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private GeoPoint getLocation(final Long locationId, final String username) throws EnMeNoResultsFoundException{
        return getGeoPointDao().getLocationById(locationId, getUserAccountId(username));
    }

    /**
     *
     * @param locationFolderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private GeoPointFolder getLocationFolder(final Long locationFolderId, final String username) throws EnMeNoResultsFoundException{
        return getGeoPointDao().getLocationFolderByIdAndUserId(locationFolderId, getUserAccountId(username));
    }

    /**
     * Update Location Name.
     * @param locationBean {@link UnitLocationBean}.
     * @param username username logged
     * @throws EnMeExpcetion exception
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationName(final UnitLocationBean locationBean, final String username) throws EnMeNoResultsFoundException{
        final GeoPoint location = getLocation(locationBean.getId(), username);
        if(location == null){
            throw new EnMeNoResultsFoundException("location not found");
        }
        else{
            final String lastName = location.getLocationDescription();
            location.setLocationDescription(locationBean.getName());
            getGeoPointDao().saveOrUpdate(location);
            log.info("location name updated");
            createNotification(NotificationEnum.LOCATION_GMAP_CHANGED_NAME,
                               lastName+" is update to "+locationBean.getName(), location.getAccount());
        }
    }

    /**
     * Update Location Folder.
     * @param locationBean
     * @param username
     * @param typeUpdate
     * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationFolder(final UnitLocationFolder locationFolderBean,
            final String username, final String typeUpdate)
            throws EnMeNoResultsFoundException {
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            if (typeUpdate.equals("name")) {
                log.debug("updating folder name");
                locationFolder.setFolderName(locationFolderBean.getName());
            }
            getGeoPointDao().saveOrUpdate(locationFolder);
            createNotification(NotificationEnum.LOCATION_GMAP_CHANGED_NAME, "Folder name change to "
                                + locationFolderBean.getName(), locationFolder.getUsers());
        }
    }

    /**
     * Create Default Location Item.
     * @param locationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void createDefaultILocationItem(final UnitLocationFolder locationFolderBean, final String username)
           throws EnMeNoResultsFoundException{
        log.info("createDefaultILocationItem");
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        log.info("createDefaultILocationItem locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            final GeoPoint geopoint = new GeoPoint();
            geopoint.setGeoPointFolder(locationFolder);
            geopoint.setAccount(getUserAccount(username).getAccount());
            geopoint.setLocationStatus(Status.ACTIVE);
            geopoint.setLocationDescription("Default Item Name");
            getGeoPointDao().saveOrUpdate(geopoint);
            log.info("Default Location Item Created");
        }
    }

    /**
     * Delete Location Folder.
     * @param unitLocationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void deleteLocationFolder(final UnitLocationFolder unitLocationFolder, final String username) throws EnMeNoResultsFoundException{
        final GeoPointFolder locationFolder = getLocationFolder(unitLocationFolder.getId(), username);
        log.info("deleteLocationFolder locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            //TODO: we need remove items on CASCADE.
            final List<GeoPoint> itemsToDelete = getGeoPointDao()
                                    .getLocationByFolder(locationFolder.getLocationFolderId(), getUserAccountId(username));
            for (GeoPoint geoPoint : itemsToDelete) {
                 getGeoPointDao().delete(geoPoint);
            }
            getGeoPointDao().delete(locationFolder);
            log.info("delete location folder");
        }
    }

    /**
     * Delete Location Item.
     * @param unitLocationBean
     * @param username
     */
    public void deleteLocationItem(final UnitLocationBean unitLocationBean, final String username) throws EnMeNoResultsFoundException{
        final GeoPoint location = getLocation(unitLocationBean.getId(), username);
        if(location == null){
            throw new EnMeNoResultsFoundException("location not found");
        }
        else{
           //TODO: Maybe we have conflict in the future if this location was used on other tables, delete on cascade
           // will not a good option, we need think how to resolve this problem.
           // A possible solution is change status to INACTIVE, and it not show on tree.
           getGeoPointDao().delete(location);
        }
    }
    
    public List<ItemGeoLocationBean> retrieveItemsByGeo(final Integer range, final Integer maxItem, final TypeSearchResult itemType, final float longitude, final Long latitude){
    	/* Definir CONST Radio Tierra
    	 * Convertir en Radianes la Long y Latitud
    	 * 
    	*/
    	return null;
    }
    
    
    
}
