/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza.
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
 *     Ruth Motza <rm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package org.verinice.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class VeriniceClassicPasswordEncoder implements PasswordEncoder {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.crypto.password.PasswordEncoder#encode(java.
     * lang.CharSequence)
     */
    @Override
    public String encode(CharSequence password) {
        // TODO rmotza Auto-generated method stub
        return encoder.encode(password);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.crypto.password.PasswordEncoder#matches(java
     * .lang.CharSequence, java.lang.String)
     */
    @Override
    public boolean matches(CharSequence arg0, String arg1) {
        // TODO rmotza Auto-generated method stub
        return encoder.matches(arg0, arg1);
    }

}
