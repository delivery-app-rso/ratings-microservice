package si.fri.rso.ratingsmicroservice.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.metrics.annotation.Timed;

import si.fri.rso.ratingsmicroservice.lib.Rating;
import si.fri.rso.ratingsmicroservice.models.converters.RatingConverter;
import si.fri.rso.ratingsmicroservice.models.entities.RatingEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

@RequestScoped
public class RatingBean {

    private Logger log = Logger.getLogger(RatingBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Rating> getRating() {

        TypedQuery<RatingEntity> query = em.createNamedQuery(
                "RatingEntity.getAll", RatingEntity.class);

        List<RatingEntity> resultList = query.getResultList();

        return resultList.stream().map(RatingConverter::toDto).collect(Collectors.toList());
    }

    @Timed
    public List<Rating> getRatingFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, RatingEntity.class, queryParameters).stream()
                .map(RatingConverter::toDto).collect(Collectors.toList());
    }

    public List<Rating> getUserRatings(Integer userId) {
        List<RatingEntity> resultsList = (List<RatingEntity>) em
                .createQuery("SELECT r FROM RatingEntity r WHERE r.userId=:userId")
                .setParameter("userId", userId)
                .getResultList();

        return resultsList.stream().map(RatingConverter::toDto).collect(Collectors.toList());
    }

    public Rating createRating(Rating rating) {

        RatingEntity ratingEntity = RatingConverter.toEntity(rating);

        try {
            beginTx();
            em.persist(ratingEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (ratingEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return RatingConverter.toDto(ratingEntity);
    }

    public Rating putRating(Integer id, Rating rating) {

        RatingEntity c = em.find(RatingEntity.class, id);

        if (c == null) {
            return null;
        }

        RatingEntity updatedRaitingEntity = RatingConverter.toEntity(rating);

        try {
            beginTx();
            updatedRaitingEntity.setId(c.getId());
            updatedRaitingEntity = em.merge(updatedRaitingEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return RatingConverter.toDto(updatedRaitingEntity);
    }

    public boolean deleteRating(Integer id) {

        RatingEntity raitingEntity = em.find(RatingEntity.class, id);

        if (raitingEntity != null) {
            try {
                beginTx();
                em.remove(raitingEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
