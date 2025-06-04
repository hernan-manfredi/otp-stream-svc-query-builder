import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PlatformDeviceUrlPatternTest {

    private static final String URL_TEMPLATE = "/platform/{platform}/device/{device}";
    private static final String REGEX = buildRegexFromTemplate(URL_TEMPLATE);
    private static final Pattern URL_PATTERN = Pattern.compile(REGEX);

    static {
        System.out.println(REGEX);
    }
    
    @Test
    public void testRegexGeneration() {
        String expectedRegex = "/platform/(?<platform>[^/]+)/device/(?<device>[^/]+)[/\\?]?";
        assertEquals(expectedRegex, REGEX, "Generated regex does not match expected pattern");
    }

    public static String buildRegexFromTemplate(String template) {
        return template
                .replaceAll("\\{(\\w+)\\}", "(?<$1>[^/]+)")
                + "[/\\?]?";
    }

    @Test
    public void testValidPatterns() {
        assertMatch( "/platform/CLEX/device/123456789012345", "CLEX", "123456789012345" );
        assertMatch( "/platform/NGP/device/987654321098765/", "NGP", "987654321098765" );
        assertMatch( "/platform/OCTO/device/111111111111111", "OCTO", "111111111111111" );
        //assertMatch("/platform/FEND/device/555555555555555?", "FEND", "555555555555555");
    }

    @Test
    public void testInvalidPatterns() {
        assertNoMatch( "/platform/CLEX/device" );  // Missing device ID
        assertNoMatch( "/platform//device/123456789012345" );  // Empty platform
        assertNoMatch( "/platform/NGP/device/" );  // Empty device
        assertNoMatch( "/platform/FEND/device/123456789012345/extra" );  // Extra path segments
        assertNoMatch( "/wrongprefix/OCTO/device/111111111111111" );  // Wrong prefix
    }

    private void assertMatch( String url, String expectedPlatform, String expectedDevice ) {
        Matcher matcher = URL_PATTERN.matcher( url );
        assertTrue( , "Pattern should match: " + url );
        
        assertEquals( expectedPlatform, matcher.group( "platform" ), "Platform mismatch for URL: " + url );
        assertEquals( expectedDevice, matcher.group( "device" ), "Device mismatch for URL: " + url );
    }

    private void assertNoMatch( String url ) {
        Matcher matcher = URL_PATTERN.matcher( url );
        assertFalse( matcher.matches(), "Pattern should not match: " + url );
    }
}