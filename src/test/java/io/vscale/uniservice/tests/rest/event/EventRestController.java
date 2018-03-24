package io.vscale.uniservice.tests.rest.event;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.EventRepository;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.security.states.UserState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class EventRestController {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {

        userRepository.save(User.builder().login("ainur").password("qwerty").state(UserState.CONFIRMED).profile(new Profile()).build());
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.eventRepository.deleteAllInBatch();
    }
    @After
    public void after(){
        userRepository.delete(userRepository.findByLogin("ainur").get());
    }

    @Test
    public void createEvent() throws Exception {
        String eventJson = json(
                Event.builder()
                        .description("Test")
                        .build()
        );

        this.mockMvc.perform(post("/api_v1/event/")
                .with(user("ainur").password("qwerty"))
                .contentType(contentType)
                .content(eventJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void readOneEvent() throws Exception {

        Event event = Event.builder()
                .description("readOneEvent")
                .build();
        eventRepository.save(event);

        mockMvc.perform(get("/api_v1/event/"
                + event.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(event.getId().intValue())))
                .andExpect(jsonPath("$.description", is("readOneEvent")));
    }

    @Test
    public void readAllEvents() throws Exception {
        Event eventOne = Event.builder()
                .description("obj1")
                .build();

        Event eventTwo = Event.builder()
                .description("obj2")
                .build();
        eventRepository.save(eventOne);
        eventRepository.save(eventTwo);


        mockMvc.perform(get("/api_v1/event/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(eventOne.getId().intValue())))
                .andExpect(jsonPath("$[0].description", is("obj1")))
                .andExpect(jsonPath("$[1].id", is(eventTwo.getId().intValue())))
                .andExpect(jsonPath("$[1].description", is("obj2")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

        return mockHttpOutputMessage.getBodyAsString();
    }


}
