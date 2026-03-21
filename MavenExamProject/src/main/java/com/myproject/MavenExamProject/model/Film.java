package com.myproject.MavenExamProject.model;

/**
 * The Film class represents a film entity with attributes such as title, genre, description, release year, and rating. 
 * It is a model class in the application, used to store and manage details about individual films.
 * 
 * The class encapsulates its fields by making them private and provides getter methods for controlled access.
 * 
 * @author MartinN
 */
public class Film {
    private String title;   
    private String genre;   
    private String description;  
    private int releaseYear;     
	private String rating;

    /**
     * Constructor for the Film class with parameters for title, genre, description, release year and rating.
     *
     * @param title       The title of the film
     * @param genre       The genre of the film (e.g., Action, Comedy)
     * @param description A brief description of the film
     * @param releaseYear The year the film was released
     * @param rating      The rating of the film (e.g., "PG", "R") indicating its suitability
     */
    public Film(String title, String genre, String description, int releaseYear, String rating) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    /**
     * Gets the title of the film.
     *
     * @return The title of the film
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the genre of the film.
     *
     * @return The genre of the film
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets a brief description of the film.
     *
     * @return The description of the film
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the release year of the film.
     *
     * @return The release year of the film
     */
    public int getReleaseYear() {
        return releaseYear;
    }
    
    /**
     * Gets the rating of the film.
     * 
     * @return The rating of the film
     * 
     */
    public String getRating() {
    	return rating;
    }
}
