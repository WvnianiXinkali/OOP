package Quiz.src.main.java.HelperMethods;

public class CreateLittleStarRatings {

    public static String generateRatingStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("&#9733; ");
        }
        return stars.toString();
    }
}
