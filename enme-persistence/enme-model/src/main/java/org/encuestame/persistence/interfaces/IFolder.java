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
package org.encuestame.persistence.interfaces;

import java.util.Date;

import org.encuestame.persistence.domain.security.Account;

/**
 * Implementation for Folders.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 9, 2010 12:47:26 PM
 * @version $Id:$
 */
public interface IFolder {

    String getFolderName();

    void setFolderName(String folderName);

    Account getUsers();

    void setUsers(Account users);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    void setId(Long id);

    Long getId();

}
