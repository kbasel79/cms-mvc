import edu.cms.model.Unit;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void constructor_setsFields_andPrereqsEmpty() {
        Unit u = new Unit("COS100","Intro",6,"Basics");
        assertEquals("COS100", u.getCode());
        assertEquals("Intro",  u.getName());
        assertEquals(6,        u.getCredits());
        assertTrue(u.getPrerequisites().isEmpty());
    }

    @Test
    public void addPrerequisite_addsUniqueCodes_only() {
        Unit u = new Unit("COS100","Intro",6,"");
        u.addPrerequisite("COS010");
        u.addPrerequisite("COS020");
        u.addPrerequisite("COS010"); // duplicate ignored
        assertEquals(2, u.getPrerequisites().size());
        assertTrue(u.getPrerequisites().contains("COS010"));
        assertTrue(u.getPrerequisites().contains("COS020"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCredits_throwsIllegalArgumentException() {
        new Unit("X","Y",-1,"");
    }
}
