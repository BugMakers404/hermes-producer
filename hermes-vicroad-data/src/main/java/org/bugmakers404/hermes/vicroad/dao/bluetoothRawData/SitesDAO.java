package org.bugmakers404.hermes.vicroad.dao.bluetoothRawData;

import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Sites;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SitesDAO extends MongoRepository<Sites, String> {

}
