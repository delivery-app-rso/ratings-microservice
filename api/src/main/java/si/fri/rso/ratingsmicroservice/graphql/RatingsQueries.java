package si.fri.rso.ratingsmicroservice.graphql;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.ratingsmicroservice.lib.Rating;
import si.fri.rso.ratingsmicroservice.services.beans.RatingBean;

@GraphQLClass
@ApplicationScoped
public class RatingsQueries {
    @Inject
    private RatingBean ratingBean;

    @GraphQLQuery
    public PaginationWrapper<Rating> getAllRatings(@GraphQLArgument(name = "pagination") Pagination pagination,
            @GraphQLArgument(name = "sort") Sort sort,
            @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(ratingBean.getRating(), pagination, sort, filter);
    }

    @GraphQLQuery
    public List<Rating> getUserRatings(@GraphQLArgument(name = "userId") Integer userId) {
        return ratingBean.getUserRatings(userId);
    }

    @GraphQLQuery
    public List<Rating> fallbackTest() {
        return ratingBean.fallbackTest();
    }
}
