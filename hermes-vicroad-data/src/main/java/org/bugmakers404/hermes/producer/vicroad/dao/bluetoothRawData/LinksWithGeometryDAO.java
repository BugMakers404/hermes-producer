package org.bugmakers404.hermes.producer.vicroad.dao.bluetoothRawData;

import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.linkInfo.LinksWithGeometry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinksWithGeometryDAO extends MongoRepository<LinksWithGeometry, String> {

}
