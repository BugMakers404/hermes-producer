package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.dao.bluetoothRawData.LinksDAO;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.Link;
import org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces.LinksService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class PersistentLinksService implements LinksService {

  @NonNull
  private final LinksDAO linksDAO;

  @Override
  public Link saveNewLinks(Link collectedLinks) {
    return linksDAO.save(collectedLinks);
  }

  @Override
  public List<Link> getAllLinks() {
    return linksDAO.findAll();
  }
}
