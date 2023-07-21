package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Search;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate template;

	//TODO: Task 2
	/*Mongo query:

	db.listings.distinct(
		"address.country"
	)

	 */
	public List<String> findAllCountries() {
		return template.findDistinct(
				new Query(), 
				"address.country", 
				"listings", 
				String.class);
	}
	
	//TODO: Task 3
	/* Mongo query: 

	db.listings.aggregate([
		{
			$match: {
				$and: [
					{"address.country": "Australia"},
					{"accommodates": 2},
					{"price": {$lte: 150}},
					{"price": {$gte: 1}}
				]
			}
		},
		{
			$project: {
				"address.street": 1,
				"price": 1,
				"images.picture_url": 1
			}
		},
		{
			$sort: {
				"price": -1
			}
		}
	])

	*/

	public List<Document> findAccomodation(Search search) {

		Criteria criteria = new Criteria();

		MatchOperation matchOp = Aggregation.match(
			criteria.andOperator(
				Criteria.where("address.country").is(search.getCountry()),
				Criteria.where("accommodates").is(search.getNumberOfPerson()),
				Criteria.where("price").lte(search.getPriceRangeMax()),
				Criteria.where("price").gte(search.getPriceRangeMin())
			)
		);

		ProjectionOperation projectOp = Aggregation.project(
				"address.street", 
				"price", 
				"images.picture_url"
		);

		SortOperation sortOp = Aggregation.sort(
				Sort.by(
					Direction.DESC, 
					"price"
				)
		);

		Aggregation pipeline = Aggregation.newAggregation(
				matchOp, 
				projectOp, 
				sortOp
		);

		return template.aggregate(
				pipeline, 
				"listings", 
				Document.class)
				.getMappedResults();
	}

	//TODO: Task 4
	/*Mongo query:

	db.listings.aggregate([
		{
			$match: {
				"_id": "5604994"
			}
		},
		{
			$project: {
				"description": 1,
				"address.street": 1,
				"address.suburb": 1,
				"address.country": 1,
				"images.picture_url": 1,
				"amenities": 1
			}
		}
	])

	*/
	public Document findDetailsById(String id) {

		MatchOperation matchOp = Aggregation.match(
				Criteria.where("_id")
				.is(id));

		ProjectionOperation projectOp = Aggregation.project(
				"description",
				"price",
				"address.street",
				"address.suburb",
				"address.country",
				"images.picture_url",
				"amenities"
		);

		Aggregation pipeline = Aggregation.newAggregation(
				matchOp, 
				projectOp
		);

		return template.aggregate(
				pipeline, 
				"listings", 
				Document.class)
				.getMappedResults()
				.get(0);
	}


	//TODO: Task 5


}
