package deti.tqs.carInfoSystem.controllers;

import deti.tqs.carInfoSystem.controllers.CarRestController;
import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.services.CarManagerService;
import deti.tqs.utils.JsonUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CarRestController.class)
public class CarControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @Test
    public void whenPostToCreateCar_ThenCreateEmployee() throws Exception {
        Car tesla = new Car("Tesla", "Model S");

        Mockito.when(service.save(Mockito.any())).thenReturn(tesla);

        mvc.perform(MockMvcRequestBuilders.post("/api/car")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(tesla))).andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.maker", CoreMatchers.is("Tesla")));

        Mockito.verify(service, Mockito.times(1)).save(Mockito.any());
    }
}
