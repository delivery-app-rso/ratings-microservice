package si.fri.rso.ratingsmicroservice.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ratings")
@NamedQueries(value = {
        @NamedQuery(name = "RatingEntity.getAll", query = "SELECT im FROM RatingEntity im")
})
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "deliveryId")
    private Integer deliveryId;

    @Column(name = "rating")
    private Integer rating;

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}