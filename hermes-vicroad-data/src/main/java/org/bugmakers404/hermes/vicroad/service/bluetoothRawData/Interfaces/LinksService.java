package org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Links;

public interface LinksService {

  Links saveNewLinks(Links collectedLinks);

  List<Links> getAllLinks();
}
