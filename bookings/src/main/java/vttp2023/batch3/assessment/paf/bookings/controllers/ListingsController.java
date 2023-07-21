package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.Search;
import vttp2023.batch3.assessment.paf.bookings.models.SearchResult;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
@RequestMapping
public class ListingsController {

	@Autowired
	private ListingsService listingsService;

	//TODO: Task 2
	@GetMapping(path = "/")
	public ModelAndView getLandingPage(HttpSession session) {
		
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view1");
		mav.addObject("search", new Search());
		mav.addObject("countries", listingsService.findAllCountries());

		return mav;
	}
	
	//TODO: Task 3
	@GetMapping(path = "/search")
	public ModelAndView getAccomodations(@Valid Search search, BindingResult binding, HttpSession session) {

		ModelAndView mav = new ModelAndView();

		if (binding.hasErrors()) {
			mav.setViewName("view1");
			return mav;
		} 

		List<SearchResult> results = listingsService.findAccomodations(search);
		String country = search.getCountry();
		
		mav.setViewName("view2");
		mav.addObject("country", search.getCountry());
		mav.addObject("results", results);
		session.setAttribute("country", country);
		session.setAttribute("results", results);
		return mav;
	}

	@GetMapping(path = "/back")
	public ModelAndView backToResults(HttpSession session) {
		
		ModelAndView mav = new ModelAndView();

		String country = (String) session.getAttribute("country");
		List<SearchResult> results = (List<SearchResult>) session.getAttribute("results");

		mav.setViewName("view2");
		mav.addObject("country", country);
		mav.addObject("results", results);

		return mav;
	}

	//TODO: Task 4
	@GetMapping(path = "/{id}")
	public ModelAndView getDetails(@PathVariable String id) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("view3");
		mav.addObject("details", listingsService.findDetailsById(id));

		Booking booking = new Booking();
		booking.setAccId(id);

		mav.addObject("booking", booking);

		System.out.println(listingsService.findDetailsById(id));

		return mav;
	}

	//TODO: Task 5
	@PostMapping(path = "/booking")
	public ModelAndView createBooking(Booking booking) {

		ModelAndView mav = new ModelAndView();

		System.out.println("\n\n" + booking.getAccId() + "\n\n");

		Boolean createSuccess = listingsService.createBooking(booking);

		if (createSuccess) {
			mav.setViewName("view4");
			mav.addObject("reference", booking.getId());
			return mav;
		}

		mav.setViewName("view3");
		mav.addObject("error", "Booking failed");
		return mav;
	}	
}
