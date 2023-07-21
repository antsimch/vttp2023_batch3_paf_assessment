package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.AccDetails;
import vttp2023.batch3.assessment.paf.bookings.models.Search;
import vttp2023.batch3.assessment.paf.bookings.models.SearchResult;
import vttp2023.batch3.assessment.paf.bookings.models.Utility;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository listingsRepo;

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


}
