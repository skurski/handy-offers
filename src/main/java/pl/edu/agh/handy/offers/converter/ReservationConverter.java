package pl.edu.agh.handy.offers.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.handy.offers.dto.ReservationDto;
import pl.edu.agh.handy.offers.model.Reservation;

import java.io.IOException;

/**
 * Created by psk on 08.06.17.
 */
@Component
public class ReservationConverter implements ModelConverter<ReservationDto, Reservation> {

    @Override
    public ReservationDto modelToDto(Reservation model) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(String.valueOf(model.getId()));
        reservationDto.setUserId(String.valueOf(model.getUser().getId()));
        reservationDto.setOfferId(String.valueOf(model.getOffer().getId()));
        reservationDto.setAppointmentId(String.valueOf(model.getAppointment().getId()));
        reservationDto.setSchedulerId(String.valueOf(model.getOffer().getScheduler().getId()));
        reservationDto.setAppointmentDetails(model.getAppointment().print());
        reservationDto.setOfferTitle(model.getOffer().getTitle());
        return reservationDto;
    }

    @Override
    public Reservation dtoToModel(ReservationDto dto) throws IOException {
        return null;
    }
}
