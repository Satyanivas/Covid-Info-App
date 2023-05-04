package com.nivas.covidtracker.services;

import com.nivas.covidtracker.model.LocationStats;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CoronaVirusDataService {

    private static String virusUrl="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allList=new ArrayList<>();

    public List<LocationStats> getAllList() {
        return allList;
    }

    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<LocationStats> newList=new ArrayList<>();
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(virusUrl)).build();
        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());

        StringReader sr=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(sr);
        for (CSVRecord record : records) {
            LocationStats ls=new LocationStats();
            ls.setState(record.get("Province/State"));
            ls.setCountry(record.get("Country/Region"));
            int now=Integer.parseInt(record.get(record.size()-1));
            int prev=Integer.parseInt(record.get(record.size()-2));
            ls.setLatest(now);
            ls.setDiff(now-prev);

            System.out.println(ls);
            newList.add(ls);
        }
        this.allList=newList;
    }
}
