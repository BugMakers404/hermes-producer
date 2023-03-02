package org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Sites;

public interface SitesService {

  Sites saveNewSites(Sites collectedSites);

  List<Sites> getAllSites();
}
