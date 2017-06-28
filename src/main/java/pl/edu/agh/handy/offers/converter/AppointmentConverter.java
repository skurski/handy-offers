package pl.edu.agh.handy.offers.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.handy.offers.dto.AppointmentDto;
import pl.edu.agh.handy.offers.model.Appointment;

import java.time.format.DateTimeFormatter;

@Component
public class AppointmentConverter implements ModelConverter<AppointmentDto, Appointment> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public AppointmentDto modelToDto(Appointment model) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(String.valueOf(model.getId()));
        if (model.getTime() != null && model.getDate() != null) {
            appointmentDto.setDate(model.getDate().format(dateFormatter));
            appointmentDto.setTime(model.getTime().format(timeFormatter));
        }

        return appointmentDto;
    }

    @Override
    public Appointment dtoToModel(AppointmentDto dto) {
        return null; // todo: change to empty object
    }
}
