package app.math;

import com.google.inject.AbstractModule;

public class LongArithmeticModule extends AbstractModule {
    //TODO убрать juice

    @Override
    protected void configure() {
        bind(LongArithmethic.class).to(LongArithmeticImplList.class);
    }
}
