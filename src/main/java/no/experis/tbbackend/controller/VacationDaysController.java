package no.experis.tbbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import no.experis.tbbackend.notification.Twingleton;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Vacation days controller.
 */
@RestController
public class VacationDaysController {


    /**
     * Gets vacation days.
     *
     * @param response the response
     * @return the vacation days
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/vacationDays/")
    public long getVacationDays(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        return Twingleton.getInstance().getMax_vacation_days();
    }

    /**
     * Patch vacation days.
     *
     * @param maxVacationDays the max vacation days
     * @param response        the response
     * @throws IOException the io exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/vacationDays")
    public void patchVacationDays(@RequestBody String maxVacationDays, HttpServletResponse response) throws IOException {

        JSONObject object = new JSONObject();
        JsonObject obj = new Gson().fromJson(maxVacationDays, JsonObject.class);

        int v_days = obj.get("max_vacation_days").getAsInt();
        Twingleton.getInstance().setMax_vacation_days(v_days);
        response.setStatus(200);
    }
}
