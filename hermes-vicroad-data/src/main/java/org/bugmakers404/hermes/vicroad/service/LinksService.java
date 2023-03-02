package org.bugmakers404.hermes.vicroad.service;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Links;

public interface LinksService {

  Links saveNewCollections(Links collectedLinks);

  List<Links> getAllCollections();
}
