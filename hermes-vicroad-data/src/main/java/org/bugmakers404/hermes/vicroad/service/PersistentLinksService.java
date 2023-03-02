package org.bugmakers404.hermes.vicroad.service;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.vicroad.dao.bluetoothRawData.LinksDAO;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Links;
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
  public Links saveNewCollections(Links collectedLinks) {
    return linksDAO.save(collectedLinks);
  }

  @Override
  public List<Links> getAllCollections() {
    return linksDAO.findAll();
  }
}
