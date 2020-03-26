package no.experis.tbbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import no.experis.tbbackend.model.VacationDays;
import no.experis.tbbackend.repository.VacationDaysRepo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class VacationDaysController {

    private VacationDaysRepo vacationDaysRepo = new VacationDaysRepo();

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/vacationDays/{v_id}")
    public VacationDays getVacationDays(@PathVariable int v_id, HttpServletResponse response) throws IOException{
        VacationDays vacationDays = vacationDaysRepo.findById(v_id);

        response.setStatus(200);
        return vacationDays;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/vacationDays")
    public void patchVacationDays(@RequestBody String maxVacationDays, HttpServletResponse response) throws IOException{
        System.out.println("HERE IT IS BEFORE!!!!!!!!!!!    "+ maxVacationDays);

        JSONObject object = new JSONObject();
        JsonObject obj = new Gson().fromJson(maxVacationDays, JsonObject.class);


        int v_days = obj.get("max_vacation_days").getAsInt();
        System.out.println("HERE IT IS AFTER!!!!!!!!!!!    TYPE OF "+ v_days);
        VacationDays vacationDays = new VacationDays();
        vacationDays.setMax_vacationDays(v_days);
        vacationDaysRepo.save(vacationDays);
        vacationDaysRepo.update(vacationDays);
        response.setStatus(200);
    }
}
