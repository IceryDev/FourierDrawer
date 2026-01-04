# ℼ Fourier Drawer

### About
A Java application that uses Discrete Fourier Transform (DFT) and epicycles to draw the contents of SVG files.

<p align="center">
  <img width="578" height="398" alt="image" src="https://github.com/user-attachments/assets/a43530d6-4704-4831-9586-070602be0542" />
</p>

### How to Use
- Run the Main.java file
- Select the SVG file (See the Examples folder), the sample count, the drawing time, and the scale
- Click on the button "Start Drawing!"

### Important Notes
-  DFT drawing works best in shapes that form closed curves and can be drawn "without lifting the pen"
-  A higher sample count usually results in a more accurate drawing
-  A very high sample count and a very low drawing time may distort the drawing due to aliasing
-  Only the longest path of the SVG file is converted into points, this means if your file has more than one path element that defines the shape, it may draw only a part of it.
-  If you are converting from PNG or JPEG, sometimes it generates unwanted paths, if one of these paths is longer than the intended drawing, the app will draw the unwanted path instead. You can remove this path from the file using a text editor to correct this.
-  To see the paths in your SVG file, you can use this tool [PathToPoints](https://github.com/Shinao/PathToPoints) by [Shinao](https://github.com/Shinao), which helped me a lot during testing, thanks Shinao!

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![Demo](https://github.com/user-attachments/assets/faaba05e-c3ad-409b-a569-e50a2361eafc)

### App Functionality
-  SVG file to a set of points using [Apache Batik](https://xmlgraphics.apache.org/batik/) library
-  DFT to generate amplitude and phase values for each sampled frequency bin
-  The image is then drawn to a Java Swing GUI frame with these generated values
