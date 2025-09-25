import edu.cms.model.Student;
import edu.cms.util.AlgorithmUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AlgorithmUtilTest {

    @Test
    public void bubbleSortByName_sortsAscending() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("S003","Charlie"));
        list.add(new Student("S001","Alice"));
        list.add(new Student("S002","Bob"));

        AlgorithmUtil.bubbleSortStudentsByName(list);

        assertEquals("Alice", list.get(0).getName());
        assertEquals("Bob",   list.get(1).getName());
        assertEquals("Charlie", list.get(2).getName());
    }

    @Test
    public void bubbleSortById_andBinarySearch_foundAndNotFound() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("S003","Charlie"));
        list.add(new Student("S001","Alice"));
        list.add(new Student("S002","Bob"));

        AlgorithmUtil.bubbleSortStudentsById(list);

        int idx = AlgorithmUtil.binarySearchStudentById(list, "S002");
        assertTrue(idx >= 0);
        assertEquals("S002", list.get(idx).getId());
        assertEquals("Bob",  list.get(idx).getName());

        assertEquals(-1, AlgorithmUtil.binarySearchStudentById(list, "S999"));
    }
}
