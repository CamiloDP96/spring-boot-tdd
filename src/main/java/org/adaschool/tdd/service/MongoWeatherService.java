package org.adaschool.tdd.service;

import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.exception.WeatherReportNotFoundException;
import org.adaschool.tdd.repository.WeatherReportRepository;
import org.adaschool.tdd.repository.document.GeoLocation;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MongoWeatherService
    implements WeatherService
{

    private final WeatherReportRepository repository;

    public MongoWeatherService( @Autowired WeatherReportRepository repository )
    {
        this.repository = repository;
    }

    @Override
    public WeatherReport report( WeatherReportDto weatherReportDto )
    {
        GeoLocation geoLocation = weatherReportDto.getGeoLocation();
        double temperature = weatherReportDto.getTemperature();
        double humidity = weatherReportDto.getHumidity();
        String reporter = weatherReportDto.getReporter();
        Date created = weatherReportDto.getCreated();

        // Create a WeatherReport object from the provided WeatherReportDto
        WeatherReport newWeatherReport = new WeatherReport(geoLocation, temperature, humidity, reporter, created);

        // Save the new WeatherReport using the repository
        return repository.save(newWeatherReport);
    }

    @Override
    public WeatherReport findById( String id )
    {
        return repository.findById(id)
                .orElseThrow(() -> new WeatherReportNotFoundException("Weather report not found for ID: " + id));
    }

    @Override
    public List<WeatherReport> findNearLocation( GeoLocation geoLocation, float distanceRangeInMeters )
    {
        return null;
    }

    @Override
    public List<WeatherReport> findWeatherReportsByName( String reporter )
    {
        return null;
    }
}
