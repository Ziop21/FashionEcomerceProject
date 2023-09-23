package project.fashionecomerce.backend.fashionecomerceproject.dto.size;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public record SizeQuery(
        String search
) {
    public Query getQuery(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(search));
        return query;
    }
}
