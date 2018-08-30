package test;

import app.controllers.JsonController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Test {

    @org.junit.Test
    public void logTest() {
        Logger logger = LogManager.getLogger(JsonController.class);
        logger.info("kek");
        logger.info("lol");
        logger.info("arbidol");
    }

    @org.junit.Test
    private void putOperationInDB(){
    }
}
