package uk.ac.newcastle.dre_ip.parsers;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * XML Parser
 *
 * @author Carlton Shepherd
 */
public class XmlParser {
    private Document dom;

    /**
     * Constructor
     *
     * @param rawXml the raw xml
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     * @throws IOException                  the io exception
     */
    public XmlParser(String rawXml) throws ParserConfigurationException, SAXException, IOException {
        Log.e("raw", rawXml);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(rawXml));
        dom = builder.parse(is);
    }

    /**
     * Detects errors returned by the server
     *
     * @return 1 : Invalid Election, 2: Invalid Password
     */
    public int findError() {
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        String error = "";
        String type = "";

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                type = getTagValue("type", element);

                if (type.equals("error")) {
                    error = getTagValue("errortype", element);
                    if (error.equals("invalidelection")) {
                        return 1;
                    } else if (error.equals("invalidpassword")) {
                        return 2;
                    } else if (error.equals("invalidpasscode")) {
                        return 3;
                    } else if (error.equals("usedpasscode")) {
                        return 4;
                    } else if (error.equals("electionfinished")) {
                        return 5;
                    }
                    return 11;
                }
            }
        }
        return 0;
    }


    /**
     * Returns the list of responses for the results
     *
     * @return Response List
     */
    public ArrayList<String> getCandidateList() {


        NodeList nList = dom.getElementsByTagName("dreipvoting");
        ArrayList<String> responseList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("text", element);
//				String[] lists = answer.split(",");
//				responseList = new String[lists.length];
//				for (int j = 0;j<lists.length;j++){
//					responseList[i]=lists[i];
//				}
                responseList.add(answer);
            }
        }

        return responseList;
    }

    /**
     * Gets question.
     *
     * @return the question
     */
    public String getQuestion() {

        NodeList nodeList = dom.getElementsByTagName("dreipvoting");
        String question = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("question", element);
                question = answer;
            }
        }
        return question;
    }

    /**
     * Gets number of question.
     *
     * @return the number of question
     */
    public String getNumberOfQuestion() {

        NodeList nodeList = dom.getElementsByTagName("dreipvoting");
        String question = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("numberofquestions", element);

                question = answer;
            }
        }
        return question;
    }

    /**
     * Gets questions finished.
     *
     * @return the questions finished
     */
    public String getQuestionsFinished() {

        NodeList nodeList = dom.getElementsByTagName("dreipvoting");
        String question = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("questionsfinished", element);

                question = answer;
            }
        }
        return question;
    }

    /**
     * Gets question id.
     *
     * @return the question id
     */
    public String getQuestionID() {

        NodeList nList = dom.getElementsByTagName("dreipvoting");
        String title = "";
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("qid", element);
                title = answer;
            }
        }

        return title;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {

        NodeList nList = dom.getElementsByTagName("dreipvoting");
        String title = "";
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("title", element);
                title = answer;
            }
        }

        return title;
    }

    /**
     * Gets stage one receipt.
     *
     * @return the stage one receipt
     */
    public String getStageOneReceipt() {
        String receipt = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("stageone", element);

                receipt = answer;
            }
        }
        return receipt;
    }

    /**
     * Gets stage two receipt.
     *
     * @return the stage two receipt
     */
    public String getStageTwoReceipt() {
        String receipt = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("stagetwo", element);

                receipt = answer;
            }
        }
        return receipt;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        String receipt = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("type", element);

                receipt = answer;
            }
        }
        return receipt;
    }

    /**
     * Gets ballot id.
     *
     * @return the ballot id
     */
    public String getBallotID() {
        String ballotid = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("ballotid", element);
                ballotid = answer;
            }
        }
        return ballotid;
    }

    /**
     * Gets candidate.
     *
     * @return the candidate
     */
    public String getCandidate() {
        String candidate = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("candidate", element);

                candidate = answer;
            }
        }
        return candidate;
    }

    /**
     * Gets passcode.
     *
     * @return the passcode
     */
    public String getPasscode() {
        String passcode = "";
        NodeList nList = dom.getElementsByTagName("dreipvoting");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String answer = getTagValue("passcode", element);
                passcode = answer;
            }
        }
        return passcode;
    }


    /**
     * Finds the value of a given tag
     *
     * @param sTag Given tag
     * @param eElement The element that retrieve result from
     * @return The value of a tag
     */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
}
