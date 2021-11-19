package fr.unice.polytech.citadelles;

class GameEngineTest {
    //Needs to be commented otherwise the unit tests will always be at 100% coverage
    /*GameEngine ge;

    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void baSetUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void setUp() {
        ge = new GameEngine();
        outContent.reset();
    }

    @Test
    void districtCardTest() {
        //prints nothing before launch
        assertTrue(outContent.toString().isEmpty());
        assertTrue(outContent.toString().isBlank());
        //random cards, we test only if it outputs
        ge.launchGame();
        assertFalse(outContent.toString().isEmpty());
        assertFalse(outContent.toString().isBlank());
    }

    @Test
    void getWinnerTest(){


    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }*/
}