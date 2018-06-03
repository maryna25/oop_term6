package view;

import log.AppLogger;
import model.Assignment;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AssignmentTag extends TagSupport {

    private Assignment assignment;

    public void setItem(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(String.format("<form action=\"UpdateAssignmentServlet\" method=\"post\">"));
            out.print(String.format("<td><input type=\"text\" name=\"title\" value=\"%s\"><br></td>", assignment.getCourse().getTitle()));
            out.print(String.format("<td><input type=\"text\" name=\"surname\" value=\"%s\"><br></td>", assignment.getStudent().getSurname()));
            out.print(String.format("<td><input type=\"text\" name=\"mark\" value=\"%s\"><br></td>", assignment.getMark()));
            out.print(String.format("<td><input type=\"text\" name=\"response\" value=\"%s\"><br></td>", assignment.getResponse()));
            out.print(String.format("<td><c:out value=\"%s\"/></td>", assignment.getStatus()));
            out.print(String.format("<td><input type=\"submit\" value=\"Next\"></td></form>"));
        } catch(IOException ioe) {
            AppLogger.getLogger().error(ioe);
        }
        return SKIP_BODY;
    }
}

