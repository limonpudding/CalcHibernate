package app.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class Log {
    public static final String USER_CONNECTED_LOG = "Подключился пользователь с IP: {}";
    public static final String CALC_FIB_LOG = "Запущена операция фибоначчи с параметром: {}";
    public static final String PUT_CONST_LOG = "Пользователь с IP: {} добавил константу key:\'{}\', value:\'{}\'";
    public static final String UPDATE_VALUE_LOG = "Пользователь с IP: {} обновил значение константы \'{}\' на value:\'{}\'";
    public static final String DELETE_CONST_LOG = "Пользователь с IP: {} удалил константу \'{}\'";
    public static final String GET_CONSTANTS_LOG = "Пользователь с IP: {} запросил список констант";
    public static final String CALC_LOG = "Пользователь с IP: {} начал выполнение операции \'{}\'";
    public static final String UPDATE_CONST_LOG = "Пользователь с IP: {} обновил константу \'{}\' на key:\'{}\', value:\'{}\'";
    public static final String INCORRECT_VALUE = "Попытка присвоить значение константы, не представляющее собой число";
    public static final String INCORRECT_ADD_KEY = "Попытка добавить константу, состоящую только из числа. Её использование будет не возможно, до изменения";
    public static final String INCORRECT_UPDATE_KEY = "Попытка переименовать константу в вид, содержащий только число. Её использование будет не возможно, до изменения";

    public static void print(Logger logger, Level level, String msg, Object ... objects) {
        if (logger.isEnabled(level)) {
            logger.log(level, msg, objects);
        }
    }

    public static void print(Logger logger, Level level, String msg) {
        if (logger.isEnabled(level)) {
            logger.log(level, msg);
        }
    }
}
