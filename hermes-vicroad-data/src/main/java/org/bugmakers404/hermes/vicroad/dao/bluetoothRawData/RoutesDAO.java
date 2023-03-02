package org.bugmakers404.hermes.vicroad.dao.bluetoothRawData;

import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Routes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoutesDAO extends MongoRepository<Routes, String> {

}
