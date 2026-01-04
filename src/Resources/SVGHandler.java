package Resources;


import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGPathSupport;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.AWTPolylineProducer;
import org.apache.batik.parser.PathParser;
import org.apache.batik.parser.PointsParser;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPathElement;
import org.w3c.dom.svg.SVGPolylineElement;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SVGHandler {

    private static ArrayList<Complex> longestPolyline = new ArrayList<>();
    private static ArrayList<Complex> polylinePoints = new ArrayList<>();
    private static ArrayList<Double> longestLinelengths = new ArrayList<>();
    private static ArrayList<Double> lengths = new ArrayList<>();

    private static double arcLengthOfLongestElement = -1;
    private static final double FLATNESS = 0.5;

    public static Complex[] SVGToPoints(String filePath, int sampleCount, int scale){

        resetArrays();
        String uri = "file:" + filePath;

        SVGDocument doc;

        SAXSVGDocumentFactory factory =
                new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());

        try {
            doc = factory.createSVGDocument(uri);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        NodeList pathElements = doc.getElementsByTagName("path");
        NodeList polylineElements = doc.getElementsByTagName("polyline");

        findLongestShape(pathElements, polylineElements);

        Complex[] points = findEquidistantPoints(sampleCount);

        double defaultScale = 5;

        for (Complex point : points) {
            point.Re *= (scale / defaultScale);
            point.Im *= -(scale / defaultScale);
        }

        //Remove last potentially broken element
        points = Arrays.copyOf(points, points.length-1);

        return points;
    }

    public static Complex[] findEquidistantPoints (int sampleCount) {

        Complex[] points = new Complex[sampleCount];
        double stepSize = arcLengthOfLongestElement / (sampleCount - 1);


        double currentTotalLineLength = 0;
        int stepNo = 0;

        for (int lineNo = 0; lineNo < longestLinelengths.size(); lineNo++){

            Complex lastPoint = longestPolyline.get(lineNo);
            double currentLength = stepNo * stepSize;
            currentTotalLineLength += longestLinelengths.get(lineNo);

            while (currentTotalLineLength >= currentLength){
                double ratio = (longestLinelengths.get(lineNo) -
                        (currentTotalLineLength - currentLength)) / longestLinelengths.get(lineNo);

                points[stepNo] = new Complex(
                        lastPoint.Re +
                                ((longestPolyline.get(lineNo+1).Re - longestPolyline.get(lineNo).Re) * ratio),
                        lastPoint.Im +
                                ((longestPolyline.get(lineNo+1).Im - longestPolyline.get(lineNo).Im) * ratio)
                );

                currentLength += stepSize;
                stepNo++;
            }
        }


        return points;
    }

    public static void findLongestShape (NodeList paths, NodeList polylines){
        Shape longestShape = null;

        for (int index = 0; index < paths.getLength(); index++){
            SVGPathElement path = (SVGPathElement) paths.item(index);
            Shape pathShape = pathToShape(path);

            double length = computeArcLength(pathShape);
            if (length > arcLengthOfLongestElement) {
                longestShape = pathShape;

                longestLinelengths = new ArrayList<>();
                longestLinelengths.addAll(lengths);

                longestPolyline = new ArrayList<>();
                longestPolyline.addAll(polylinePoints);
            }

            lengths.clear();
            polylinePoints.clear();
            arcLengthOfLongestElement = Math.max(arcLengthOfLongestElement, length);
        }

        for (int index = 0; index < polylines.getLength(); index++){
            SVGPolylineElement polyline = (SVGPolylineElement) polylines.item(index);
            Shape polylineShape = polylineToShape(polyline);

            double length = computeArcLength(polylineShape);
            if (length > arcLengthOfLongestElement) {
                longestShape = polylineShape;

                longestLinelengths = new ArrayList<>();
                longestLinelengths.addAll(lengths);

                longestPolyline = new ArrayList<>();
                longestPolyline.addAll(polylinePoints);
            }

            lengths.clear();
            polylinePoints.clear();
            arcLengthOfLongestElement = Math.max(arcLengthOfLongestElement, length);
        }

    }

    public static double computeArcLength(Shape shape){
        PathIterator it = shape.getPathIterator(null, FLATNESS);

        double[] coords = new double[6];

        double lastX = 0, lastY = 0;
        double startX = 0, startY = 0;
        double length = 0;

        while (!it.isDone()){
            int type = it.currentSegment(coords);

            switch (type){
                case PathIterator.SEG_LINETO:
                    double dx = coords[0] - lastX;
                    double dy = coords[1] - lastY;
                    double tmp = Math.hypot(dx, dy);
                    length += tmp;
                    lengths.add(tmp);
                    lastX = coords[0];
                    lastY = coords[1];
                    polylinePoints.add(new Complex(lastX, lastY));
                    break;
                case PathIterator.SEG_MOVETO:
                    lastX = startX = coords[0];
                    lastY = startY = coords[1];
                    polylinePoints.add(new Complex(startX, startY));
                    break;
                case PathIterator.SEG_CLOSE:
                    double tmp2 = Math.hypot(startX - lastX, startY - lastY);
                    length += tmp2;
                    lengths.add(tmp2);
                    polylinePoints.add(new Complex(startX, startY));
                    lastX = startX;
                    lastY = startY;
                    break;
                default:
                    break;
            }
            it.next();
        }
        return length;
    }

    public static Shape pathToShape(SVGPathElement pathElement) {
        String pathData = pathElement.getAttribute("d");

        AWTPathProducer producer = new AWTPathProducer();

        PathParser parser = new PathParser();
        parser.setPathHandler(producer);
        parser.parse(pathData);

        return producer.getShape();
    }

    public static Shape polylineToShape(SVGPolylineElement polylineElement) {
        String pointsData = polylineElement.getAttribute("points");

        AWTPolylineProducer producer = new AWTPolylineProducer();

        producer.setWindingRule(java.awt.geom.GeneralPath.WIND_NON_ZERO);

        PointsParser parser = new PointsParser();
        parser.setPointsHandler(producer);
        parser.parse(pointsData);

        return producer.getShape();
    }

    public static void resetArrays(){
        longestPolyline = new ArrayList<>();
        longestLinelengths = new ArrayList<>();
        arcLengthOfLongestElement = -1;
    }
}
