package com.kkoon9.springbatch.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JaxbDateSerializer extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public Date unmarshal(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    @Override
    public String marshal(Date date) {
        return dateFormat.format(date);
    }
}
