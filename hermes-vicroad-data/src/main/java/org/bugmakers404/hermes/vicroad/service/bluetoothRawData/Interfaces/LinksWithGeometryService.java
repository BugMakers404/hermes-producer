package org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.LinksWithGeometry;

public interface LinksWithGeometryService {

  LinksWithGeometry saveNewLinksWithGeometry(LinksWithGeometry collectedLinksWithGeometry);

  List<LinksWithGeometry> getAllLinksWithGeometry();

}
