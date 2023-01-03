package si.fri.rso.ratingsmicroservice.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Ratings API", version = "v1", contact = @Contact(email = "rso@fri.uni-lj.si"), license = @License(name = "dev"), description = "API for managing ratings."), servers = @Server(url = "http://localhost:8004/"))
@ApplicationPath("/v1")
public class RatingsApplication extends Application {

}
