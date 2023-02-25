package org.bugmakers404.hermes.vicroad.dao;

import org.bugmakers404.hermes.vicroad.POJO.BluetoothTravelTimePOJO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BluetoothTravelTimeDAO extends MongoRepository<BluetoothTravelTimePOJO, String> {

}
