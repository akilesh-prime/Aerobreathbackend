package com.example.demo.service;

import com.example.demo.entity.SensorStation;
import com.example.demo.exception.SensorStationNotFoundException;
import com.example.demo.repository.SensorStationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StationService {
    private final SensorStationRepository stationRepository;

    @Transactional(readOnly = true)
    public List<SensorStation> getAllStations() {
        return stationRepository.findAll();
    }

    @Transactional
    public SensorStation createStation(SensorStation station) {
        if (station.getDataCapacity() == null) {
            station.setDataCapacity(0);
        }
        return stationRepository.save(station);
    }

    @Transactional
    public SensorStation updateStation(Long id, SensorStation details) {
        SensorStation station = stationRepository.findById(id).orElseThrow(() -> new SensorStationNotFoundException(id));
        station.setStationName(details.getStationName());
        station.setCity(details.getCity());
        station.setRegistrationId(details.getRegistrationId());
        station.setLatitude(details.getLatitude());
        station.setLongitude(details.getLongitude());
        station.setStatus(details.getStatus());
        station.setDataCapacity(details.getDataCapacity());
        return stationRepository.save(station);
    }

    @Transactional
    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new SensorStationNotFoundException(id);
        }
        stationRepository.deleteById(id);
    }
}
