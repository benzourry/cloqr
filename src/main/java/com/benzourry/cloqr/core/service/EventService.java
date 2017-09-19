package com.benzourry.cloqr.core.service;

import com.benzourry.cloqr.core.dao.AccountRepository;
import com.benzourry.cloqr.core.dao.EventRepository;
import com.benzourry.cloqr.core.dao.LogEntryRepository;
import com.benzourry.cloqr.core.helper.Constant;
import com.benzourry.cloqr.core.model.Account;
import com.benzourry.cloqr.core.model.Event;
import com.benzourry.cloqr.core.model.LogEntry;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
//import com.itextpdf.text.pdf.BarcodeQRCode;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.awt.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by MohdRazif on 4/10/2015.
 */
@Service("eventService")
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    LogEntryRepository logEntryRepository;

    @Autowired
    AccountRepository accountRepository;

    public Event findById(Long id) {
        return eventRepository.findOne(id);
    }

    public List<Event> findByUsername(String username) {
        return eventRepository.findByOrganizeBy(username);
    }

//    public LogEntry checkIn(String code, String username) {
//
//        LogEntry le = null;
//        if (logEntryRepository.findByTokenAndUsername(code, username) == null) {
//            le = new LogEntry();
//            Account acc = accountRepository.findByUsername(username);
//            Event event = eventRepository.findByToken(code);
//            le.setAccount(acc);
//            le.setEvent(event);
//            logEntryRepository.save(le);
//        }
//        return le;
//    }

    @Transactional
    public Map<String, Object> check(String code, String username) {

        Map<String, Object> data = new HashMap<>();

        boolean hasDone = false;
        boolean noCheckIn = false;
        String checkType = "";

        String token = code.substring(Constant.PREFIX_LENGTH);
        System.out.println("TOKEN:" + token);
        // retrieve existing if any.
        LogEntry le = logEntryRepository.findByTokenAndUsername(token, username);
        System.out.println("LE:" + le);


        // ### PROBLEMATIC PART
        if (le == null) {
            le = new LogEntry();
            Account acc = new Account(username);
            Event event = eventRepository.findByToken(token);
            le.setAccount(acc);
            le.setEvent(event);
        }

        if (code.contains(Constant.CHECK_IN_PREFIX)) {
            checkType="CHECK IN";
            if (le.getCheckInTime()==null) {
                le.setCheckInTime(new DateTime().toString());
            }else{
                hasDone = true;
            }
        } else if (code.contains(Constant.CHECK_OUT_PREFIX)) {
            checkType="CHECK OUT";
            if(le.getCheckInTime()==null) {
                noCheckIn=true;
            }
            if (le.getCheckOutTime() == null) {
                le.setCheckOutTime(new DateTime().toString());
            } else {
                hasDone = true;
            }
        }
        System.out.println("LE with ID:" + le.getId());
        System.out.println("LE username:" + le.getAccount().getUsername());
//        System.out.println("LE username:"+le.getAccount().getUsername());
        logEntryRepository.save(le);

        data.put("hasDone", hasDone);
        data.put("checkType", checkType);
        data.put("noCheckIn", noCheckIn);
        data.put("data",le);
        return data;
    }

//    public String checkOut(String code, String userId){
//        return code + userId;
//    }

    public BufferedImage getCheckInToken(Long id) {
        Event e = eventRepository.findOne(id);
        return getBarcode(Constant.CHECK_IN_PREFIX + e.getToken(), "CHECK-IN CODE", "");
    }

    public BufferedImage getCheckOutToken(Long id) {
        Event e = eventRepository.findOne(id);
        return getBarcode(Constant.CHECK_OUT_PREFIX + e.getToken(),"CHECK-OUT CODE", "");
    }

//    public Image getCheckInImage(Long eventId){
////        BarcodeQRCode qrcode = new BarcodeQRCode("test", 1, 1, null);
//       // Image qrcodeImage = qrcode.getImage();
////        return qrcode.createAwtImage(Color.BLACK, Color.WHITE);
//    }

    @Transactional
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event getEvent(Long id) {
        return eventRepository.findOne(id);
    }

    public void deleteEvent(Long id){
        eventRepository.delete(id);
    }

    protected BufferedImage getBarcode(String code, String label, String labelBottom) {

//        byte[] b = null;
        BufferedImage buffImg = null;
//        String data;
        BitMatrix matrix = null;
        int h = 600;
        int w = 600;
        com.google.zxing.Writer writer = new MultiFormatWriter();
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            matrix = writer.encode(code, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);

        } catch (com.google.zxing.WriterException e) {
            System.out.println(e.getMessage());
        }

        // change this path to match yours (this is my mac home folder, you can use: c:\\qr_png.png if you are on windows)
//        String filePath = "/Users/shaybc/Desktop/OutlookQR/qr_png.png";
//        File file = new File(filePath);
//            try {
        buffImg = MatrixToImageWriter.toBufferedImage(matrix);//.writeToFile(matrix, "PNG", file);
//        buffImg.

//        int k = buffImg.getWidth();
//        int l = buffImg.getHeight();
//        BufferedImage img = new BufferedImage(k, l, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImg.createGraphics();
        g2d.drawImage(buffImg, 0, 0, null);
        g2d.setPaint(Color.red);
//        g2d.setRenderingHint(
//                RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
//        Graphics2D g2d = buffImg.createGraphics();
//        String s = "CHECK-OUT TOKEN";
        FontMetrics fm = g2d.getFontMetrics();
        int x = Math.round((buffImg.getWidth() / 2) - (fm.stringWidth(label)/2));
        int y = fm.getHeight()+10;
        g2d.drawString(label,x,y);
        int x2 = Math.round((buffImg.getWidth() / 2) - (fm.stringWidth(labelBottom)/2));
        g2d.drawString(labelBottom, x2, buffImg.getHeight() - fm.getHeight() - 10);
        g2d.dispose();
//                System.out.println("printing to " + file.getAbsolutePath());
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
        //    } catch (UnsupportedEncodingException e) {
        //        System.out.println(e.getMessage());
        //    }

        return buffImg;

    }

    public List<LogEntry> getLogEntryList(Long id){
//        List<LogEntry> test = logEntryRepository.findByEventId(id);
//        for (LogEntry l: test){
//            System.out.println("log id:"+l.getId());
//        }
        return logEntryRepository.findByEventId(id);
    }
}
