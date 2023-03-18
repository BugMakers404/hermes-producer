package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.Sites;

public interface SitesService {

  Sites saveNewSites(Sites collectedSites);

  List<Sites> getAllSites();
}
