package com.thatz.services;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;

@Api(name="api")
public class ContactServices {

	@ApiMethod(name="say", path = "say", httpMethod = "get")
	public ApiResponse sayHello(@Named("name") String name){
		ApiResponse resp = new ApiResponse();
		resp.responsecode = "SUCCESS";
		resp.responseMessage = "Hello " + name;
		return resp;
	}
	
	@ApiMethod(name="addContact", path = "addContact", httpMethod = "POST")
	public ApiResponse addContact(Contact contact){
		
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(contact);
		ApiResponse resp = new ApiResponse();
		resp.responsecode = "SUCCESS";
		resp.responseMessage = "Contact Added ";
		return resp;
	}
	
	@ApiMethod(name="deleteContact", path = "deleteContact", httpMethod = "GET")
	   public ApiResponse deleteContact(@Named("id") Long id){
        
        PersistenceManager pm = getPersistenceManager();
               Object e = pm.getObjectById(Contact.class, id);
        pm.deletePersistent(e);
       
        ApiResponse resp = new ApiResponse();
        resp.responsecode = "SUCCESS";
        resp.responseMessage = "Contact Deleted ";
        return resp;
   }
	
	@ApiMethod(name="updateContact", path = "updateContact", httpMethod = "GET")
	public void updateEmployeeTitle(@Named("id") Long id,@Named("firstname") String firstname) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	Contact e = pm.getObjectById(Contact.class,id);
	            e.setFirstname(firstname);
	    } finally {
	        pm.close();
	    }
	}
	
	@ApiMethod(name="getContacts", path = "getContacts", httpMethod = "GET")
	public List<Contact> getContact(){
		
		PersistenceManager pm = getPersistenceManager();
	    Query query = pm.newQuery(Contact.class);
	    
	    return (List<Contact>) pm.newQuery(query).execute();
	}
	
	 private static PersistenceManager getPersistenceManager() {
		    return PMF.get().getPersistenceManager();
		  }
}
