package dvdcollectionapp.model;

import java.util.StringTokenizer;

/**
 * DVD Class
 *
 * @author Josh Hanrahan
 */
public class DVD {

    /**
     * The title of the DVD
     */
    private String title;
    /**
     * The genre of the DVD
     */
    private String genre;
    /**
     * The Australian classifications board rating of the DVD
     */
    private String rating;
    /**
     * The stars/actors of the DVD
     */
    private String stars;
    /**
     * The year of the DVD
     */
    private String year;
    /**
     * A short blurb describing the DVD
     */
    private String blurb;
    /**
     * The image location of the DVD
     */
    private String imageURL;

    /**
     * DVD constructor.
     */
    public DVD() {
        setDVD(new DVD("", "", "", "", "", "", ""));
    }

    /**
     * 2nd DVD constructor
     *
     * @param title
     * @param genre
     * @param rating
     * @param stars
     * @param year
     * @param blurb
     * @param imageURL
     */
    public DVD(String title, String genre, String rating, String stars, String year, String blurb, String imageURL) {

        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.stars = stars;
        this.year = year;
        this.blurb = blurb;
        this.imageURL = imageURL;
    }

    /**
     * toString method which outputs all fields
     */
    public String toString() {
        return "DVD{" + "title=" + title + ", genre=" + genre + ", rating=" + rating + ", stars=" + stars + ", year=" + year + ", blurb=" + blurb + ", image URL=" + imageURL + '}';
    }

    /**
     * toCSV method which outputs all fields but seperates with commas
     *
     * @return a string of what all the fields are
     */
    public String toCSV() {
        return title + "," + genre + "," + rating + "," + stars + "," + year + "," + blurb + "," + imageURL;
    }

    /**
     * creates a DVD object
     *
     * @param line refers to the line of text the data file is reading
     * @return the new dvd object
     */
    public static DVD createDVD(String line) {
        StringTokenizer theTokens = new StringTokenizer(line, ",");
        DVD newDVD = new DVD();
        if (theTokens.countTokens() == 7) {
            newDVD.setTitle(theTokens.nextToken().trim());
            newDVD.setGenre(theTokens.nextToken().trim());
            newDVD.setRating(theTokens.nextToken().trim());
            newDVD.setStars(theTokens.nextToken().trim());
            newDVD.setYear(theTokens.nextToken().trim());
            newDVD.setBlurb(theTokens.nextToken().trim());
            newDVD.setImageURL(theTokens.nextToken().trim());
            return newDVD;
        } else {
            return null;
        }
    }

    /**
     * gets the image url
     *
     * @return the URL of the image file
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * sets the image url
     *
     * @param imageURL this is the file location of the image
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * gets the DVD's title
     *
     * @return the name of the DVD
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the dvd
     *
     * @param title this is the title of the DVD
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the DVD's genre type
     *
     * @return the genre of the DVD
     */
    public String getGenre() {
        return genre;
    }

    /**
     * sets the genre type of the dvd
     *
     * @param genre this is the genre of the DVD
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * gets the DVD's rating
     *
     * @return the rating of the DVD
     */
    public String getRating() {
        return rating;
    }

    /**
     * sets the rating of the dvd
     *
     * @param rating this is the rating of the DVD
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * gets the DVD's stars or actors
     *
     * @return the actors of the movie
     */
    public String getStars() {
        return stars;
    }

    /**
     * sets the stars of the dvd
     *
     * @param stars this is the stars of the DVD
     */
    public void setStars(String stars) {
        this.stars = stars;
    }

    /**
     * gets the DVD's year
     *
     * @return the year of the DVD
     */
    public String getYear() {
        return year;
    }

    /**
     * sets the year of the dvd
     *
     * @param year this is the year of the DVD
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * gets the DVD's blurb (short description)
     *
     * @return the blurb of the DVD
     */
    public String getBlurb() {
        return blurb;
    }

    /**
     * sets the blurb of the dvd
     *
     * @param blurb this is the blurb of the DVD
     */
    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    /**
     * sets the DVD
     *
     * @param d this is a DVD object
     */
    public void setDVD(DVD d) {
        this.title = d.getTitle();
        this.genre = d.getGenre();
        this.rating = d.getRating();
        this.stars = d.getStars();
        this.year = d.getYear();
        this.blurb = d.getBlurb();
        this.imageURL = d.getImageURL();
    }

}
