package org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link.Link;

public interface LinksService {

  Link saveNewLinks(Link collectedLinks);

  List<Link> getAllLinks();
}
