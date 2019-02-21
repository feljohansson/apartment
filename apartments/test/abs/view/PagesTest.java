package abs.view;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;


/*
     *** Nothing to do here, EXCEPT if doing the last optional point (6.) ***

 */
public class PagesTest {

       /*

        Testing view parts are cumbersome, hard to automate
        So this is the way we do (not in real life)

        Run test
        Copy and paste output into empty file test.html
        Open test.html in a browser

     */
    @Test
    public void test(){
        Pages ps = new Pages();
        String s = ps.getHomePage();

        out.println(s);
        assertTrue(true);
    }
}