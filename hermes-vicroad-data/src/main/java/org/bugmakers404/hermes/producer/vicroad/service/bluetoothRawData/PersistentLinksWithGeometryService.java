package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.dao.bluetoothRawData.LinksWithGeometryDAO;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.linkInfo.LinksWithGeometry;
import org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces.LinksWithGeometryService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class PersistentLinksWithGeometryService implements LinksWithGeometryService {

  @NonNull
  private final LinksWithGeometryDAO linksWithGeometryDAO;

  @Override
  public LinksWithGeometry saveNewLinksWithGeometry(LinksWithGeometry collectedLinksWithGeometry) {
    return linksWithGeometryDAO.save(collectedLinksWithGeometry);
  }

  @Override
  public List<LinksWithGeometry> getAllLinksWithGeometry() {
    return linksWithGeometryDAO.findAll();
  }
}
