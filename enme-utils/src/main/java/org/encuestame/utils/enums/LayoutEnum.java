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
package org.encuestame.utils.enums;

/**
 * Layout Enumeration
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum LayoutEnum {

    /** Three columns. **/
    AAA_COLUMNS,

    /** Two Blocks. **/
    BB_BLOCK,

    /** One Block. **/
    B_BLOCK,

    /** One Column and One Block. **/
    AB_COLUMN_BLOCK,

    /** One Block and One column. **/
    BA_BLOCK_COLUMN,

    LayoutEnum(){

    };

    /**
     * To String.
     */
    public String toString() {
        String layout = "";
        if (this == AAA_COLUMNS) { layout = "AAA"; }
        else if (this == BB_BLOCK) { layout = "BB"; }
        else if (this == B_BLOCK) { layout = "B"; }
        else if (this == AB_COLUMN_BLOCK) { layout = "AB"; }
        else if (this == BA_BLOCK_COLUMN) { layout = "BA"; }
        return layout;
    }

    /**
     *
     * @param layout
     * @return
     */
    public static LayoutEnum getDashboardLayout(final String layout) {
        if (null == layout) { return null; }
        else if (layout.equalsIgnoreCase("AAA")) { return AAA_COLUMNS; }
        else if (layout.equalsIgnoreCase("BB")) { return BB_BLOCK; }
        else if (layout.equalsIgnoreCase("B")) { return B_BLOCK; }
        else if (layout.equalsIgnoreCase("AB")) { return AB_COLUMN_BLOCK; }
        else if (layout.equalsIgnoreCase("BA")) { return BA_BLOCK_COLUMN; }
        else return null;
    }
}
