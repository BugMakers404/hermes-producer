package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.linkInfo.LinksWithGeometry;

public interface LinksWithGeometryService {

  LinksWithGeometry saveNewLinksWithGeometry(LinksWithGeometry collectedLinksWithGeometry);

  List<LinksWithGeometry> getAllLinksWithGeometry();

}
