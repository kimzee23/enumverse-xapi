package org.enums.repository;

import org.enums.model.XapiStatement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends MongoRepository<XapiStatement, String> {
}
