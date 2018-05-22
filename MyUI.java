package net.ijoha;

import javax.management.openmbean.TabularType;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality. mvn exec:java
 * -D"exec.mainClass"="ie.ulster.MyUI"
 */
@Theme("mytheme")
public class MyUI extends UI {
    ArrayList<StudentGrid> students = new ArrayList<StudentGrid>();


    @Override
    protected void init(VaadinRequest vaadinRequest)
    {

        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Enquire SQL Data");

        Grid <StudentGrid> grid=new Grid<>("Students From Azure");        
    
        grid.setItems(students);
        grid.setWidth("600px");
        grid.addColumn(StudentGrid::getFName).setCaption("First Name").setWidth(100);
        grid.addColumn(StudentGrid::getLName).setCaption("Last Name").setWidth(100);
        grid.addColumn(StudentGrid::getEmail).setCaption("Email").setWidth(300);
        grid.addColumn(StudentGrid::getGender).setCaption("Gender").setWidth(100);

        button.addClickListener(e ->
        {
            layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
            // connect to database (need azure conn stirngs)
            String connectionString = "jdbc:sqlserver://ijoha.database.windows.net:1433;" + "database=StudentGradeDB;"
                    + "user=iJoha;" + "password=ide2872W;" + "encrypt=true;" + "trustServerCertificate=false;"
                    + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection connection = null;

            try 
            {
                // run a query on database table
                // loop through the restuls
                connection = DriverManager.getConnection(connectionString);
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Student");
            
                while (rs.next())
                {
                    String fName = rs.getString("FIRST_NAME");
                    String lName = rs.getString("LAST_NAME");
                    String email = rs.getString("EMAIL");
                    String gender = rs.getString("GENDER");

                    students.add(new StudentGrid(fName,lName,email,gender));

                    /**
                    layout.addComponent(new Label(rs.getString("FIRST_NAME") + rs.getString("LAST_NAME")
                            + "\t" + rs.getString("EMAIL") +rs.getString("GENDER") + "\n"));
                    */
                    grid.getDataProvider().refreshAll();

                }

            }
            catch (Exception error)
            {
                layout.addComponent(new Label(error.getMessage()));
            }
        });

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/Orang01.jpg"));
        
        // Show the image in the application
        Image image = new Image("Image from file", resource);
        image.setWidth(400, Unit.PIXELS);
        
        // Let the user view the file in browser or download
        Link link = new Link("Link to the image file", resource);
        Label linkText = new Label("{\"url\":\"" + link.getData() + "\"}");

        Button button1 = new Button("Is this a picture of a person");
        button1.addClickListener(e ->
        {
            layout.addComponent(new Label(Faceing.detectFaces(basepath+"https://https://mynosql.blob.core.windows.net/pictures/pictures/Orang01.jpg")));
        });
        layout.addComponents(image,link,linkText,button1,name, button, grid);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet 
    {
    }
}
