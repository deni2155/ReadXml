package com.mycompany.readxml;

import com.mycompany.readxml.beans.TeachersBeans;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//https://java-online.ru/java-xml.xhtml
//https://javadevblog.com/kak-chitat-xml-fajl-v-java-ispol-zuem-dom-parser.html

/**
 *
 * @author dreamer
 */
public class ReadXml {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("/home/dreamer/NetBeansProjects/ReadXml/example.xml"));
        NodeList nNodeList = doc.getElementsByTagName("teacher");//берём тег нужного блока в качестве основого
        Node nNode;
        List<TeachersBeans> teachers = new ArrayList<>();
        TeachersBeans teacheBeans;
        if (nNodeList.getLength() > 0) {//если в документе есть теги
            //основной тег дркумента - teacher
            for (int i = 0; i < nNodeList.getLength(); i++) {//двигаемся по найденным тегам
                teacheBeans = new TeachersBeans();
                nNode = nNodeList.item(i);//получаю узел

//                //работает вывод атрибутов ввиде массива, создаётся подобие карты списка аттрибутов
//                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    System.out.println(nNode.getTextContent());
//                    if (nNode.getAttributes().getLength() > 0) {
//                        NamedNodeMap attributes = nNode.getAttributes();
//                        Node nameAttrib = attributes.getNamedItem("uid");
//                        teacheBeans.setUid(i + "." + nameAttrib.getTextContent());
//                    }
//                }
                //если текущий узел документа (тег) является узлом документа (является тегом документа)
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    //работа с аттрибутами основного тега
                    Element eElement = (Element) nNode;
                    //если тег содержит аттрибуты
                    if (nNode.getAttributes().getLength() > 0) {
                        //если аттрибута не будет, в переменную пишется null
                        teacheBeans.setUid(eElement.getAttribute("uid"));
                    }
                    //если есть дочерние теги
                    if (nNode.getChildNodes().getLength() > 0) {
                        NodeList nodeList = eElement.getChildNodes();
                        //проходим по ним
                        for (int j = 0; j < nodeList.getLength(); j++) {
                            Node node = nodeList.item(j);
                            //если текущий узел документа (тег) является узлом документа (является тегом документа)
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) node;
                                //если тега не будет, в переменную пишется null
                                if(element.getTagName().equals("name")){
                                    teacheBeans.setName(element.getTextContent());
                                }
                                if(element.getTagName().equals("discipline")){
                                    teacheBeans.setDiscipline(element.getTextContent());
                                }
                            }
                        }
                    }
                }
               teachers.add(teacheBeans);
            }
            for(int i=0;i<teachers.size();i++){
                System.out.println(i+"."+teachers.get(i).getUid());
                System.out.println(i+"."+teachers.get(i).getName());
                System.out.println(i+"."+teachers.get(i).getDiscipline());
            }
        }
    }
}
