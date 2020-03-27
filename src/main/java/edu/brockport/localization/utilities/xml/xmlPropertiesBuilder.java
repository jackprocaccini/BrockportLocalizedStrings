package edu.brockport.localization.utilities.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;

public class xmlPropertiesBuilder extends DefaultHandler {
    private boolean dataFound;
    private boolean valueFound;
    private boolean dataCommentFound;
    private Properties props;
    private String language;

    private ArrayList<Data> dataList = new ArrayList();
    private Stack<String> elementStack = new Stack();
    private Stack<Data> objectStack = new Stack();

    public xmlPropertiesBuilder(File xmlFile) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFile, this);
        dataFound = false;
        valueFound = false;
        dataCommentFound = false;
        props = new Properties();

        if(xmlFile.getName().indexOf("-") == -1){
            language = "en_US";
        } else {
            String lang = xmlFile.getName().substring(xmlFile.getName().indexOf(".") + 1, xmlFile.getName().indexOf("-"));
            String region = xmlFile.getName().substring(xmlFile.getName().indexOf("-") + 1, xmlFile.getName().lastIndexOf("."));
            language = lang + "_" + region;
        }
    }

    public Properties getProps(){
        Properties props = new Properties();
        for(Data d: dataList){
            props.setProperty(d.getName(), d.getValue());
        }

        return props;
    }

    @Override
    public void startDocument() throws SAXException {
//        System.out.println("Start Document");
    }

    @Override
    public void endDocument() throws SAXException {
//        System.out.println("End Document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementStack.push(qName);

        if(qName.equals("data")){
            Data data = new Data();
            data.setName(attributes.getValue("name"));
            objectStack.push(data);
            dataFound = true;
        } else if(qName.equals("value")){
            valueFound = true;
        } else if(qName.equals("comment")){
            dataCommentFound = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementStack.pop();

        if(qName.equals("data")){
            dataList.add(objectStack.pop());
            dataFound = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length);
        str = str.trim(); //used to make sure empty lines aren't being put into the properties file

        if(str.length() == 0){
            return;
        }

        if(valueFound && dataFound){
            objectStack.peek().setValue(str);
            valueFound = false;
        } else if(dataCommentFound && dataFound){
            objectStack.peek().setComment(str);
            dataCommentFound = false;
        }
    }

    public String getLanguage(){
        return language;
    }
}