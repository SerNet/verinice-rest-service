/** Copyright (c) 2016 Daniel Murygin.
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *  Daniel Murygin - initial API and implementation
 */
package org.verinice.persistence.entities;

import org.verinice.model.Account;

/**
 *
 * @author Daniel Murygin
 */
public class MockBuilder {
    
    private MockBuilder() {
        super();
    }

    public static final CnaTreeElement createAsset(String uuid) {
        CnaTreeElement asset = new CnaTreeElement();
        asset.setUuid(uuid);
        asset.setType("asset");
        return asset;
    }

    public static final Account createTestAccount() {
        return new Account("test", "test");
    }
    
}
