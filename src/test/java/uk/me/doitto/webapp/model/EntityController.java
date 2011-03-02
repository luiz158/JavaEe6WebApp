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
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import uk.me.doitto.webapp.beans.EntityService;
import uk.me.doitto.webapp.dao.SimpleEntity;

/**
 *
 * @author ian
 */
@ManagedBean
@SessionScoped
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EntityController extends ControllerBase<SimpleEntity> {

	private static final long serialVersionUID = 1L;

    @EJB
    private EntityService service;

    /**
	 * @param service the service to set
	 */
	public void setService (final EntityService service) {
		this.service = service;
	}

	public enum Navigation {
        LIST("/pages/listEntities"),
        EDIT("/pages/editEntity");

        private final String page;

        public String getPage () {
            return page + ".xhtml?faces-redirect=true";
        }

        Navigation (final String page) {
            this.page = page;
        }
    }

    @Override
	public Object jsfList () {
        items = service.findAll();
        return Navigation.LIST.getPage();
    }

    @Override
	public Object jsfSave () {
        if (item.isNew()) {
        	service.create(item);
        } else {
        	service.update(item);
        }
        return jsfList();
    }

    @Override
	public Object jsfCreate () {
    	item = new SimpleEntity();
        return Navigation.EDIT.getPage();
    }

    @Override
	public Object jsfEdit (final Long id) {
    	item = service.find(id);
        return Navigation.EDIT.getPage();
    }

    @Override
	public Object jsfDelete (final Long id) {
    	service.delete(id);
        return jsfList();
    }

	@Override
	public Object firstPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object previousPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object nextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lastPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object changePageSize() {
		// TODO Auto-generated method stub
		return null;
	}
}
