package com.bazaarvoice.example.browseproducts;

import com.bazaarvoice.ApiVersion;
import com.bazaarvoice.BazaarRequest;
import com.bazaarvoice.DisplayParams;
import com.bazaarvoice.Equality;
import com.bazaarvoice.OnBazaarResponse;
import com.bazaarvoice.RequestType;

/**
 * BazaarFunctions.java <br>
 * ReviewSubmissionExample<br>
 * 
 * <p>
 * This is a suite of functions that leverage the BazaarvoiceSDK. This class
 * consolidates the usage of these functions for easier understanding of how to
 * use the SDK.
 * 
 * <p>
 * Created on 6/29/12. Copyright (c) 2012 BazaarVoice. All rights reserved.
 * 
 * @author Bazaarvoice Engineering
 * 
 */
public class BazaarFunctions {

	public static final String API_URL = "reviews.apitestcustomer.bazaarvoice.com/bvstaging";
	public static final String API_KEY = "kuy3zj9pr3n7i0wxajrzj04xo";
	public static final ApiVersion API_VERSION = ApiVersion.FIVE_TWO;

	/**
	 * Sends off a product query with the search term provided.
	 * 
	 * @param searchPhrase
	 *            the search term(s)
	 * @param listener
	 *            the response listener
	 */
	public static void runProductSearchQuery(String searchPhrase,
			OnBazaarResponse listener) {
		BazaarRequest request = new BazaarRequest(API_URL, API_KEY, API_VERSION);
		DisplayParams params = new DisplayParams();

		if (!"".equals(searchPhrase.trim())) {
			// Add search terms to params
			String[] tokens = searchPhrase.split("\\s+");
			for (String term : tokens) {
				params.addSearch(term);
			}
		}

		params.addStats("Reviews");

		request.sendDisplayRequest(RequestType.PRODUCTS, params, listener);
	}

	/**
	 * Sends off a reiview query for the given product Id. It sorts the results
	 * by rating from highest to lowest.
	 * 
	 * @param prodId
	 *            the product id
	 * @param listener
	 *            the response listener
	 */
	public static void runReviewQuery(String prodId, OnBazaarResponse listener) {
		BazaarRequest request = new BazaarRequest(API_URL, API_KEY, API_VERSION);
		DisplayParams params = new DisplayParams();

		params.addFilter("ProductId", Equality.EQUAL, prodId);
		params.setLimit(10);

		// false => descending order
		params.addSort("Rating", false);

		OnBazaarResponse response = listener;
		request.sendDisplayRequest(RequestType.REVIEWS, params, response);
	}

}
