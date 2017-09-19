package com.benzourry.cloqr.core.controller;

import com.benzourry.cloqr.core.model.Event;
import com.benzourry.cloqr.core.model.LogEntry;
import com.benzourry.cloqr.core.service.EventService;
//import com.itextpdf.text.BadElementException;
//import com.itextpdf.text.pdf.BarcodeQRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MohdRazif on 4/10/2015.
 */
@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    //    @Autowired
//    private LogEntryRepository logEntryRepository;
    @RequestMapping(value = "{id}/logs", method = RequestMethod.GET)
    public List<LogEntry> getLogEntryList(@PathVariable("id") Long id) {
        List<LogEntry> test = eventService.getLogEntryList(id);
//        Map<String, Object> data = new HashMap<>();
//        data.put("test",test);
//        for (LogEntry l : test) {
//            System.out.println("log id:" + l.getId());
//            data.put("list", test);
//            data.put("message", "j");
//        }
        return test;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Event create(@RequestBody Event event, Principal principal) {
        event.setOrganizeBy(principal.getName());
        return eventService.create(event);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Event> getList(Principal principal) {
        System.out.println("principal:"+principal.getName());
        return eventService.findByUsername(principal.getName());
    }

    @RequestMapping(value = "list/logs", method = RequestMethod.GET)
    public List<LogEntry> getListAttendance(Principal principal) {
        return eventService.getLogEntryList(1L);
    }

//    @RequestMapping(value = "checkin")
//    public LogEntry checkIn(@RequestParam("code") String code, @RequestParam("username") String username) {
//        return eventService.checkIn(code, username);
//    }

    @RequestMapping(value = "check")
    public Map<String, Object> check(@RequestParam("code") String code, @RequestParam("username") String username) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> d = eventService.check(code, username);
        boolean hasDone = (boolean)d.get("hasDone");
        boolean noCheckIn = (boolean)d.get("noCheckIn");
        String checkType = (String)d.get("checkType");
        LogEntry le = (LogEntry)d.get("data");
        data.put("eventName",le.getEvent().getName());
        data.put("eventType",le.getEvent().getEventType());
        data.put("hasDone",hasDone);
        data.put("noCheckIn",noCheckIn);
        data.put("checkType", checkType);

        return data;
    }

    @RequestMapping(value = "{id}/checkin/key", produces = "images/jpg")
    @ResponseBody
    public ResponseEntity<byte[]> generateCheckInKey(@PathVariable("id") Long id) throws IOException {
        BufferedImage bi = eventService.getCheckInToken(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(getDataFromBufferedImage(bi));
    }

    @RequestMapping(value = "{id}/checkout/key", produces = "images/jpg")
    @ResponseBody
    public ResponseEntity<byte[]> generateCheckOutKey(@PathVariable("id") Long id) throws IOException {
        BufferedImage bi = eventService.getCheckOutToken(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(getDataFromBufferedImage(bi));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable("id") Long id) {
        return eventService.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Event deleteEvent(@PathVariable("id") Long id) {
        Event event = eventService.findById(id);
        eventService.deleteEvent(id);
        return event;
    }


    private byte[] getDataFromBufferedImage(BufferedImage thumbnail) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.setUseCache(false);
            ImageIO.write(thumbnail, "jpg", baos);
            baos.flush();
            return baos.toByteArray();
        } finally {
            baos.close();
        }
    }
}
