package pl.edu.agh.handy.offers.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.handy.offers.common.TimePeriods;
import pl.edu.agh.handy.offers.dto.AppointmentDto;
import pl.edu.agh.handy.offers.dto.OfferDto;
import pl.edu.agh.handy.offers.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class OfferConverter implements ModelConverter<OfferDto, Offer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferConverter.class);

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Autowired
    private AppointmentConverter appointmentConverter;
    
    @Override
    public OfferDto modelToDto(Offer model) {
        OfferDto offerDto = new OfferDto();
        offerDto.setId(String.valueOf(model.getId()));
        offerDto.setTitle(model.getTitle());
        offerDto.setContent(model.getContent());
        offerDto.setStartDate(model.getStartDate().format(dateFormatter));
        offerDto.setEndDate(model.getEndDate().format(dateFormatter));
        offerDto.setStreet(model.getCommunication().getAddress().getStreet());
        offerDto.setNumber(model.getCommunication().getAddress().getNumber());
        offerDto.setCity(model.getCommunication().getAddress().getCity());
        offerDto.setPhone(model.getCommunication().getPhone());
        offerDto.setPostcode(model.getCommunication().getAddress().getPostcode());
        offerDto.setCategoryName(model.getCategory().getName());
        offerDto.setCategoryId(String.valueOf(model.getCategory().getId()));
        offerDto.setLatitude(model.getCoordinate().getLatitude());
        offerDto.setLongitude(model.getCoordinate().getLongitude());
        offerDto.setEmail(model.getCommunication().getEmail());
        offerDto.setReported(String.valueOf(model.getReported()));
        offerDto.setBanned(String.valueOf(model.getBanned()));
        offerDto.setThreshold(String.valueOf(model.getThreshold()));
        offerDto.setCoordinateId(String.valueOf(model.getCoordinate().getId()));
        offerDto.setCommunicationId(String.valueOf(model.getCommunication().getId()));
        if (model.getCoordinate().getDistance() != 0) {
            offerDto.setDistance(String.valueOf(model.getCoordinate().getDistance()));
        } else {
            offerDto.setDistance("");
        }
        if (!model.getScheduler().getAppointments().isEmpty()) {
            offerDto.setScheduler(convertAppointmentsToJson(model.getScheduler().getAppointments()));
            offerDto.setSchedulerId(String.valueOf(model.getScheduler().getId()));
            
            List<AppointmentDto> appointments = new ArrayList<>();
            for (Appointment appointment : model.getScheduler().getAppointments()) {
                appointments.add(appointmentConverter.modelToDto(appointment));
            }
            offerDto.setAppointments(appointments);
        }

        offerDto.setRating(String.valueOf(model.getUser().getRating().getPercent()));
        List opinions = new ArrayList<>();
        model.getUser().getRating().getOpinions().forEach(opinion -> opinions.add(opinion.getValue()));
        offerDto.setOpinions(opinions);

        return offerDto;
    }

    @Override
    public Offer dtoToModel(OfferDto dto) {
        Offer offer = new Offer();
        offer.setStartDate(LocalDateTime.parse(dto.getStartDate(), dateFormatter));
        offer.setEndDate(LocalDateTime.parse(dto.getEndDate(), dateFormatter));
        offer.setTitle(dto.getTitle());
        offer.setContent(dto.getContent());
        offer.setReported(Integer.valueOf(dto.getReported()));
        offer.setBanned(Integer.valueOf(dto.getBanned()));
        offer.setThreshold(Integer.parseInt(dto.getThreshold()));

        Communication communication = new Communication();
        communication.setEmail(dto.getEmail());
        communication.setPhone(dto.getPhone());

        Address address = new Address();
        address.setCity(dto.getCity());
        address.setPostcode(dto.getPostcode());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());

        communication.setAddress(address);
        offer.setCommunication(communication);

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(dto.getLatitude());
        coordinate.setLongitude(dto.getLongitude());
        offer.setCoordinate(coordinate);

        Scheduler scheduler = new Scheduler();
        scheduler.setAppointments(convertScheduleDtoToModel(dto.getScheduler(), offer.getStartDate()));
        offer.setScheduler(scheduler);

        return offer;
    }

    public List<Appointment> convertScheduleDtoToModel(String json, LocalDateTime startDate) {
        if (json.isEmpty()) {
            return Collections.emptyList();
        }

        List<Appointment> appointments = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            for (int i = 0; i < 7; i++) {
                String key = "" + i;
                for (int j = 0; j < obj.getJSONArray(key).length(); j++) {
                    JSONArray array = obj.getJSONArray(key).getJSONArray(j);
                    String startHour = (String) array.get(0);
                    String endHour = (String) array.get(1);

                    String[] startSplitted = startHour.split(":");
                    LocalTime start = LocalTime.of(Integer.valueOf(startSplitted[0]), Integer.valueOf(startSplitted[1]));
                    String[] endSplitted = endHour.split(":");
                    LocalTime end = LocalTime.of(Integer.valueOf(endSplitted[0]), Integer.valueOf(endSplitted[1]));
                    while (true) {
                        Appointment appointment = new Appointment();
                        appointment.setDate(startDate.toLocalDate().plusDays(i));
                        appointment.setTime(start);
                        appointment.setJsonObjectKey(i);
                        appointments.add(appointment);

                        if (ChronoUnit.MINUTES.between(start, end) == TimePeriods.APPOINTMENT_TIME) {
                            break;
                        } else {
                            start = start.plus(TimePeriods.APPOINTMENT_TIME, ChronoUnit.MINUTES);
                        }
                    }

                }
            }

        } catch (JSONException e) {
            LOGGER.info("Exception message: {}", e);
        }

        return appointments;
    }

    public String convertAppointmentsToJson(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            return "{'0':[], '1':[], '2':[], '3':[], '4':[], '5':[], '6':[]}";
        }

        JSONObject jsonObject = new JSONObject();
        try {
            for (int i = 0; i < 7; i++) {
                JSONArray jsonArray = new JSONArray();

                for (Appointment appointment : appointments) {
                    JSONArray jsonArrayWithHours = new JSONArray();
                    if (appointment.getJsonObjectKey() == i) {
                        jsonArrayWithHours.put(0, appointment.getTime().toString());
                        jsonArrayWithHours.put(1, appointment.getTime()
                                .plus(TimePeriods.APPOINTMENT_TIME, ChronoUnit.MINUTES).toString());
                    }
                    jsonArray.put(jsonArrayWithHours);
                }
                jsonObject.put("" + i, jsonArray);
            }
        } catch (JSONException e) {
            LOGGER.info("Exception message: {}", e);
        }

        return jsonObject.toString();
    }
}