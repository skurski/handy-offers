package pl.edu.agh.handy.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.handy.offers.model.Opinion;
import pl.edu.agh.handy.offers.model.Rating;
import pl.edu.agh.handy.offers.model.User;
import pl.edu.agh.handy.offers.repository.OpinionRepository;
import pl.edu.agh.handy.offers.repository.RatingRepository;
import pl.edu.agh.handy.offers.repository.UserRepository;

import java.util.List;

/**
 * Created by psk on 16.06.17.
 */
@Service
public class RatingService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OpinionRepository opinionRepository;

    @Transactional
    public void addOpinion(String userId, String comment, String stars) {
        int numberOfStars = stars == null ? 0 : Integer.valueOf(stars);
        User user = userRepository.findOne(Long.valueOf(userId));
        Rating rating = ratingRepository.findByUser(user);
        Opinion opinion = new Opinion();
        opinion.setValue(comment);
        opinion.setStars(numberOfStars);
        opinionRepository.save(opinion);

        rating.getOpinions().add(opinion);
        rating.setPercent(calculateTrust(rating.getOpinions()));
        ratingRepository.save(rating);
    }

    private int calculateTrust(List<Opinion> opinions) {
        if (opinions.isEmpty()) {
            return 100; // initial trust is 100
        }

        float percent = 0;
        for (Opinion opinion : opinions) {
            percent += opinion.getStars();
        }
        return (int) ((percent / opinions.size()) * 10);
    }
}
