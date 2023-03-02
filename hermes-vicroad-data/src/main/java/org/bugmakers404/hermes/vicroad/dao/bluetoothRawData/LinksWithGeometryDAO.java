package org.bugmakers404.hermes.vicroad.dao.bluetoothRawData;

import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.LinksWithGeometry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinksWithGeometryDAO extends MongoRepository<LinksWithGeometry, String> {

}
