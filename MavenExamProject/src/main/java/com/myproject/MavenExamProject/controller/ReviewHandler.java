package com.myproject.MavenExamProject.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.myproject.MavenExamProject.model.MyList;
import com.myproject.MavenExamProject.model.Review;
import com.myproject.MavenExamProject.view.ReviewView;

/**
 * Controller class managing the interaction between the ReviewView and review
 * data
 * 
 * @author Vendela
 * @author Marius
 */
public class ReviewHandler {
	private ReviewView reviewView;

	/**
	 * constructor method for the ReviewHandler
	 * 
	 * @param view
	 * @author Vendela
	 */
	public ReviewHandler(ReviewView view) {
		this.reviewView = view;

		reviewView.fillOutReviewArea(formatReviews());
		setupAddReviewButtonListener();

	}

	/**
	 * method for handling what happens when the "create new review"-button in the
	 * view class is clicked
	 * 
	 * @author vendela
	 */
	private void setupAddReviewButtonListener() {
		reviewView.getAddReviewButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyList.getFilmTitles().isEmpty()) {
					JOptionPane.showMessageDialog(reviewView, "Add movies to your list to write a review");
				} else {
					reviewView.openNewReviewWindow();
					loadFilmTitlesIntoView();
					setupCancelButtonListener();
					setupSaveButtonListener();
				}
			}
		});
	}

	/**
	 * method for handling what happens when the "save"-button in the review form is
	 * clicked
	 * 
	 * @author Marius
	 * @author Vendela
	 */
	private void setupSaveButtonListener() {
		reviewView.getSaveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reviewView.getUsernameInput().isEmpty() || reviewView.getFilmTitleInput().isEmpty()
						|| reviewView.getReviewTextInput().isEmpty()) {
					JOptionPane.showMessageDialog(null, "All fields must be filled out.");
					return;
				}

				// Create a new Review object
				Review newReview = new Review(reviewView.getUsernameInput(), reviewView.getFilmTitleInput(),
						reviewView.getReviewTextInput());

				// Add the new review to the file
				addReview(newReview);

				reviewView.closeNewReviewWindow();
				reviewView.fillOutReviewArea(formatReviews());
				JOptionPane.showMessageDialog(null, "Review saved successfully!");

			}
		});
	}

	/**
	 * Method for what happens when a user clicks the cancel button in the create
	 * review form
	 * 
	 * @author Vendela
	 */
	private void setupCancelButtonListener() {
		reviewView.getCancelButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reviewView.closeNewReviewWindow();
			}
		});
	}

	private void loadFilmTitlesIntoView() {
		ArrayList<String> filmTitles = MyList.getFilmTitles();
		reviewView.setFilmTitlesForComboBox(filmTitles);
	}

	/**
	 * method for adding a new review object to an ArrayList containing all review
	 * objects. Updates the reviews.txt file
	 * 
	 * @param newReview the review that should be added to the txt file
	 * @author Vendela
	 */
	public void addReview(Review newReview) {
		ArrayList<Review> reviewList = listOfReviews(); // Load existing reviews
		reviewList.add(newReview); // Add new review to the list

		try {
			FileOutputStream fout = new FileOutputStream("reviews.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(reviewList); // Save the updated list
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("Error saving the review: " + e.getMessage());
		}
	}

	/**
	 * method for generating dummy-reviews when the application is launched for the
	 * first time
	 * 
	 * @author Vendela
	 */
	public static void generateDummyReviews() {
		Review rev1 = new Review("HanneP", "Academy Dinosaur",
				"Academy Dinosaur offers a surprising blend of feminist drama and mad science, set against the rugged beauty of the Canadian Rockies. The clash between a strong-willed protagonist and a deranged scientist keeps the story engaging, even as a teacher becomes an unusual antagonist. Though it occasionally stumbles with pacing, the film's emotional depth and striking visuals make it a memorable watch. It's thought-provoking, albeit strange.");
		Review rev2 = new Review("HaraldB", "Ace Goldfinger",
				"Despite an intriguing premise of a database administrator and an explorer searching for a car in ancient China, \"Ace Goldfinger\" is a muddled mess. The mix of historical adventure and tech jargon feels disjointed, and the quest itself is underwhelming. The film lacks chemistry between its leads and ultimately fails to deliver on its promise. It’s a confusing experience best left unwatched.");
		Review rev3 = new Review("Movielover12", " Adaptation Holes",
				"\"Adaptation Holes\" takes absurdity to a new level with a lumberjack and a car trying to sink another lumberjack in a balloon factory. While the film embraces its oddball nature, the storyline feels too scattered to be enjoyable. The bizarre concept struggles to find a balance, leaving viewers more puzzled than entertained. Ultimately, the movie’s surreal charm wears thin fast.");
		Review rev4 = new Review("Jimbo", "Affair Prejudice",
				"\"Affair Prejudice\" blends whimsy and chaos in a documentary-style tale of a frisbee and a lumberjack chasing a monkey in a shark tank. The strange premise is entertaining at first, but quickly loses momentum. While the film boasts some amusing moments, it never fully capitalizes on its potential. It's an odd watch that doesn't quite deliver on the zaniness it promises.");
		// Create a list of reviews
		ArrayList<Review> reviewList = new ArrayList<>();
		reviewList.add(rev1);
		reviewList.add(rev2);
		reviewList.add(rev3);
		reviewList.add(rev4);
		try {
			FileOutputStream fout = new FileOutputStream("reviews.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(reviewList);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * method for retrieving an ArrayList of Review objects saved from the reviews
	 * text file
	 * 
	 * @return ArrayList containing Review objects
	 * @author Vendela
	 */
	public ArrayList<Review> listOfReviews() {
		ArrayList<Review> reviewList = new ArrayList();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("reviews.txt"));
			reviewList = (ArrayList<Review>) in.readObject();

			// closing the stream
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return reviewList;
	}

	/**
	 * method that takes in an arraylist of all current reviews and formats them as
	 * a String for the JTextArea
	 * 
	 * @return String containing the previously created reviews about films
	 * @author Vendela
	 */
	public String formatReviews() {
		StringBuilder reviewsContent = new StringBuilder();
		try {
			ArrayList<Review> reviewList = this.listOfReviews();
			for (int i = reviewList.size() - 1; i >= 0; i--) {
				reviewsContent.append("Username: ").append(reviewList.get(i).getUsername()).append("\n")
						.append("Movie Title: ").append(reviewList.get(i).getMovieTitle()).append("\n")
						.append("Comment: ").append(reviewList.get(i).getComment()).append("\n")
						.append("--------------------------------------------\n");
			}
			// closing the stream
		} catch (Exception e) {
			System.out.println(e);
		}
		return reviewsContent.toString();
	}
}
