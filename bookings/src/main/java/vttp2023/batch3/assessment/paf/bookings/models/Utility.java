package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class Utility {
    
    public static SearchResult documentToSearchResult(Document doc) {
        return new SearchResult(
                doc.getString("_id"),
                doc.getString("street"), 
                doc.getDouble("price"), 
                doc.getString("picture_url"));
    }

    public static AccDetails documentToAccDetails(Document doc) {

        System.out.println("\n\nin utility >>>> " + doc.toString() + "\n\n");

        AccDetails details = new AccDetails();

        details.setId(doc.getString("_id"));
        details.setDescription(doc.getString("description"));
        details.setImage(doc.getString("picture_url"));    
        details.setPrice(doc.getDouble("price"));
        details.setAmenities(doc.get("amenities").toString());    
        
        List<String> address = new LinkedList<>();
        address.add(doc.getString("street"));
        address.add(doc.getString("suburb"));
        address.add(doc.getString("country"));

        details.setAddress(address);

        return details;
    }
}
