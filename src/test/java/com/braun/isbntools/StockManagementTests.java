package com.braun.isbntools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    ExternalISBNDataService testWebService;
    ExternalISBNDataService testDBService;
    StockManager stockManager;

    @Before
    public void setUp() {
        testWebService = mock(ExternalISBNDataService.class);
        testDBService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setDbService(testDBService);
        stockManager.setWebService(testWebService);
    }

    @Test
    public void canGetACorrectLocatorCodeTest() {

        when(testWebService.lookUp(anyString())).thenReturn(new Book("0140177396", "Of Mice and Men", "J. Steinberg"));
        when(testDBService.lookUp(anyString())).thenReturn(null);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void dbIsUsedIfDataIsPresentInDB() {

        when(testDBService.lookUp("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Men", "J. Steinberg"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(testDBService, times(1)).lookUp("0140177396");
        verify(testWebService, times(0)).lookUp(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase() {
        when(testDBService.lookUp("0140177396")).thenReturn(null);
        when(testWebService.lookUp("0140177396")).thenReturn(new Book("0140177396", "Of Mice and Men", "J. Steinberg"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(testDBService, times(1)).lookUp("0140177396");
        verify(testWebService, times(1)).lookUp("0140177396");
    }
}
