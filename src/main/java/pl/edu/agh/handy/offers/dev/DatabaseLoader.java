package pl.edu.agh.handy.offers.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.edu.agh.handy.offers.converter.OfferConverter;
import pl.edu.agh.handy.offers.dto.ReservationDto;
import pl.edu.agh.handy.offers.model.*;
import pl.edu.agh.handy.offers.repository.*;
import pl.edu.agh.handy.offers.security.Roles;
import pl.edu.agh.handy.offers.security.SecurityUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for development purposes.
 *
 * Populates database with testing data.
 * (Run after all beans are created by spring - implements CommandLineRunner).
 */
@Component
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private OfferConverter offerConverter;

    public DatabaseLoader() {}

    @Override
    public void run(String... strings) throws Exception {
        User user1 = new User("aga", 1, SecurityUtil.passwordGenerator("password"));
        User user2 = new User("piotr", 1, SecurityUtil.passwordGenerator("password"));
        User user3 = new User("admin", 1, SecurityUtil.passwordGenerator("admin"));

        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);

        UserRole userRole1 = new UserRole(user1, Roles.ROLE_USER.toString());
        UserRole userRole2 = new UserRole(user2, Roles.ROLE_USER.toString());
        UserRole userRole3 = new UserRole(user3, Roles.ROLE_ADMIN.toString());

        this.userRoleRepository.save(userRole1);
        this.userRoleRepository.save(userRole2);
        this.userRoleRepository.save(userRole3);

        List<Opinion> opinions = createOpinions();
        Rating rating = new Rating();
        rating.setOpinions(opinions);
        rating.setUser(user1);
        ratingRepository.save(rating);
        ratingRepository.save(new Rating(user2));
        ratingRepository.save(new Rating(user3));

        Offer offer = new Offer();
        offer.setTitle("Naprawa motocykli");
        offer.setContent("Jestem pasjonatem japońskich motocykli, takich marek jak Honda, Yamaha, Suzuki, Kawasaki. " +
                "Chętnie podejmę się w wolnym czasie naprawy lub renowacji motocykli na terenie Krakowa.");
        offer.setStartDate(LocalDateTime.of(2017, 06, 3, 12, 0));
        offer.setEndDate(LocalDateTime.of(2017, 06, 10, 12, 0));
        offer.setUser(userRepository.getOne(1L));

        Offer offer2 = new Offer();
        offer2.setTitle("Serwis pozakupowy motocykla");
        offer2.setContent("Jako motocyklista amator chętnie podejmę się podstawowego serwisu motocykla w takim zakresie " +
                "jak wymiana oleju, regulacja zaworów, synchronizacja gaźników, wymiana napinacza rozrządu lub nawet" +
                " remontu kapitalnego silnika.");
        offer2.setStartDate(LocalDateTime.of(2017, 03, 16, 12, 0));
        offer2.setEndDate(LocalDateTime.of(2017, 04, 29, 12, 0));
        offer2.setUser(userRepository.getOne(2L));

        Offer offer3 = new Offer();
        offer3.setTitle("Elektryk profesjonalny");
        offer3.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        offer3.setStartDate(LocalDateTime.of(2017, 03, 16, 12, 0));
        offer3.setEndDate(LocalDateTime.of(2017, 03, 13, 12, 0));
        offer3.setUser(userRepository.getOne(3L));

        Offer offer4 = new Offer();
        offer4.setTitle("Elektryk amator");
        offer4.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        offer4.setStartDate(LocalDateTime.of(2017, 03, 16, 12, 0));
        offer4.setEndDate(LocalDateTime.of(2017, 06, 13, 12, 0));
        offer4.setUser(userRepository.getOne(3L));

        Category cat1 = new Category();
        cat1.setName("Budownictwo");
        Category cat2 = new Category();
        cat2.setName("Motoryzacja");

        this.categoryRepository.save(cat1);
        this.categoryRepository.save(cat2);

        Communication communication = new Communication();
        communication.setPhone("555 555 555");
        communication.setEmail("peter@wp.pl");
        Address address = new Address();
        address.setCity("Krakow");
        address.setPostcode("31-690");
        address.setStreet("Piastow");
        address.setNumber("99/22");
        communication.setAddress(address);

        this.addressRepository.save(address);
        this.communicationRepository.save(communication);

        Coordinate coor1 = new Coordinate("50.06465010000003", "19.968325847265646");
        Coordinate coor2 = new Coordinate("50.07465010000003", "19.868325847265646");
        Coordinate coor3 = new Coordinate("50.08465010000003", "19.768325847265646");
        Coordinate coor4 = new Coordinate("50.09465010000003", "19.668325847265646");

        this.coordinateRepository.save(coor1);
        this.coordinateRepository.save(coor2);
        this.coordinateRepository.save(coor3);
        this.coordinateRepository.save(coor4);

        this.offerRepository.save(offer);
        this.offerRepository.save(offer2);
        this.offerRepository.save(offer3);
        this.offerRepository.save(offer4);

        Scheduler scheduler = new Scheduler();
        String jsonSchedule = "{\"0\":[],\"1\":[[\"12:30\",\"16:30\"]],\"2\":[[\"14:00\",\"15:30\"]],\"3\":[],\"4\":[[\"14:00\",\"17:30\"]],\"5\":[],\"6\":[[\"14:30\",\"16:00\"]]}";
        List<Appointment> appointments = offerConverter.convertScheduleDtoToModel(jsonSchedule, offer.getStartDate());
        appointments.forEach(appointment -> this.appointmentRepository.save(appointment));
        scheduler.setAppointments(appointments);
        this.schedulerRepository.save(scheduler);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setAppointmentId("1");
        reservationDto.setUserId("2");
        reservationDto.setOfferId("1");
        reservationDto.setSchedulerId("1");
        Appointment appointment = appointmentRepository.findOne(Long.valueOf(reservationDto.getAppointmentId()));
        appointments.remove(appointment);
        schedulerRepository.save(scheduler);
        Reservation reservation = new Reservation();
        reservation.setAppointment(appointment);
        reservation.setUser(userRepository.findOne(Long.valueOf(reservationDto.getUserId())));
        reservation.setOffer(offerRepository.findOne(Long.valueOf(reservationDto.getOfferId())));
        reservationRepository.save(reservation);

        List<Offer> offers = this.offerRepository.findAll();
        Coordinate[] coordinates = {coor1, coor2, coor3, coor4};
        int counter = 0;
        for (Offer o : offers) {
            o.setCommunication(communication);
            o.setCoordinate(coordinates[counter]);
            o.setCategory(o.getTitle().contains("Elektryk") ? cat1 : cat2);
            o.setScheduler(scheduler);
            this.offerRepository.save(o);
            counter++;
        }
    }

    private List<Opinion> createOpinions() {
        List<Opinion> opinions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Opinion opinion = new Opinion();
            opinion.setValue("Jestem zadowolony z usługi, było ok jak na pierwszy raz.");
            opinion.setStars(10);
            opinions.add(opinionRepository.save(opinion));
        }
        return opinions;
    }
}