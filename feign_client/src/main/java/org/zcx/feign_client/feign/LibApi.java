package org.zcx.feign_client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zcx.feign_client.pojo.LibResult;

@FeignClient(name = "lib-api", url = "https://seat.ujn.edu.cn:8443")
public interface LibApi {

    @GetMapping(value = "/rest/auth")
    LibResult auth(@RequestParam String username,
                   @RequestParam String password);

    @GetMapping(value = "/rest/v2/startTimesForSeat/{seat}/{date}")
    LibResult getStartTime(@PathVariable(value = "seat") String seat,
                           @PathVariable(value = "date") String date,
                           @RequestParam String token);


    @GetMapping(value = "/rest/v2/endTimesForSeat/{seat}/{date}/{startTime}")
    LibResult getEndTime(@PathVariable(value = "seat") String seat,
                         @PathVariable(value = "date") String date,
                         @PathVariable(value = "startTime") String startTime,
                         @RequestParam String token);


    @GetMapping(value = "/rest/v2/history/{page}/{size}")
    LibResult history(@PathVariable(value = "page") int page, @PathVariable(value = "size") int size, @RequestParam String token);


    @GetMapping(value = "/rest/v2/checkIn")
    LibResult checkIn(@RequestParam String token);

    @PostMapping(value = "/rest/v2/freeBook")
    LibResult freeBook(@RequestParam String token,
                       @RequestParam String startTime,
                       @RequestParam String endTime,
                       @RequestParam String seat,
                       @RequestParam String date);
}
