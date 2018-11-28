import java.io.File;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class Parser {
    public static Devise domParser() {
        try {
            File inputFile = new File("src\\Comp.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Component");
            Devise dev = new Devise();
            Component[] components = new Component[nList.getLength()];
            for(int i = 0; i < nList.getLength(); i++) {
                components[i] = new Component();
            }
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    components[temp].setId(eElement.getAttribute("id"));
                    components[temp].setNameAtr(eElement.getAttribute("name"));
                    components[temp].setName(eElement.getElementsByTagName("Name")
                            .item(0).getTextContent());
                    components[temp].setOrigin(eElement.getElementsByTagName("Origin")
                            .item(0).getTextContent());
                    components[temp].setPrise(Float.parseFloat(eElement.getElementsByTagName("Prise")
                            .item(0).getTextContent()));
                    components[temp].setCritical(eElement.getElementsByTagName("Critical")
                            .item(0).getTextContent());
                    Type type = new Type();
                    Element typeElement = (Element) eElement.getElementsByTagName("Type").item(0);
                    type.setEneryCons(Integer.valueOf(typeElement.getElementsByTagName("EneryCons")
                            .item(0).getTextContent()));
                    type.setCooler(typeElement.getElementsByTagName("Cooler")
                            .item(0).getTextContent());
                    type.setGroup(typeElement.getElementsByTagName("Group")
                            .item(0).getTextContent());
                    type.setPort(typeElement.getElementsByTagName("Port")
                            .item(0).getTextContent());
                    components[temp].setType(type);
                }
            }
            ArrayList<Component> sortedComponents = new ArrayList<>();
            for(int i = 0; i < components.length; i++) {
                sortedComponents.add(components[i]);
            }
            Collections.sort(sortedComponents, new SortByPrice());

            components = sortedComponents.toArray(new Component[sortedComponents.size()]);

            dev.setComponent(components);
            System.out.println(dev.toString());
            return dev;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static Devise saxParser(){
        try {
            File inputFile = new File("src\\Comp.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
            ArrayList<Component> sortedComponents = new ArrayList<>();
            Devise dev = UserHandler.dev;
            Component[] components = dev.getComponent();
            for(int i = 0; i < components.length; i++) {
                sortedComponents.add(components[i]);
            }
            Collections.sort(sortedComponents, new SortByPrice());

            components = sortedComponents.toArray(new Component[sortedComponents.size()]);

            dev.setComponent(components);
            System.out.println(dev.toString());
            return dev;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Devise staxParser(){
        try {
            ArrayList<Component> components = new ArrayList<>();
            Component comp = null;
            Type type = null;
            Devise dev = new Devise();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader("src\\Comp.xml"));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:{
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("component")) {
                            comp = new Component();
                            type = new Type();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String name = attributes.next().getValue();
                            comp.setNameAtr(name);
                            String id = attributes.next().getValue();
                            comp.setId(id);
                        }
                        else if(startElement.getName().getLocalPart().equals("Name")) {
                            event = eventReader.nextEvent();
                            comp.setName(event.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("Origin")) {
                            event = eventReader.nextEvent();
                            comp.setOrigin(event.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("Prise")) {
                            event = eventReader.nextEvent();
                            comp.setPrise(Float.parseFloat(event.asCharacters().getData()));
                        }
                        else if(startElement.getName().getLocalPart().equals("Critical")) {
                            event = eventReader.nextEvent();
                            comp.setCritical(event.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("EneryCons")) {
                            event = eventReader.nextEvent();
                            type.setEneryCons(Integer.valueOf(event.asCharacters().getData()));
                        }
                        else if(startElement.getName().getLocalPart().equals("Cooler")) {
                            event = eventReader.nextEvent();
                            type.setCooler(event.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("Group")) {
                            event = eventReader.nextEvent();
                            type.setGroup(event.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("Port")) {
                            event = eventReader.nextEvent();
                            type.setPort(event.asCharacters().getData());
                        }
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT:{
                        EndElement endElement = event.asEndElement();

                        if(endElement.getName().getLocalPart().equalsIgnoreCase("component")) {
                            comp.setType(type);
                            components.add(comp);
                        }
                        else if(endElement.getName().getLocalPart().equalsIgnoreCase("devise")) {
                            Component[] componentsTodev = components.toArray(new Component[components.size()]);
                            dev.setComponent(componentsTodev);
                        }
                        break;
                }

            }
            }
            Component[] componentsToDev = dev.getComponent();
            Collections.sort(components, new SortByPrice());

            componentsToDev = components.toArray(new Component[components.size()]);

            dev.setComponent(componentsToDev);
            System.out.println(dev.toString());
            return dev;
        }
        catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        saxParser();
    }
}

class UserHandler extends DefaultHandler {

    String tmpvalue;
    static Devise dev = new Devise();
    ArrayList<Component> components = new ArrayList<>();
    Component comp;
    Type type;

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {



        if (qName.equalsIgnoreCase("component")) {
            comp = new Component();
            type = new Type();
            String id = attributes.getValue("id");
            String name = attributes.getValue("name");
            comp.setNameAtr(name);
            comp.setId(id);

        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("Name")) {
            comp.setName(tmpvalue);
        }
        else if(qName.equalsIgnoreCase("Prise"))
        {
            comp.setPrise(Float.valueOf(tmpvalue));
        }
        else if(qName.equalsIgnoreCase("Origin"))
        {
            comp.setOrigin(tmpvalue);
        }
        else if(qName.equalsIgnoreCase("Critical"))
        {
            comp.setCritical(tmpvalue);
        }
        else if(qName.equalsIgnoreCase("EneryCons"))
        {
            type.setEneryCons(Integer.valueOf(tmpvalue));
        }
        else if(qName.equalsIgnoreCase("Cooler"))
        {
            type.setCooler(tmpvalue);
        }
        else if(qName.equalsIgnoreCase("Group"))
        {
            type.setGroup(tmpvalue);
        }
        else if(qName.equalsIgnoreCase("Port"))
        {
            type.setPort(tmpvalue);
        }
        else if (qName.equalsIgnoreCase("component")) {
            comp.setType(type);
            components.add(comp);


        }
        else if(qName.equalsIgnoreCase("devise")) {
            Component[] componentsTodev = components.toArray(new Component[components.size()]);
            dev.setComponent(componentsTodev);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        tmpvalue = new String(ch, start, length);
    }
}
