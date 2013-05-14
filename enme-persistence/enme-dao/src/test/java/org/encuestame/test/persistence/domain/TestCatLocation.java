/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.persistence.domain;

import static org.junit.Assert.assertNotNull;

import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.test.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.Status;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test Catalog Location.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since 02/11/2009 16:18:49
 */
@Category(DefaultTest.class)
public class TestCatLocation extends AbstractBase{
    /**
     * Test Catalag Location.
     */
    @Test
    public void testGeoPoint(){
        final GeoPoint catLoc = new GeoPoint();
        catLoc.setLocationStatus(Status.ACTIVE);
        catLoc.setLocationDescription("Managua");
        catLoc.setLocationLatitude(2F);
        catLoc.setLocationLongitude(3F);
        catLoc.setTidtype(createGeoPointType("aldea"));
        catLoc.getProjects().add(createProject("encuestame", "survey", "open source",  createAccount()));
        getGeoPointDao().saveOrUpdate(catLoc);
        assertNotNull(catLoc.getLocateId());
    }

}
