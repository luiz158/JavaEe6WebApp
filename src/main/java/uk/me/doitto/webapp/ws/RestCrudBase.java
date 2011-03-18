package uk.me.doitto.webapp.ws;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.me.doitto.webapp.dao.AbstractEntity;
import uk.me.doitto.webapp.dao.AbstractEntity_;
import uk.me.doitto.webapp.dao.Crud;

/**
 * Specialises IRestCrud for Long PKs, specifies an overlay method to selectively allow editing over REST
 * @author super
 *
 * @param <T> the entity type
 */
@SuppressWarnings("serial")
public abstract class RestCrudBase<T extends AbstractEntity> implements IRestCrud<T, Long> {
	
    public static final String COUNT = "count";
    
    public static final String NAMED = "named";
    
    public static final String BEFORE = "before";
    
    public static final String SINCE = "since";
    
    public static final String DURING = "during";
    
    public static final String NOT_DURING = "notduring";
    
    public static final String SEARCH = "search";
    
    public static final String ID = "id";
    
    public static final String ATTRIBUTE = "attribute";
    
    public static final String FIRST = "first";
    
    public static final String MAX = "max";
    
    public static final String DATE = "date";
    
    public static final String START = "start";
    
    public static final String END = "end";
    
    public static final String QUERY = "query";
    
	/**
	 * Copies selected fields from the returned object to a local object, should be overridden and called from subclasses
	 * 
	 * @param incoming edited entity from client
	 * @param existing destination object for updated fields
	 * @return the updated destination object
	 */
	protected T overlay (final T incoming, final T existing) {
		assert incoming != null;
		assert existing != null;
    	if (incoming.getName() != null) {
    		existing.setName(incoming.getName());
    	}
		return existing;
	}
	
	/**
	 * Subclass provides the underlying crud service
	 * @return the entity-specific service
	 */
	protected abstract Crud<T> getService ();
	
    @GET
    @Path(COUNT)
    @Produces(MediaType.TEXT_PLAIN)
	@Override
	public String count () {
		return String.valueOf(getService().count());
	}

	@PUT
    @Path("{" + ID + "}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public T update (@PathParam(ID) final Long id, final T t) {
		assert id >= 0;
    	assert t != null;
    	return getService().update(overlay(t, getService().find(id)));
    }
    
    @DELETE
    @Path("{" + ID + "}")
    @Override
    public Response delete (@PathParam(ID) final Long id) {
		assert id >= 0;
		getService().remove(id);
        return Response.ok().build();
    }

    @GET
    @Path("{" + ID + "}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public T getById (@PathParam(ID) final Long id) {
		assert id >= 0;
		return getService().find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public List<T> getAll() {
        return getService().findAll();
    }

    @GET
    @Path("{" + FIRST + "}/{" + MAX + "}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public List<T> getRange(@PathParam(FIRST) final int first, @PathParam(MAX) final int max) {
		assert first >= 0;
		assert max >= 0;
		return getService().findAllRange(first, max);
	}

    @PUT
    @Path(NAMED)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public List<T> getByNamedQuery (final String queryName, final Map<String, Object> parameters) {
		return getByNamedQuery(queryName, parameters, 0, 0);
	}

    @PUT
    @Path(NAMED + "/{" + FIRST + "}/{" + MAX + "}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public List<T> getByNamedQuery (final String queryName, Map<String, Object> parameters, @PathParam(FIRST) final int first, @PathParam(MAX) final int max) {
		assert parameters != null;
		assert first >= 0;
		assert max >= 0;
		return getService().findByNamedQueryRange(queryName, parameters, first, max);
	}

    private SingularAttribute<AbstractEntity, Date> getMetaModelDateAttribute (final String attribute) {
    	return AbstractEntity.TimeStamp.valueOf(attribute).getAttribute();
    }
    
    @GET
    @Path(BEFORE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> before (@QueryParam(ATTRIBUTE) final String attribute, @QueryParam(DATE) final long date) {
		assert attribute != null;
		assert date > 0;
    	return getService().before(getMetaModelDateAttribute(attribute), new Date(date));
    }
    
    @GET
    @Path(SINCE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> since (@QueryParam(ATTRIBUTE) final String attribute, @QueryParam(DATE) final long date) {
		assert attribute != null;
		assert date > 0;
    	return getService().since(getMetaModelDateAttribute(attribute), new Date(date));
    }
    
    @GET
    @Path(DURING)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> during (@QueryParam(ATTRIBUTE) final String attribute, @QueryParam(START) final long start, @QueryParam(END) final long end) {
		assert attribute != null;
		assert start > 0;
		assert end > start;
    	return getService().during(getMetaModelDateAttribute(attribute), new Date(start), new Date(end));
    }
    
    @GET
    @Path(NOT_DURING)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> notDuring (@QueryParam(ATTRIBUTE) final String attribute, @QueryParam(START) final long start, @QueryParam(END) final long end) {
		assert attribute != null;
		assert start > 0;
		assert end > start;
    	return getService().notDuring(getMetaModelDateAttribute(attribute), new Date(start), new Date(end));
    }
    
    @GET
    @Path(SEARCH)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public List<T> search (@QueryParam(QUERY) final String query) {
		assert query != null;
		return getService().search(AbstractEntity_.name, query);
	}
}
