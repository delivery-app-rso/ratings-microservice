package si.fri.rso.ratingsmicroservice.graphql;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.ratingsmicroservice.lib.Rating;
import si.fri.rso.ratingsmicroservice.services.beans.RatingBean;

@GraphQLClass
@ApplicationScoped
public class RatingsMutations {
    @Inject
    private RatingBean ratingBean;

    @GraphQLMutation
    public Rating addRating(@GraphQLArgument(name = "rating") Rating rating) {
        return ratingBean.createRating(rating);
    }

    @GraphQLMutation
    public DeleteResponse deleteImageMetadata(@GraphQLArgument(name = "id") Integer id) {
        return new DeleteResponse(ratingBean.deleteRating(id));
    }
}
