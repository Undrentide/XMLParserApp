package ua.undrentide.xmlparserapp.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import ua.undrentide.xmlparserapp.entity.User;

import java.io.File;
import java.io.IOException;

@Component
public class XmlDomParserCore {

    /** Realisation without Jackson Library:
    public User xmlImport() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        User user = new User();
        user.setId(null);
        user.setName(null);
        user.setPhone(null);
        user.setEmail(null);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("User.xml");
            NodeList userList = document.getElementsByTagName("user");
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    NodeList nameList = userElement.getChildNodes();
                    for (int j = 0; j < nameList.getLength(); j++) {
                        Node nameNode = nameList.item(j);
                        if (nameNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element nameElement = (Element) nameNode;
                            if (nameElement.getTagName().equals("name")) {
                                user.setName(nameElement.getTextContent());
                            }
                            if (nameElement.getTagName().equals("phone")) {
                                user.setPhone(nameElement.getTextContent());
                            }
                            if (nameElement.getTagName().equals("email")) {
                                user.setEmail(nameElement.getTextContent());
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        System.out.println(user);
        return user;
    }
    */

    public User xmlDeserialize() {
        File file = new File("User.xml");
        XmlMapper xmlMapper = new XmlMapper();
        User user;
        try {
            user = xmlMapper.readValue(file, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void xmlSerialize(User user) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File("User.xml"), user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}