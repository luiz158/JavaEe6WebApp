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

/**
 * 
 */
package uk.me.doitto.webapp.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import uk.me.doitto.webapp.util.Globals;

/**
 * @author super
 *
 */
@ManagedBean
@SessionScoped
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TemplateController implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Navigation {

        index("/index" + Globals.PAGE_SUFFIX),
        next("/next" + Globals.PAGE_SUFFIX),
        login("/login" + Globals.PAGE_SUFFIX),
        error("/error" + Globals.PAGE_SUFFIX),
        another(Globals.PAGE_PREFIX + "another" + Globals.PAGE_SUFFIX),
        listusers(Globals.PAGE_PREFIX + "listUsers" + Globals.PAGE_SUFFIX),
        dwr("/dwr"),
        wadl("/resources/application.wadl");
        
        private final String page;

        public String getPage() {
            return page;
        }

        Navigation (final String page) {
            this.page = page;
        }
    }
    
    private boolean debug;

    private String currentPage;

    public String getWorld () {
        return "Hello World! - Current time is: " + new Date();
    }
    
    public String getLoggedInUser () {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            return principal.getName();
        }
        return "No User";
    }

    public Object dwr () {
        currentPage = Navigation.dwr.getPage();
        return currentPage;
    }

    public Object wadl () {
        currentPage = Navigation.wadl.getPage();
        return currentPage;
    }

    public Object logout () {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return Navigation.index.getPage();
    }

    public Object index () {
        currentPage = Navigation.index.getPage();
        return currentPage;
    }

    public Object next () {
        currentPage = Navigation.next.getPage();
        return currentPage;
    }

    public Object another () {
        currentPage = Navigation.another.getPage();
        return currentPage;
    }

//    public Object listUsers () {
//        currentPage = Navigation.listusers.getPage();
//        return currentPage;
//    }

    public Object toggleDebug () {
        debug = !debug;
        return currentPage;
    }

    public boolean isDebug() {
        return debug;
    }
}
