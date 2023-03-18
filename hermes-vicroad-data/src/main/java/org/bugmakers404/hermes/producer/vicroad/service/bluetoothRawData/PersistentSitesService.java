package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.dao.bluetoothRawData.SitesDAO;
import org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces.SitesService;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.Sites;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class PersistentSitesService implements SitesService {

  @NonNull
  private final SitesDAO sitesDAO;

  @Override
  public Sites saveNewSites(Sites collectedSites) {
    return sitesDAO.save(collectedSites);
  }

  @Override
  public List<Sites> getAllSites() {
    return sitesDAO.findAll();
  }
}
