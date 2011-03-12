package uk.me.doitto.webapp.ws;

import java.util.Date;
import java.util.List;

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
 * Specializes IRestCrud for Long PKs, specifies an overlay method to selectively allow editing over REST
 * @author super
 *
 * @param <T> the entity type
 */
@SuppressWarnings("serial")
public abstract class RestCrudBase<T extends AbstractEntity> implements IRestCrud<T, Long> {
	
	private enum TimeStamp {
		created(AbstractEntity_.created),
		modified(AbstractEntity_.modified),
		accessed(AbstractEntity_.accessed);
		
		private final SingularAttribute<AbstractEntity, Date> attribute;

		TimeStamp (final SingularAttribute<AbstractEntity, Date> attribute) {
			this.attribute = attribute;
		}
		
		public SingularAttribute<AbstractEntity, Date> getAttribute() {
			return attribute;
		}
	}
	
    public static final String COUNT = "count";
    
    public static final String BEFORE = "before";
    
    public static final String SINCE = "since";
    
    public static final String DURING = "during";
    
    public static final String NOT_DURING = "notduring";
    
	/**
	 * Copies selected fields from the returned object to a local object
	 * 
	 * @param incoming edited entity from client
	 * @param existing destination object for updated fields
	 * @return the updated destination object
	 */
	protected abstract T overlay (final T incoming, final T existing);
	
	/**
	 * Subclass provides the service
	 * @return the entity-specific service
	 */
	protected abstract Crud<T> getService ();
	
	@PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public T update (@PathParam("id") final Long id, final T t) {
		assert id >= 0;
    	assert t != null;
    	return getService().update(overlay(t, getService().find(id)));
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public List<T> getAll() {
        return getService().findAll();
    }

    @GET
    @Path("{first}/{max}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public List<T> getRange(@PathParam("first") final int first, @PathParam("max") final int max) {
		assert first >= 0;
		assert max >= 0;
		return getService().findAll(first, max);
	}

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public T getById (@PathParam("id") final Long id) {
		assert id >= 0;
		return getService().find(id);
    }

    @DELETE
    @Path("{id}")
    @Override
    public Response delete (@PathParam("id") final Long id) {
		assert id >= 0;
		getService().delete(id);
        return Response.ok().build();
    }

    @GET
    @Path(BEFORE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> before (@QueryParam("attribute") final String attribute, @QueryParam("date") final long date) {
    	return getService().before(TimeStamp.valueOf(attribute).getAttribute(), new Date(date));
    }
    
    @GET
    @Path(SINCE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> since (@QueryParam("attribute") final String attribute, @QueryParam("date") final long date) {
    	return getService().since(TimeStamp.valueOf(attribute).getAttribute(), new Date(date));
    }
    
    @GET
    @Path(DURING)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> during (@QueryParam("attribute") final String attribute, @QueryParam("date1") final long date1, @QueryParam("date2") final long date2) {
    	return getService().during(TimeStamp.valueOf(attribute).getAttribute(), new Date(date1), new Date(date2));
    }
    
    @GET
    @Path(NOT_DURING)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
	public List<T> notDuring (@QueryParam("attribute") final String attribute, @QueryParam("date1") final long date1, @QueryParam("date2") final long date2) {
    	return getService().notDuring(TimeStamp.valueOf(attribute).getAttribute(), new Date(date1), new Date(date2));
    }
    
    @GET
    @Path(COUNT)
    @Produces(MediaType.TEXT_PLAIN)
	@Override
	public String count () {
		return String.valueOf(getService().count());
	}
}
