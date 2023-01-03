package si.fri.rso.ratingsmicroservice.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import si.fri.rso.ratingsmicroservice.lib.Rating;
import si.fri.rso.ratingsmicroservice.services.beans.RatingBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(allowOrigin = "*")
public class RatingResource {

        private Logger log = Logger.getLogger(RatingResource.class.getName());

        @Inject
        private RatingBean ratingBean;

        @Context
        protected UriInfo uriInfo;

        @Operation(description = "Get all ratings.", summary = "Get all ratings")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "List of ratings", content = @Content(schema = @Schema(implementation = Rating.class, type = SchemaType.ARRAY)), headers = {
                                        @Header(name = "X-Total-Count", description = "Number of objects in list") }) })
        @GET
        public Response getRatings() {

                List<Rating> ratingsMetadata = ratingBean.getRatingFilter(uriInfo);

                return Response.status(Response.Status.OK).entity(ratingsMetadata).build();
        }

        @Operation(description = "Get status", summary = "Get status")
        @APIResponses({ @APIResponse(responseCode = "200", description = "status") })
        @GET
        @Path("/status")
        public Response getStatus() {
                return Response.status(Response.Status.OK).build();
        }

        @Operation(description = "Get ratings for user.", summary = "Get rating")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "Ratings data for user", content = @Content(schema = @Schema(implementation = Rating.class))),
                        @APIResponse(responseCode = "404", description = "Rating not found.") })
        @GET
        @Path("/{userId}")
        public Response getUserRatings(
                        @Parameter(description = "user ID.", required = true) @PathParam("userId") Integer userId) {

                List<Rating> ratings = ratingBean.getUserRatings(userId);

                if (ratings == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }

                return Response.status(Response.Status.OK).entity(ratings).build();
        }

        @Operation(description = "Add rating.", summary = "Add rating")
        @APIResponses({
                        @APIResponse(responseCode = "201", description = "Raiting successfully added."),
                        @APIResponse(responseCode = "400", description = "Bad request error.")
        })
        @POST
        public Response createRating(
                        @RequestBody(description = "DTO object with rating data.", required = true, content = @Content(schema = @Schema(implementation = Rating.class))) Rating rating) {

                if (rating.getUserId() == null || rating.getRating() == null) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                } else {
                        rating = ratingBean.createRating(rating);
                }

                return Response.status(Response.Status.CREATED).entity(rating).build();

        }

        @Operation(description = "Update rating.", summary = "Update rating")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "rating successfully updated."),
                        @APIResponse(responseCode = "404", description = "Rating not found.")
        })
        @PUT
        @Path("{ratingId}")
        public Response putUser(
                        @Parameter(description = "Rating ID.", required = true) @PathParam("ratingId") Integer ratingId,
                        @RequestBody(description = "DTO object with rating.", required = true, content = @Content(schema = @Schema(implementation = Rating.class))) Rating rating) {

                rating = ratingBean.putRating(ratingId, rating);

                if (rating == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }

                return Response.status(Response.Status.OK).build();

        }

        @Operation(description = "Delete rating.", summary = "Delete rating")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "Rating successfully deleted."),
                        @APIResponse(responseCode = "404", description = "Not found.")
        })
        @DELETE
        @Path("{ratingId}")
        public Response deleteUser(
                        @Parameter(description = "Rating ID.", required = true) @PathParam("ratingId") Integer ratingId) {

                boolean deleted = ratingBean.deleteRating(ratingId);

                if (deleted) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
        }

}
