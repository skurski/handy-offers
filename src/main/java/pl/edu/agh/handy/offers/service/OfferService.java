package pl.edu.agh.handy.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.handy.offers.converter.OfferConverter;
import pl.edu.agh.handy.offers.dto.OfferDto;
import pl.edu.agh.handy.offers.dto.ReservationDto;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.exception.ReservationException;
import pl.edu.agh.handy.offers.model.*;
import pl.edu.agh.handy.offers.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService implements DaoService<Offer, OfferDto> {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferConverter offerConverter;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    @Transactional
    public Offer create(OfferDto offerDto) {
        Offer offer = offerConverter.dtoToModel(offerDto);
        offerRepository.save(offer);
        return offer;
    }

    @Override
    @Transactional(rollbackFor=EntityNotFound.class)
    public void delete(OfferDto offerDto) throws EntityNotFound {
        Offer offerToDelete = offerRepository.findOne(Long.valueOf(offerDto.getId()));

        if (offerToDelete == null) {
            throw new EntityNotFound("Entity not present in database!");
        }

        offerRepository.delete(offerToDelete);
    }

    @Override
    @Transactional
    public List<OfferDto> findAll() {
        return prepareOffers(offerRepository.findAll());
    }

    @Transactional
    public List<OfferDto> findByKeywords(String keywords) {
        return prepareOffers(offerRepository.findByTitleContainingOrContentContaining(keywords, keywords));
    }

    private List<OfferDto> prepareOffers(List<Offer> offers) {
        return offers
                .stream()
                .filter(offer -> offer.getBanned() == 0)
                .map(offer -> offerConverter.modelToDto(prepareOffer(offer)))
                .collect(Collectors.toList());
    }

    private Offer prepareOffer(Offer offer) {
        Rating rating = ratingRepository.findByUser(offer.getUser());
        offer.getUser().setRating(rating);
        return offer;
    }

    /**
     * Not used - implemented in updateOffer method
     */
    @Override
    @Transactional(rollbackFor=EntityNotFound.class)
    public Offer update(OfferDto offerDto) throws EntityNotFound {
        Offer offer = offerConverter.dtoToModel(offerDto);
        Category category = categoryRepository.findOne(Long.valueOf(offerDto.getCategoryId()));
        offer.setCategory(category);

        addressRepository.save(offer.getCommunication().getAddress());
        communicationRepository.save(offer.getCommunication());
        coordinateRepository.save(offer.getCoordinate());
        offer.getScheduler().getAppointments().forEach(ap -> appointmentRepository.save(ap));
        schedulerRepository.save(offer.getScheduler());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        offer.setUser(user);

        Rating rating = ratingRepository.findByUser(user);
        offerRepository.save(offer);
        return offer;
    }

    @Override
    @Transactional
    public OfferDto findById(long id) {
        Offer offer = offerRepository.findOne(id);
        return offerConverter.modelToDto(prepareOffer(offer));
    }

    @Transactional
    public Offer makeBanned(long id, boolean setBanned) {
        Offer offer = offerRepository.getOne(id);
        if (setBanned) {
            offer.setBanned(1);
        } else {
            offer.setBanned(0);
        }
        offerRepository.save(offer);
        return offer;
    }

    @Transactional
    public OfferDto makeReported(long id) {
        Offer offer = offerRepository.getOne(id);
        offer.setReported(1);
        offerRepository.save(offer);
        return offerConverter.modelToDto(offer);
    }

    @Transactional
    public List<OfferDto> findByUser(User user) {
        List<OfferDto> offers = new ArrayList<>();
        for (Offer offer : offerRepository.findByUser(user)) {
            offers.add(offerConverter.modelToDto(prepareOffer(offer)));
        }
        return offers;
    }

    @Transactional
    public List<OfferDto> findByIdIn(List<Long> ids) {
        return prepareOffers(offerRepository.findByIdIn(ids));
    }

    @Transactional
    public List<OfferDto> findByReported(int invalid) {
        List<OfferDto> offers = new ArrayList<>();
        for (Offer offer : offerRepository.findByReported(invalid)) {
            offers.add(offerConverter.modelToDto(prepareOffer(offer)));
        }
        return offers;
    }

    @Transactional
    public List<Offer> findByCategory(Category category) {
        return offerRepository.findByCategory(category);
    }

    @Transactional
    public List<OfferDto> findDtoByCategory(Category category) {
        return prepareOffers(offerRepository.findByCategory(category));
    }

    @Transactional
    public List<OfferDto> findByStartDate(LocalDateTime start) {
        return prepareOffers(offerRepository.findByStartDateGreaterThan(start));
    }

    @Transactional
    public List<OfferDto> findByEndDate(LocalDateTime end) {
        return prepareOffers(offerRepository.findByEndDateLessThan(end));
    }

    @Transactional
    public List<OfferDto> findByKeywordsAndCategory(String keywords, Category category) {
        return prepareOffers(offerRepository
                .findByCategoryAndContentContaining(category, keywords));
    }

    @Transactional
    public List<OfferDto> findByKeywordsAndStartDate(String keywords, LocalDateTime start) {
        return prepareOffers(offerRepository.findByContentContainingAndStartDateGreaterThan(keywords, start));
    }

    @Transactional
    public List<OfferDto> findByKeywordsAndEndDate(String keywords, LocalDateTime end) {
        return prepareOffers(offerRepository.findByContentContainingAndEndDateLessThan(keywords, end));
    }

    @Transactional
    public List<OfferDto> findByKeywordsAndStartDateAndEndDate(String keywords, LocalDateTime start, LocalDateTime end) {
        return prepareOffers(offerRepository
                .findByContentContainingAndStartDateGreaterThanAndEndDateLessThan(keywords, start, end));
    }

    @Transactional
    public List<OfferDto> findByIdInAndBetweenDates(List<Long> ids, LocalDateTime from, LocalDateTime to) {
        return offerRepository.findByIdIn(ids)
                .stream()
                .filter(o -> {
                    if (o.getScheduler() == null) {
                        return false;
                    }
                    for (Appointment ap : o.getScheduler().getAppointments()) {
                        if (ap.getDate().isAfter(from.toLocalDate()) && ap.getDate().isBefore(to.toLocalDate())) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(o -> o.getBanned() == 0)
                .map(o -> offerConverter.modelToDto(prepareOffer(o)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addOffer(OfferDto offerDto) {
        Offer offer = offerConverter.dtoToModel(offerDto);
        Category category = categoryRepository.findOne(Long.valueOf(offerDto.getCategoryId()));
        offer.setCategory(category);

        addressRepository.save(offer.getCommunication().getAddress());
        communicationRepository.save(offer.getCommunication());
        coordinateRepository.save(offer.getCoordinate());
        offer.getScheduler().getAppointments().forEach(ap -> appointmentRepository.save(ap));
        schedulerRepository.save(offer.getScheduler());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        offer.setUser(user);

        Rating rating = ratingRepository.findByUser(user);
        offerRepository.save(offer);
    }

    @Transactional
    public void updateOffer(OfferDto dto) {
        Offer offer = offerRepository.findOne(Long.valueOf(dto.getId()));
        offer.setStartDate(LocalDateTime.parse(dto.getStartDate(), offerConverter.dateFormatter));
        offer.setEndDate(LocalDateTime.parse(dto.getEndDate(), offerConverter.dateFormatter));
        offer.setTitle(dto.getTitle());
        offer.setContent(dto.getContent());
        offer.setReported(Integer.valueOf(dto.getReported()));
        offer.setBanned(Integer.valueOf(dto.getBanned()));
        offer.setThreshold(Integer.parseInt(dto.getThreshold()));

        Communication communication = communicationRepository.findOne(offer.getCommunication().getId());
        communication.setEmail(dto.getEmail());
        communication.setPhone(dto.getPhone());

        Address address = communication.getAddress();
        address.setCity(dto.getCity());
        address.setPostcode(dto.getPostcode());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());

        communication.setAddress(address);
        offer.setCommunication(communication);

        Coordinate coordinate = coordinateRepository.findOne(offer.getCoordinate().getId());
        coordinate.setLatitude(dto.getLatitude());
        coordinate.setLongitude(dto.getLongitude());
        offer.setCoordinate(coordinate);

        Scheduler scheduler = schedulerRepository.findOne(offer.getScheduler().getId());
        List<Appointment> appointments = offerConverter.convertScheduleDtoToModel(dto.getScheduler(), offer.getStartDate());
        appointmentRepository.save(appointments);
        scheduler.setAppointments(appointments);
        offer.setScheduler(scheduler);
        offerRepository.save(offer);
    }

    /**
     * Reservation logic.
     *
     * Remove appointment from offer scheduler
     * and persist it in Reservation object.
     *
     * @param reservationDto
     */
    public void reserveOffer(ReservationDto reservationDto) throws ReservationException {
        User user = userRepository.findOne(Long.valueOf(reservationDto.getUserId()));
        Offer offer = offerRepository.findOne(Long.valueOf(reservationDto.getOfferId()));
        if (user.getRating().getPercent() < offer.getThreshold()) {
            throw new ReservationException("User rating too low!");
        }

        Appointment appointment = appointmentRepository.findOne(Long.valueOf(reservationDto.getAppointmentId()));
        removeAppointmentFromScheduler(reservationDto, appointment);

        Reservation reservation = new Reservation();
        reservation.setAppointment(appointment);
        reservation.setUser(user);
        reservation.setOffer(offer);
        reservationRepository.save(reservation);
    }

    private void removeAppointmentFromScheduler(ReservationDto reservationDto, Appointment appointment) {
        Scheduler scheduler = schedulerRepository.findOne(Long.valueOf(reservationDto.getSchedulerId()));
        List<Appointment> appointments = scheduler.getAppointments();
        appointments.remove(appointment);
        schedulerRepository.save(scheduler);
    }

    public void cancelReservation(ReservationDto reservationDto) {
        Appointment appointment = appointmentRepository.findOne(Long.valueOf(reservationDto.getAppointmentId()));
        addAppointmentToScheduler(reservationDto, appointment);
        reservationRepository.delete(Long.valueOf(reservationDto.getId()));
    }

    private void addAppointmentToScheduler(ReservationDto reservationDto, Appointment appointment) {
        Scheduler scheduler = schedulerRepository.findOne(Long.valueOf(reservationDto.getSchedulerId()));
        List<Appointment> appointments = scheduler.getAppointments();
        appointments.add(appointment);
        schedulerRepository.save(scheduler);
    }
}
