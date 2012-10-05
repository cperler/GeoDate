package geodate.client.proxy;

import geodate.client.data.CategoryList;
import geodate.client.data.Profile;

import java.io.IOException;

public interface GDServerProxy {
	@Path(methodName = "schemaversion", hasSimpleReturnType = true)	
	int getVersion() throws IOException;
	
	@Path(methodName = "controls")	
	CategoryList getControls() throws IOException;
	
	@Path(methodName = "profile/{id}")	
	Profile getProfileByUserId(@PathParam("id") int userId) throws IOException;
	
	@Path(methodName = "profile/{name}")
	Profile getProfileByUserName(@PathParam("name") String userName) throws IOException;
}