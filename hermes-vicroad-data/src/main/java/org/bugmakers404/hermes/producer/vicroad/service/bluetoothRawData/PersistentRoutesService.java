package org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.dao.bluetoothRawData.RoutesDAO;
import org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData.Routes;
import org.bugmakers404.hermes.producer.vicroad.service.bluetoothRawData.Interfaces.RoutesService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class PersistentRoutesService implements RoutesService {

  @NonNull
  private final RoutesDAO routesDAO;

  @Override
  public Routes saveNewRoutes(Routes collectedRoutes) {
    return routesDAO.save(collectedRoutes);
  }

  @Override
  public List<Routes> getAllRoutes() {
    return routesDAO.findAll();
  }
}
