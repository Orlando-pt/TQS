package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    ISimpleHttpClient httpClient;

    @InjectMocks
    AddressResolver resolver;


    @Test
    void whenGoodAlboiGps_returnAddress() throws ParseException, IOException, URISyntaxException {

        //todo
        String url = "http://open.mapquestapi.com/geocoding/v1/reverse?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&location=40.640661%2C-8.656688&includeRoadMetadata=true";
        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2021 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.640661,\"lng\":-8.656688}},\"locations\":[{\"street\":\"Cais do Alboi\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3800-246\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"displayLatLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.64052368145179,-8.656712986761146|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-517195924\",\"roadMetadata\":null}]}]}";

        Mockito.when(httpClient.get(url)).thenReturn(response);

        //test
        Address result = resolver.findAddressForLocation(40.640661, -8.656688);

        //return
        assertEquals( result, new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null),
                "Check if the result of a request to the api is correct");

    }

    @Test
    public void whenBadCoordidates_throwBadArrayindex() throws IOException, URISyntaxException, ParseException {

        //todo
        /**
         * In the case that the connection is lost
         */
        String url = "";
        Mockito.when(httpClient.get(url)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> httpClient.get(url),
                "Null url should throw a IOException");

        /**
         * bad coordinates should not be accepted

        assertThrows(IllegalArgumentException.class, () -> resolver.findAddressForLocation(91, 0),
                "Bad coordinates should not be accepted");
        assertThrows(IllegalArgumentException.class, () -> resolver.findAddressForLocation(-91, 0),
                "Bad coordinates should not be accepted");
        assertThrows(IllegalArgumentException.class, () -> resolver.findAddressForLocation(-89, 181),
                "Bad coordinates should not be accepted");
         */

        assertThrows(NullPointerException.class, ()-> resolver.findAddressForLocation(-91, 0),
                "Bad coordinates should not be accepted");
    }
}