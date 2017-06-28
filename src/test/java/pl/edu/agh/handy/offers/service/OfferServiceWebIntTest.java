package pl.edu.agh.handy.offers.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.handy.offers.HandyOffersWebAppApplication;
import pl.edu.agh.handy.offers.dto.OfferDto;
import pl.edu.agh.handy.offers.model.Offer;
import pl.edu.agh.handy.offers.repository.OfferRepository;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=HandyOffersWebAppApplication.class)
public class OfferServiceWebIntTest {

    private OfferDto offerDto;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Before
    public void setUp() throws Exception {
        offerDto = new OfferDto();
        offerDto.setId("1");
        offerDto.setTitle("Naprawa motocykli");
        offerDto.setContent("Jestem pasjonatem japońskich motocykli, takich marek jak Honda, Yamaha, Suzuki, Kawasaki. " +
                "Chętnie podejmę się w wolnym czasie naprawy lub renowacji motocykli na terenie Krakowa.");
        offerDto.setStartDate("2017-06-03 12:00");
        offerDto.setEndDate("2017-06-10 12:00");
        offerDto.setEmail("test@wp.pl");
        offerDto.setPhone("222-333-444");
        offerDto.setCity("Kraków");
        offerDto.setPostcode("31-443");
        offerDto.setStreet("Długa");
        offerDto.setNumber("1");
        offerDto.setThreshold("100");
        offerDto.setScheduler("");
    }

    @Test
    @Transactional
    public void createTest() throws Exception {
        Offer offer = offerService.create(offerDto);
//        Assert.assertEquals(null, offerRepository.findOne(Long.valueOf(offerDto.getId())));
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        offerService.delete(offerDto);
        Assert.assertEquals(null, offerRepository.findOne(Long.valueOf(offerDto.getId())));
    }

    @Test
    public void findAllTest() throws Exception {
        List<OfferDto> offers = offerService.findAll();
        Assert.assertEquals(4, offers.size());
    }

    @Test
    public void findByKeywordsTest() {
        String keywords = "motocykl";
        List<OfferDto> offerDtos = offerService.findByKeywords(keywords);
        Assert.assertEquals(2, offerDtos.size());
        offerDtos.forEach(offerDto -> {
            Assert.assertTrue(offerDto.getTitle().contains(keywords) || offerDto.getContent().contains(keywords));
        });
    }
}