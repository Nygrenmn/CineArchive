package com.myproject.MavenExamProject.model;

import java.io.Serializable;

/**
 * Model class representing a movie review
 * 
 * @author Vendela
 */
public class Review implements Serializable {
	private String username;
	private String movieTitle;
	private String comment;

	/**
	 * method constructor for the initialization of new review
	 * 
	 * @param username   the username of the user making the review
	 * @param movieTitle the title of the movie the review is about
	 * @param comment    the comment, which is the review itself
	 */
	public Review(String username, String movieTitle, String comment) {
		this.username = username;
		this.movieTitle = movieTitle;
		this.comment = comment;
	}

	/**
	 * Returns the username
	 * 
	 * @return the username
	 * @author Vendela
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the movie title
	 * 
	 * @return the movie title
	 * @author Vendela
	 */
	public String getMovieTitle() {
		return movieTitle;
	}

	/**
	 * Returns the review comment
	 * 
	 * @return the review comment
	 * @author Vendela
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the username
	 * 
	 * @param username
	 * @author Vendela
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the movie title
	 * 
	 * @param movieTitle
	 * @author Vendela
	 */
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	/**
	 * Sets the comment of the review
	 * 
	 * @param comment
	 * @author Vendela
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
