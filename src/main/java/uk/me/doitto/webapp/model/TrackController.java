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
package uk.me.doitto.webapp.model;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import uk.me.doitto.webapp.beans.TrackService;
import uk.me.doitto.webapp.entity.Track;
import uk.me.doitto.webapp.util.Globals;

/**
 *
 * @author ian
 */
@ManagedBean
@SessionScoped
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TrackController extends ControllerBase<Track> {

    private static final long serialVersionUID = 1L;

    @EJB
    private TrackService service;

    private enum Navigation {
        LIST("listTracks"),
        EDIT("editTrack");
        
        private final String page;

        public String getPage () {
            return Globals.PAGE_PREFIX + page + Globals.PAGE_SUFFIX;
        }

        Navigation (final String page) {
            this.page = page;
        }
    }

    @Override
    public Object list () {
        items = service.findAll();
        pagedItems = listPagedItems();
        return Navigation.LIST.getPage();
    }

    @Override
    public Object save () {
        if (item.isNew()) {
            service.create(item);
        } else {
            service.update(item);
        }
        return list();
    }

    @Override
    public Object create () {
        item = new Track();
        return Navigation.EDIT.getPage();
    }

    @Override
    public Object edit (final Long id) {
        item = service.find(id);
        return Navigation.EDIT.getPage();
    }

    @Override
    public Object delete (final Long id) {
        service.delete(id);
        return list();
    }

    @Override
    public Object firstPage () {
        pagedItems = getFirstPage();
        return Navigation.LIST.getPage();
    }

    @Override
    public Object previousPage () {
        pagedItems = getPreviousPage();
        return Navigation.LIST.getPage();
    }

    @Override
    public Object nextPage () {
        pagedItems = getNextPage();
        return Navigation.LIST.getPage();
    }

    @Override
    public Object lastPage () {
        pagedItems = getLastPage();
        return Navigation.LIST.getPage();
    }

    @Override
    public Object changePageSize () {
        pagedItems = getFirstPage();
        return Navigation.LIST.getPage();
    }
}
