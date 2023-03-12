package org.bugmakers404.hermes.vicroad.dao.bluetoothRawData;

import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link.Link;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinksDAO extends MongoRepository<Link, String> {

}
