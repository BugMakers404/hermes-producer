package org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces;

import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Routes;

public interface RoutesService {

  Routes saveNewRoutes(Routes collectedRoutes);

  List<Routes> getAllRoutes();
}
