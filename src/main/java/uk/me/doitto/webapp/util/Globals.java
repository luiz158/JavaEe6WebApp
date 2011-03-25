/**
 * Copyright (C) 2010 Ian C. Smith <m4r35n357@gmail.com>
 *
 * This file is part of JavaEE6Webapp.
 *
 *     JavaEE6Webapp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     JavaEE6Webapp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with JavaEE6Webapp.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.me.doitto.webapp.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author super
 */
public class Globals {
	
    public static final Logger LOGGER = Logger.getLogger("uk.me.doitto.webapp");
    
    public static final String PAGE_PREFIX = "/pages/";

    public static final String PAGE_SUFFIX = ".xhtml?faces-redirect=true";

    public enum LogFile {
        HANDLER("logging.out");

        private transient FileHandler fh;

        public FileHandler get () {
            return fh;
        }

        LogFile (String fileName) {
            try {
                fh = new FileHandler(fileName);
                fh.setFormatter(new SimpleFormatter());
            } catch (IOException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Object jndiLookup (final String name) {
    	assert name != null && name.length() > 0;
    	try {   		
        	return new InitialContext().lookup(name);
		} catch (NamingException e) {
			throw new RuntimeException("Lookup failed for: " + name, e);
		}
    }
    
    // Do not instantiate this class!
    private Globals () {    	
    }
}
