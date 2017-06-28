package pl.edu.agh.handy.offers.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.agh.handy.offers.common.Links;
import pl.edu.agh.handy.offers.converter.ModelConverter;
import pl.edu.agh.handy.offers.converter.UserConverter;
import pl.edu.agh.handy.offers.model.Offer;
import pl.edu.agh.handy.offers.service.DaoService;
import pl.edu.agh.handy.offers.service.UserService;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerMockIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter userConverter;

    @Test
    @WithMockUser
    //    @WithMockUser(username="example", password="password", roles={"ANONYMOUS"})
    public void registerFormTest() throws Exception {
        mockMvc.perform(get(Links.Url.USER_REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name(Links.View.USER_REGISTER))
                .andExpect(model().attributeExists("userDto"));
    }
}