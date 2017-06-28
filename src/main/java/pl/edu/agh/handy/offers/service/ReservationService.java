package pl.edu.agh.handy.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.handy.offers.converter.ReservationConverter;
import pl.edu.agh.handy.offers.dto.ReservationDto;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.model.Reservation;
import pl.edu.agh.handy.offers.model.User;
import pl.edu.agh.handy.offers.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reservation Service.
 */
@Service
public class ReservationService implements DaoService<Reservation, ReservationDto> {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationConverter reservationConverter;

    @Override
    public Reservation create(ReservationDto dto) {
        return null;
    }

    @Override
    public void delete(ReservationDto dto) throws EntityNotFound {

    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> reservationConverter.modelToDto(reservation))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReservationDto> findCurrentByUser(User user) {
        return reservationRepository.findByUser(user)
                .stream()
                .filter(reservation -> {
                    LocalDateTime localDateTime = LocalDateTime.of(reservation.getAppointment().getDate(),
                            reservation.getAppointment().getTime());
                    if (localDateTime.isAfter(LocalDateTime.now())) {
                        return true;
                    }
                    return false;
                })
                .map(reservation -> reservationConverter.modelToDto(reservation))
                .collect(Collectors.toList());
    }

    public Object findPastByUser(User user) {
        return reservationRepository.findByUser(user)
                .stream()
                .filter(reservation -> {
                    LocalDateTime localDateTime = LocalDateTime.of(reservation.getAppointment().getDate(),
                            reservation.getAppointment().getTime());
                    if (localDateTime.isAfter(LocalDateTime.now())) {
                        return false;
                    }
                    return true;
                })
                .map(reservation -> reservationConverter.modelToDto(reservation))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation update(ReservationDto dto) throws EntityNotFound {
        return null;
    }

    @Override
    public ReservationDto findById(long id) {
        return reservationConverter.modelToDto(reservationRepository.findOne(id));
    }
}
