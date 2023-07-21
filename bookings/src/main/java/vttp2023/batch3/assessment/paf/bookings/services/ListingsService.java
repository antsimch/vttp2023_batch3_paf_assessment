package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2023.batch3.assessment.paf.bookings.models.AccDetails;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.Search;
import vttp2023.batch3.assessment.paf.bookings.models.SearchResult;
import vttp2023.batch3.assessment.paf.bookings.models.Utility;
import vttp2023.batch3.assessment.paf.bookings.repositories.BookingRepository;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingsRepo;

	@Autowired
	private BookingRepository bookRepo;

	//TODO: Task 2
	public List<String> findAllCountries() {
		return listingsRepo.findAllCountries();
	}
	
	//TODO: Task 3
	public List<SearchResult> findAccomodations(Search search) {
		List<SearchResult> results = new ArrayList<>();
		List<Document> docs = listingsRepo.findAccomodation(search);

		System.out.println("\n\ndocs in service >>>> " 
				+ docs.toString() 
				+ "\n\n");

		for (Document doc : docs) {
			SearchResult result = Utility.documentToSearchResult(doc);
			results.add(result);
		}
		
		System.out.println("\n\nresults in service >>>> " 
				+ docs.toString() 
				+ "\n\n");

		return results;
	}

	//TODO: Task 4
	public AccDetails findDetailsById(String id) {
		return Utility.documentToAccDetails(
				listingsRepo.findDetailsById(id));
	}

	//TODO: Task 5
	@Transactional
	public Boolean createBooking(Booking booking) {
		
		Integer vacancy = bookRepo.findVacancy(booking.getAccId()).getVacancy();

		if (vacancy - booking.getDuration() < 0) {
			return false;
		}

		booking.setId(UUID.randomUUID().toString().substring(0, 8));
		
		Boolean insertSuccess = bookRepo.insertBooking(booking) > 0;
		Boolean updateSuccess = bookRepo.updateVacancy(booking) > 0;

		System.out.println("\n\ninsert success >>>> " + insertSuccess + "\n\n");
		System.out.println("\n\nupdate success >>>> " + updateSuccess + "\n\n");

		return true;
	}
}
