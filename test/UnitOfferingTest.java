import edu.cms.model.Instructor;
import edu.cms.model.Student;
import edu.cms.model.Unit;
import edu.cms.model.UnitOffering;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfferingTest {

    private Unit unit;
    private Instructor ins;
    private UnitOffering off;
    private Student s1, s2;

    @Before
    public void setup() {
        unit = new Unit("BN305","VPN Security",6,"");
        ins  = new Instructor("I1","Prof Smith");
        off  = new UnitOffering("BN305-S1-2025", unit, "S1 2025", ins);
        s1   = new Student("S1","Alice");
        s2   = new Student("S2","Bob");
    }

    @Test
    public void enroll_once_returnsTrue_andAdds() {
        assertTrue(off.enrollStudent(s1));
        assertEquals(1, off.getEnrolled().size());
        assertEquals("S1", off.getEnrolled().get(0).getId());
    }

    @Test
    public void enroll_duplicate_returnsFalse_andKeepsSize() {
        off.enrollStudent(s1);
        assertFalse(off.enrollStudent(s1));
        assertEquals(1, off.getEnrolled().size());
    }

    @Test
    public void unenroll_removesById() {
        off.enrollStudent(s1);
        off.enrollStudent(s2);
        assertTrue(off.unenrollStudent("S1"));
        assertEquals(1, off.getEnrolled().size());
        assertEquals("S2", off.getEnrolled().get(0).getId());
    }
}
