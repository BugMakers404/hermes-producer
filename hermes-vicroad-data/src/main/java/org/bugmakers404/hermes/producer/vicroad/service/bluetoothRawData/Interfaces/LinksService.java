package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.Link;

public interface LinksService {

  Link saveNewLinks(Link collectedLinks);

  List<Link> getAllLinks();
}
